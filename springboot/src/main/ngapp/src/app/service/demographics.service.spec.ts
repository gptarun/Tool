import { TestBed } from '@angular/core/testing';

import { DemographicsService } from './demographics.service';

describe('DemographicsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DemographicsService = TestBed.get(DemographicsService);
    expect(service).toBeTruthy();
  });
});
