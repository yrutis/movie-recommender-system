import { TestBed } from '@angular/core/testing';

import { FreeService } from './free.service';

describe('FreeService', () => {
  let service: FreeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FreeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
