# FakeAndDraw backend application
Server side application for FakeAndDraw game

### Help:
- /src/main/resources/schema.sql -> schema definition for embebed database, will be executed on boot
- /src/main/resources/data.sql -> data definition for embebed database, will be executed on boot
- /src/main/resources/application.properties -> main configuration file, check comments there
- Deploy -> ./mvnw spring-boot:run
  - H2 embebed database console will be loaded on http://localhost:8080/h2-console