import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;

  constructor(
    private fb: FormBuilder
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
    console.log(this.registerForm.getRawValue());
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
