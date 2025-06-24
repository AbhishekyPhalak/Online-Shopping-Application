import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-loggedin-navbar',
  templateUrl: './loggedin-navbar.component.html',
})
export class LoggedinNavbarComponent {
  constructor(private router: Router) {}

  signOut() {
    localStorage.removeItem('jwtToken');
    this.router.navigate(['/login']);    
  }
}
