package ch.uzh.ifi.seal.ase.mrs.memberservice.repository;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository to store user ratings
 */
@Repository
public interface RatingRepository extends JpaRepository<MovieRating, Long> {

    /**
     * Native Query that returns the current highest userId
     * @return highest User Id
     */
    @Query(value = "select max(user_id) from tbl_rating", nativeQuery = true)
    Long getHighestId();

    /**
     * Get all ratings of a certain user
     * @param userId id of the user
     * @return list of ratings
     */
    List<MovieRating> findAllByUserId(Long userId);
}
