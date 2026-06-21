package com.ai.SpringAIDemo.Audio_To_Text;

import com.openai.models.audio.AudioResponseFormat;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class TranscriptionService {

    private final OpenAiAudioTranscriptionModel openAiTranscriptionModel;


    private final String apiKey;

    public TranscriptionService(OpenAiAudioTranscriptionModel openAiTranscriptionModel,@Value("${spring.ai.openai.api-key}") String apiKey) {
        this.openAiTranscriptionModel = openAiTranscriptionModel;
        this.apiKey = apiKey;
    }

    public String ConvertAudioToText(MultipartFile audioFile) throws IOException {
        Resource audioResource;

        File file = File.createTempFile("audio", ".wav");
        audioFile.transferTo(file);
        audioResource = new FileSystemResource(file);

        AudioTranscriptionPrompt prompt = new AudioTranscriptionPrompt(
                audioResource,
                OpenAiAudioTranscriptionOptions.builder()
                        .language("en")
                        .responseFormat(AudioResponseFormat.TEXT)
                        .temperature(0f)
                        .apiKey(apiKey)
                        .build()
        );

        AudioTranscriptionResponse response = openAiTranscriptionModel.call(prompt);

        file.delete();
        return response.getResult().getOutput();
    }
}
