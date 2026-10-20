package pl.bochunator.doncards.appuser.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bochunator.doncards.appuser.exception.AppUserNotFoundException;
import pl.bochunator.doncards.appuser.model.AppUser;
import pl.bochunator.doncards.appuser.model.AppUserDetailsResponse;
import pl.bochunator.doncards.appuser.repository.AppUserRepository;
import pl.bochunator.doncards.card.repository.CardRepository;
import pl.bochunator.doncards.deck.repository.DeckRepository;

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
}
