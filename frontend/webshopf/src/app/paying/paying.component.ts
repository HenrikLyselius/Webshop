import { Component, OnInit } from '@angular/core';
import { RestapiService } from '../restapi.service';
import { Item } from '../item';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-paying',
  templateUrl: './paying.component.html',
  styleUrls: ['./paying.component.css']
})

export class PayingComponent implements OnInit {


  basket: any;
  basketItems: Array<any> = [];
  itemListToShow: any;
  


  constructor(private restService:RestapiService, private cookieService: CookieService, private router: Router) 
  { 
    this.getBasket();
  }

  ngOnInit(): void {
  }


  getBasket()
  {
    this.restService.getBasket().subscribe(
      (response: any) => { 
        this.basket = JSON.parse(response.body); 
        this.getBasketItems(this.basket.basketID);

        console.log(this.basket.items);

      },
      (Error) => { this.handleError(Error); }
      );
  }


  getBasketItems(basketID: string)
  {

    this.restService.getBasketItems(basketID).subscribe(
    (response: any) => { this.basketItems = JSON.parse(response.body); 
      console.log(this.basketItems);
    },
    (Error) => { this.handleError(Error); }
    );
  }

 


  handleError(error)
  {

  }

  addToBasket(item: Item, change: number)
  {
        console.log("I addToBasket: ItemID " + item.itemID + " basketID: " + this.basket.basketID);

    this.restService.addItemToBasket(item.itemID, this.basket.basketID, change, item.name).subscribe(
      (Response) => { this.handleAddItemResponse(Response); },
      (Error) => { 
        console.log(Error);
        this.router.navigateByUrl('/login'); }
    )
  }

  handleAddItemResponse(response: any)
  {
    if(response.status === 200)
    {
      this.getBasket();
    }
  }

  makeOrder()
  {
    this.restService.makeOrder(this.basket.basketID).subscribe( 
      (Response) => { this.handleMakeOrderResponse(Response); },
      (Error) => { console.log((Error)); }
    );
  }

  handleMakeOrderResponse(response: any)
  {
    if(response.status === 200)
    {
      alert("Ditt köp är genomfört!!");
      this.getBasket();
    }
  }


  getOrdersNotExpediated()
  {
    this.restService.getOrdersNotExpediated().subscribe(
      (Response) => { console.log(Response); },
      (Error) => { console.log((Error)); }
    );
  }

}
