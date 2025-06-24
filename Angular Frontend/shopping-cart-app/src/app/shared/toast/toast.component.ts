import { Component, OnInit } from '@angular/core';
import { ToastService } from 'src/app/services/toast.service';

@Component({
  selector: 'app-toast',
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.css']
})
export class ToastComponent implements OnInit {
  message: string | null = null;
  type: 'success' | 'error' = 'success';

  constructor(private toastService: ToastService) {}

  ngOnInit(): void {
    this.toastService.toast$.subscribe(toast => {
      this.message = toast.message;
      this.type = toast.type;

      // Auto-hide after 3 seconds
      setTimeout(() => this.message = null, 3000);
    });
  }
}
