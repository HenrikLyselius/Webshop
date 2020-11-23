import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { catchError } from 'rxjs/operators'
import { throwError } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class RestapiService {

  optionsLogin : any = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
      }),
      observe: "response",
      responseType: 'json'
  };

  optionsJwt : object;
  username: string;



  constructor(private http:HttpClient, private cookieService:CookieService) 
  {
    this.username = cookieService.get("username");

    this.optionsJwt = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + this.cookieService.get("jwt")
        }),
        observe: "response",
        responseType: 'text' as 'json'
    }
  }

  


  public login(username:string, password:string)
  {
    this.cookieService.set("username", username);
    console.log(username + " " + password);
    let object = {"username": username, "password": password};
    return this.http.post<any>("http://localhost:8080/authenticate", object, this.optionsLogin)
  }

  public jwtTest()
  {
    return this.http.get("http://localhost:8080/admintest", this.optionsJwt);
  }

  public setJwt(jwt:string)
  {
    this.cookieService.set("jwt", jwt);

    this.optionsJwt = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + jwt
        }),
        observe: "response",
        responseType: 'text' as 'json'
    }
  }


  public searchItems(search:string)
  {
    console.log(this.optionsJwt);
    return this.http.get("http://localhost:8080/item/" + search, this.optionsJwt);
  }

  public getBasket()
  {
    return this.http.get("http://localhost:8080/basket/" + this.username, this.optionsJwt);
  }


  public getBasketItems(basketID: number)
  {
    return this.http.get("http://localhost:8080/basketitems/" + basketID);
  }

  public addItemToBasket(itemID:number, basketID: number, change: number)
  {
    let obj = {};
    return this.http.put("http://localhost:8080/basket/additem/" + basketID + "/" + itemID + "/" + change, obj, this.optionsJwt);
  }

  public removeItemFromBasket(itemID:number, basketID: number, change: number)
  {
    let obj = {};
    return
  }
}
