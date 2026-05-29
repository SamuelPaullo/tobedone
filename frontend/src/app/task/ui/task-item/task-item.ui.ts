import { Task } from '../../model/task.model';
import { Component, computed, ElementRef, input, output, signal, ViewChild } from '@angular/core';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { TaskTitleUpdatedOutput, ToggleTaskCompletionOutput, ArchiveTaskOutput, TaskEditSkippedOutput } from '.';

@Component({
  selector: 'task-item-ui',
  imports: [MatMenuModule, MatIconModule, MatCheckboxModule],
  templateUrl: './task-item.ui.html',
  styleUrl: './task-item.ui.scss',
})
export class TaskItemUi {
  readonly task = input.required<Task>();

  readonly onTitleUpdated = output<TaskTitleUpdatedOutput>();
  readonly onToggleTaskCompletion = output<ToggleTaskCompletionOutput>();
  readonly onArchiveTask = output<ArchiveTaskOutput>();
  readonly onEditSkipped = output<TaskEditSkippedOutput>();

  readonly editModeEnabled = input<boolean>(false);
  private readonly internalEditMode = signal(false);
  protected readonly isEditing = computed(() => this.editModeEnabled() || this.internalEditMode());

  @ViewChild('titleInput')
  private titleInputRef!: ElementRef<HTMLInputElement>;

  protected toggleCompleted() {
    this.onToggleTaskCompletion.emit({
      taskId: this.task().id,
      completed: !this.task().completed,
    });
  }

  protected startEditing() {
    this.internalEditMode.set(true);
  }

  protected cancelEditing() {
    this.internalEditMode.set(false);
    this.onEditSkipped.emit({ taskId: this.task().id });
  }

  protected finishEditing() {
    const newTitle = this.titleInputRef.nativeElement.value.trim();
    if (newTitle !== this.task().title) {
      this.onTitleUpdated.emit({
        taskId: this.task().id,
        newTitle,
      });
      this.internalEditMode.set(false);
    } else {
      this.cancelEditing();
    }
  }

  protected archive() {
    this.onArchiveTask.emit({ taskId: this.task().id });
  }

  protected ngAfterViewChecked() {
    if (this.isEditing()) {
      this.setFocusInTitleInput();
    }
  }

  private setFocusInTitleInput() {
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
