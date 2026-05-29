export interface Task {
  id: string;
  title: string;
  completed: boolean;
  createdAt: string;
  completedAt: string;
}

export const emptyTask = (): Task => ({
  id: '',
  title: '',
  completed: false,
  createdAt: '',
  completedAt: '',
});
