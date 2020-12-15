import { Component, OnInit, Input } from '@angular/core';
import { RestapiService } from '../restapi.service';
import { Item } from '../item';
import { Basket } from '../Basket'
import { Router, NavigationEnd } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-shopping',
  templateUrl: './shopping.component.html',
  styleUrls: ['./shopping.component.css']
})
export class ShoppingComponent implements OnInit {

  
  @Input('search')search: string;
  itemList: Array<Item>;
  basket: any;
  navigationSubscription;


  constructor(private restService:RestapiService, private router: Router, private cookieService: CookieService) 
  {
    this.navigationSubscription = this.router.events.subscribe((e: any) => {
      // If it is a NavigationEnd event re-initalise the component
      if (e instanceof NavigationEnd) { this.initialiseInvites(); }
    });
    this.getBasket();
  }


  initialiseInvites() {
    this.getBasket();
  }

  ngOnInit(): void {
  }


  ngOnDestroy() {
    // avoid memory leaks here by cleaning up after ourselves. If we  
    // don't then we will continue to run our initialiseInvites()   
    // method on every navigationEnd event.
    if (this.navigationSubscription) {  
       this.navigationSubscription.unsubscribe();
    }
  }

  searchItems()
  {
    this.restService.searchItems(this.search).subscribe(
      (Response) => { this.handleSearchResponse(Response); },
      (Error) => { this.handleErrorResponse(Error); 
        this.router.navigateByUrl('/login');}
    );
  }


  handleSearchResponse(response: any)
  { 
    this.itemList = JSON.parse(response.body);
  }

  handleErrorResponse(error: any)
  {
    alert("Någonting gick fel.");
  }

  getBasket()
  {
    this.restService.getBasket().subscribe(
      (Response) => { this.handleGetBasket(Response); },
      (Error) => { this.handleErrorResponse(Error); }
    );
  }


  handleGetBasket(response: any)
  {
    this.basket = JSON.parse(response.body);
  }



 
  addToBasket(item: Item)
  {
    let elem = document.getElementById(item.name);
    let change = parseInt((<HTMLInputElement>elem).value, 10);

    this.restService.addItemToBasket(item.itemID, this.basket.basketID, change).subscribe(
      (Response) => { 
        alert(change + " " + item.name + " lades i varukorgen.");
      },
      (Error) => { this.handleErrorResponse(Error); }
    )
  }



}
