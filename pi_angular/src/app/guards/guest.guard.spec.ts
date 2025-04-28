import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { guestGuard } from './guest.guard';

describe('guestGuard', () => {
  
    const executeGuard: CanActivateFn = (guardParameter) => 
        TestBed.runInInjectionContext(() => guestGuard.canActivate(guardParameter));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
