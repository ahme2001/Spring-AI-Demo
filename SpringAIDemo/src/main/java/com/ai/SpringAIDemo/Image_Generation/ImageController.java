package com.ai.SpringAIDemo.Image_Generation;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/generate-image")
    public Object generateImage(@RequestParam String prompt) throws IOException {
        return imageService.getImage(prompt);
    }

    @GetMapping("/generate-image/options")
    public List<String> generateImage(@RequestParam String prompt,
                                      @RequestParam(required = false, defaultValue = "1") Integer n){
        return imageService.getImageByOptions(prompt,n);
    }
}
