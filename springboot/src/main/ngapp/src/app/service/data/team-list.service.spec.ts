import { TestBed } from '@angular/core/testing';

import { TeamListService } from './team-list.service';

describe('TeamListService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TeamListService = TestBed.get(TeamListService);
    expect(service).toBeTruthy();
  });
});
