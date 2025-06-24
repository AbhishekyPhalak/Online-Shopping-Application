import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderDetails } from 'src/app/models/order.model';
import { OrderService } from 'src/app/services/order.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ToastService } from 'src/app/services/toast.service'
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-view-order',
  templateUrl: './view-order.component.html',
  styleUrls: ['./view-order.component.css']
})
export class ViewOrderComponent implements OnInit {
  order!: OrderDetails;
  errorMessage: string | null = null;
  isAdmin: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private toastService: ToastService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    const token = localStorage.getItem('jwtToken');
      if (token) {
        const decoded = jwtDecode<any>(token);
        this.isAdmin = decoded.role !== 'user';
      }

    const orderId = Number(this.route.snapshot.paramMap.get('id'));
    this.orderService.getOrderById(orderId).subscribe({
      next: (res) => {
        this.order = res.data;
        console.log(res.data)
      },
      error: (err: HttpErrorResponse) => {
        console.error(err)
        if (err.status === 404) {
          this.toastService.show('Order not found.', 'error');
          this.router.navigate(['/orders']);
        } else if (err.status === 403) {
          this.toastService.show('You are not authorized to view this order.', 'error');
          this.router.navigate(['/orders']);
        } else {
          this.toastService.show('Something went wrong. Please try again later.', 'error');
          this.router.navigate(['/orders']);
        }
      }
    });
  }

  viewProduct(productId: number) {
    console.log('View product', productId);
    this.router.navigate(['/products', productId]);
  }

  cancelOrder(orderId: number) {
    this.orderService.cancelOrder(orderId).subscribe({
      next: (res) => {
        this.toastService.show(res.message, 'success');
        // Optionally update UI without refresh
        this.order.orderStatus = 'Canceled';
      },
      error: (err: HttpErrorResponse) => {
        if (err.status === 400) {
          this.toastService.show(err.error.message || 'Cannot cancel order.', 'error');
        } else {
          this.toastService.show('Something went wrong. Please try again.', 'error');
        }
      }
    });

    
}

completeOrder(orderId: number) {
      this.orderService.completeOrder(orderId).subscribe({
        next: (res) => {
          this.toastService.show(res.message, 'success');
        },
        error: (err) => {
        if (err.status === 400) {
          this.toastService.show(err.error.message || 'Cannot cancel order.', 'error');
        } else {
          this.toastService.show('Something went wrong. Please try again.', 'error');
        }
      }
      });
  }


}
