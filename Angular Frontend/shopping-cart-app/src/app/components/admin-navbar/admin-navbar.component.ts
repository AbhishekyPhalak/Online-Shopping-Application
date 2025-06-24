import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-navbar',
  templateUrl: './admin-navbar.component.html',
})
export class AdminNavbarComponent {
  constructor(private router: Router) {}

  signOut() {
    localStorage.removeItem('jwtToken');
    this.router.navigate(['/login']);
  }
}
