import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Movie} from '../../model/movie';
import {DomSanitizer, SafeResourceUrl, SafeStyle} from '@angular/platform-browser';
import {StarRatingComponent} from 'ng-starrating';
import {NgbTooltipConfig} from '@ng-bootstrap/ng-bootstrap';
import {fadeInLeftOnEnterAnimation, fadeInOnEnterAnimation, flipInXOnEnterAnimation, zoomInOnEnterAnimation} from 'angular-animations';
import {animate, state, style, transition, trigger} from '@angular/animations';

@Component({
  selector: 'app-movie-rater',
  templateUrl: './movie-rater.component.html',
  styleUrls: ['./movie-rater.component.scss'],
  animations: [
    zoomInOnEnterAnimation({anchor: 'enter'}),
    trigger('opacity_anim', [
      state('full_op', style({
        opacity: 1
      })),
      state('semi_op', style({
        opacity: 0.4
      })),
      transition('full_op => semi_op', [
        animate('0.5s')
      ])])
  ]

})
export class MovieRaterComponent implements OnInit {

  @Input()
  movie: Movie;

  @Input()
  delay: number;

  @Output()
  movieRated: EventEmitter<number> = new EventEmitter<number>();

  movieStyle: SafeStyle;
  ratable = true;

  constructor(private san: DomSanitizer, private config: NgbTooltipConfig) {
    this.config.container = 'body';
  }

  ngOnInit(): void {
    console.log(this.movie);
    if (this.movie.posterPath) {
      this.movieStyle =
        this.san.bypassSecurityTrustStyle('background-image: url(\'https://image.tmdb.org/t/p/original' + this.movie.posterPath + '\')');
    }
  }

  reload(): void {
    this.movieRated.emit(null);
  }

  onRate($event: { oldValue: number; newValue: number; starRating: StarRatingComponent }): void {
    this.ratable = false;
    this.movieRated.emit($event.newValue);
  }
}
