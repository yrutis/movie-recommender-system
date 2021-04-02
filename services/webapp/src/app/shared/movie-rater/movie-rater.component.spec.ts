import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieRaterComponent } from './movie-rater.component';

describe('MovieRaterComponent', () => {
  let component: MovieRaterComponent;
  let fixture: ComponentFixture<MovieRaterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MovieRaterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieRaterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
