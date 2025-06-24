import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service'; // 👈 import the service

declare var bootstrap: any;

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  submitted = false;
  toastMessage: string = '';
  toastClass: string = '';

  @ViewChild('liveToast', { static: false }) toastRef!: ElementRef;

  constructor(private fb: FormBuilder, private authService: AuthService) {}

  ngOnInit() {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(3)]],
    });
  }

  get f() {
    return this.registerForm.controls;
  }

  showToast(message: string, type: 'success' | 'error') {
    this.toastMessage = message;
    this.toastClass = type === 'success' ? 'bg-success' : 'bg-danger';

    const toastEl = new bootstrap.Toast(this.toastRef.nativeElement);
    toastEl.show();
  }

  onSubmit() {
    this.submitted = true;
    if (this.registerForm.invalid) return;

    this.authService.register(this.registerForm.value).subscribe({
      next: (res: any) => {
        this.registerForm.reset();
        this.submitted = false;
        this.showToast(res.message || 'Registration successful!', 'success');
      },
      error: (err) => {
        this.showToast(err.error?.message || 'Registration failed!', 'error');
      }
    });
  }
}
