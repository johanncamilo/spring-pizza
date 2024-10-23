## Conexión Mysql desde terminal
mysql -u root -p


## Conexión SpringBoot a Mysql
application.properties
```gradle
spring.application.name=spring-pizza
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/pizzeria?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=12345678

#para actualizar el esquema automaticamente si cambian los entities
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Conexión SpringBoot a Posgres
application.properties
```gradle
spring.datasource.driver-class-name=org.postgresql.Driver

spring.datasource.url=jdbc:postgresql://localhost:5432:<NombreDeBaseDeDatos>
```

build.gradle
```gradle
runtimeOnly 'org.postgresql:postgresql'
```

### application.yaml con postgresql
borrar application.properties y poner application.yaml
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/pizza
    username: admin
    password: Jaime2030
  jpa:
    hibernate:
      ddl-auto: update
      dialect: org.hibernate.dialect.PostgreSQLDialect
      jdbc:
        lob.non-contextual-creation: true
    properties:
      hibernate:
        show_sql: true 
```
