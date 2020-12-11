import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RestapiService } from './restapi.service';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ShoppingComponent } from './shopping/shopping.component';
import { PayingComponent } from './paying/paying.component';
import { CookieService } from 'ngx-cookie-service';
import { AdminComponent } from './admin/admin.component';
import { ResetpasswordComponent } from './resetpassword/resetpassword.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ShoppingComponent,
    PayingComponent,
    AdminComponent,
    ResetpasswordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [RestapiService, CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
