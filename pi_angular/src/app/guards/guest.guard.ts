import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../services/user/auth.service';

@Injectable({
  providedIn: 'root'
})
export class guestGuard implements CanActivate {
  static canActivate(arg0: RouterStateSnapshot | ActivatedRouteSnapshot): any {
    throw new Error('Method not implemented.');
  }

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    if (this.authService.isLoggedIn()) {
      // Redirect authenticated users to the dashboard
      this.router.navigate(['/dashboard']);
      return false;
    }
    return true;
  }
}
