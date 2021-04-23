import {AfterViewInit, Component, HostListener, Input, OnInit} from '@angular/core';
import {Movie} from '../../model/movie';
import {zoomInOnEnterAnimation} from 'angular-animations';

@Component({
  selector: 'app-recommendation-displayer',
  templateUrl: './recommendation-displayer.component.html',
  styleUrls: ['./recommendation-displayer.component.scss'],
  animations: [zoomInOnEnterAnimation({anchor: 'enterAnim'})]
})
export class RecommendationDisplayerComponent implements OnInit, AfterViewInit {

  @Input()
  recommendations: Movie[];
  filteredRecommendations: Movie[];
  p = 1;
  height = 385;

  @HostListener('window:resize', ['$event'])
  onResize(event): void {
    if (document.getElementsByClassName('movie-poster')[0]) {
      this.height = (document.getElementsByClassName('movie-poster')[0] as HTMLElement).offsetWidth * 1.45;
    }
  }

  constructor() {
  }

  ngOnInit(): void {
    this.filteredRecommendations = this.recommendations;
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      if (document.getElementsByClassName('movie-poster')[0]) {
        this.height = (document.getElementsByClassName('movie-poster')[0] as HTMLElement).offsetWidth * 1.45;
      }
    }, 100);

  }

}
