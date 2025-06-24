export interface Product {
  productId: number;
  name: string;
  description: string;
  retailPrice: number;
  quantity?: number;
  wholesalePrice?: number;

}

export interface ProductResponse {
  success: boolean;
  message: string;
  data: Product;
}

export interface ProductListResponse {
  success: boolean;
  message: string;
  data: Product[];
}
