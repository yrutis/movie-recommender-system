export class Movie {
  id: number;
  imdbId: string;
  originalLanguage: string;
  originalTitle: string;
  spokenLanguages: SpokenLanguage[];
  title: string;
  tagline: string;
  overview: string;
  posterPath: string;
  genres: Genre[];
  productionCompanies: ProductionCompany[];
  productionCountries: ProductionCountry[];
  popularity: number;
  runtime: number;
  revenue: number;
  budget: number;
  voteAverage: number;
  voteCount: number;
  releaseDate: Date;
  displayFullOverview = false;
}

export class SpokenLanguage {
  englishName: string;
  languageCode: string;
  name: string;
}

export class ProductionCountry {
  countryCode: string;
  name: string;
}

export class ProductionCompany {
  originCountry: string;
  name: string;
}
export class Genre{
  id: number;
  name: string;
}
