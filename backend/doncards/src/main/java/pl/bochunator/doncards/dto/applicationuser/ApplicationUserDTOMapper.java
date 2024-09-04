package pl.bochunator.doncards.dto.applicationuser;

import org.springframework.data.domain.Page;
import pl.bochunator.doncards.dto.deck.DeckSummaryDTO;
import pl.bochunator.doncards.model.ApplicationUser;

import java.util.Set;

public class ApplicationUserDTOMapper {

    private ApplicationUserDTOMapper() {
    }

    public static ApplicationUserWithDecksDTO mapToApplicationUserWithDecksDTO(ApplicationUser applicationUser, Page<DeckSummaryDTO> deckSummaryDTOs) {
        return ApplicationUserWithDecksDTO.builder()
                .userId(applicationUser.getUserId())
                .authorities(applicationUser.getAuthorities())
                .deckSummaryDTOs(deckSummaryDTOs)
                .email(applicationUser.getEmail())
                .username(applicationUser.getUsername())
                .accountNonExpired(applicationUser.isAccountNonExpired())
                .accountNonLocked(applicationUser.isAccountNonLocked())
                .credentialsNonExpired(applicationUser.isCredentialsNonExpired())
                .enabled(applicationUser.isEnabled())
                .build();
    }
}
