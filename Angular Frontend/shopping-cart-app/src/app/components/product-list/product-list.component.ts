import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';
import { CartService } from 'src/app/services/cart.service';
import { WatchlistService } from 'src/app/services/watchlist.service';
import { ToastService } from 'src/app/services/toast.service';
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  isAdmin: boolean = false;
 newProduct = {
  name: '',
  description: '', 
  retailPrice: 0,
  wholesalePrice: 0,
  quantity: 0
};



  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private watchlistService: WatchlistService,
    private toastService: ToastService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    const token = localStorage.getItem('jwtToken');
      if (token) {
        const decoded = jwtDecode<any>(token);
        this.isAdmin = decoded.role !== 'user';
      }

    this.productService.getAllProducts().subscribe({
      next: (res) => this.products = res.data,
      error: () => this.toastService.show('Failed to load products.', 'error')
    });
  }


  viewProduct(id: number) {
    this.router.navigate(['/products', id]);
  }

  editProduct(productId: number) {
    this.router.navigate(['/admin/products', productId, 'edit']);
  }

    submitNewProduct() {
    this.productService.addProduct(this.newProduct).subscribe({
      next: (res) => {
        this.toastService.show('Product added successfully!', 'success');
        this.products.push({ ...this.newProduct, productId: res.data.productId }); 
        this.newProduct = { name: '', retailPrice: 0, description: '', wholesalePrice: 0, quantity: 0 };
        const modal = document.getElementById('addProductModal');
        if (modal) {
          (modal as any).classList.remove('show');
          document.body.classList.remove('modal-open');
          modal.setAttribute('style', 'display: none;');
          document.querySelector('.modal-backdrop')?.remove();
        }
      },
      error: () => this.toastService.show('Failed to add product.', 'error')
    });
  }

  addToCart(product: Product) {
    this.cartService.addToCart({ ...product, quantity: 1 });
    this.toastService.show('Added to cart!', 'success');
  }

  addToWatchlist(productId: number) {
    this.watchlistService.addToWatchlist(productId).subscribe({
      next: res => this.toastService.show(res.message, 'success'),
      error: () => this.toastService.show('Failed to add to watchlist.', 'error')
    });
  }
}
