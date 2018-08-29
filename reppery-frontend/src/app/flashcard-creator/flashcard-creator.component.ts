import { Component, OnInit } from '@angular/core';
import { RestService } from '../core/service/rest.service';
import { ActivatedRoute } from '@angular/router';
import { Flashcard } from '../core/model/flashcard';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-flashcard-creator',
  templateUrl: './flashcard-creator.component.html',
  styleUrls: ['./flashcard-creator.component.scss']
})
export class FlashcardCreatorComponent implements OnInit {

  private deckId: number;

  constructor(
    private rest: RestService,
    private route: ActivatedRoute,
    private title: Title
  ) { }

  ngOnInit() {
    this.title.setTitle('Add new flashcard');
    this.deckId = this.route.snapshot.params['deckId'];
  }

  public onSubmit(flashcard: Flashcard): void {
    this.rest.post<Flashcard>('flashcard/' + this.deckId, flashcard).subscribe(
      response => {}
    );
  }
}
