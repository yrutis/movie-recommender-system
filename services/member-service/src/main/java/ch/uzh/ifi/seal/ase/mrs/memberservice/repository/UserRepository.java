package ch.uzh.ifi.seal.ase.mrs.memberservice.repository;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository to manage users
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a user by Username
     * @param username username to search with
     * @return Optional of User
     */
    Optional<User> findByUsername(String username);

    /**
     * Find a user by Rating user id
     * @param tblRatingUserId user id to search with
     * @return Optional of User
     */
    Optional<User> findByTblRatingUserId(Long tblRatingUserId);
}
