package ch.uzh.ifi.seal.ase.mrs.memberservice.repository;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.Rating;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository to store user ratings
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

}
