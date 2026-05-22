export class TaskTitleUpdatedOutput {
    constructor(
        public readonly taskId: string,
        public readonly newTitle: string,
    ) { }
}