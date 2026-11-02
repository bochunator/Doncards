package pl.bochunator.doncards.appuser.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.bochunator.doncards.PageResponse;
import pl.bochunator.doncards.appuser.exception.AppUserNotFoundException;
import pl.bochunator.doncards.appuser.model.AppUser;
import pl.bochunator.doncards.appuser.model.AppUserDetailsResponse;
import pl.bochunator.doncards.appuser.model.AppUserSearchResponse;
import pl.bochunator.doncards.appuser.repository.AppUserRepository;
import pl.bochunator.doncards.card.repository.CardRepository;
import pl.bochunator.doncards.deck.repository.DeckRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;

    public AppUserDetailsResponse findById(Long id) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new AppUserNotFoundException(id));
        long deckCount = deckRepository.countByAppUserId(id);
        long cardCount = cardRepository.countByDeck_AppUser_Id(id);
        return AppUserDetailsResponse.from(appUser, deckCount, cardCount);
    }

    public PageResponse<AppUserSearchResponse> findAllByQuery(int page, int size, String query) {
        Pageable pageable = PageRequest.of(page, size);

        Page<AppUser> appUserPage = appUserRepository.findAllByUsernameContainingIgnoreCase(query, pageable);

        List<AppUserSearchResponse> content = appUserPage.getContent().stream()
                .map(AppUserSearchResponse::from)
                .toList();

        return PageResponse.from(appUserPage, content);
    }
}
