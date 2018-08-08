import { Component, OnInit } from '@angular/core';
import {User} from "../core/model/user";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  public onSubmit(user: User): void{
    console.log(user);
  }
}
