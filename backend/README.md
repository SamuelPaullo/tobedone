# Tobedone Backend

Feature `task` implementada com organizacao por feature:

- `tobedone.task.api`
- `tobedone.task.application`
- `tobedone.task.domain`
- `tobedone.task.infrastructure`

## Endpoints

- `POST /tasks`
- `GET /tasks`
- `PATCH /tasks/{id}/complete`

### Exemplo de criacao

```json
{
  "title": "Estudar Spring Boot"
}
```

## Executar

Use o wrapper do Gradle:

- `./gradlew bootRun` (Linux/macOS)
- `gradlew.bat bootRun` (Windows)

## Testes

- `./gradlew test` (Linux/macOS)
- `gradlew.bat test` (Windows)

