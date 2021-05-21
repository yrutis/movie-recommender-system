import {Component, OnInit} from '@angular/core';
import {FreeService} from '../../service/free.service';
import {Movie} from '../../model/movie';
import {ActorRating, MovieRating, RatingDto} from '../../model/ratings';
import {ToastrService} from 'ngx-toastr';
import {Actor} from '../../model/actor';
import {ChangeContext, LabelType} from '@angular-slider/ngx-slider';

@Component({
  selector: 'app-free-area',
  templateUrl: './free-area.component.html',
  styleUrls: ['./free-area.component.scss']
})
export class FreeAreaComponent implements OnInit {
  movies: Movie[];
  actors: Actor[];
  ratedMovies: MovieRating[] = [];
  ratedMoviesPos: boolean[] = [false, false, false];
  ratedActors: ActorRating[] = [];
  ratedActorsPos: boolean[] = [false, false, false];
  recommendations: Movie[];
  firstInit = true;
  serviceProblem = false;
  loading = false;
  step = 0;
  moviePopularity = 5;
  actorPopularity = 5;
  moviePopularitySlideOptions = {
    step: 1,
    floor: 1,
    ceil: 10,
    translate: (value: number, label: LabelType): string => {
      switch (label) {
        case LabelType.Low:
          return '<b>Movie Popularity: </b>' + value;
        default:
          return '';
      }
    }
  };
  actorPopularitySlideOptions = {
    step: 1,
    floor: 1,
    ceil: 10,
    translate: (value: number, label: LabelType): string => {
      switch (label) {
        case LabelType.Low:
          return '<b>Actor Popularity: </b>' + value;
        default:
          return '';
      }
    }
  };

  constructor(private freeService: FreeService, private toastr: ToastrService) {
  }

  ngOnInit(): void {
    // if (localStorage.getItem('tempRec')) {
    //   this.recommendations = JSON.parse(localStorage.getItem('tempRec'));
    //   this.step = 2;
    // } else {
    //   this.initMovies();
    // }
    this.initMovies(false);
  }

  initMovies(changeOnly: boolean): void {
    this.loading = true;
    this.freeService.getMovies(3, this.moviePopularity).subscribe(value => {
        if (!changeOnly) {
          this.movies = value;

        } else {
          for (let i = 0; i < value.length; i++) {
            if (!this.ratedMoviesPos[i]) {
              this.movies[i] = value[i];
            }
          }
        }
        this.loading = false;

      },
      () => {
        this.serviceProblem = true;
        this.loading = false;
        this.toastr.error('Something went wrong, please try again later!');
      });
  }

  initActors(changeOnly: boolean): void {
    this.step = 1;
    this.loading = true;
    this.firstInit = true;
    this.freeService.getActors(3, this.moviePopularity).subscribe(value => {
        if (!changeOnly) {
          this.actors = value;
        } else {
          for (let i = 0; i < value.length; i++) {
            if (!this.ratedActorsPos[i]) {
              this.actors[i] = value[i];
            }
          }
        }
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
      newRating.tmdbId = this.movies[i].id;
      newRating.rating = value;
      this.ratedMovies.push(newRating);
      this.ratedMoviesPos[i] = true;
      if (this.ratedMovies.length === 3) {
        this.initActors(false);
      }
    } else {
      this.reloadMovie(i);
    }
  }

  actorWasRated(i: number, value: number): void {
    this.firstInit = false;
    if (value !== null) {
      const newRating = new ActorRating();
      newRating.tmdbId = this.actors[i].id;
      newRating.rating = value;
      this.ratedActors.push(newRating);
      this.ratedActorsPos[i] = true;
      if (this.ratedActors.length === 3) {
        this.getRecommendations();
      }
    } else {
      this.reloadActor(i);
    }
  }

  reloadMovie(index: number): void {
    this.freeService.getMovies(1, this.moviePopularity).subscribe(newMovie => {
      const ids = this.movies.map(o => o.id);
      if (ids.indexOf(newMovie[0].id) > -1) {
        this.reloadMovie(index);
      } else {
        this.movies[index] = newMovie[0];
      }
    });
  }

  reloadActor(index: number): void {
    this.freeService.getActors(1, this.actorPopularity).subscribe(newActor => {
      const ids = this.actors.map(o => o.id);
      if (ids.indexOf(newActor[0].id) > -1) {
        this.reloadActor(index);
      } else {
        this.actors[index] = newActor[0];
      }
    });
  }

  getRecommendations(): void {
    const ratingDto = new RatingDto();
    ratingDto.actorRatings = this.ratedActors;
    ratingDto.movieRatings = this.ratedMovies;
    this.loading = true;
    this.step = 2;
    this.freeService.getRecommendations(ratingDto).subscribe(value => {
      this.recommendations = value;
      this.loading = false;
      localStorage.setItem('tempRec', JSON.stringify(value));
    });
  }

  moviePopularityChanged($event: ChangeContext): void {
    this.initMovies(true);
  }

  actorPopularityChanged($event: ChangeContext): void {
    this.initActors(true);
  }
}
