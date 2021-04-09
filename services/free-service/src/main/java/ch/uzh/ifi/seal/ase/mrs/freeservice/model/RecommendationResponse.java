package ch.uzh.ifi.seal.ase.mrs.freeservice.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationResponse {
    List<Long> message;
}
