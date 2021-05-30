import {Component, OnInit} from '@angular/core';
import {MemberService} from '../../service/member.service';
import {Movie} from '../../model/movie';
import {FreeService} from '../../service/free.service';
import {ToastrService} from 'ngx-toastr';
import {ActorRating, MovieRating} from '../../model/ratings';
import {Actor} from '../../model/actor';
import {ChangeContext, LabelType} from '@angular-slider/ngx-slider';

/**
 * Member area component
 */
@Component({
  selector: 'app-member-area',
  templateUrl: './member-area.component.html',
  styleUrls: ['./member-area.component.scss']
})
export class MemberAreaComponent implements OnInit {
  movies: Movie[];
  actors: Actor[];
  recommendations: Movie[];
  step = 0;
  loading = false;
  serviceProblem = false;
  firstInit = true;
  lastTrainedOn: Date = null;
  moviePopularity = 5;
  actorPopularity = 5;
  moviePopularitySlideOptions = {
    step: 1,
    floor: 1,
    ceil: 10,
    translate: (value: number, label: LabelType): string => {
      if (label === LabelType.Low) {
        return '<b>Movie Popularity: </b>' + value;
      } else {
        return '';
      }
    }
  };
  actorPopularitySlideOptions = {
    step: 1,
    floor: 1,
    ceil: 10,
    translate: (value: number, label: LabelType): string => {
      if (label === LabelType.Low) {
        return '<b>Actor Popularity: </b>' + value;
      } else {
        return '';
      }
    }
  };

  constructor(private memberService: MemberService, private freeService: FreeService, private toastr: ToastrService) {
  }

  /**
   * Init the member area
   */
  ngOnInit(): void {
    this.changeStep(0);
    this.memberService.getLastTrained().subscribe(value => {
      this.lastTrainedOn = value;
    });
  }

  /**
   * logout the user
   */
  logout(): void {
    this.memberService.logout();
  }

  /**
   * change the current view
   * @param step step (0=movie, 1=actor, 2=recommendations)
   */
  changeStep(step: number): void {
    switch (step) {
      case 0:
        this.movies = null;
        this.initMovieRating();
        break;
      case 1:
        this.actors = null;
        this.initActorRating();
        break;
      case 2:
        this.recommendations = null;
        this.initRecommendations();
        break;

    }
    this.step = step;
  }

  /**
   * load movies for rating with according popularity
   */
  initMovieRating(): void {
    this.loading = true;
    this.freeService.getMovies(3, this.moviePopularity).subscribe(value => {
        this.movies = value;
        this.loading = false;
      },
      () => {
        this.serviceProblem = true;
        this.loading = false;
        this.toastr.error('Something went wrong, please try again later!');
      });
  }

  /**
   * load actors for rating with according popularity
   */
  initActorRating(): void {
    this.loading = true;
    this.freeService.getActors(3, this.actorPopularity).subscribe(value => {
        this.actors = value;
        this.loading = false;
      },
      () => {
        this.serviceProblem = true;
        this.loading = false;
        this.toastr.error('Something went wrong, please try again later!');
      });
  }

  /**
   * Load custom recommendations for a logged in user
   */
  initRecommendations(): void {
    this.loading = true;
    this.memberService.getRecommendations().subscribe(value => {
        this.recommendations = value;
        this.loading = false;
      },
      () => {
        this.serviceProblem = true;
        this.loading = false;
        this.toastr.error('Something went wrong, please try again later!');
      });
  }

  /**
   * handle the rating of a movie
   * @param i index of the movie in the rating components
   * @param $event rating value
   */
  movieWasRated(i: number, $event: number): void {
    if ($event) {
      const movieRating = new MovieRating();
      movieRating.tmdbId = this.movies[i].id;
      movieRating.rating = $event;
      this.memberService.rateMovie(movieRating).subscribe(value => {
      });
    }
    setTimeout(() => {
      this.freeService.getMovies(1, this.moviePopularity).subscribe(value => {
        this.movies[i] = value[0];
      });
    }, 350);
  }

  /**
   * handle the rating of an actor
   * @param i index of the actor in the rating components
   * @param $event rating value
   */
  actorWasRated(i: number, $event: number): void {
    if ($event) {
      const actorRating = new ActorRating();
      actorRating.tmdbId = this.actors[i].id;
      actorRating.rating = $event;
      this.memberService.rateActor(actorRating).subscribe(value => {
      });
    }
    setTimeout(() => {
      this.freeService.getActors(1, this.actorPopularity).subscribe(value => {
        this.actors[i] = value[0];
      });
    }, 350);
  }

  /**
   * handle change in movie popularity
   * @param $event change event
   */
  moviePopularityChanged($event: ChangeContext): void {
    this.movies = undefined;
    this.initMovieRating();
  }

  /**
   * handle change in actor popularity
   * @param $event change event
   */
  actorPopularityChanged($event: ChangeContext): void {
    this.actors = undefined;
    this.initActorRating();
  }
}
