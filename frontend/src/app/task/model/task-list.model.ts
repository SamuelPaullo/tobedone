import { Task } from "./task.model";

export interface TaskList {
  id: string;
  title: string;
  tasks: Task[];
}
