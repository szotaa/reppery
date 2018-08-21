import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {StudyComponent} from "./study/study.component";
import {FlashcardCreatorComponent} from "./flashcard-creator/flashcard-creator.component";
import {DeckCreatorComponent} from "./deck-creator/deck-creator.component";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'study/:deckId', component: StudyComponent},
  {path: 'add', component: DeckCreatorComponent},
  {path: 'add/:deckId', component: FlashcardCreatorComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule { }
