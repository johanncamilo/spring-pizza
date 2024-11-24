0# SPRING DATA JPA

* [Drive](https://drive.google.com/drive/u/2/folders/1fI-a1iHDykf1mxImRk2gq3sYNu6Qp4cM)
* [Captura Apuntes](https://docs.google.com/document/d/1AUU2yWlbI9tvZ-sUAyw5rv9EtTVBJmAIe9HsoNVh8qM/edit?tab=t.0)

## MER Proyecto

![img.png](MER.png)

### Initializr

![img.png](initialzr.png)

## Conexión Mysql desde terminal

```shell
mysql -u root -p
```

## Conexión SpringBoot a Mysql

application.properties

```properties
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

```properties
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

## Lombok

### @Getter @Setter

lo que hacen es generar automáticamente los getters y setters de cada una de las propiedades, esto ayuda a que el código
sea más legible y no que apenas abres la clase, ves el chorrero de getters y setters, otra ventaja es que si a la clase
le añades una propiedad pero olvidaste añadir el get y set, Lombok usando estas anotaciones lo hace automáticamente por
ti.
<br>
no se recomienda @Data ya que lombok gestiona automáticamente los métodos equals() y hasCode() lo cual es una mala
practica hacerlo a través de lombok para entidades

en su lugar se coloca @Getter @Setter @NoArgsConstructor

## JdbcTemplate

* se inyecta en el constructor de un servicio usando @Autowired
* Actua como una capa de abstracción sobre api jdbc

## Spring Repository

* JPA REPOSITORY Extiende de CrudRepository y PagingAndSortingRepository
* Tiene operaciones de JPA como FLUSH y tareas especificas de DB
* usar el tag `@EnableJpaRepositories` en la clase principal
* CrudRepository retorna un Iterable<T> que es un set
* ListCrudRepository retorna un List<T>
* Los Spring Repository ayudan a interactuar con la DB con poco codigo

## LAZY & EAGER

* **FetchType.LAZY:** No carga la relación sino hasta que se use, si no se usa la relac. no se carga nada
* **FetchType.EAGER:** Cuando se recupere una entidad se recupere automaticamente la relación

#### Valores por defecto

![img.png](LazyEager.png)
> Sólo usar relaciones estrictamente necesarias para el código y usar el FetchType.LAZY para mejorar el rendimiento

* OneToMany: LAZY
* ManyToOne: EAGER
* ManyToMany: LAZY
* OneToOne: EAGER

## Query Methods

* Convierten los queryMethod a sql en tiempo de ejecución
* Orden QueryMethod en Pizza: Repository->Service->Controller
* Siempre el uso de los QueryMethods son de mayor utilidad a la hora de personalizar nuestras consultas hacia la base de
  datos, y la utilidad de poder retornar una sublista de una lista de objetos como lo es **Page ** nos da la posibilidad
  de paginar las respuestas y adicional agregar una dirección de ordenamiento ascendente o descendente

### Sinónimos queryMethod findAllBy()

* getAllBy()
* queryBy()
* searchAllBy()

## Actualización queryMethod en PizzaRepostitory

Se reemplazó

```java
PizzaEntity findAllByAvailableTrueAndNameIgnoreCase(String name);
```

por

```java
Optional<PizzaEntity> findFirstByAvailableTrueAndNameIgnoreCase(String name);
```

También se puede usar findByTop

## ListPagingAndSortingRepository<PizzaEntity, Integer>

* crear interface PizzaPagSortReposotory que extiende de esta gran interface
* Provee de Paginación y Sorting de consultas
* Siempre hay que reemplazar los tipos de return List por Page
* Hay que utilizar interfaces de Spring Data como Pageable, PageRequest, Sort

### Pageable

Interface de Spring Data para implementar paginación en consultas

## JPQL

* Java Persistence Query Language
* Permite hacer consultas a la DB basadas en las entities, en lugar del sql tracicional que usa tablas y columnas
* Hibernate convierte el jpql en sql nativo
* Se definen en el Repositorio y son una alternativa a los queryMethods
* usan el tag @Query
* el nombre de la variable `:phone` coincide con el nombre `@Param("phone")`

```java

@Query(value = "select c from CustomerEntity c where c.phoneNumber = :phone")
CustomerEntity findByPhone(@Param("phone") String phone);
```

## Query Nativo

* Similar al jpql sólo que hay que ponerle el atributo `nativeQuery = true` en el tag @Query
* se puede implementar un @OrderBy en un atributo de la entidad

```java

@OneToMany(mappedBy = "order")
@OrderBy("price desc")
private List<OrderItemEntity> items;
```

## Query Projections

* personalizacion: querys complejos
* projections -> Dtos a medida con atributos personalizados
* query Projections a traves interfaces para querys complejos con joins y demas

## @Transactional

* Debe ir en método de servicio que pretenda hacer transacciones de modificación
* Gestiona internamente todo el ACID de las transacciones
* Si un paso falla lanza error 500
* Claúsula `noRollBack` para poner excepciones
* Claúsula `propagation = Propagation.REQUIRED` para que deba existir una transacción para ejecutar ese método, si no
  existe la transacción automáticamente la creará

```java
@Transactional(noRollbackFor = EmailApiException.class, propagation = Propagation.REQUIRED)
```

* [propagations](https://www.baeldung.com/spring-transactional-propagation-isolation)

## @Modifying

* Update/ Delete queries must be annotated with @Modifying, otherwise an InvalidDataAccessApiUsageException will be
  thrown.
* debe ir en el metodo de un repositorio que vaya a hacer transacciones de update
* un @Query sin @Modifying sólo puede hacer selects

### Importante!

Usar verbo PATCH para modificaciones parciales como esta

## Crear una excepcion

* Crear package exception dentro de /service
* Crear EmailApiException extends RuntimeException
* Llamar al constructor super dentro del constructor de esa clase con el mensaje como parametro
* le llama así:

```java
throw new EmailApiException();
```

## Auditoria

* @CreatedDate -> para el atributo de fechaCreacion
* @LastModifiedDate -> para el atributo de fechaActualizacion
* @JsonIgnore -> para que se ignore en la consulta, se pone manualmente uno a uno
* @MappedSuperclass -> se pone en la clase Entidad que se quiera que herede a otras Entidades en este caso se creo un
  entity/AuditableEntity para que los atributos queden encapsulados y no se retornen directamente en las peticiones
* PizzaEntity extends AuditableEntity

## Listener personalizado para auditoria

* Crear package autit en persitence/
* Crear clase AuditPizzaListener
* crear metodo onPostPersist() con los tags arriba: @PostPersist, @PostUpdate (también existen @PrePersist y,
  @PreUpdate)
* Crear metodo onPreDelete con tag @PreRemove
* Crear metodo potLoad con tag @PostLoad para ser ejecutado despues del select
* En PizzaEntity agregar el listener personalizado así:

```java
@EntityListeners({AuditingEntityListener.class, AuditPizzaListener.class})
```

* implementar Serializable en la entidad para lograr clonarla en la entidad