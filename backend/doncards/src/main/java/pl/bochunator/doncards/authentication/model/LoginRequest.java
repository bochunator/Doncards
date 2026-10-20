package pl.bochunator.doncards.authentication.model;

public record LoginRequest(
        String username,
        String password
) {
}
