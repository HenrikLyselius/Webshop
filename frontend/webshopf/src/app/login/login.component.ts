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
  @Input('newUsername') newUsername: string = "";
  @Input('email') email: string = "";
  @Input('newPassword') newPassword: string = "";
  @Input('confirmNewPassword') confirmNewPassword: string = "";
  @Input('usernameResetPassword') usernameResetPassword = "";
  

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
       alert("Användarnamnet eller lösenordet är fel.");
     }

     
  }

  handleLoginError(response: any)
  {
    console.log(response);
    alert("Användarnamnet eller lösenordet är fel.");
  }

  createNewUser()
  {
    this.restService.createUser(this.newUsername, this.newPassword, this.email).subscribe(
      (Response) => {this.createUserResponse(Response)},
      (Error) => {this.handleSignUpError(Error);}
    );
  }

  handleSignUpError(error: any)
  {
    if(error.status === 409)
    {
      alert("Användarnamnet är redan taget.");
    }
  }

  createAccount()
  {
    if(this.allIsFilledInCorrectly())
    {
      this.createNewUser()
    }
  }

  allIsFilledInCorrectly()
  {
      console.log(this.newUsername.length, this.email.length, this.newPassword.length, this.confirmNewPassword.length);

    if(this.newUsername.length == 0 || this.email.length == 0 || this.newPassword.length == 0 || this.confirmNewPassword.length == 0)
    {
      alert("Alla fälten måste fyllas i.");
      return false;
    }

    if(this.newPassword !== this.confirmNewPassword)
    {
      alert("Du har angett två olika lösenord.")
      return false;
    }

    if(this.newPassword.length < 5)
    {
      alert("Lösenordet måste vara minst sex symboler långt.");
      return false;
    }

    if(!this.emailIsVAlid(this.email))
    {
      alert("Du har inte angett en giltig epost-adress.");
      return false;
    }

    return true;
  }


  emailIsVAlid(email: string)
  {
    return /\S+@\S+\.\S+/.test(email);
  }


  getNewPassword()
  {
    this.restService.getNewPassword(this.usernameResetPassword).subscribe(
      (Response) => {this.handleGetNewPasswordResponse(Response)},
      (Error) => {console.log(Error);}
    );
  }

  handleGetNewPasswordResponse(response: any)
  {
    if(response.status === 200)
    {
      alert("Ett mejl med ett nytt lösenord har skickats till det konto som är kopplat till användarnamnet.");
    }
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

