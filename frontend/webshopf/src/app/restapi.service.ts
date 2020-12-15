import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class RestapiService {

  backendURL: string = "80.216.204.53:8090";
  //backendURL: string = "localhost:8090";


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

  public setUsername(username: string)
  {
    this.username = username;
  }

  

  public login(username:string, password:string)
  {
    this.cookieService.set("username", username);
    this.username = username;
    let object = {"username": username, "password": password};
    return this.http.post<any>("http://" + this.backendURL +"/authenticate", object, this.optionsLogin)
  }

  public jwtTest()
  {
    return this.http.get("http://" + this.backendURL + "/admintest", this.optionsJwt);
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
    return this.http.get("http://" + this.backendURL + "/item/" + search, this.optionsJwt);
  }


  public addItem(name: string, description: string, price: number)
  {
    
    let obj = {"name": name,
               "description": description,
               "price": price};

    return this.http.post("http://" + this.backendURL +"/item/", obj, this.optionsJwt);
  }

  public getBasket()
  {
    return this.http.get("http://" + this.backendURL + "/basket/" + this.username, this.optionsJwt);
  }


  public getBasketItems(basketID: string)
  {
    return this.http.get("http://" + this.backendURL + "/basketitems/" + basketID, this.optionsJwt);
  }

  public addItemToBasket(itemID:number, basketID: number, change: number)
  {
    let obj = {};
    return this.http.put("http://" + this.backendURL + "/basket/additem/" + basketID + "/" + itemID + "/" + change, obj, this.optionsJwt);
  }

  

  public makeOrder(basketID: number)
  {
    let obj = {};
    return this.http.post("http://" + this.backendURL + "/order/" + basketID, obj, this.optionsJwt);
  }

  public getOrdersNotExpediated()
  {
    return this.http.get("http://" + this.backendURL + "/orders/notexpediated", this.optionsJwt);
  }

  public expediateOrder(orderID: number)
  {
    let obj ={};
    return this.http.put("http://" + this.backendURL + "/order/expediate/" + orderID, obj, this.optionsJwt);
  }

  public createUser(username: string, password: string, email: string)
  {
    let object = {"username": username, "password": password, "email": email};
    return this.http.post<any>("http://" + this.backendURL + "/user", object, this.optionsLogin);
  }


  public isUserAdmin()
  {
    return this.http.get("http://" + this.backendURL + "/isadmin/" + this.username, this.optionsJwt)
  }

  public getOrderDetails(orderID: string)
  {
    return this.http.get("http://" + this.backendURL + "/order/getdetails/" + orderID, this.optionsJwt);
  }

  public getNewPassword(username: string)
  {
    return this.http.get("http://" + this.backendURL + "/user/forgotpassword/" + username, this.optionsLogin);
  }

  public updatePassword(username: string, newPassword: string, token: string)
  {
    let obj = {"username": username,
               "newPassword": newPassword,
               "token": token};
    return this.http.put("http://" + this.backendURL + "/user/updatepassword", obj, this.optionsLogin);
  }
}
