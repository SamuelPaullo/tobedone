import { Task } from './task.model';

export class TaskList {
  constructor(
    public readonly id: string,
    public readonly title: string,
    public readonly createdAt: string,
    public readonly tasks: Task[],
  ) {}
}
