import {AfterViewInit, Component, HostListener, Input, OnInit} from '@angular/core';
import {Movie} from '../../model/movie';
import {collapseOnLeaveAnimation, expandOnEnterAnimation, zoomInOnEnterAnimation} from 'angular-animations';
import {LabelType, Options} from '@angular-slider/ngx-slider';

@Component({
  selector: 'app-recommendation-displayer',
  templateUrl: './recommendation-displayer.component.html',
  styleUrls: ['./recommendation-displayer.component.scss'],
  animations: [zoomInOnEnterAnimation({anchor: 'enterAnim'}), collapseOnLeaveAnimation(), expandOnEnterAnimation()]
})
export class RecommendationDisplayerComponent implements OnInit, AfterViewInit {

  @Input()
  recommendations: Movie[];
  filteredRecommendations: Movie[];
  p = 1;
  height = 385;
  filterExpanded = false;

  genreFilterValues: Map<string, boolean> = new Map();
  genreFilterKeys: string[];

  spokenLanguagesFilterValues: Map<string, boolean> = new Map();
  spokenLanguagesFilterKeys: string[];

  originalLanguagesFilterValues: Map<string, boolean> = new Map();
  originalLanguagesFilterKeys: string[];

  languageCodeMap: Map<string, string> = new Map();

  releaseMinValue;
  releaseMaxValue;
  releaseOptions: Options;

  ratingMinValue;
  ratingMaxValue;
  ratingOptions: Options;

  popularityMinValue;
  popularityMaxValue;
  popularityOptions: Options;

  revenueMinValue;
  revenueMaxValue;
  revenueOptions: Options;

