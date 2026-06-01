import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskItemEditableUi } from './task-item-editable.ui';

describe('TaskItemEditableUi', () => {
  let component: TaskItemEditableUi;
  let fixture: ComponentFixture<TaskItemEditableUi>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaskItemEditableUi],
    }).compileComponents();

    fixture = TestBed.createComponent(TaskItemEditableUi);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
