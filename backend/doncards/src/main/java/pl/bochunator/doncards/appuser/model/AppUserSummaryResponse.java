package pl.bochunator.doncards.appuser.model;

import java.time.LocalDateTime;

public record AppUserSummaryResponse(
        Long id,
        String email,
        String username,
        LocalDateTime createdAt
) {

    public static AppUserSummaryResponse from(AppUser appUser) {
        return new AppUserSummaryResponse(
                appUser.getId(),
                appUser.getEmail(),
                appUser.getUsername(),
                appUser.getCreatedAt()
        );
    }
}

