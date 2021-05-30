import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FreeAreaComponent } from './free-area.component';

describe('FreeAreaComponent', () => {
  let component: FreeAreaComponent;
  let fixture: ComponentFixture<FreeAreaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FreeAreaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FreeAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
