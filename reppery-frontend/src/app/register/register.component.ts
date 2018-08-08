import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {RestService} from "../core/service/rest.service";
import {User} from "../core/model/user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  showError: boolean = false;

  constructor(
    private fb: FormBuilder,
    private rest: RestService,
    private router: Router
  ) { }

  ngOnInit() {
    this.registerForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      passwords: this.fb.group({
        password: [null, [Validators.required, Validators.minLength(8)]],
        passwordRepeat: [null, Validators.required]
      },{validator: this.matchValidator})
    });
  }

  public onSubmit(): void {
    let rawValue = this.registerForm.getRawValue();
    let user = new User(
      null,
      rawValue.email,
      rawValue.passwords.password
    );
    this.rest.post<User>("user", user).subscribe(
      success => {this.router.navigateByUrl('/login?new=true');},
          err => {this.showError = true;}
    )
  }

  private matchValidator(group: FormGroup){
    let password = group.controls.password;
    let passwordRepeat = group.controls.passwordRepeat;

    if(password.value !== passwordRepeat.value){
      passwordRepeat.setErrors({mismatch: true})
    }
    return null;
  }
}
