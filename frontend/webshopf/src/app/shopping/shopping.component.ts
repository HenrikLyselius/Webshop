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
      if (e instanceof NavigationEnd) {
        this.initialiseInvites();
      }
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
    this.restService.jwtTest().subscribe(
      (Response) => { console.log(Response); }
    );

    this.restService.searchItems(this.search).subscribe(
      (Response) => { this.handleSearchResponse(Response); },
      (Error) => { this.handleErrorResponse(Error); 
        console.log("Omrouting.");
        this.router.navigateByUrl('/login');}
    );
  }


  handleSearchResponse(response: any)
  { console.log(response.body);
    this.itemList = JSON.parse(response.body);
    console.log(this.itemList);
    //this.generateHtmlList();
  }

  generateHtmlList()
  {
    document.getElementById('searchResults').innerHTML = '';

    let ul = document.createElement('ul');
    

    document.getElementById('searchResults').appendChild(ul);
    
      for(let i = 0; i < this.itemList.length; i++)
      {
        let li = document.createElement('li');
        li.innerHTML = "item: " + this.itemList[i].name + "     description: " + this.itemList[i].description;
        ul.appendChild(li);
      }
     
      
    }
  



    clearlist (){ 
      let elem = document.getElementById('ul');
      elem.parentNode.removeChild(elem);
    } 

  handleErrorResponse(error: any)
  {

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
    console.log(response.body);
    this.basket = JSON.parse(response.body);
    console.log("Basket: " + this.basket);
    console.log(this.basket.basketID);
  }



 
  addToBasket(item: Item)
  {
    let elem = document.getElementById(item.name);
    let change = parseInt((<HTMLInputElement>elem).value, 10);

    this.restService.addItemToBasket(item.itemID, this.basket.basketID, change).subscribe(
      (Response) => { 
        console.log(Response);
        alert(change + " " + item.name + " lades i varukorgen.");
      },
      (Error) => { this.handleErrorResponse(Error); }
    )
  }

  /* logOut()
  {
    this.cookieService.set("username", "x");
    this.restService.setUsername("x");
    this.cookieService.set("jwt", '');
    this.router.navigateByUrl('/login');
  } */
}
