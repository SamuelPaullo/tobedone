import { Component, ElementRef, inject, signal, ViewChild } from '@angular/core';
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
import { Task, TransientTask, TaskList } from '../../model';
import { TaskService, TaskListService } from '../../service';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'task-board-feat',
  imports: [TaskListUi, MatButtonModule, MatInputModule],
  templateUrl: './task-board.feat.html',
  styleUrl: './task-board.feat.scss',
})
export class TaskBoardFeat {
  protected readonly taskLists = signal<TaskList[]>([]);

  private readonly taskListService = inject(TaskListService);
  private readonly taskService = inject(TaskService);

  protected readonly isAddingNewList = signal(false);
  @ViewChild('newListTitleInput')
  private newListTitleInputRef!: ElementRef<HTMLInputElement>;

  constructor() {
    const lists = this.taskListService.getTaskLists();
    this.taskLists.set(lists);
  }

  /***************************
   * ANGULAR LIFECYCLE HOOKS *
   ***************************/
  ngAfterViewChecked() {
    if (this.isAddingNewList() && this.newListTitleInputRef) {
      this.newListTitleInputRef.nativeElement.focus();
    }
  }

  /**************************
   * LIST MUTATION HANDLERS *
   **************************/
  protected async handleTaskListTitleUpdated({ taskListId, newTitle }: TaskListTitleUpdatedOutput) {
    const taskListIndex = this.getTaskListIndexById(taskListId);
    if (taskListIndex !== -1) {
      const updatedTaskList = await this.taskListService.updateTaskListTitle(taskListId, newTitle);
      this.taskLists.update((lists) => {
        const updatedLists = [...lists];
        updatedLists.splice(taskListIndex, 1, updatedTaskList);
        return updatedLists;
      });
    }
  }

  /**************************
   * LIST CREATION HANDLERS *
   **************************/
  protected handleAddNewListInputBlur() {
    this.isAddingNewList.set(false);
  }

  protected async handleAddNewListInputKeydown(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      const inputElement = event.target as HTMLInputElement;
      const title = inputElement.value.trim();
      if (title) {
        console.log('Creating new list with title:', title);
        const newTaskList = await this.taskListService.createTaskList(title);
        this.taskLists.update((lists) => [...lists, newTaskList]);
        this.isAddingNewList.set(false);
      }
    } else if (event.key === 'Escape') {
      this.isAddingNewList.set(false);
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
  private getTaskListById(taskListId: string): TaskList | undefined {
    const taskList = this.taskLists().find((list) => list.id === taskListId);
    return taskList;
  }

  private getTaskListIndexById(taskListId: string): number {
    const taskListIndex = this.taskLists().findIndex((list) => list.id === taskListId);
    if (taskListIndex === -1) {
      throw new Error(`TaskList with id ${taskListId} not found`);
    }
    return taskListIndex;
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
