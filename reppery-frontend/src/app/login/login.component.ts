import { Component, OnInit } from '@angular/core';
import {User} from "../core/model/user";
import {RestService} from "../core/service/rest.service";
import {AuthService} from "../core/service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  showLoginError: boolean;
  showRegistrationSuccessMessage: boolean;

  constructor(
    private rest: RestService,
    private auth: AuthService,
    private activated: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.showRegistrationSuccessMessage = false;
    this.showLoginError = false;
    if(this.checkIfNewUser()){
      this.showRegistrationSuccessMessage = true;
    }
  }

  public onSubmit(user: User): void{
    this.rest.post<User>("auth", user).subscribe(
      response => {
        this.auth.setAuthentication(response['jwtToken']);
        this.router.navigateByUrl("/")
        },
      err => {
        if(err.status == 403){
          this.showRegistrationSuccessMessage = false;
          this.showLoginError = true;
        }
      }
    )
  }

  private checkIfNewUser(): boolean {
    this.activated.queryParams.subscribe(
      params => {
        let newUser = params['new'];
        if(newUser){
          this.showRegistrationSuccessMessage = true;
        }
      }
    );
    return false;
  }
}
