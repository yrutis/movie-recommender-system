package ch.uzh.ifi.seal.ase.mrs.freeservice.controller;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbActor;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.IActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller which serves a certain amount of actors
 */
@RestController
@RequestMapping("api/actors")
@CrossOrigin(origins = "${HOSTS.GUI}")
public class ActorController {

    private final IActorService actorService;

    /**
     * Constructor, autowires the actor service
     *
     * @param actorService actor service
     */
    @Autowired
    public ActorController(IActorService actorService) {
        this.actorService = actorService;
    }


    /**
     * List of actors
     * @param amount the number of actors
     * @return List of actors
     */
    @GetMapping("/{amount}")
    public List<TmdbActor> getActors(@PathVariable("amount") Integer amount) {
        return actorService.getActors(amount);
    }
}
