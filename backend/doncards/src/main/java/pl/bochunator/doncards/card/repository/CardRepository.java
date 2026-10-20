package pl.bochunator.doncards.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bochunator.doncards.card.model.Card;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findTop5ByDeckId(Long deckId);
    long countByDeck_AppUser_Id(Long appUserId);
}
