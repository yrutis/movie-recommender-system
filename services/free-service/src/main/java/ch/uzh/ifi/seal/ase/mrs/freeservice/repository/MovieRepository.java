package ch.uzh.ifi.seal.ase.mrs.freeservice.repository;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository for storing movie data
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
