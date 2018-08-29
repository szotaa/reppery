import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from '../core/service/rest.service';
import { User } from '../core/model/user';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  showError = false;
  showEmailTaken = false;

  constructor(
    private fb: FormBuilder,
    private rest: RestService,
    private router: Router,
    private title: Title
  ) { }

  ngOnInit() {
    this.title.setTitle('Register');
    this.registerForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      passwords: this.fb.group({
        password: [null, [Validators.required, Validators.minLength(8)]],
        passwordRepeat: [null, Validators.required]
      }, {validator: this.matchValidator})
    });
  }

  public onSubmit(): void {
    const rawValue = this.registerForm.getRawValue();
    const user = new User(
      rawValue.email,
      rawValue.passwords.password
    );
    this.rest.post<User>('user', user).subscribe(
      request => {this.router.navigateByUrl('/login?new=true'); },
          err => {
        if (err.status === 409) {
          this.handleEmailTaken();
        } else {
          this.showError = true;
        }
      }
    );
  }

  private handleEmailTaken(): void {
    this.showEmailTaken = true;
  }

  private matchValidator(group: FormGroup) {
    const password = group.controls.password;
    const passwordRepeat = group.controls.passwordRepeat;

    if (password.value !== passwordRepeat.value) {
      passwordRepeat.setErrors({mismatch: true});
    }
    return null;
  }
}
