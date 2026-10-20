package pl.bochunator.doncards.authentication.model;

public record RegisterRequest(
        String email,
        String username,
        String password
) {
}
