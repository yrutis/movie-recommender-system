package ch.uzh.ifi.seal.ase.mrs.freeservice.repository;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository for storing actor data
 */
public interface ActorRepository extends JpaRepository<Actor, Long> {
}
