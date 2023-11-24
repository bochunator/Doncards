package pl.bochunator.doncards.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bochunator.doncards.model.English;
import pl.bochunator.doncards.repository.EnglishRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnglishService {
    private final EnglishRepository englishRepository;
    public List<English> getEnglishes() {
        return englishRepository.findAll();
    }
}
