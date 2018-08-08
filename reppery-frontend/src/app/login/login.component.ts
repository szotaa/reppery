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

  showLoginError: boolean = false;
  showRegistrationSuccessMessage: boolean = false;
  showLogoutMessage: boolean = false;

  constructor(
    private rest: RestService,
    private auth: AuthService,
    private activated: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.processUrlParams();
  }

  public onSubmit(user: User): void{
    this.rest.post<User>("auth", user).subscribe(
      response => {
        this.auth.setAuthentication(response['jwtToken']);
        this.router.navigateByUrl("/")
        },
      err => {
        if(err.status == 403){
          this.hideMessages();
          this.showLoginError = true;
        }
      }
    )
  }

  private processUrlParams(): void {
    this.activated.queryParams.subscribe(
      params => {
        if(params['new']){
          this.handleNewUser();
        } else if (params['logout']){
          this.logout();
        }
      }
    );
  }

  private handleNewUser(): void {
    this.showRegistrationSuccessMessage = true;
  }

  private logout(): void {
    console.log('logout');
    this.auth.removeAuthentication();
    this.showLogoutMessage = true;
  }

  private hideMessages(): void {
    this.showRegistrationSuccessMessage = false;
    this.showLoginError = false;
    this.showLogoutMessage = false;
  }
}
