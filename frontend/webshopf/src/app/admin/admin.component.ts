import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { RestapiService } from '../restapi.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})

export class AdminComponent implements OnInit {

  
  orderList: Array<any>
  @Input('name')name: string;
  @Input('description')description: string;


  constructor(private restService:RestapiService, private cookieService: CookieService, private router: Router) { }

  ngOnInit(): void {
  }




  showOrders()
  {
    this.restService.getOrdersNotExpediated().subscribe(
      (Response) => { this.handleOrders(Response); },
      (Error) => { console.log(Error); }
    );
  }

  handleOrders(response: any)
  {
    this.orderList = JSON.parse(response.body);
  }



  expediateOrder(order: any)
  {
    this.restService.expediateOrder(order.orderID).subscribe(
      (Response) => { this.showOrders(); },
      (Error) => { console.log(Error); }
    );
  }



  addItem()
  {
    this.restService.addItem(this.name, this.description).subscribe(
      (response: any) => { if(response.status === 201) {alert("Varan är tillagd.")}; },
      (Error) => { console.log(Error); }
  );
  }
}
