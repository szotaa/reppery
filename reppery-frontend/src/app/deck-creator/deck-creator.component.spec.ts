import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeckCreatorComponent } from './deck-creator.component';

describe('DeckCreatorComponent', () => {
  let component: DeckCreatorComponent;
  let fixture: ComponentFixture<DeckCreatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeckCreatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeckCreatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
