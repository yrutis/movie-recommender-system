export class MovieRating {
  movieId: number;
  rating: number;
}

export class ActorRating {
  actorId: number;
  rating: number;
}

export class RatingDto {
  movieRatings: MovieRating[];
  actorRatings: ActorRating[];
}
