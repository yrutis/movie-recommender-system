import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecommendationDisplayerComponent } from './recommendation-displayer.component';

describe('RecommendationDisplayerComponent', () => {
  let component: RecommendationDisplayerComponent;
  let fixture: ComponentFixture<RecommendationDisplayerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecommendationDisplayerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecommendationDisplayerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
