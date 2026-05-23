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
  }

  protected cancelEditing() {
    this.isEditing.set(false);
  }

  protected finishEditing() {
    const newTitle = this.titleInputRef.nativeElement.value.trim();
    if (newTitle !== this.task().title) {
      this.onTitleUpdated.emit({
        taskId: this.task().id,
        newTitle,
      });
    }
    this.isEditing.set(false);
  }

  protected archive() {
    this.onArchiveTask.emit({ taskId: this.task().id });
  }

  protected ngAfterViewChecked() {
    if (this.isEditing()) {
      this.requestFocusInTitleInput();
    }
  }

  private requestFocusInTitleInput() {
    if (this.titleInputRef) {
      const input = this.titleInputRef.nativeElement;
      input.focus();
    }
  }

  protected handleTitleInputBlur() {
    if (this.isEditing()) {
      this.finishEditing();
    }
  }

  protected handleTitleInputKeydown(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.finishEditing();
    } else if (event.key === 'Escape') {
      this.cancelEditing();
    }
  }
}
