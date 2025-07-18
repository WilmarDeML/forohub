# 🗨️ Forohub - API REST para la gestión de foros

![Java24](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![SpringBoot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=maven&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)

## 📚 Descripción
Forohub es una API REST desarrollada en Java con Spring Boot, diseñada para la gestión de foros. Permite a los usuarios crear, leer, actualizar y eliminar tópicos, así como crear usuarios y cursos. La API utiliza JWT para la autenticación y autorización, y está respaldada por una base de datos MySQL.

## 📦 Contenido
- [Descripción](#-descripción)
- [Contenido](#-contenido)
- [Características](#-características)
- [Requisitos](#-requisitos)
- [Instalación](#-instalación)
- [Uso](#-uso)
- [Endpoints](#-endpoints)
  - [Usuarios](#usuarios)
  - [Tópicos](#tópicos)
  - [Cursos](#cursos)
- [Seguridad](#-seguridad)
- [Documentación](#-documentación)
- [Características futuras](#-características-futuras)
- [Contribución](#-contribución)
- [Tecnologías](#-tecnologías)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Contacto](#-contacto)
- [Autores](#-autores)
- [Licencia](#-licencia)

## 🚀 Características
- Creación, actualización y eliminación de tópicos.
- Creación de usuarios.
- Creación de cursos.
- Autenticación y autorización mediante JWT.
- Validación de datos de entrada.
- Documentación de la API con Swagger.

## 🛠️ Requisitos
- Java 17 o superior
- Maven
- MySQL
- Postman (opcional, para probar la API) o [Hoppscotch](hoppscotch.io)
- Conexión a Internet (para descargar dependencias y documentación)

## 📥 Instalación
1. Clona el repositorio:
   ```bash
   git clone https://github.com/WilmarDeML/forohub.git
   ```
2. Navega al directorio del proyecto:
   ```bash
   cd forohub
   ```
3. Configura la base de datos MySQL y actualiza el archivo `application.properties`
   con tus credenciales:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/forohub
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   ```
4. Compila el proyecto con Maven:
   ```bash
   mvn clean install
   ```
5. Ejecuta la aplicación:
   ```bash
   mvn spring-boot:run
   ```
## 🖥️ Uso
Puedes probar la API utilizando Postman, [Hoppscotch](hoppscotch.io) o cualquier cliente HTTP. Asegúrate de incluir el token JWT en los encabezados de las solicitudes que requieran autenticación.

## 🛣️ Endpoints
### Base URL
- En desarrollo local:
    ```
    http://localhost:8080/v1/api
    ```
- En producción (Render):
    ```
    https://forohub-se3w.onrender.com/v1/api
    ```
### Usuarios no requieren autenticación JWT.
- `POST /usuarios`: Registrar un nuevo usuario.
  - Requiere un objeto JSON con los campos `nombre`, `correoElectronico`, `contrasena` y `perfiles` (arreglo de objetos JSON con campos `id` y `nombre`).
  - Los perfiles disponibles son: `admin`, `particular`, `registrado`. Con los ids correspondientes: `1`, `2`, `3`.
- `POST /usuarios/login`: Iniciar sesión y obtener un token JWT
  - Requiere un objeto JSON con los campos `correoElectronico` y `contrasena`.
  - Respuesta: Un objeto JSON con el token JWT.

### Tópicos requieren autenticación JWT rol 'registrado'.
- `GET /topicos`: Obtener todos los tópicos paginados.
  - Opcionalmente, puedes enviar parámetros de consulta para la paginación: `page`, `size`, `sort`.
  - Si no se envían parámetros, se devolverán los primeros 10 tópicos ordenados por fecha de creación.
  - Respuesta: Un objeto JSON con los tópicos.
- `GET /topicos/{id}`: Obtener un tópico por ID.
  - Respuesta: Un objeto JSON con los detalles del tópico.
- `GET /topicos/curso/{nombreCurso}`: Obtener tópicos por nombre de curso paginados.
  - Opcionalmente, puedes enviar parámetros de consulta para la paginación: `page`, `size`, `sort`.
  - Si no se envían parámetros, se devolverán los primeros 10 tópicos del curso ordenados por fecha de creación.
  - Respuesta: Un objeto JSON con los tópicos del curso especificado.
- `POST /topicos`: Crear un nuevo tópico.
  - Requiere un objeto JSON con los campos `titulo`, `mensaje`, `autorId` y `cursoId`.
  - Respuesta: Un objeto JSON con el tópico creado.
- `PUT /topicos/{id}`: Actualizar un tópico por ID.
  - Requiere un objeto JSON con los campos `titulo`, `mensaje`, `autorId` y `cursoId`.
  - Respuesta: Un objeto JSON con el tópico actualizado.
- `DELETE /topicos/{id}`: Eliminar un tópico por ID.
  - Respuesta: Sin contenido, pero con un código de estado 204 (No Content) si la eliminación fue exitosa.

### Cursos requieren autenticación JWT rol 'admin'.
- `POST /v1/api/cursos`: Crear un nuevo curso.
  - Requiere un objeto JSON con los campos `nombre` y `categoria`.
  - Respuesta: Un objeto JSON con el curso creado.

## 🔒 Seguridad
La API utiliza JWT para la autenticación y autorización. Los usuarios deben iniciar sesión para obtener un token JWT, que debe incluirse en los encabezados de las solicitudes que requieran autenticación.
El token JWT se genera al iniciar sesión y tiene una duración de 2 horas.

## 📖 Documentación
La documentación de la API está disponible en Swagger. Una vez que la aplicación esté en ejecución, accede a la documentación en:
- Para la versión local:
    ```
    http://localhost:8080/api/v1/swagger-ui.html
    ```
- Para la versión en producción (Render):
    ```
    https://forohub-se3w.onrender.com/api/v1/swagger-ui.html
    ```

## 🚀 Características Futuras
- Implementación de los endpoints de gestión de cursos.
- Implementación de los endpoints de gestión de respuestas.
- Mejora de la gestión de errores y excepciones.
- Integración con servicios externos para notificaciones.
- Implementación de un sistema de búsqueda avanzada.
- Soporte para múltiples idiomas.

## 🤝 Contribución
Si deseas contribuir al proyecto, sigue estos pasos:
1. Haz un fork del repositorio.
2. Crea una nueva rama (`git checkout -b feature/nueva-caracteristica`).
3. Realiza tus cambios y haz commit (`git commit -m 'Añadir nueva característica'`).
4. Sube tus cambios (`git push origin feature/nueva-caracteristica`).
5. Crea un Pull Request.

## 🛠️ Tecnologías
- Java 24
- Spring Boot 3.5.3
- MySQL 8.0
- Maven 3.9.6
- JWT
- OpenAPI (Swagger)

## 📝 Notas
Este proyecto es un trabajo en progreso y se actualizará con nuevas características y mejoras en el futuro. Agradezco cualquier comentario o sugerencia para mejorar la API.

## 🗂️ Estructura del Proyecto
```
forohub
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── wilmardeml
│   │   │           └── forohub
│   │   │               ├── infra
│   │   │               │   ├── excepciones
│   │   │               │   ├── seguridad
│   │   │               │   └── springdoc
│   │   │               ├── modelos
│   │   │               │   ├── dtos
│   │   │               │   ├── entidades
│   │   │               ├── repositorios
│   │   │               ├── servicios
│   │   │               ├── controladores
│   │   │               └── ForohubApplication.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── application-prod.properties
│   │       ├── db.migration
│   │       │   └── V1__creaTablasIniciales.sql
│   │       │   └── V2__creaTablaUsuariosPerfiles.sql
│   │       │   └── V3__insertaPerfilesStandard.sql
│   │       │   └── V4__insertaUsuarioTest.sql
│   │       └── static
│   └── test
│       └── java
│           └── com
│               └── wilmardeml
│                   └── forohub
│                       └── servicios
│                           └── CursoServicioTest.java
└── pom.xml
|__ README.md
|__ Dockerfile
|__ .gitignore
```

## 📌 Contacto
Si tienes alguna pregunta o sugerencia, no dudes en contactarme a través de los siguientes medios:
### [![Correo Electrónico](https://img.shields.io/badge/Outlook-WilmarDeML-orange?style=for-the-badge&logo=microsoft-outlook&logoColor=white)](mailto:carewaz23@hotmail.com)
### [![GitHub](https://img.shields.io/badge/GitHub-WilmarDeML-black?style=for-the-badge&logo=github)](https://www.github.com/WilmarDeML)
### [![LinkedIn](https://img.shields.io/badge/LinkedIn-WilmarDeML-blue?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/in/wilmardeml-dev/)

## 👨‍💻 Autores
### [![GitHub](https://img.shields.io/badge/GitHub-WilmarDeML-black?style=for-the-badge&logo=github)](https://www.github.com/WilmarDeML)
### [![LinkedIn](https://img.shields.io/badge/LinkedIn-WilmarDeML-blue?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/in/wilmardeml-dev/)

## 📜 Licencia
Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.
