import { Injectable } from '@angular/core';

export interface CartItem {
  productId: number;
  name: string;
  description: string;
  retailPrice: number;
  quantity: number;
}


@Injectable({
  providedIn: 'root'
})
export class CartService {
  private key = 'cart';

  getCart(): CartItem[] {
    const data = localStorage.getItem(this.key);
    return data ? JSON.parse(data) : [];
  }

  addToCart(item: CartItem): void {
    const cart = this.getCart();
    const existing = cart.find(ci => ci.productId === item.productId);

    if (existing) {
      existing.quantity += item.quantity;
    } else {
      cart.push(item);
    }

    localStorage.setItem(this.key, JSON.stringify(cart));
  }

  removeFromCart(productId: number): void {
    const updated = this.getCart().filter(item => item.productId !== productId);
    localStorage.setItem(this.key, JSON.stringify(updated));
  }

  clearCart(): void {
    localStorage.removeItem(this.key);
  }
}
