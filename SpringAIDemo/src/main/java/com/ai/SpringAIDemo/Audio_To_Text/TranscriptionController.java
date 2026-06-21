package com.ai.SpringAIDemo.Audio_To_Text;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class TranscriptionController {

    private final TranscriptionService transcriptionService;

    public TranscriptionController(TranscriptionService transcriptionService) {
        this.transcriptionService = transcriptionService;
    }

    @PostMapping("/convert/audio-to-text")
    public String ConvertAudioToText(@RequestParam(name = "audio") MultipartFile audioFile) throws IOException {
        return transcriptionService.ConvertAudioToText(audioFile);
    }

}
