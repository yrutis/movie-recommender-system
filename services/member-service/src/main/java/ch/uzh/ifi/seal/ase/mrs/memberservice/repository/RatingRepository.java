package ch.uzh.ifi.seal.ase.mrs.memberservice.repository;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository to store user ratings
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

}
