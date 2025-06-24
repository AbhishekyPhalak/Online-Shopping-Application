import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { ToastService } from '../services/toast.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router, private toastService: ToastService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Skip login/signup
    if (req.url.includes('/login') || req.url.includes('/signup')) {
      return next.handle(req);
    }

    const token = localStorage.getItem('jwtToken');

    let cloned = req;
    if (token) {
      cloned = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(cloned).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 403 || error.status === 401) {
          this.toastService.show('Session expired. Please login again.', 'error');
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('userRole');

        setTimeout(() => this.router.navigate(['/login']), 2000);
        }
        return throwError(() => error);
      })
    );
  }
}
