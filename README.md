# AutoDiagnosis

Для тестовых запусков используется база H2 - она развертывается и заполняется при запуске приложения.

Можно к ней подключиться во время работы приложения:
```
driver: H2
Connection type: Remote
Host: localhost
Port: 9092
User: sa
Password: sa
Database: mem
URL: jdbc:h2:tcp://localhost:9092/mem:db
```

Известна проблема с работой проекта на Java 11. Рекомендую использовать 8-ую.
