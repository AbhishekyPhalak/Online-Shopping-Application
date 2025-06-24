import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule, FormsModule  } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UnloginNavbarComponent } from './components/unlogin-navbar/unlogin-navbar.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './interceptors/auth.interceptors';
import { HomeComponent } from './components/home/home.component';
import { AdminHomeComponent } from './components/admin-home/admin-home.component';
import { OrderComponent } from './components/order/order.component';
import { ViewOrderComponent } from './components/view-order/view-order.component';
import { ToastComponent } from './shared/toast/toast.component';
import { ProductComponent } from './components/product/product.component';
import { CartComponent } from './components/cart/cart.component';
import { LoggedinNavbarComponent } from './components/loggedin-navbar/loggedin-navbar.component';
import { WatchlistComponent } from './components/watchlist/watchlist.component';
import { FrequentProductsComponent } from './components/frequent-products/frequent-products.component';
import { RecentProductsComponent } from './components/recent-products/recent-products.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import { AdminNavbarComponent } from './components/admin-navbar/admin-navbar.component';
import { AdminProfitableProductsComponent } from './components/admin-profitable-products/admin-profitable-products.component';
import { AdminPopularProductsComponent } from './components/admin-popular-products/admin-popular-products.component';
import { AdminEditProductComponent } from './components/admin-edit-product/admin-edit-product.component';
import { AdminTotalSoldProductsComponent } from './components/admin-total-sold-products/admin-total-sold-products.component';
import { AdminOrdersComponent } from './components/admin-orders/admin-orders.component';

@NgModule({
  declarations: [
    AppComponent,
    UnloginNavbarComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    AdminHomeComponent,
    OrderComponent,
    ViewOrderComponent,
    ToastComponent,
    ProductComponent,
    CartComponent,
    LoggedinNavbarComponent,
    WatchlistComponent,
    FrequentProductsComponent,
    RecentProductsComponent,
    ProductListComponent,
    AdminNavbarComponent,
    AdminProfitableProductsComponent,
    AdminPopularProductsComponent,
    AdminEditProductComponent,
    AdminTotalSoldProductsComponent,
    AdminOrdersComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    RouterModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
