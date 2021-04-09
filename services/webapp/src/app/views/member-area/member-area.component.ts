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
  step = 0;
  loading = false;
  serviceProblem = false;
  firstInit = true;

  constructor(private memberService: MemberService, private freeService: FreeService, private toastr: ToastrService) {
  }

  ngOnInit(): void {
    this.changeStep(0);
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
        break;

    }
    this.step = step;
  }

  initMovieRating(): void {
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


  movieWasRated(i: number, $event: number): void {
    if ($event) {
      const movieRating = new MovieRating();
      movieRating.tmdbId = this.movies[i].tmdbId;
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
      actorRating.tmdbId = this.actors[i].tmdbId;
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
