package pl.bochunator.doncards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bochunator.doncards.model.English;

@Repository
public interface EnglishRepository extends JpaRepository<English, Long> {
}
