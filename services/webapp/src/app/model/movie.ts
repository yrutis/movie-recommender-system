/**
 * Collection of DTOs related to the movies
 */

/**
 * Movie DTO
 */
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
  cast: Cast;
  watchProviders: WatchProviderCountry;
}

/**
 * SpokenLanguage DTO
 */
export class SpokenLanguage {
  englishName: string;
  languageCode: string;
  name: string;
}

/**
 * ProductionCountry DTO
 */
export class ProductionCountry {
  countryCode: string;
  name: string;
}

/**
 * ProductionCompany DTO
 */
export class ProductionCompany {
  originCountry: string;
  name: string;
}

/**
 * Genre DTO
 */
export class Genre{
  id: number;
  name: string;
}

/**
 * Cast DTO
 */
export class Cast{
  cast: CastMember[];
}

/**
 * CastMember DTO
 */
export class CastMember {
  name: string;
  character: string;
  order: number;
}

/**
 * WatchProviderCountry DTO
 */
export class WatchProviderCountry {
  link: string;
  rent: WatchProvider[];
  buy: WatchProvider[];
  flatrate: WatchProvider[];
}

/**
 * WatchProvider DTO
 */
export class WatchProvider {
  providerName: string;
  logoPath: string;
}
