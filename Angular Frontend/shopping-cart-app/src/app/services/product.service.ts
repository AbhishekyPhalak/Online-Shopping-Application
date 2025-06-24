import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductListResponse, ProductResponse } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseUrl = 'http://localhost:8080/products';

  constructor(private http: HttpClient) {}

  getProductById(productId: number): Observable<ProductResponse> {
    return this.http.get<ProductResponse>(`${this.baseUrl}/${productId}`);
  }

  getTopFrequentProducts(count: number): Observable<ProductListResponse> {
    return this.http.get<ProductListResponse>(`${this.baseUrl}/frequent/${count}`);
  }

  getTopRecentProducts(count: number): Observable<ProductListResponse> {
    return this.http.get<ProductListResponse>(`${this.baseUrl}/recent/${count}`);
  }

  getAllProducts(): Observable<ProductListResponse> {
  return this.http.get<ProductListResponse>(`${this.baseUrl}/all`);
}

  getTopProfitableProducts(count: number): Observable<ProductListResponse> {
    return this.http.get<ProductListResponse>(`${this.baseUrl}/profit/${count}`);
  }

  getTopPopularProducts(count: number): Observable<ProductListResponse> {
    return this.http.get<ProductListResponse>(`${this.baseUrl}/popular/${count}`);
  }

  editProduct(productId: number, payload: any) {
  return this.http.patch<{ success: boolean; message: string; data: any }>(
    `${this.baseUrl}/${productId}`, payload
  );
}

  getTotalSoldProducts() {
  return this.http.get<{ success: boolean; message: string; data: number }>(
    `${this.baseUrl}/totalSold`
  );
}

addProduct(product: any) {
  return this.http.post<{ success: boolean; message: string; data: any }>(
    `${this.baseUrl}`, product
  );
}



}
