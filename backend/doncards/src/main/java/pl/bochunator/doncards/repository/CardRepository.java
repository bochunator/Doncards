package pl.bochunator.doncards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bochunator.doncards.model.Card;

import java.util.List;
import java.util.Set;

public interface CardRepository extends JpaRepository<Card, Long> {

    boolean existsByTermAndTranslation(String term, String translation);

    Set<Card> findByCardIdIn(Set<Long> cardIds);

    /**
     * {@link #findByTermInAndTranslationIn(List, List)} method retrieves all cards that match the given terms and translations.
     * This method returns a set of unique cards, where duplicate entries are removed.
     * Even if there are multiple identical pairs of term and translation in the input lists,
     * the database query will return only unique records that match the criteria.
     *
     * Note: Changing the return type to List would not change the behavior of the method regarding uniqueness.
     *
     * @param terms the list of terms to search for
     * @param translations the list of translations to search for
     * @return a set of unique cards matching the provided terms and translations
     */
    Set<Card> findByTermInAndTranslationIn(List<String> terms, List<String> translations);

}
