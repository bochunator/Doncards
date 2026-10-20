package pl.bochunator.doncards.card.model;

public record CardResponse(
        Long id,
        String term,
        String translation
) {

    public static CardResponse from(Card card) {
        return new CardResponse(
                card.getId(),
                card.getTerm(),
                card.getTranslation()
        );
    }
}
