import { Component } from '@angular/core';
import { TaskListUi } from '../../ui/task-list';
import { TaskList } from '../../model';

@Component({
  selector: 'task-board-feat',
  imports: [TaskListUi],
  templateUrl: './task-board.feat.html',
  styleUrl: './task-board.feat.scss',
})
export class TaskBoardFeat {
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
