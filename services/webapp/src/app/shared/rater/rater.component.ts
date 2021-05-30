import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Movie} from '../../model/movie';
import {DomSanitizer, SafeStyle} from '@angular/platform-browser';
import {StarRatingComponent} from 'ng-starrating';
import {NgbTooltipConfig} from '@ng-bootstrap/ng-bootstrap';
import {zoomInOnEnterAnimation} from 'angular-animations';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {Actor} from '../../model/actor';
import {DatePipe} from '@angular/common';

/**
 * General rater component that is used for movies and actors
 */
@Component({
  selector: 'app-rater',
  templateUrl: './rater.component.html',
  styleUrls: ['./rater.component.scss'],
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
export class RaterComponent implements OnInit {

  @Input()
  ratingObject: Movie | Actor;

  @Input()
  delay: number;

  @Output()
  objectRated: EventEmitter<number> = new EventEmitter<number>();

  objectStyle: SafeStyle;
  ratable = true;
  objectTitle = '';

  constructor(private san: DomSanitizer, private config: NgbTooltipConfig) {
    this.config.container = 'body';
  }

  /**
   * Initialize rating component. Sanitizes images in order to properly display them and set the rating object title
   */
  ngOnInit(): void {
    const datePipe = new DatePipe('en-US');
    if ((this.ratingObject as Movie).posterPath) {
      this.objectStyle =
        this.san.bypassSecurityTrustStyle(
          'background-image: url(\'https://image.tmdb.org/t/p/original' + (this.ratingObject as Movie).posterPath + '\')');
      this.objectTitle = `${(this.ratingObject as Movie).title} (${datePipe.transform((this.ratingObject as Movie).releaseDate, 'yyyy')})`;
    }

    if ((this.ratingObject as Actor).profilePath) {
      this.objectStyle =
        this.san.bypassSecurityTrustStyle(
          'background-image: url(\'https://image.tmdb.org/t/p/original' + (this.ratingObject as Actor).profilePath + '\')');
      this.objectTitle = (this.ratingObject as Actor).name;
    }
  }

  /**
   * Reload a object without rating it
   */
  reload(): void {
    this.objectRated.emit(null);
  }

  /**
   * Rate a object
   * @param $event rating event from StarRatingComponent
   */
  onRate($event: { oldValue: number; newValue: number; starRating: StarRatingComponent }): void {
    this.ratable = false;
    this.objectRated.emit($event.newValue);
  }
}
