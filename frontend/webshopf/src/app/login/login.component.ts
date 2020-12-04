import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { RestapiService } from '../restapi.service';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {



  @Input('username') username: string;
  @Input('password') password: string;
  showSignIn: boolean = true;
  showSignUp: boolean = false;
  showFormReset: boolean = false;
  @Output() loginStatus: EventEmitter<boolean> = new EventEmitter();
  

  constructor(private cookieService:CookieService, private restService:RestapiService, private router: Router) { }

  ngOnInit(): void {
  }

  doLogin()
  {
    this.restService.login(this.username, this.password).subscribe(
      (Response) => {this.handleLoginResponse(Response)},
      (Error) => {this.handleLoginError(Error);}

    );
  }

  handleLoginResponse(response: any)
  {
      //console.log(response.body);
      console.log(response.status);
      console.log(response.body);

     if(response.status === 200)
     {
       console.log(response.body.jwt);
       this.restService.setJwt(response.body.jwt);
       //this.loginStatus.emit(true);

       this.cookieService.set("loggedIn", "true");

       this.restService.jwtTest().subscribe(
         (Response) => { this.jwtTest(Response);}
       );

        this.router.navigateByUrl('/shopping');
        

     }
     else if(response.status === 403)
     {
       console.log("Här i erroren, vanlig respons.");
      console.log("Username or password is incorrect.");
     }

     
  }

  handleLoginError(response: any)
  {
    console.log(response);
    console.log("Här i erroren, äkta error.");
    console.log("Username or password is incorrect.");
  }

  createNewUser()
  {
    this.restService.createUser(this.username, this.password).subscribe(
      (Response) => {this.createUserResponse(Response)},
      (Error) => {this.handleLoginError(Error);}
    );
  }

  createUserResponse(response: any)
  {
    if(response.status === 201) { alert("En ny användare skapades."); }
  }
  jwtTest(response:any)
  {
    console.log(response.body)
  }

  toggleSignIn() 
  { 
    this.showSignIn = !this.showSignIn;
    this.showSignUp = !this.showSignUp;
  }

  toggleSignUp() 
  { 
    this.showSignUp = !this.showSignUp; 
  }

  toggleFormReset() 
  { 
    this.showFormReset = !this.showFormReset; 
    this.showSignIn = !this.showSignIn;
  }
}

