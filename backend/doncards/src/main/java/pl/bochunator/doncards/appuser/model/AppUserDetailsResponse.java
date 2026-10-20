package pl.bochunator.doncards.appuser.model;

import java.time.LocalDateTime;

public record AppUserDetailsResponse(
        Long id,
        String email,
        String username,
        LocalDateTime createdAt,
        long deckCount,
        long cardCount
) {

    public static AppUserDetailsResponse from(AppUser appUser, long deckCount, long cardCount) {
        return new AppUserDetailsResponse(
                appUser.getId(),
                appUser.getEmail(),
                appUser.getUsername(),
                appUser.getCreatedAt(),
                deckCount,
                cardCount
        );
    }
}
