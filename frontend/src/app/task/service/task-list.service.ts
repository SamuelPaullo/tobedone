import { Injectable } from '@angular/core';
import { TaskList } from '../model';

@Injectable({
  providedIn: 'root',
})
export class TaskListService {
  readonly lists: TaskList[] = [
    {
      id: '1',
      title: 'To Do',
      createdAt: new Date().toISOString(),
      tasks: [],
    },
    {
      id: '2',
      title: 'In Progress',
      createdAt: new Date().toISOString(),
      tasks: [],
    },
    {
      id: '3',
      title: 'Done',
      createdAt: new Date().toISOString(),
      tasks: [],
    },
  ];

  async createTaskList(title: string): Promise<TaskList> {
    const newTaskList = new TaskList(this.generateUniqueId(), title, new Date().toISOString(), []);
    this.lists.push(newTaskList);
    return newTaskList;
  }

  async updateTaskListTitle(id: string, newTitle: string): Promise<TaskList> {
    const taskListIndex = this.lists.findIndex((list) => list.id === id);
    if (taskListIndex !== -1) {
      const updatedTaskList = { ...this.lists[taskListIndex], title: newTitle };
      this.lists.splice(taskListIndex, 1, updatedTaskList);
      return updatedTaskList;
    }
    throw new Error(`TaskList with id ${id} not found`);
  }

  getTaskLists(): TaskList[] {
    return [...this.lists];
  }

  private generateUniqueId(): string {
    return Math.random().toString(36).substr(2, 9);
  }
}
