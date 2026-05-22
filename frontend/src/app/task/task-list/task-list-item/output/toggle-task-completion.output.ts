export class ToggleTaskCompletionOutput {
    constructor(
        public readonly taskId: string,
        public readonly completed: boolean
    ) { }
}