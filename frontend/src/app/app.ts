import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TaskListUi } from './task/ui/task-list/task-list.ui';
import { TaskList } from './task/model';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, TaskListUi],
  templateUrl: './app.html',
  styleUrls: ['./app.scss'],
})
export class App {
  protected readonly taskListMock: TaskList = {
    id: '1',
    title: 'Minha Lista de Tarefas',
    tasks: [
      {
        id: '1',
        title: 'Criar tela inicial do Task Man',
        completed: false,
        createdAt: '2026-05-20 09:00',
        completedAt: '',
      },
      {
        id: '2',
        title: 'Conectar lista ao backend',
        completed: true,
        createdAt: '2026-05-20 09:15',
        completedAt: '2026-05-20 10:05',
      },
      {
        id: '3',
        title: 'Adicionar filtro por status',
        completed: false,
        createdAt: '2026-05-20 10:20',
        completedAt: '',
      },
    ],
  };
}
