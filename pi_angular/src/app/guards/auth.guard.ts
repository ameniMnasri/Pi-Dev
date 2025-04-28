import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/user/auth.service';

export const authGuard = (p0: unknown) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.getToken()) {
    return true;
  } else {
    router.navigate(['/login']).then(r => false);
    return false;
  }
};
