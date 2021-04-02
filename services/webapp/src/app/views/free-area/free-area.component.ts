import {Component, OnInit} from '@angular/core';
import {FreeService} from '../../service/free.service';
import {Movie} from '../../model/movie';
import {MovieRating} from '../../model/movieRating';

@Component({
  selector: 'app-free-area',
  templateUrl: './free-area.component.html',
  styleUrls: ['./free-area.component.scss']
})
export class FreeAreaComponent implements OnInit {
  movies: Movie[];
  ratedMovies: MovieRating[] = [];
  constructor(private freeService: FreeService) {
  }

  ngOnInit(): void {
    this.freeService.getMovies(3).subscribe(value => {
      this.movies = value;
    });
  }

  movieWasRated(i: number, value: number): void {
    if (value !== null) {
      const newRating = new MovieRating();
      newRating.movieId = this.movies[i].id;
      newRating.rating = value;
      this.ratedMovies.push(newRating);
      if (this.ratedMovies.length === 3) {
        console.log('GO TO ACTOR RATING');
        console.log(this.ratedMovies);
      }
    } else {
      this.freeService.getMovies(1).subscribe(value1 => {
        this.movies[i] = value1[0];
      });
    }
  }
}
