export class MovieRating {
  tmdbId: number;
  rating: number;
}

export class ActorRating {
  tmdbId: number;
  rating: number;
}

export class RatingDto {
  movieRatings: MovieRating[];
  actorRatings: ActorRating[];
}
