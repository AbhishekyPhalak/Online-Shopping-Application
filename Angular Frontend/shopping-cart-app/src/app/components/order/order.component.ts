import { Component, OnInit } from '@angular/core';
import { OrderService } from 'src/app/services/order.service';
import { Order } from 'src/app/models/order.model';
import { Router } from '@angular/router';
import { ToastService } from 'src/app/services/toast.service'
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  orders: Order[] = [];
  isAdmin: boolean = false;
  page: number = 1;
  size: number = 8;
  hasMore: boolean = true;
  pages: number[] = [1];
  constructor(private orderService: OrderService, private router: Router, private toastService: ToastService) {}

  ngOnInit(): void {

    const token = localStorage.getItem('jwtToken');
      if (token) {
        const decoded = jwtDecode<any>(token);
        this.isAdmin = decoded.role !== 'user';
      }


     if (this.isAdmin) {
        this.loadOrders(); // paginated loading
      } else {
        this.orderService.getAllOrders().subscribe({ // new service method (see below)
          next: (response) => this.orders = response.data,
          error: (err) => console.error(err)
        });
      }
  }

   loadOrders(): void {
    this.orderService.getAllOrders(this.page-1, this.size).subscribe({
      next: (response) => {
        const newOrders = response.data;
        this.orders = newOrders;
        this.hasMore = newOrders.length === this.size;

        // update pages only if we got full results
        if (this.hasMore && !this.pages.includes(this.page + 1)) {
          this.pages.push(this.page + 1);
        }
      },
      error: (err) => console.error(err)
    });
  }

  goToPage(p: number): void {
  this.page = p;
  this.loadOrders();
}
  

  loadNextPage() {
    if (this.hasMore) {
      this.page++;
      this.loadOrders();
    }
  }

  viewOrder(orderId: number) {
    this.router.navigate(['/orders', orderId]);
  }

  cancelOrder(orderId: number) {
    this.orderService.cancelOrder(orderId).subscribe({
      next: (res) => {
        this.toastService.show(res.message, 'success');
        const order = this.orders.find(o => o.orderId === orderId);
        if (order) order.orderStatus = 'Canceled';
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

    completeOrder(orderId: number) {
      this.orderService.completeOrder(orderId).subscribe({
        next: (res) => {
          this.toastService.show(res.message, 'success');
          const order = this.orders.find(o => o.orderId === orderId);
          if (order) order.orderStatus = 'Completed';
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