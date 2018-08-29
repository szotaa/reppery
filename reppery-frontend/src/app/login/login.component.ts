import { Component, OnInit } from '@angular/core';
import { User } from '../core/model/user';
import { RestService } from '../core/service/rest.service';
import { AuthService } from '../core/service/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  showLoginError = false;
  showRegistrationSuccessMessage = false;
  showLogoutMessage = false;
  showAccountVerifiedMessage = false;

  constructor(
    private rest: RestService,
    private auth: AuthService,
    private activated: ActivatedRoute,
    private router: Router,
    private title: Title
  ) { }

  ngOnInit() {
    this.title.setTitle('Login');
    this.processUrlParams();
  }

  public onSubmit(user: User): void {
    this.rest.post<User>('auth', user).subscribe(
      response => {
        this.auth.setAuthentication(response['jwtToken']);
        this.router.navigateByUrl('/');
        },
      err => {
        if (err.status === 403) {
          this.hideMessages();
          this.showLoginError = true;
        }
      }
    );
  }

  private processUrlParams(): void {
    this.activated.queryParams.subscribe(
      params => {
        if (params['new']) {
          this.handleNewUser();
        } else if (params['logout']) {
          this.logout();
        } else if (params['verify']) {
          this.verifyAccount(params['verify']);
        }
      }
    );
  }

  private handleNewUser(): void {
    this.showRegistrationSuccessMessage = true;
  }

  private logout(): void {
    this.auth.removeAuthentication();
    this.showLogoutMessage = true;
  }

  private verifyAccount(verificationToken: string): void {
    this.rest.getOne('activate', verificationToken).subscribe(
      response => {
        this.showAccountVerifiedMessage = true;
      }
    );
  }

  private hideMessages(): void {
    this.showRegistrationSuccessMessage = false;
    this.showLoginError = false;
    this.showLogoutMessage = false;
  }
}
