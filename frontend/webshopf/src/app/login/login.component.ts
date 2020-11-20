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
      (Response) => {this.handleLoginResponse(Response)}
    );
  }

  handleLoginResponse(response: any)
  {
     if(response.status === 200)
     {
       console.log(response.body.jwt);
       this.restService.setJwt(response.body.jwt);

       this.restService.jwtTest().subscribe(
         (Response) => { this.jwtTest(Response);}
       )


     }
  }



  jwtTest(response:any)
  {
    console.log(response.body)
  }
}
