import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

declare var bootstrap: any;

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  toastMessage: string = '';
  toastClass: string = '';
  submitted = false;
  loginForm!: FormGroup;

  @ViewChild('liveToast', { static: false }) toastRef!: ElementRef;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(3)]],
    });
  }

  showToast(message: string, type: 'success' | 'error') {
    this.toastMessage = message;
    this.toastClass = type === 'success' ? 'bg-success' : 'bg-danger';

    const toastEl = new bootstrap.Toast(this.toastRef.nativeElement);
    toastEl.show();
  }

  onSubmit() {
    this.submitted = true;
    if (this.loginForm.invalid) return;

    this.authService.login(this.loginForm.value).subscribe({
      next: (res: any) => {
        this.loginForm.reset();
        this.submitted = false;
        this.showToast(res.message || 'Login successful!', 'success');

        const token = res.data.token;
        localStorage.setItem('jwtToken', token);
        localStorage.setItem('userRole', res.data.userRole);

        if (res.data.userRole === "user") {
          this.router.navigate(['/home']);
        } else {
          this.router.navigate(['/admin-home']);
        }
      },
      error: (err) => {
        this.showToast(err.error?.message || 'Login failed!', 'error');
      }
    });
  }

  get f() {
    return this.loginForm.controls;
  }

}
