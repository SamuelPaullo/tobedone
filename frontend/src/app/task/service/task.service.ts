import { Injectable } from '@angular/core';
import { Task } from '../model';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  readonly tasks: Task[] = [];

  async createTask(task: Task): Promise<Task> {
    const newTask = {
      ...task,
      id: this.generateUniqueId(),
      createdAt: new Date().toISOString(),
    };
    this.tasks.push(newTask);
    return newTask;
  }

  async updateTaskTitle({ taskId, newTitle }: { taskId: string; newTitle: string }): Promise<Task> {
    const taskIndex = this.tasks.findIndex((t) => t.id === taskId);
    if (taskIndex !== -1) {
      this.tasks.splice(taskIndex, 1, { ...this.tasks[taskIndex], title: newTitle });
      return this.tasks[taskIndex];
    }
    throw new Error(`Task with id ${taskId} not found`);
  }

  async toggleTaskCompletion({
    taskId,
    completed,
  }: {
    taskId: string;
    completed: boolean;
  }): Promise<Task> {
    const taskIndex = this.tasks.findIndex((t) => t.id === taskId);
    if (taskIndex !== -1) {
      const updatedTask = {
        ...this.tasks[taskIndex],
        completed,
        completedAt: completed ? new Date().toISOString() : '',
      };
      this.tasks.splice(taskIndex, 1, updatedTask);
      return updatedTask;
    }
    throw new Error(`Task with id ${taskId} not found`);
  }

  async archiveTask({ taskId }: { taskId: string }) {
    throw new Error('Not implemented');
  }

  async moveTask({
    taskId,
    previousListId,
    newListId,
    previousPosition,
    newPosition,
  }: {
    taskId: string;
    previousListId: string;
    newListId: string;
    previousPosition: number;
    newPosition: number;
  }): Promise<Task> {
    const taskIndex = this.tasks.findIndex((task) => task.id === taskId);
    if (taskIndex === -1) {
      throw new Error(`Task with id ${taskId} not found`);
    }

    const currentTask = this.tasks[taskIndex];
    const sourceListTasks = this.tasks.filter((task) => task.listId === previousListId);
    const targetListTasks = previousListId === newListId ? sourceListTasks : this.tasks.filter((task) => task.listId === newListId);

    const sourceTask = sourceListTasks[previousPosition] ?? currentTask;
    const movedTask: Task = {
      ...sourceTask,
      listId: newListId,
    };

    const remainingTasks = this.tasks.filter((task) => task.id !== taskId);
    const normalizedTargetPosition = Math.max(0, Math.min(newPosition, targetListTasks.length));

    const reorderedTargetTasks = [...targetListTasks];
    if (previousListId === newListId) {
      const sourceIndexInTarget = reorderedTargetTasks.findIndex((task) => task.id === taskId);
      if (sourceIndexInTarget !== -1) {
        reorderedTargetTasks.splice(sourceIndexInTarget, 1);
      }
    }
    reorderedTargetTasks.splice(normalizedTargetPosition, 0, movedTask);

    const otherTasks = remainingTasks.filter((task) => task.listId !== newListId);
    this.tasks.splice(0, this.tasks.length, ...otherTasks, ...reorderedTargetTasks);

    return movedTask;
  }

  getTasksByListId(listId: string): Task[] {
    return this.tasks.filter((t) => t.listId === listId);
  }

  getTaskById(taskId: string): Task {
    const task = this.tasks.find((t) => t.id === taskId);
    if (!task) {
      throw new Error(`Task with id ${taskId} not found`);
    }
    return task;
  }

  private generateUniqueId(): string {
    return Math.random().toString(36).substring(2, 9);
  }
}
