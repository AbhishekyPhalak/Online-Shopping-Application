import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrderResponse } from '../models/order.model';
import { OrderDetailsResponse } from '../models/order.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private baseUrl = 'http://localhost:8080/orders';

  constructor(private http: HttpClient) {}

  getAllOrders(page?: number, size?: number): Observable<OrderResponse> {
    let url = `${this.baseUrl}/all`;

    if (page !== undefined && size !== undefined) {
      url += `?page=${page}&size=${size}`;
    }

    return this.http.get<OrderResponse>(url);
  }


  getOrderById(orderId: number): Observable<OrderDetailsResponse> {
    return this.http.get<OrderDetailsResponse>(`${this.baseUrl}/${orderId}`);
  }

  cancelOrder(orderId: number) {
  return this.http.patch<{ success: boolean; message: string; data: any }>(
    `http://localhost:8080/orders/${orderId}/cancel`, null
  );
}

completeOrder(orderId: number) {
  return this.http.patch<{ success: boolean; message: string; data: any }>(
    `${this.baseUrl}/${orderId}/complete`, null
  );
}


}
