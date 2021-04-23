package ch.uzh.ifi.seal.ase.mrs.memberservice.controller;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.ActorRating;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.MovieRating;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.IMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Rest Controller which handles updating member preferences and recommendations
 */
@RestController
@RequestMapping("api/members")
@CrossOrigin(origins = "${hosts.gui}")
@Slf4j
public class MemberController {

    private final IMemberService memberService;

    /**
     * Constructor, autowires the member service
     * @param memberService member service
     */
    @Autowired
    public MemberController(IMemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * Handles a new movie rating
     * @param movieRating rating of the movie
     * @param principal user rating the movie
     */
    @PostMapping("/movieRating")
    public void newMovieRating(@RequestBody MovieRating movieRating, Principal principal){
        this.memberService.rateMovie(movieRating, principal);
    }

    /**
     * Handles a new actor rating
     * @param actorRating rating of the actor
     * @param principal user rating the actor
     */
    @PostMapping("/actorRating")
    public void newActorRating(@RequestBody ActorRating actorRating, Principal principal){
        this.memberService.rateActor(actorRating, principal);
    }
}