  budgetMinValue;
  budgetMaxValue;
  budgetOptions: Options;


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
    this.initFilterValues();
  }

  initFilterValues(): void {
    this.recommendations.forEach(movie => {
      movie.genres.forEach(genre => {
        this.genreFilterValues.set(genre.name, true);
      });
      this.originalLanguagesFilterValues.set(movie.originalLanguage, true);
      movie.spokenLanguages.forEach(language => {
        this.spokenLanguagesFilterValues.set(language.languageCode, true);
        this.languageCodeMap.set(language.languageCode, language.englishName);
      });
    });
    this.genreFilterKeys = Array.from(this.genreFilterValues.keys());
    this.spokenLanguagesFilterKeys = Array.from(this.spokenLanguagesFilterValues.keys());
    this.originalLanguagesFilterKeys = Array.from(this.originalLanguagesFilterValues.keys());

    const minDate = this.recommendations.reduce(
      (min, currentValue) => currentValue.releaseDate < min ? currentValue.releaseDate : min, this.recommendations[0].releaseDate);
    const maxDate = this.recommendations.reduce(
      (max, currentValue) => currentValue.releaseDate > max ? currentValue.releaseDate : max, this.recommendations[0].releaseDate);
    const minDateNr = +(minDate + '').substr(0, 4);
    const maxDateNr = +(maxDate + '').substr(0, 4);
    this.releaseMinValue = minDateNr;
    this.releaseMaxValue = maxDateNr;
    this.releaseOptions = {
      floor: minDateNr,
      ceil: maxDateNr,
      translate: (value: number, label: LabelType): string => {
        switch (label) {
          case LabelType.Low:
            return '<b>Released After:</b> ' + value;
          case LabelType.High:
            return '<b>Released Before:</b> ' + value;
          default:
            return '' + value;
        }
      }
    };

    this.ratingMinValue = 1;
    this.ratingMaxValue = 5;
    this.ratingOptions = {
      floor: 1,
      ceil: 5,
      step: 0.5,
      translate: (value: number, label: LabelType): string => {
        switch (label) {
          case LabelType.Low:
            return '<b>Rating Higher Than:</b> ' + value;
          case LabelType.High:
            return '<b>Rating Lower Than:</b> ' + value;
          default:
            return '' + value;
        }
      }
    };

    const minPopularity = Math.floor(this.recommendations.reduce(
        (min, currentValue) => currentValue.popularity < min ? currentValue.popularity : min, this.recommendations[0].popularity));
    const maxPopularity = Math.ceil(this.recommendations.reduce(
      (max, currentValue) => currentValue.popularity > max ? currentValue.popularity : max, this.recommendations[0].popularity));
    this.popularityMinValue = minPopularity;
    this.popularityMaxValue = maxPopularity;
    this.popularityOptions = {
      floor: minPopularity,
      ceil: maxPopularity,
      // step: (maxPopularity - minPopularity) / 20,
      translate: (value: number, label: LabelType): string => {
        switch (label) {
          case LabelType.Low:
            return '<b>Popularity Higher Than:</b> ' + value;
          case LabelType.High:
            return '<b>Popularity Lower Than:</b> ' + value;
          default:
            return '' + value;
        }
      }
    };


    const minRevenue = Math.floor(this.recommendations.reduce(
      (min, currentValue) => currentValue.revenue < min ? currentValue.revenue : min, this.recommendations[0].revenue) / 1000000);
    const maxRevenue = Math.ceil(this.recommendations.reduce(
      (max, currentValue) => currentValue.revenue > max ? currentValue.revenue : max, this.recommendations[0].revenue) / 1000000);
    console.log(minRevenue);
    console.log(maxRevenue);
    this.revenueMinValue = minRevenue;
    this.revenueMaxValue = maxRevenue;
    this.revenueOptions = {
      floor: minRevenue,
      ceil: maxRevenue,
      translate: (value: number, label: LabelType): string => {
        switch (label) {
          case LabelType.Low:
            return '<b>Revenue Higher Than:</b> $' + value + ' MM';
          case LabelType.High:
            return '<b>Revenue Lower Than:</b> $' + value + ' MM';
          default:
            return '$' + value + ' MM';
        }
      }
    };
    const minBudget = Math.floor(this.recommendations.reduce(
      (min, currentValue) => currentValue.budget < min ? currentValue.budget : min, this.recommendations[0].budget) / 1000000);
    const maxBudget = Math.ceil(this.recommendations.reduce(
      (max, currentValue) => currentValue.budget > max ? currentValue.budget : max, this.recommendations[0].budget) / 1000000);
    this.budgetMinValue = minBudget;
    this.budgetMaxValue = maxBudget;
    this.budgetOptions = {
      floor: minBudget,
      ceil: maxBudget,
      translate: (value: number, label: LabelType): string => {
        switch (label) {
          case LabelType.Low:
            return '<b>Budget Higher Than:</b> $' + value + ' MM';
          case LabelType.High:
            return '<b>Budget Lower Than:</b> $' + value + ' MM';
          default:
            return '$' + value + ' MM';
        }
      }
    };
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      if (document.getElementsByClassName('movie-poster')[0]) {
        this.height = (document.getElementsByClassName('movie-poster')[0] as HTMLElement).offsetWidth * 1.45;
      }
    }, 100);

  }

  changeGenreFilterValue(key: string): void {
    const current = this.genreFilterValues.get(key);
    this.genreFilterValues.set(key, !current);
  }

  changeGenreFilterAll(value: boolean): void {
    this.genreFilterKeys.forEach(key => {
      this.genreFilterValues.set(key, value);
    });
  }

  changeSpokenLanguageFilterValue(key: string): void {
    const current = this.spokenLanguagesFilterValues.get(key);
    this.spokenLanguagesFilterValues.set(key, !current);
  }

  changeSpokenLanguageFilterAll(value: boolean): void {
    this.spokenLanguagesFilterKeys.forEach(key => {
      this.spokenLanguagesFilterValues.set(key, value);
    });
  }

  changeOriginalLanguageFilterValue(key: string): void {
    const current = this.originalLanguagesFilterValues.get(key);
    this.originalLanguagesFilterValues.set(key, !current);
  }

  changeOriginalLanguageFilterAll(value: boolean): void {
    this.originalLanguagesFilterKeys.forEach(key => {
      this.originalLanguagesFilterValues.set(key, value);
    });
  }
  resetFilter(): void {
    this.initFilterValues();
    this.filteredRecommendations = this.recommendations;
    this.p = 1;
  }
  applyFilter(): void {
    let currentRecommendations = this.recommendations;
    currentRecommendations = currentRecommendations.filter(value => {
      let valid = false;
      value.genres.forEach(genre => {
        if (this.genreFilterValues.get(genre.name)) {
          valid = true;
        }
      });
      return valid;
    });

    currentRecommendations = currentRecommendations.filter(value => {
      let valid = false;
      value.spokenLanguages.forEach(language => {
        if (this.spokenLanguagesFilterValues.get(language.languageCode)) {
          valid = true;
        }
      });
      return valid;
    });

    currentRecommendations = currentRecommendations.filter(value => {
      return this.originalLanguagesFilterValues.get(value.originalLanguage);
    });


    currentRecommendations = currentRecommendations.filter(value => {
      const releaseYear = +((value.releaseDate + '').substr(0, 4));
      if (releaseYear >= this.releaseMinValue && releaseYear <= this.releaseMaxValue) {
        return true;
      }
      return false;
    });

    currentRecommendations = currentRecommendations.filter(value => {
      if (value.popularity >= this.popularityMinValue && value.popularity <= this.popularityMaxValue) {
        return true;
      }
      return false;
    });

    currentRecommendations = currentRecommendations.filter(value => {
      if (value.voteAverage / 2 >= this.ratingMinValue && value.voteAverage / 2 <= this.ratingMaxValue) {
        return true;
      }
      return false;
    });

    currentRecommendations = currentRecommendations.filter(value => {
      if (value.revenue / 1000000 >= this.revenueMinValue && value.revenue / 1000000 <= this.revenueMaxValue) {
        return true;
      }
      return false;
    });

    currentRecommendations = currentRecommendations.filter(value => {
      if (value.budget / 1000000 >= this.budgetMinValue && value.budget / 1000000 <= this.budgetMaxValue) {
        return true;
      }
      return false;
    });

    this.filteredRecommendations = currentRecommendations;
    this.p = 1;
    this.filterExpanded = false;
  }
}
