import { Task } from '../../model/task.model';
import { Component, ElementRef, input, output, signal, ViewChild } from '@angular/core';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { TaskTitleUpdatedOutput } from './output/task-title-updated.output';
import { ToggleTaskCompletionOutput } from './output/toggle-task-completion.output';
import { ArchiveTaskOutput } from './output/archive-task.output';

@Component({
  selector: 'task-list-item',
  imports: [MatMenuModule, MatIconModule, MatCheckboxModule],
  templateUrl: './task-list-item.html',
  styleUrl: './task-list-item.scss',
})
export class TaskListItem {

  readonly task = input.required<Task>();

  readonly onTitleUpdated = output<TaskTitleUpdatedOutput>();
  readonly onToggleTaskCompletion = output<ToggleTaskCompletionOutput>();
  readonly onArchiveTask = output<ArchiveTaskOutput>();

  protected readonly isEditing = signal(false);
  private shouldFocus = false;
  private preventEditFinished = false;

  @ViewChild('titleInput')
  private titleInputRef!: ElementRef<HTMLInputElement>;

  protected toggleCompleted() {
    this.onToggleTaskCompletion.emit({
      taskId: this.task().id,
      completed: !this.task().completed,
    });
  }

  protected startEditing() {
    this.isEditing.set(true);
    this.shouldFocus = true;
  }

  protected cancelEditing() {
    this.preventEditFinished = true;
    this.isEditing.set(false);
  }

  protected finishEditing() {
    if (this.preventEditFinished) {
      this.preventEditFinished = false;
      return;
    }
    this.onTitleUpdated.emit({
      taskId: this.task().id,
      newTitle: this.titleInputRef.nativeElement.value,
    });
    this.isEditing.set(false);
  }

  protected archive() {
    this.onArchiveTask.emit({ taskId: this.task().id });
  }

  protected ngAfterViewChecked() {
    console.log('ngAfterViewChecked called');
    if (this.shouldFocus) {
      this.ensureFocusInTitleInput();
      this.shouldFocus = false;
    }
  }

  private ensureFocusInTitleInput(): void {
    if (this.titleInputRef) {
      const input = this.titleInputRef.nativeElement;
      input.focus();
    }
  }
}
