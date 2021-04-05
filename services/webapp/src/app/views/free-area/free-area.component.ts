import {Component, OnInit} from '@angular/core';
import {FreeService} from '../../service/free.service';
import {Movie} from '../../model/movie';
import {MovieRating} from '../../model/ratings';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-free-area',
  templateUrl: './free-area.component.html',
  styleUrls: ['./free-area.component.scss']
})
export class FreeAreaComponent implements OnInit {
  movies: Movie[];
  ratedMovies: MovieRating[] = [];
  firstInit = true;
  serviceProblem = false;
  loading = false;

  constructor(private freeService: FreeService, private toastr: ToastrService) {
  }

  ngOnInit(): void {
    this.loading = true;
    this.freeService.getMovies(3).subscribe(value => {
        this.movies = value;
        this.loading = false;
      },
      () => {
        this.serviceProblem = true;
        this.loading = false;
        this.toastr.error('Something went wrong, please try again later!');
      });
  }

  movieWasRated(i: number, value: number): void {
    this.firstInit = false;
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
      this.reloadMovie(i);
    }
  }

  reloadMovie(index: number): void {
    this.freeService.getMovies(1).subscribe(newMovie => {
      const ids = this.movies.map(o => o.id);
      if (ids.indexOf(newMovie[0].id) > -1) {
        this.reloadMovie(index);
      } else {
        this.movies[index] = newMovie[0];
      }
    });
  }
}
