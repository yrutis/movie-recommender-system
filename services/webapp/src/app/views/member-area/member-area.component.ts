import {Component, OnInit} from '@angular/core';
import {MemberService} from '../../service/member.service';
import {Movie} from '../../model/movie';
import {FreeService} from '../../service/free.service';
import {ToastrService} from 'ngx-toastr';
import {ActorRating, MovieRating} from '../../model/ratings';
import {Actor} from '../../model/actor';

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

  constructor(private memberService: MemberService, private freeService: FreeService, private toastr: ToastrService) {
  }

  ngOnInit(): void {
    this.changeStep(0);

    this.memberService.getLastTrained().subscribe(value => {
      this.lastTrainedOn = value;
    });
  }

  logout(): void {
    this.memberService.logout();
  }

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

  initMovieRating(): void {
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


  initActorRating(): void {
    this.loading = true;
    this.freeService.getActors(3).subscribe(value => {
        this.actors = value;
        this.loading = false;
      },
      () => {
        this.serviceProblem = true;
        this.loading = false;
        this.toastr.error('Something went wrong, please try again later!');
      });
  }

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

  movieWasRated(i: number, $event: number): void {
    if ($event) {
      const movieRating = new MovieRating();
      movieRating.tmdbId = this.movies[i].id;
      movieRating.rating = $event;
      this.memberService.rateMovie(movieRating).subscribe(value => {});
    }
    setTimeout(() => {
      this.freeService.getMovies(1).subscribe(value => {
        this.movies[i] = value[0];
      });
    }, 350);
  }

  actorWasRated(i: number, $event: number): void {
    if ($event) {
      const actorRating = new ActorRating();
      actorRating.tmdbId = this.actors[i].id;
      actorRating.rating = $event;
      this.memberService.rateActor(actorRating).subscribe(value => {});
    }
    setTimeout(() => {
      this.freeService.getActors(1).subscribe(value => {
        this.actors[i] = value[0];
      });
    }, 350);
  }
}
