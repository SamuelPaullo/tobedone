import { Component, ElementRef, input, output, signal, ViewChild } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { TaskList } from '../../model';
import { TaskListTitleUpdatedOutput } from './ouput';
import {
  TaskItemUi,
  ArchiveTaskOutput,
  TaskTitleUpdatedOutput,
  ToggleTaskCompletionOutput,
} from '../task-item';

@Component({
  selector: 'task-list-ui',
  imports: [TaskItemUi, MatIcon],
  templateUrl: './task-list.ui.html',
  styleUrl: './task-list.ui.scss',
})
export class TaskListUi {
  readonly taskList = input.required<TaskList>();

  readonly onTaskListTitleUpdated = output<TaskListTitleUpdatedOutput>();

  protected isEditingTitle = signal(false);

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

  /******************************
   * TASK ITEMS OUTPUT HANDLERS *
   ******************************/
  protected handleTaskTitleUpdated(output: TaskTitleUpdatedOutput): TaskTitleUpdatedOutput {
    return output;
  }

  protected handleToggleTaskCompletion(
    output: ToggleTaskCompletionOutput,
  ): ToggleTaskCompletionOutput {
    return output;
  }

  protected handleTaskArchived(output: ArchiveTaskOutput): ArchiveTaskOutput {
    return output;
  }
}
