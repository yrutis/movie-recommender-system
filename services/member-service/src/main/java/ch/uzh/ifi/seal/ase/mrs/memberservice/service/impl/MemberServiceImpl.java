package ch.uzh.ifi.seal.ase.mrs.memberservice.service.impl;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.MovieRating;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.IMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * Service that handles movie and actor ratings and gets recommendations
 */
@Service
@Slf4j
public class MemberServiceImpl implements IMemberService {

    @Override
    public void rateMovie(MovieRating movieRating, Principal principal) {
        // TODO: get user with principal
        log.info(movieRating.toString());
        // TODO: add rating to the database
        log.info(principal.getName());
    }
}
