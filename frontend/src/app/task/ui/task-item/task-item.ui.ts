import { Task } from '../../model/task.model';
import { Component, input, output } from '@angular/core';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { ToggleTaskCompletionOutput, ArchiveTaskOutput, TaskTitleEditRequestOutput } from '.';

@Component({
  selector: 'task-item-ui',
  imports: [MatMenuModule, MatIconModule, MatCheckboxModule],
  templateUrl: './task-item.ui.html',
  styleUrl: './task-item.ui.scss',
})
export class TaskItemUi {
  readonly task = input.required<Task>();

  readonly onToggleTaskCompletion = output<ToggleTaskCompletionOutput>();
  readonly onArchiveTask = output<ArchiveTaskOutput>();
  readonly onTaskTitleEditRequest = output<TaskTitleEditRequestOutput>();

  protected toggleCompleted() {
    this.onToggleTaskCompletion.emit({
      taskId: this.task().id,
      completed: !this.task().completed,
    });
  }

  protected archive() {
    this.onArchiveTask.emit({ taskId: this.task().id });
  }

  protected handleTitleClick() {
    this.emitTaskTitleEditRequest();
  }

  protected emitTaskTitleEditRequest() {
    this.onTaskTitleEditRequest.emit({ taskId: this.task().id });
  }
}
