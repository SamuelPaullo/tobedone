import { Component, ElementRef, input, output, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { Task } from '../../model';
import { TaskEditCanceledOutput, TaskEditCompletedOutput } from './output';

@Component({
  selector: 'task-item-editable-ui',
  imports: [MatIconModule, MatCheckboxModule],
  templateUrl: './task-item-editable.ui.html',
  styleUrl: './task-item-editable.ui.scss',
})
export class TaskItemEditableUi {
  readonly task = input.required<Task>();
  readonly onComplete = output<TaskEditCompletedOutput>();
  readonly onCancel = output<TaskEditCanceledOutput>();

  @ViewChild('titleInput')
  private titleInputRef!: ElementRef<HTMLInputElement>;

  protected handleTitleInputBlur() {
    this.commit();
  }

  protected handleTitleInputKeydown(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      const input = this.titleInputRef.nativeElement;
      input.blur();
    } else if (event.key === 'Escape') {
      this.cancel();
    }
  }

  private commit() {
    const newTitle = this.diff();
    if (newTitle) {
      this.onComplete.emit({ taskId: this.task().id, newTitle });
    } else {
      this.cancel();
    }
  }

  private cancel() {
    this.onCancel.emit({ taskId: this.task().id });
  }

  private diff(): string | null {
    const newTitle = this.titleInputRef.nativeElement.value.trim();
    return newTitle !== this.task().title ? newTitle : null;
  }

  /***************************
   * ANGULAR LIFECYCLE HOOKS *
   ***************************/
  protected ngAfterViewChecked() {
    if (this.titleInputRef) {
      const input = this.titleInputRef.nativeElement;
      input.focus();
    }
  }
}
