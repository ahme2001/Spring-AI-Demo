package com.ai.SpringAIDemo.Image_Generation;

import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private final ImageModel openaiImageModel;

    public ImageService(ImageModel imageModel) {
        this.openaiImageModel = imageModel;
    }
    // Image url is always return with null value , so deal with base64Json

    public String getImage(String prompt) {
        ImageResponse response;
        try {
            response = openaiImageModel.call(new ImagePrompt(prompt));
        } catch (Exception e) {
            return e.getMessage();
        }
        String fileName = UUID.randomUUID() + ".png";
        try {
             return saveImage(Objects.requireNonNull(response.getResult()).getOutput().getB64Json(), fileName);
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public List<String> getImageByOptions(String prompt, Integer nofImages) {
        nofImages = (nofImages < 1 || nofImages > 10) ? 1 : nofImages;

        ImageResponse response;
        try {
            response = openaiImageModel.call(
                    new ImagePrompt(prompt,
                            OpenAiImageOptions.builder()
                                    .n(nofImages)
                                    .build()));
        } catch (Exception e) {
            return List.of(e.getMessage());
        }
        List<String> file_paths = new ArrayList<>();
        Objects.requireNonNull(response.getResults())
                .forEach(tmp -> {
                    String fileName = UUID.randomUUID() + ".png";
                    try {
                        file_paths.add(saveImage(tmp.getOutput().getB64Json(), fileName));
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                });
        return file_paths;
    }

    private String saveImage(String b64Json, String fileName) throws IOException {

        byte[] imageBytes = Base64.getDecoder().decode(b64Json);

        Path outputPath = Path.of("images", fileName);

        Files.createDirectories(outputPath.getParent());

        Files.write(outputPath, imageBytes);

        return outputPath.toString();
    }

}
