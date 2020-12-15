import { getLocaleEraNames } from '@angular/common';
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
  @Input('price')price: number;


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
    let obj = JSON.parse(response.body);
    this.orderList = obj.orderList;
  }



  expediateOrder(order: any)
  {
    this.restService.expediateOrder(order.orderID).subscribe(
      (Response) => { this.showOrders(); },
      (Error) => { console.log(Error); }
    );
  }



 
  toggleOrderDetails(orderID: any)
  {
    if(document.getElementById(orderID).getAttribute("style") != "display:block;")
    {
      document.getElementById(orderID).setAttribute("style", "display:block;");
      document.getElementById("details" + orderID).innerHTML = "Dölj detaljer";
    }
    else {
      document.getElementById(orderID).setAttribute("style", "display:none;");
      document.getElementById("details" + orderID).innerHTML = "Visa detaljer";
    }
  }


  addItem()
  {
    if(this.allIsFilledInCorrectly())
    {
      this.restService.addItem(this.name, this.description, this.price).subscribe(
        (response: any) => { if(response.status === 201) {alert("Varan är tillagd.")}; },
        (Error) => { if(Error.status === 409) {alert("En vara med det namnet finns redan.")}; }
      );
    }
    
  }

  allIsFilledInCorrectly()
  {
    if(this.name.length == 0 || this.description.length == 0 || this.price === null )
    {
      alert("Alla fälten måste fyllas i.");
      return false;
    }

    if(isNaN(this.price))
    {
      alert("Priset måste anges med siffror.");
      return false;
    }

    return true;
  }
}
