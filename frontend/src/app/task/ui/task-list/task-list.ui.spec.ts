import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TaskListUi } from './task-list.ui';

describe('TaskListUi', () => {
  let component: TaskListUi;
  let fixture: ComponentFixture<TaskListUi>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaskListUi],
    }).compileComponents();

    fixture = TestBed.createComponent(TaskListUi);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
