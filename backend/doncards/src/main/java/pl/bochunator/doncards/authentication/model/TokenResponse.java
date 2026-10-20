package pl.bochunator.doncards.authentication.model;

public record TokenResponse(
        String accessToken,
        String tokenyType
) {
}
