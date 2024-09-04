package pl.bochunator.doncards.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.bochunator.doncards.dto.applicationuser.ApplicationUserDTOMapper;
import pl.bochunator.doncards.dto.applicationuser.ApplicationUserWithDecksDTO;
import pl.bochunator.doncards.dto.deck.DeckCreateDTO;
import pl.bochunator.doncards.dto.deck.DeckDTOMapper;
import pl.bochunator.doncards.dto.deck.DeckDetailsDTO;
import pl.bochunator.doncards.dto.deck.DeckSummaryDTO;
import pl.bochunator.doncards.exception.DeckAlreadyExistsException;
import pl.bochunator.doncards.exception.DeckNotFoundException;
import pl.bochunator.doncards.model.ApplicationUser;
import pl.bochunator.doncards.model.Card;
import pl.bochunator.doncards.model.Deck;
import pl.bochunator.doncards.repository.DeckRepository;
import pl.bochunator.doncards.utils.PaginationUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeckService {

    private final AuthenticationService authenticationService;
    private final DeckRepository deckRepository;
    private final CardService cardService;
    private final ApplicationUserService applicationUserService;

    public Deck createDeck(String jwt, DeckCreateDTO deckCreateDTO) {
        ApplicationUser author = authenticationService.verifyUserByToken(jwt);
        if (deckRepository.existsByAuthorAndName(author, deckCreateDTO.getName())) {
            throw new DeckAlreadyExistsException(author.getUsername(), deckCreateDTO.getName());
        }
        List<Card> savedCards = cardService.saveAll(deckCreateDTO.getCardDTOs());
        // TODO: One request to save all cards in junction
        Deck deck = Deck.builder()
                .cards(savedCards)
                .author(author)
                .name(deckCreateDTO.getName())
                .description(deckCreateDTO.getDescription())
                .version(1)
                .createdAt(LocalDateTime.now())
                .build();
        return deckRepository.save(deck);
    }

    public Page<Deck> getDecks(Integer page) {
        int pageNumber = PaginationUtils.getValidPageNumber(page);
        return deckRepository.findAllDecks(PageRequest.of(pageNumber, 1));
    }

    public Page<DeckDetailsDTO> getDetailsDecksDTOs(Integer page) {
        return DeckDTOMapper.mapToDeckDetailsDTOPage(getDecks(page));
    }

    public Page<Deck> getDecksByAuthor(ApplicationUser author, Integer page) {
        int pageNumber = PaginationUtils.getValidPageNumber(page);
        return deckRepository.findByAuthor(author, PageRequest.of(pageNumber, 1));
    }

    public Page<DeckSummaryDTO> getNextDecksByUserId(Long userId, Integer page) {
        ApplicationUser applicationUserProfile = applicationUserService.getApplicationUserById(userId);
        Page<Deck> decks = getDecksByAuthor(applicationUserProfile, page);
        return DeckDTOMapper.mapToDeckSummaryDTOPage(decks);
    }

    public ApplicationUserWithDecksDTO getApplicationUserWithDecksByUserId(Long userId) {
        ApplicationUser applicationUser = applicationUserService.getApplicationUserById(userId);
        Page<Deck> decks = getDecksByAuthor(applicationUser, 0);
        Page<DeckSummaryDTO> deckSummaryDTOs = DeckDTOMapper.mapToDeckSummaryDTOPage(decks);
        return ApplicationUserDTOMapper.mapToApplicationUserWithDecksDTO(applicationUser, deckSummaryDTOs);
    }

    public Deck getDeckByDeckId(Long deckId) {
        return deckRepository.findById(deckId)
                .orElseThrow(() -> new DeckNotFoundException(deckId));
    }
}
