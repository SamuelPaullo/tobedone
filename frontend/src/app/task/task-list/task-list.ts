import { Component, input } from '@angular/core';
import { Task } from '../model/task.model';
import { TaskListItem } from './task-list-item/task-list-item';

@Component({
  selector: 'task-list',
  imports: [TaskListItem],
  templateUrl: './task-list.html',
  styleUrl: './task-list.scss',
})
export class TaskList {
  readonly tasks = input<Task[]>([]);
}
