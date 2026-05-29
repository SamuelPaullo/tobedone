import { Component } from '@angular/core';
import {
  TaskListUi,
  TaskListTitleUpdatedOutput,
  TaskUpdateOutput,
  TaskTitleUpdatedOutput,
  ToggleTaskCompletionOutput,
  ArchiveTaskOutput,
  NewTaskRequestOutput,
  NewTaskSkippedOutput,
} from '../../ui/task-list';
import { Task, emptyTask, TaskList } from '../../model';

@Component({
  selector: 'task-board-feat',
  imports: [TaskListUi],
  templateUrl: './task-board.feat.html',
  styleUrl: './task-board.feat.scss',
})
export class TaskBoardFeat {
  protected readonly todo: TaskList = {
    id: '1',
    title: 'To Do',
    tasks: [
      {
        id: '1',
        title: 'Task 1',
        completed: false,
        createdAt: '2026-05-28T09:00:00Z',
        completedAt: '',
      },
      {
        id: '2',
        title: 'Task 2',
        completed: false,
        createdAt: '2026-05-28T10:00:00Z',
        completedAt: '',
      },
      {
        id: '3',
        title: 'Task 3',
        completed: false,
        createdAt: '2026-05-28T11:00:00Z',
        completedAt: '',
      },
    ],
  };

  protected readonly inProgress: TaskList = {
    id: '2',
    title: 'In Progress',
    tasks: [],
  };

  protected readonly done: TaskList = {
    id: '3',
    title: 'Done',
    tasks: [],
  };

  protected handleTaskListTitleUpdated({ taskListId, newTitle }: TaskListTitleUpdatedOutput) {
    const taskList = this.getTaskListById(taskListId);
    if (taskList) {
      taskList.title = newTitle;
    }
  }

  protected handleAddTaskButtonClick() {}

  /**************************
   * TASK MUTATION HANDLERS *
   **************************/
  protected handleTaskTitleUpdated({
    taskListId,
    value: { taskId, newTitle },
  }: TaskUpdateOutput<TaskTitleUpdatedOutput>) {
    const taskList = this.getTaskListById(taskListId);
    if (taskList) {
      const task = taskList.tasks.find((t) => t.id === taskId);
      if (task) {
        task.title = newTitle;
      }
    }
  }

  protected handleToggleTaskCompletion({
    taskListId,
    value: { taskId, completed },
  }: TaskUpdateOutput<ToggleTaskCompletionOutput>) {
    const taskList = this.getTaskListById(taskListId);
    if (taskList) {
      const task = taskList.tasks.find((t) => t.id === taskId);
      if (task) {
        task.completed = completed;
        task.completedAt = completed ? new Date().toISOString() : '';
      }
    }
  }

  protected handleTaskArchived({
    taskListId,
    value: { taskId },
  }: TaskUpdateOutput<ArchiveTaskOutput>) {
    const taskList = this.getTaskListById(taskListId);
    if (taskList) {
      const taskIndex = taskList.tasks.findIndex((t) => t.id === taskId);
      if (taskIndex !== -1) {
        taskList.tasks.splice(taskIndex, 1);
      }
    }
  }

  /**************************
   * TASK CREATION HANDLERS *
   **************************/
  protected handleNewTaskRequested({ taskListId }: NewTaskRequestOutput) {
    const taskList = this.getTaskListById(taskListId);
    if (taskList) {
      const newTask: Task = emptyTask();
      taskList.tasks.push(newTask);
    }
  }

  protected handleNewTaskSkipped({ taskListId }: NewTaskSkippedOutput) {
    const taskList = this.getTaskListById(taskListId);
    if (taskList) {
      const newTaskSkippedIndex = taskList.tasks.findIndex(this.isNewTask);
      if (newTaskSkippedIndex !== -1) {
        taskList.tasks.splice(newTaskSkippedIndex, 1);
      }
    }
  }

  /******************
   * HELPER METHODS *
   ******************/
  private getTaskListById(taskListId: string): TaskList | null {
    switch (taskListId) {
      case this.todo.id:
        return this.todo;
      case this.inProgress.id:
        return this.inProgress;
      case this.done.id:
        return this.done;
      default:
        return null;
    }
  }

  private isNewTask(task: Task): boolean {
    return !task.id;
  }
}
