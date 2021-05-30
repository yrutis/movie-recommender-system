package ch.uzh.ifi.seal.ase.mrs.memberservice.repository;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * Repository for storing actor data
 */
public interface ActorRepository extends JpaRepository<Actor, Long> {
    Optional<Actor> findByTmdbId(Long tmdbId);
}
