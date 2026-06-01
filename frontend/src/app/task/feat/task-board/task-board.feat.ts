import { Component, inject, signal } from '@angular/core';
import {
  TaskListUi,
  TaskListTitleUpdatedOutput,
  TaskUpdateOutput,
  TaskEditCompletedOutput,
  ToggleTaskCompletionOutput,
  ArchiveTaskOutput,
  NewTaskRequestOutput,
  NewTaskSkippedOutput,
  NewTaskConfirmedOutput,
} from '../../ui/task-list';
import { Task, TaskList } from '../../model';
import { TaskService } from '../../service/task.service';
import { TransientTask } from '../../model/transient-task.model';

@Component({
  selector: 'task-board-feat',
  imports: [TaskListUi],
  templateUrl: './task-board.feat.html',
  styleUrl: './task-board.feat.scss',
})
export class TaskBoardFeat {
  private readonly taskService = inject(TaskService);

  protected readonly todo = signal<TaskList>({
    id: '1',
    title: 'To Do',
    tasks: [
      {
        id: '1',
        title: 'Task 1',
        completed: false,
        createdAt: '2026-05-28T09:00:00Z',
        completedAt: '',
        listId: '1',
      },
      {
        id: '2',
        title: 'Task 2',
        completed: false,
        createdAt: '2026-05-28T10:00:00Z',
        completedAt: '',
        listId: '1',
      },
      {
        id: '3',
        title: 'Task 3',
        completed: false,
        createdAt: '2026-05-28T11:00:00Z',
        completedAt: '',
        listId: '1',
      },
    ],
  });

  protected readonly inProgress = signal<TaskList>({
    id: '2',
    title: 'In Progress',
    tasks: [],
  });

  protected readonly done = signal<TaskList>({
    id: '3',
    title: 'Done',
    tasks: [],
  });

  protected handleTaskListTitleUpdated({ taskListId, newTitle }: TaskListTitleUpdatedOutput) {
    const taskList = this.getTaskListById(taskListId);
    if (taskList) {
      taskList.title = newTitle;
    }
  }

  /**************************
   * TASK MUTATION HANDLERS *
   **************************/
  protected async handleTaskTitleUpdated({
    taskListId,
    value: { taskId, newTitle },
  }: TaskUpdateOutput<TaskEditCompletedOutput>) {
    const taskList = this.getTaskListById(taskListId);
    if (taskList) {
      const updatedTask = await this.taskService.updateTaskTitle({ taskId, newTitle });
      const taskIndex = this.getTaskIndexById(taskList, taskId);
      taskList.tasks.splice(taskIndex, 1, updatedTask);
    }
  }

  protected async handleToggleTaskCompletion({
    taskListId,
    value: { taskId, completed },
  }: TaskUpdateOutput<ToggleTaskCompletionOutput>) {
    console.log('Toggling task completion for taskId:', taskId, 'to completed:', completed);
    const taskList = this.getTaskListById(taskListId);
    if (taskList) {
      const updatedTask = await this.taskService.toggleTaskCompletion({ taskId, completed });
      const taskIndex = this.getTaskIndexById(taskList, taskId);
      taskList.tasks.splice(taskIndex, 1, updatedTask);
    }
  }

  protected async handleTaskArchived({
    taskListId,
    value: { taskId },
  }: TaskUpdateOutput<ArchiveTaskOutput>) {
    const taskList = this.getTaskListById(taskListId);
    if (taskList) {
      await this.taskService.archiveTask({ taskId });
      const taskIndex = this.getTaskIndexById(taskList, taskId);
      taskList.tasks.splice(taskIndex, 1);
    }
  }

  /**************************
   * TASK CREATION HANDLERS *
   **************************/
  protected handleNewTaskRequested({ taskListId }: NewTaskRequestOutput) {
    const taskList = this.getTaskListById(taskListId);
    if (taskList) {
      const newTask: Task = TransientTask.create();
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

  protected async handleNewTaskConfirmed({ taskListId, title }: NewTaskConfirmedOutput) {
    const taskList = this.getTaskListById(taskListId);
    if (taskList) {
      const newTaskIndex = taskList.tasks.findIndex(this.isNewTask);
      const newTask = await this.taskService.createTask({
        ...Task.createEmpty(),
        title,
        listId: taskListId,
      });
      taskList.tasks.splice(newTaskIndex, 1, newTask);
    }
  }

  /******************
   * HELPER METHODS *
   ******************/
  private getTaskListById(taskListId: string): TaskList | null {
    switch (taskListId) {
      case this.todo().id:
        return this.todo();
      case this.inProgress().id:
        return this.inProgress();
      case this.done().id:
        return this.done();
      default:
        return null;
    }
  }

  private getTaskIndexById(taskList: TaskList, taskId: string): number {
    const taskIndex = taskList.tasks.findIndex((t) => t.id === taskId);
    if (taskIndex === -1) {
      throw new Error(`Task with id ${taskId} not found in list ${taskList.id}`);
    }
    return taskIndex;
  }

  private isNewTask(task: Task): boolean {
    return task instanceof TransientTask;
  }
}
