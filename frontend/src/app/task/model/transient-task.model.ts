import { Task } from './task.model';

export class TransientTask implements Task {
  constructor(
    public readonly id: string,
    public readonly title: string,
    public readonly completed: boolean,
    public readonly createdAt: string,
    public readonly completedAt: string,
    public readonly listId: string,
  ) {}

  static create(listId = ''): TransientTask {
    return new TransientTask('', '', false, '', '', listId);
  }
}
