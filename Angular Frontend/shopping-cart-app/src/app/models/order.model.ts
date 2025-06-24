export interface Order {
  orderId: number;
  datePlaced: string;
  orderStatus: string;
}

export interface OrderResponse {
  success: boolean;
  message: string;
  data: Order[];
}

export interface OrderItem {
  itemId: number;
  productId: number;
  productName: string;
  quantity: number;
  purchasedPrice: number;
  wholesalePrice?: number;
}

export interface OrderDetails {
  orderId: number;
  datePlaced: string;
  orderStatus: string;
  items?: OrderItem[];       
  adminItems?: OrderItem[]; 
  username?: string;  
  
}

export interface OrderDetailsResponse {
  success: boolean;
  message: string;
  data: OrderDetails;
}

