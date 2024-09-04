package pl.bochunator.doncards.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.bochunator.doncards.model.ApplicationUser;
import pl.bochunator.doncards.model.Deck;

import java.util.List;

public interface DeckRepository extends JpaRepository<Deck, Long> {

    boolean existsByAuthorAndName(ApplicationUser author, String name);

    @Query("select d from Deck d")
    Page<Deck> findAllDecks(Pageable pageable);

    @Query("select d from Deck d where d.author = :author")
    Page<Deck> findByAuthor(ApplicationUser author, Pageable pageable);

}
