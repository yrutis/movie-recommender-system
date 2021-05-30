/**
 * Collection of DTOs related to ratings
 */

/**
 * Movie Rating DTO
 */
export class MovieRating {
  tmdbId: number;
  rating: number;
}

/**
 * Actor Rating DTO
 */
export class ActorRating {
  tmdbId: number;
  rating: number;
}

/**
 * Rating Wrapper DTO
 */
export class RatingDto {
  movieRatings: MovieRating[];
  actorRatings: ActorRating[];
}
