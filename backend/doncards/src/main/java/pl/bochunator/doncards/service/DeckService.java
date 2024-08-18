package pl.bochunator.doncards.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.bochunator.doncards.dto.common.deck.DeckDTO;
import pl.bochunator.doncards.dto.request.deck.CreateDeckRequestDTO;
import pl.bochunator.doncards.exception.DeckAlreadyExistsException;
import pl.bochunator.doncards.model.ApplicationUser;
import pl.bochunator.doncards.model.Card;
import pl.bochunator.doncards.model.Deck;
import pl.bochunator.doncards.repository.DeckRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeckService {

    private final AuthenticationService authenticationService;

    private final CardService cardService;

    private final DeckRepository deckRepository;

    public Deck createDeck(String jwt, CreateDeckRequestDTO deckDTO) {
        ApplicationUser author = authenticationService.verifyUserByToken(jwt);
        if (deckRepository.existsByAuthorAndName(author, deckDTO.getName())) {
            throw new DeckAlreadyExistsException(author.getUsername(), deckDTO.getName());
        }
        List<Card> savedCards = cardService.saveAll(deckDTO.getCardDTOs());
        // TODO: One request to save all cards in junction
        Deck deck = Deck.builder()
                .cards(savedCards)
                .author(author)
                .name(deckDTO.getName())
                .description(deckDTO.getDescription())
                .version(1)
                .createdAt(LocalDateTime.now())
                .build();
        return deckRepository.save(deck);
    }

    public List<DeckDTO> getDecks(Integer page) {
        int pageNumber = (page != null && page >= 0) ? page : 0;
        return deckRepository.findAllDecks(PageRequest.of(pageNumber, 1)).stream()
                .map(deck -> DeckDTO.builder()
                        .authorName(deck.getAuthor().getUsername())
                        .name(deck.getName())
                        .description(deck.getDescription())
                        .cards(deck.getCards())
                        .build())
                .toList();
    }

}
