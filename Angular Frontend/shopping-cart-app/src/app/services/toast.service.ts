import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ToastService {
  private toastSubject = new Subject<{ message: string; type: 'success' | 'error' }>();
  toast$ = this.toastSubject.asObservable();

  show(message: string, type: 'success' | 'error' = 'error') {
    this.toastSubject.next({ message, type });
  }
}
