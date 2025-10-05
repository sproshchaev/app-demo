# app-demo (Java 21)  
## spring-boot-starter-web  


1. POST /api/persons — создать
```bash
curl -X POST http://localhost:8080/api/persons -H "Content-Type: application/json" -d '{"name":"Иван","age":30}'
```

2. GET /api/persons — получить всех
```bash
curl http://localhost:8080/api/persons
```

3. GET /api/persons/1 — получить по id
```bash
curl http://localhost:8080/api/persons/1
```

4. PUT /api/persons/1 — обновить по id
```bash
curl -X PUT http://localhost:8080/api/persons/1 -H "Content-Type: application/json" -d '{"name":"Петр","age":25}'
```

5. DELETE /api/persons/1 — удалить по id
```bash
curl -X DELETE http://localhost:8080/api/persons/1
```