import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TaskItemUi } from './';

describe('TaskItemUi', () => {
  let component: TaskItemUi;
  let fixture: ComponentFixture<TaskItemUi>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaskItemUi],
    }).compileComponents();

    fixture = TestBed.createComponent(TaskItemUi);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
