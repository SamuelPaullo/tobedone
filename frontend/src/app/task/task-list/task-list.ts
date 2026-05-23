import { Component, input } from '@angular/core';
import { Task } from '../model/task.model';
import { TaskListItem } from './task-list-item/task-list-item';
import {
  ArchiveTaskOutput,
  TaskTitleUpdatedOutput,
  ToggleTaskCompletionOutput
} from './task-list-item/output';

@Component({
  selector: 'task-list',
  imports: [TaskListItem],
  templateUrl: './task-list.html',
  styleUrl: './task-list.scss',
})
export class TaskList {
  readonly tasks = input<Task[]>([]);

  protected handleTitleUpdated({ taskId, newTitle }: TaskTitleUpdatedOutput) {
    const task = this.tasks().find(task => task.id === taskId);
    if (task) {
      task.title = newTitle;
    }
  }

  protected handleToggleTaskCompletion({ taskId, completed }: ToggleTaskCompletionOutput) {
    const task = this.tasks().find(task => task.id === taskId);
    if (task) {
      task.completed = completed;
    }
  }

  protected handleArchiveTask({ taskId }: ArchiveTaskOutput) {
    console.log('Archive task:', taskId);
  }
}
