import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RestService } from '../core/service/rest.service';
import { Flashcard } from '../core/model/flashcard';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-study',
  templateUrl: './study.component.html',
  styleUrls: ['./study.component.scss']
})
export class StudyComponent implements OnInit {

  deckId: number;
  flashcards: Array<Flashcard>;
  isAnswerRevealed = false;
  currentFlashcard: Flashcard;
  index = 0;

  constructor(
    private route: ActivatedRoute,
    private rest: RestService,
    private router: Router,
    private title: Title
  ) { }

  ngOnInit() {
    this.title.setTitle('Study');
    this.deckId = this.route.snapshot.params['deckId'];
    this.rest.getAll<Flashcard[]>('revise/' + this.deckId).subscribe(
      response => {
        this.flashcards = response;
        this.displayCard();
        }
    );
  }

  public revealAnswer(): void {
    this.isAnswerRevealed = true;
  }

  public processAnswerQuality(answerQuality: number): void {
    this.sendAnswerQuality(answerQuality);
    this.index++;
    this.displayCard();
  }

  private displayCard(): void {
    if (this.index === this.flashcards.length) {
      this.end();
      return;
    }
    this.isAnswerRevealed = false;
    this.currentFlashcard = this.flashcards[this.index];
  }

  private end(): void {
      this.router.navigateByUrl('/dashboard');
  }

  private sendAnswerQuality(answerQuality: number): void {
    this.rest.post('revise/' + this.currentFlashcard.id, answerQuality)
      .subscribe(
        response => {}
      );
  }
}
