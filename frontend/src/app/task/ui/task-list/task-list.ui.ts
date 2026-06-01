import { Component, ElementRef, input, output, signal, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { Task, TaskList, TransientTask } from '../../model';
import {
  TaskListTitleUpdatedOutput,
  TaskUpdateOutput,
  NewTaskRequestOutput,
  NewTaskSkippedOutput,
  NewTaskConfirmedOutput,
} from './ouput';
import {
  TaskItemUi,
  ArchiveTaskOutput,
  ToggleTaskCompletionOutput,
  TaskTitleEditRequestOutput,
} from '../task-item';
import { TaskEditCanceledOutput, TaskEditCompletedOutput, TaskItemEditableUi } from '../task-item-editable';

@Component({
  selector: 'task-list-ui',
  imports: [TaskItemUi, TaskItemEditableUi, MatIcon, MatButtonModule],
  templateUrl: './task-list.ui.html',
  styleUrl: './task-list.ui.scss',
})
export class TaskListUi {
  readonly taskList = input.required<TaskList>();
  readonly editingTaskId = signal<string | null>(null);

  readonly onTaskListTitleUpdated = output<TaskListTitleUpdatedOutput>();
  readonly onTaskTitleUpdated = output<TaskUpdateOutput<TaskEditCompletedOutput>>();
  readonly onToggleTaskCompletion = output<TaskUpdateOutput<ToggleTaskCompletionOutput>>();
  readonly onTaskArchived = output<TaskUpdateOutput<ArchiveTaskOutput>>();
  readonly onNewTaskRequested = output<NewTaskRequestOutput>();
  readonly onNewTaskSkipped = output<NewTaskSkippedOutput>();
  readonly onNewTaskConfirmed = output<NewTaskConfirmedOutput>();

  protected isEditingTitle = signal(false);
  protected isMouseOverTitle = signal(false);

  @ViewChild('taskListTitleInput')
  private taskListTitleInputRef!: ElementRef<HTMLInputElement>;

  /*******************************
   * TITLE LIST EDITION HANDLERS *
   *******************************/
  protected startEditingListTitle() {
    this.isEditingTitle.set(true);
  }

  protected cancelEditingListTitle() {
    this.isEditingTitle.set(false);
  }

  protected handleListTitleInputBlur() {
    if (this.isEditingTitle()) {
      this.finishEditingListTitle();
    }
  }

  protected handleListTitleInputKeydown(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.finishEditingListTitle();
    } else if (event.key === 'Escape') {
      this.cancelEditingListTitle();
    }
  }

  private finishEditingListTitle() {
    const newTitle = this.taskListTitleInputRef.nativeElement.value.trim();
    if (newTitle !== this.taskList().title) {
      this.onTaskListTitleUpdated.emit({
        taskListId: this.taskList().id,
        newTitle,
      });
    }
    this.isEditingTitle.set(false);
  }

  private requestFocusInTitleInput() {
    if (this.taskListTitleInputRef) {
      const input = this.taskListTitleInputRef.nativeElement;
      input.focus();
    }
  }

  /***************************
   * ANGULAR LIFECYCLE HOOKS *
   ***************************/
  protected ngAfterViewChecked() {
    if (this.isEditingTitle()) {
      this.requestFocusInTitleInput();
    }
  }

  /*********************
   * ADD TASK HANDLERS *
   *********************/
  protected handleAddTaskButtonClick() {
    this.onNewTaskRequested.emit({ taskListId: this.taskList().id });
  }

  protected handleNewTaskSkipped(output: TaskEditCanceledOutput) {
    this.onNewTaskSkipped.emit({ taskListId: this.taskList().id });
  }

  protected handleNewTaskConfirmed(output: TaskEditCompletedOutput) {
    this.onNewTaskConfirmed.emit({
      taskListId: this.taskList().id,
      title: output.newTitle,
    });
  }

  /*********************************
   * TASK ITEM EDIT TITLE HANDLERS *
   *********************************/
  protected handleTaskTitleEditRequest(output: TaskTitleEditRequestOutput) {
    this.editingTaskId.set(output.taskId);
  }

  protected handleTaskTitleUpdated(output: TaskEditCompletedOutput) {
    this.editingTaskId.set(null);
    this.onTaskTitleUpdated.emit({
      taskListId: this.taskList().id,
      value: output,
    });
  }

  protected handleTaskTitleEditCanceled() {
    this.editingTaskId.set(null);
  }

  /*****************************************
   * TASK ITEM OUTPUT PROPAGATION HANDLERS *
   *****************************************/
  protected handleToggleTaskCompletion(output: ToggleTaskCompletionOutput) {
    this.onToggleTaskCompletion.emit({
      taskListId: this.taskList().id,
      value: output,
    });
  }

  protected handleTaskArchived(output: ArchiveTaskOutput) {
    this.onTaskArchived.emit({
      taskListId: this.taskList().id,
      value: output,
    });
  }

  /******************
   * HELPER METHODS *
   ******************/
  isNewTask(task: Task): boolean {
    return task instanceof TransientTask;
  }
}
