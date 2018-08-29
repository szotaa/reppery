import { Component, OnInit } from '@angular/core';
import { RestService } from '../core/service/rest.service';
import { Deck } from '../core/model/deck';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-deck-creator',
  templateUrl: './deck-creator.component.html',
  styleUrls: ['./deck-creator.component.scss']
})
export class DeckCreatorComponent implements OnInit {

  constructor(
    private rest: RestService,
    private title: Title
  ) { }

  ngOnInit() {
    this.title.setTitle('Create new deck');
  }

  public onSubmit(deck: Deck): void {
    this.rest.post<Deck>('deck', deck).subscribe(
      response => {}
    );
  }
}
