import { Task } from '../../model/task.model';
import { Component, ElementRef, input, signal, ViewChild } from '@angular/core';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';

@Component({
  selector: 'task-list-item',
  imports: [MatMenuModule, MatIconModule, MatCheckboxModule],
  templateUrl: './task-list-item.html',
  styleUrl: './task-list-item.scss',
})
export class TaskListItem {

  readonly task = input.required<Task>();
  protected readonly isEditing = signal(false);
  private shouldFocus = false;
  private preventEditFinished = false;

  @ViewChild('titleInput')
  private titleInputRef!: ElementRef<HTMLInputElement>;

  protected markAsCompleted() {
    this.task().completed = true;
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
    if(this.preventEditFinished) {
      this.preventEditFinished = false;
      return;
    }
    this.task().title = this.titleInputRef.nativeElement.value;
    this.isEditing.set(false);
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
