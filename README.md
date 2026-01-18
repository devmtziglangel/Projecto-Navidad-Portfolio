# Projecto-Navidad-Portfolio

# 🚀 Backend Portfolio - Spring Boot & PostgreSQL (Cloud)

### 📝 Introducción y Objetivos
El objetivo de estas prácticas de Navidad era intentar entender cómo enlazar las asignaturas de **Bases de Datos, Programación y Lenguaje de Marcas**. Decidí hacerlo por dos motivos principales: 
1. Entender (o mejor dicho, ver) qué es realmente **Spring Boot**.
2. Perder el miedo a intentar hacer algo que escape de mis habilidades actuales. 

Soy consciente de que no habría sido capaz de realizar este proyecto sin seguir el curso o sin el apoyo de la IA. Hay muchas cosas (la mayoría) que aún no entiendo profundamente, pero mi meta era experimentar qué se siente al "hacer un proyecto real", conectando una base de datos profesional con un frontend funcional.

---

### 🏗️ 1. Arquitectura y Configuración Cloud
El proyecto se basa en **Spring Boot 4.0.1** y **Java 21**. La principal innovación fue sacar la base de datos de un entorno local:

* **Persistencia en Neon.tech:** Se utiliza **PostgreSQL** alojado en la nube. Configuré la conexión en `application.properties` usando **HikariCP**, un gestor que permite hasta 10 conexiones simultáneas (túneles) para que la web responda rápido aunque haya varios usuarios.
* **Inicialización Automatizada:** Gracias a los archivos `schema.sql` y `data.sql`, las tablas y los datos de prueba se generan automáticamente en la nube al arrancar el sistema.



---

### 🛠️ 2. El Flujo de Trabajo (Backend)
He aplicado una arquitectura de capas para organizar el código y asegurar que cada parte tenga una responsabilidad única:

* **Modelos (Entities):** Clases Java que representan mis datos (Experiencia, Educación, Skills, Proyectos).
* **Repositorios (JdbcTemplate):** Aquí es donde la programación se une con las BBDD. Uso `RowMapper` para traducir los resultados de las consultas SQL a objetos que Java pueda entender.
* **Servicios:** Capa donde reside la lógica. He aprendido a usar `@Transactional` para que, si algo falla al guardar, el sistema haga un "rollback" y no deje datos a medias.

---

### 🎨 3. Frontend y Lenguaje de Marcas (Thymeleaf)
Aquí es donde conectamos con la asignatura de Lenguaje de Marcas:

* **Thymeleaf:** Este motor de plantillas permite que el HTML sea dinámico. En lugar de escribir cada habilidad a mano, el código recorre la lista que viene de la BBDD.
* **Fragmentos:** Dividí la web en pequeñas piezas (`sections`) para que el código sea fácil de leer y mantener, usando un archivo `index.html` como contenedor principal.
* **Assets:** Gestión de CSS y recursos estáticos (imágenes de logos, fotos de proyectos) servidos directamente por Spring.

---

### 🛡️ 4. Seguridad y Transferencia de Datos (DTO)
Para mejorar la seguridad y no exponer directamente la estructura de mi base de datos, implementé el patrón **DTO (Data Transfer Object)**:

* **Mappers:** Clases especializadas en transformar las "Entidades" en "DTOs". Esto asegura que solo enviamos al frontend la información necesaria.
* **Validaciones:** Uso de `jakarta.validation` (`@NotBlank`, `@Min`, etc.) para que el sistema rechace automáticamente formularios mal rellenados, devolviendo mensajes de error claros.



---

### 📧 5. Integración de Contacto (Formspree)
Para el formulario de contacto, utilicé un endpoint externo de **Formspree**:
* **Endpoint:** `https://formspree.io/f/mdaakbep`
* Esta integración permite que el formulario de contacto sea 100% funcional, enviando los mensajes de los usuarios directamente a mi bandeja de entrada de forma segura, con protección contra spam y sin configurar servidores SMTP complejos.

---



### 🧪 6. Calidad mediante Testing
Implementé pruebas para asegurar que el código no se rompa al hacer cambios:
* **Tests de Integración:** Validan el flujo completo desde el Controller hasta la base de datos real en Neon.
* **Tests Unitarios (Mockito):** Prueban la lógica de los servicios aislando la base de datos mediante "Mocks".
