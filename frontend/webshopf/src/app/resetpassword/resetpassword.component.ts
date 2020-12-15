import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RestapiService } from '../restapi.service';

@Component({
  selector: 'app-resetpassword',
  templateUrl: './resetpassword.component.html',
  styleUrls: ['./resetpassword.component.css']
})
export class ResetpasswordComponent implements OnInit {

  token: string = "";
  @Input('newPassword') newPassword: string;
  @Input('newPassword2') newPassword2: string;
  username: string = "";

  constructor(private restService:RestapiService, private router: Router, private route: ActivatedRoute) { }

 
  ngOnInit(): void {
    this.token = this.route.snapshot.paramMap.get("token");
    this.username = this.route.snapshot.paramMap.get("username");
  }


  updatePassword()
  {
    if(this.newPassword === this.newPassword2)
    {
      this.restService.updatePassword(this.username, this.newPassword, this.token).subscribe(
        (Response) => { 
          alert("Lösenordet är nu uppdaterat.");
          this.router.navigateByUrl('/login');
        },
        (Error) => { alert("Något gick fel, prova att begära en ny uppdateringslänk."); }
      );
    }
    else { alert("Lösenorden är inte identiska."); }
  }


}
