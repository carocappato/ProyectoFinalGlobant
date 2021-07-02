<p align="center">  
    <img width=400px src="https://user-images.githubusercontent.com/69480831/124279621-1a333f00-db1e-11eb-9160-fe86dde89e0e.png" alt="Globant">
  </a>
</p>

# Back in the Game - Final Java Project: CRUD Library
### By [Carolina Cappato](https://github.com/carocappato) & [Zaira Gamarra](https://github.com/zaigamarra)

## Resumen
<p>El proyecto final de Back In the Game: Java consistió en el desarrollo de un microservicio que permita crear, actualizar y borrar libros de una biblioteca, así como gestionar los prestamos de los mismos a los usuarios.</p>
<p>Para realizarlo se utilizaron las siguientes tecnologías: Java 11, Spring Boot, Maven, JPA, Hibernate, JUnit 5, Mockito y MySQL.</p>

## Configuración
<p>Para que corra la aplicacion conectada a una base de datos MySQL local, completar los datos con la base que se quiere usar, detallando la URL completa, usuario y contraseña en el archivo application.properties ubicado en main/resources.</p>

```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```

## Métodos

**HTTP Method: POST**

*_Create Book_*

```
http://localhost:8080/books/create
```
```
{
    "title": "book title",
    "author": "bookt author",
    "publishYear": 2021,
    "bookStatus": "disponible"
}
```

*_Create Reservation_*

```
http://localhost:8080/reservations/create
```
```
{
    "startDate": "01-01-2021",
    "endDate": "31-01-2021",
    "user": { "id": 19 },
    "book": { "id": 6 }
}
```

*_Create User_*

```
http://localhost:8080/users/create
```
```
{
    "firstName": "John",
    "lastName": "Doe",
    "documentNumber": 23598765,
    "email": "johndoe@gmail.com"
}
```

**HTTP Method: PUT**

*_Update Book_*

```
http://localhost:8080/books/update/{id}
```
```
{
    "title": "Updated Title",
    "author": "Author",
    "publishYear": 2021,
    "bookStatus": "DISPONIBLE"
}
```

*_Update Reservation_*

```
http://localhost:8080/reservations/update/{id}
```
```
{
    "startDate": "10-10-2020",
    "endDate": "11-12-2020",
    "user": { "id": 15 },
    "book": { "id": 14 }
}
```

**HTTP Method: DELETE**

*_Delete Book_*

```
http://localhost:8080/books/delete/{id}
```

**HTTP Method: GET**

*_Get All Books_*

```
http://localhost:8080/books/getAll
```

*_Get Book By Id_*

```
http://localhost:8080/books/getById/{id}
```

*_Get Book By Status_*

```
http://localhost:8080/books/getByStatus/{status}
```

*_Get Reservation By Id_*

```
http://localhost:8080/reservations/getById/{id}
```


