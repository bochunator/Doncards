package pl.bochunator.doncards.deck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pl.bochunator.doncards.PageResponse;
import pl.bochunator.doncards.appuser.exception.AppUserNotFoundException;
import pl.bochunator.doncards.appuser.model.AppUser;
import pl.bochunator.doncards.appuser.model.AppUserSummaryResponse;
import pl.bochunator.doncards.appuser.repository.AppUserRepository;
import pl.bochunator.doncards.card.model.CardResponse;
import pl.bochunator.doncards.card.repository.CardRepository;
import pl.bochunator.doncards.deck.exception.DeckAccessDeniedException;
import pl.bochunator.doncards.deck.exception.DeckNotFoundException;
import pl.bochunator.doncards.deck.model.*;
import pl.bochunator.doncards.deck.repository.DeckRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeckService {

    private final DeckRepository deckRepository;
    private final AppUserRepository appUserRepository;
    private final CardRepository cardRepository;


    public CreateDeckResponse create(CreateDeckRequest dto, Long appUserId) {
        AppUser appUser = appUserRepository.findById(appUserId)
                .orElseThrow(() -> new AppUserNotFoundException(appUserId));
        Deck deck = new Deck();
        deck.setAppUser(appUser);
        deck.setName(dto.name());
        deck.setDescription(dto.description());
        deck.setCreatedAt(LocalDateTime.now());
        deck.setViewCount((long) (Math.random() * Integer.MAX_VALUE));
        return CreateDeckResponse.from(deckRepository.save(deck));
    }

    public PageResponse<DeckDetailsResponse> findAll(int page, int size, DeckSort deckSort) {
        Sort sort;
        if (deckSort == DeckSort.LATEST) {
            sort = Sort.by("createdAt").descending();
        } else {
            sort = Sort.by("viewCount").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Deck> deckPage = deckRepository.findAll(pageable);

        List<DeckDetailsResponse> content = deckPage.getContent().stream()
                .map(deck -> {
                    List<CardResponse> previewCards =
                            cardRepository.findTop5ByDeckId(deck.getId())
                                    .stream()
                                    .map(CardResponse::from)
                                    .toList();
                    AppUserSummaryResponse appUserSummaryResponse = AppUserSummaryResponse.from(deck.getAppUser());
                    return DeckDetailsResponse.from(deck, appUserSummaryResponse, previewCards);
                })
                .toList();

        return PageResponse.from(deckPage, content);
    }

    public PageResponse<DeckDetailsResponse> findAllByAppUserId(Long appUserId, int page, int size, DeckSort deckSort) {
        Sort sort;
        if (deckSort == DeckSort.LATEST) {
            sort = Sort.by("createdAt").descending();
        } else {
            sort = Sort.by("viewCount").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);

        AppUser appUser = appUserRepository.findById(appUserId)
                .orElseThrow(() -> new AppUserNotFoundException(appUserId));

        Page<Deck> deckPage = deckRepository.findAllByAppUserId(appUser.getId(), pageable);

        List<DeckDetailsResponse> content = deckPage.getContent().stream()
                .map(deck -> {
                    List<CardResponse> cardResponses =
                            cardRepository.findTop5ByDeckId(deck.getId())
                                    .stream()
                                    .map(CardResponse::from)
                                    .toList();
                    AppUserSummaryResponse appUserSummaryResponse = AppUserSummaryResponse.from(deck.getAppUser());
                    return DeckDetailsResponse.from(deck, appUserSummaryResponse, cardResponses);
                })
                .toList();

        return PageResponse.from(deckPage, content);
    }

    public DeckDetailsResponse findById(Long id) {
        Deck deck = deckRepository.findById(id)
                .orElseThrow(() -> new DeckNotFoundException(id));

        AppUserSummaryResponse appUser = AppUserSummaryResponse.from(deck.getAppUser());

        List<CardResponse> cards = deck.getCards().stream()
                .map(CardResponse::from)
                .toList();

        return DeckDetailsResponse.from(deck, appUser, cards);
    }

    public DeckSummaryResponse update(Long id, UpdateDeckRequest dto, Long appUserId) {
        Deck deck = deckRepository.findById(id)
                .orElseThrow(() -> new DeckNotFoundException(id));
        if (!deck.getAppUser().getId().equals(appUserId)) {
            throw new AccessDeniedException("You cannot edit this deck");
        }
        if (dto.name() != null) {
            deck.setName(dto.name());
        }
        deck.setDescription(dto.description());
        return DeckSummaryResponse.from(deckRepository.save(deck));
    }

    public void delete(Long id, Long appUserId) {
        Deck deck = deckRepository.findById(id)
                .orElseThrow(() -> new DeckNotFoundException(id));
        if (!deck.getAppUser().getId().equals(appUserId)) {
            throw new DeckAccessDeniedException();
        }
        deckRepository.delete(deck);
    }
}
