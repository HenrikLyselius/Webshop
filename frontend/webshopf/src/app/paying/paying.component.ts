import { Component, OnInit } from '@angular/core';
import { RestapiService } from '../restapi.service';
import { Item } from '../item';

@Component({
  selector: 'app-paying',
  templateUrl: './paying.component.html',
  styleUrls: ['./paying.component.css']
})

export class PayingComponent implements OnInit {

  
  basketItems: Array<Item>;

  constructor(private restService:RestapiService) { }

  ngOnInit(): void {
  }





  getBasketItems(basketID: number)
  {
    this.restService.getBasketItems(basketID).subscribe(
    (response: any) => { this.basketItems = JSON.parse(response.body); },
    (Error) => { this.handleError(Error); }
    );
  }


  handleError(error)
  {

  }


}
