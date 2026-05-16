package tobedone.task.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TaskStatus {

	OPEN("OPEN"),
	COMPLETED("COMPLETED");

	private final String description;
}

