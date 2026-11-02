package pl.bochunator.doncards.appuser.model;

public record AppUserSearchResponse(
        Long id,
        String username
) {

    public static AppUserSearchResponse from(AppUser appUser) {
        return new AppUserSearchResponse(
                appUser.getId(),
                appUser.getUsername()
        );
    }
}
