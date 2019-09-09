import { TestBed } from '@angular/core/testing';

import { OutcomesService } from './outcomes.service';

describe('OutcomesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OutcomesService = TestBed.get(OutcomesService);
    expect(service).toBeTruthy();
  });
});
