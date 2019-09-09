import { TestBed } from '@angular/core/testing';

import { PageOrderService } from './page-order.service';

describe('PageOrderService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PageOrderService = TestBed.get(PageOrderService);
    expect(service).toBeTruthy();
  });
});
