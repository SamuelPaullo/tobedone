import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskBoardFeat } from './task-board.feat';

describe('TaskBoardFeat', () => {
  let component: TaskBoardFeat;
  let fixture: ComponentFixture<TaskBoardFeat>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaskBoardFeat],
    }).compileComponents();

    fixture = TestBed.createComponent(TaskBoardFeat);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
