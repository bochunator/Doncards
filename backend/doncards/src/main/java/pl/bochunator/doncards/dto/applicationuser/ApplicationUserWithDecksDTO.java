package pl.bochunator.doncards.dto.applicationuser;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import pl.bochunator.doncards.dto.deck.DeckSummaryDTO;
import pl.bochunator.doncards.model.Role;

import java.util.Set;

@Data
@Builder
public class ApplicationUserWithDecksDTO {

    private Long userId;
    private Set<Role> authorities;
    private Page<DeckSummaryDTO> deckSummaryDTOs;
    private String email;
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}
