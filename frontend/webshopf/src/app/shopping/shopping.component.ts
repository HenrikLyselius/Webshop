import { Component, OnInit, Input } from '@angular/core';
import { RestapiService } from '../restapi.service';
import { Item } from '../item';
import { Basket } from '../Basket'

@Component({
  selector: 'app-shopping',
  templateUrl: './shopping.component.html',
  styleUrls: ['./shopping.component.css']
})
export class ShoppingComponent implements OnInit {


  
  @Input('search')search: string;

  
  itemList: Array<Item>;
  basket: any;

  constructor(private restService:RestapiService) 
  {
    this.getBasket();
  }

  ngOnInit(): void {
  }


  searchItems()
  {
    this.restService.jwtTest().subscribe(
      (Response) => { console.log(Response);}
    );

    this.restService.searchItems(this.search).subscribe(
      (Response) => { this.handleSearchResponse(Response); },
      (Error) => { this.handleErrorResponse(Error); }
    )
  }


  handleSearchResponse(response: any)
  { console.log(response.body);
    this.itemList = JSON.parse(response.body);
    console.log(this.itemList);
    //this.generateHtmlList();
  }

  generateHtmlList()
  {
    console.log(this.itemList);
    console.log(this.itemList[0]);
    console.log(this.itemList[0].name);


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
    )
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
    console.log(elem);
    let change = parseInt((<HTMLInputElement>elem).value, 10);
    console.log(change);

    console.log("I addToBasket: ItemID " + item.itemID + " basketID: " + this.basket.basketID);

    this.restService.addItemToBasket(item.itemID, this.basket.basketID, change).subscribe(
      (Response) => { console.log(Response); },
      (Error) => { this.handleErrorResponse(Error); }
    )
  }
}
