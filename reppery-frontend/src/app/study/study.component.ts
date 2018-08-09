import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {RestService} from "../core/service/rest.service";
import {Flashcard} from "../core/model/flashcard";

@Component({
  selector: 'app-study',
  templateUrl: './study.component.html',
  styleUrls: ['./study.component.scss']
})
export class StudyComponent implements OnInit {

  deckId: number;
  flashcards: Array<Flashcard>;
  isAnswerRevealed: boolean = false;
  isEndDisplayed: boolean = false;
  currentFlashcard: Flashcard;
  index: number = 0;

  constructor(
    private route: ActivatedRoute,
    private rest: RestService
  ) { }

  ngOnInit() {
    this.deckId = this.route.snapshot.params['deckId'];
    this.rest.getAll<Flashcard[]>('revise/' + this.deckId).subscribe(
      response => {
        this.flashcards = response;}
    );
  }

  private revealAnswer(): void {
    this.isAnswerRevealed = true;
  }

  private processAnswerQuality(): void {
    //TODO: send answer quality
    this.index++;
    this.displayCard();
  }

  private displayCard(): void {
    if(this.index == this.flashcards.length){
      this.displayEnd();
      return;
    }
    this.isAnswerRevealed = false;
    this.currentFlashcard = this.flashcards[this.index];
  }

  private displayEnd(): void {
      this.isEndDisplayed = true;
  }
}
