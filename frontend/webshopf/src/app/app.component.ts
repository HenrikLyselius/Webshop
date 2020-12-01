import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Router, NavigationEnd } from '@angular/router';
import { RestapiService } from './restapi.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {



  loggedIn: boolean = false;
  navigationSubscription;
  userIsAdmin: boolean = false;


  constructor(private cookieService:CookieService, private router: Router, private restapiService: RestapiService)
  {
    this.navigationSubscription = this.router.events.subscribe((e: any) => {
      if (e instanceof NavigationEnd) {
        this.initialiseInvites();
      }
    });
  }


  ngOnInit()
  {

  }


  initialiseInvites() {
    this.updateUserRole();
    this.updateLoggedIn();
  }

  logOut()
  {
    this.cookieService.set("loggedIn", "false");
    this.cookieService.set("username", "x");
    this.restapiService.setUsername("x");
    this.cookieService.set("jwt", '');
    this.router.navigateByUrl('/login');
  }


  updateLoggedIn()
  {
    this.loggedIn = ("true" === this.cookieService.get("loggedIn"));
  }


  loginStatusChange(loggedIn: boolean)
  {
    this.loggedIn = loggedIn;

  }


  updateUserRole()
  {
    this.restapiService.isUserAdmin().subscribe(
      (Response) => { this.setUserRole(Response); },
      (Error) => { console.log(Error); }
    );
  }


  setUserRole(response: any)
  {
    this.userIsAdmin = (response.body === 'true');
    console.log("UserIsAdmin is set: " + this.userIsAdmin);
  }


  goToAdminPage()
  {
    this.router.navigateByUrl('/admin');
  }


  showShoppingPage()
  {
    this.router.navigateByUrl('/shopping');
  }


  showPayingPage()
  {
    this.router.navigateByUrl('/paying');
  }
}
