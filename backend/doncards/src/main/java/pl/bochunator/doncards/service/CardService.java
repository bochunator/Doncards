package pl.bochunator.doncards.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bochunator.doncards.dto.common.card.CardDTO;
import pl.bochunator.doncards.model.Card;
import pl.bochunator.doncards.repository.CardRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public List<Card> saveAll(List<CardDTO> cardDTOs) {
        List<String> terms = cardDTOs.stream()
                .map(CardDTO::getTerm)
                .toList();
        List<String> translations = cardDTOs.stream()
                .map(CardDTO::getTranslation)
                .toList();
        Set<Card> existingCards = cardRepository.findByTermInAndTranslationIn(terms, translations);
        Set<Card> newCards = cardDTOs
                .stream()
                .map(dto -> Card
                        .builder()
                        .term(dto.getTerm())
                        .translation(dto.getTranslation())
                        .build())
                .filter(c -> !(existingCards.contains(c)))
                .collect(Collectors.toSet());
        existingCards.addAll(cardRepository.saveAll(new HashSet<>(newCards)));
        Map<CardDTO, Card> cardDTOsToCards = new HashMap<>();
        cardDTOs.forEach(dto -> {
            Card card = existingCards.stream()
                    .filter(c -> c.getTerm().equals(dto.getTerm()) && c.getTranslation().equals(dto.getTranslation()))
                    .findFirst()
                    .orElse(null);
            cardDTOsToCards.put(dto, card);
        });
        return cardDTOs.stream()
                .map(cardDTOsToCards::get)
                .toList();
    }

}
