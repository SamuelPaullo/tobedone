import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TaskBoardFeat } from './task/feat';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, TaskBoardFeat],
  templateUrl: './app.html',
  styleUrls: ['./app.scss'],
})
export class App {

}
