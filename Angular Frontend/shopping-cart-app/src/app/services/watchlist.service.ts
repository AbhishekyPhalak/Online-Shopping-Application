import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface ApiResponse {
  success: boolean;
  message: string;
  data: any | null;
}

@Injectable({
  providedIn: 'root',
})
export class WatchlistService {
  private baseUrl = 'http://localhost:8080/watchlist/product';

  constructor(private http: HttpClient) {}

  addToWatchlist(productId: number): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`${this.baseUrl}/${productId}`, null);
  }

  removeFromWatchlist(productId: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`${this.baseUrl}/${productId}`);
  }

  getAllWatchlistItems(): Observable<ApiResponse> {
  return this.http.get<ApiResponse>(`${this.baseUrl}s/all`);
  }

}
