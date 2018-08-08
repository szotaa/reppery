import { Component, OnInit } from '@angular/core';
import {RestService} from "../core/service/rest.service";
import {Deck} from "../core/model/deck";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  decks: Array<Deck>;

  constructor(
    private rest: RestService
  ) { }

  ngOnInit() {
    this.rest.getAll<Deck[]>('deck').subscribe(
      response => {
        this.decks = response;
      },
      err => {
        console.log('idk');
      }
    )
  }

}
