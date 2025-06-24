import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';
import { ToastService } from 'src/app/services/toast.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-admin-edit-product',
  templateUrl: './admin-edit-product.component.html',
  styleUrls: ['./admin-edit-product.component.css']
})
export class AdminEditProductComponent implements OnInit {
  productForm!: FormGroup;
  productId!: number;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private toastService: ToastService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('id'));

    this.productForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      wholesalePrice: [0, [Validators.required, Validators.min(0)]],
      retailPrice: [0, [Validators.required, Validators.min(0)]],
      quantity: [0, [Validators.required, Validators.min(0)]]
    });

    this.productService.getProductById(this.productId).subscribe({
      next: (res) => {
        this.productForm.patchValue(res.data);
      },
      error: () => this.toastService.show('Failed to load product.', 'error')
    });
  }

  onSubmit() {
    if (this.productForm.invalid) return;

    this.productService.editProduct(this.productId, this.productForm.value).subscribe({
      next: (res) => {
        this.toastService.show(res.message, 'success');
        this.router.navigate(['/all-products']);
      },
      error: (err) => this.toastService.show(err.error.message || 'Failed to update product.', 'error')
    });
  }
}
