import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Movie} from '../../model/movie';
import {DomSanitizer, SafeResourceUrl, SafeStyle} from '@angular/platform-browser';

@Component({
  selector: 'app-movie-rater',
  templateUrl: './movie-rater.component.html',
  styleUrls: ['./movie-rater.component.scss']
})
export class MovieRaterComponent implements OnInit {

  @Input()
  movie: Movie;

  @Output()
  movieRated: EventEmitter<number> = new EventEmitter<number>();

  movieStyle: SafeStyle;
  movieImage: SafeResourceUrl;

  constructor(private san: DomSanitizer) {
  }

  ngOnInit(): void {
    console.log(this.movie);
    console.log(this.movie.posterPath);
    if (this.movie.posterPath) {
      this.movieStyle = this.san.bypassSecurityTrustStyle('background-image: url(\'https://image.tmdb.org/t/p/original' + this.movie.posterPath + '\')');
      this.movieImage = this.san.bypassSecurityTrustResourceUrl('https://image.tmdb.org/t/p/original' + this.movie.posterPath);
    }

  }

  rate(value: number): void {
    if (value !== null) {
      this.movieStyle = this.san.bypassSecurityTrustStyle('background-image: linear-gradient(rgb(127 127 127 / 90%), rgb(178 178 178 / 90%)), url(\'https://image.tmdb.org/t/p/original' + this.movie.posterPath + '\')');
    }
    this.movieRated.emit(value);
  }
}
