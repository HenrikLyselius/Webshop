import { Component, OnInit, Input } from '@angular/core';
import { RestapiService } from '../restapi.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {



  @Input('username') username: string;
  @Input('password') password: string;
  

  constructor(private restService:RestapiService) { }

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

       this.restService.jwtTest().subscribe(
         (Response) => { this.jwtTest(Response);}
       )
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

  jwtTest(response:any)
  {
    console.log(response.body)
  }
}
