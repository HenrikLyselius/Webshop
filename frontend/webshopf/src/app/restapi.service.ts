import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators'
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RestapiService {


  jwt:string = null;

  options : any = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
      }),
      observe: "response",
      responseType: 'json'
  };

  options2 : any = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': 'Bearer ' + this.jwt
      }),
      observe: "response",
      responseType: 'text' as 'json'
  };

  constructor(private http:HttpClient) { }


  public login(username:string, password:string)
  {

    console.log(username + " " + password);
    let object = {"username": username, "password": password};
    const headers = new HttpHeaders({})
    return this.http.post<any>("http://localhost:8080/authenticate", object, this.options)
  }

  public jwtTest()
  {
    let options3 : any = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + this.jwt
        }),
        observe: "response",
        responseType: 'text' as 'json'
    }
    return this.http.get("http://localhost:8080/admintest", options3);
  }

  public setJwt(jwt:string)
  {
    this.jwt = jwt;
  }
}
