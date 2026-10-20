package pl.bochunator.doncards.deck.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.bochunator.doncards.deck.model.Deck;

public interface DeckRepository extends JpaRepository<Deck, Long> {
    Page<Deck> findAllByAppUserId(Long appUserId, Pageable pageable);
    long countByAppUserId(Long appUserId);
}
