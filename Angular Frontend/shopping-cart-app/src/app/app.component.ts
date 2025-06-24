import { Component, ElementRef, ViewChild  } from '@angular/core';
import { ToastService } from './services/toast.service';

declare var bootstrap: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'shopping-cart-app';
  toastMessage = '';
  toastClass = '';

  @ViewChild('liveToast', { static: false }) toastRef!: ElementRef;

  constructor(private toastService: ToastService) {}

  ngOnInit(): void {
    this.toastService.toast$.subscribe(({ message, type }) => {
      this.toastMessage = message;
      this.toastClass = type === 'success' ? 'bg-success' : 'bg-danger';

      setTimeout(() => {
        const toastEl = new bootstrap.Toast(this.toastRef.nativeElement);
        toastEl.show();
      }, 0);
    });
  }
}
