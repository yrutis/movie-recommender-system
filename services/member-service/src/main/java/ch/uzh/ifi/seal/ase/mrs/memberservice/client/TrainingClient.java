package ch.uzh.ifi.seal.ase.mrs.memberservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Feign Client to interact with the training backend
 */
@FeignClient(url = "${hosts.training}", name = "training")
public interface TrainingClient {

    /**
     * Starts the training in the training backend
     */
    @GetMapping("/train-recommendations")
    void startTraining();
}
