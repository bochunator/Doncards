package pl.bochunator.doncards.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.bochunator.doncards.model.English;
import pl.bochunator.doncards.service.EnglishService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EnglishController {
    private final EnglishService englishService;
    @GetMapping("/english")
    public List<English> getEnglishes() {
        return  englishService.getEnglishes();
    }
    @GetMapping("/english/{id}")
    public List<English> getEnglish(@PathVariable long id) {
        throw new IllegalArgumentException("Not Implemented yet!");
    }
}
