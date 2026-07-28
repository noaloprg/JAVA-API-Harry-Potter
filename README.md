# ORM Harry Potter API

API REST desarrollada en **Java con Spring Boot**, temática del universo de Harry Potter mediante **JPA/Hibernate** sobre una base de datos relacional con **PostgreSQL**.

## **Indice**

- [Modelado de datos y relaciones](#️-modelo-de-datos-y-relaciones)

- [Funcionalidades y endpoints principales](#funcionalidades-y-endpoints-principales)
    - [Funcionalidades](#funcionalidades)
    - [Endpoints](#principales-endpoints)

- [Estructura del proyecto](#estructura-del-proyecto)
- [Puesta en marcha](#puesta-en-marcha)

## Modelo de datos y relaciones

```
Casa (1) ──────< (N) Personaje
Personaje (1) ──────< (N) Varita        (una varita pertenece a un único personaje, opcional)
Personaje (N) ──────< (N) Hechizo       (tabla intermedia: personaje_hechizo)
```

- **Casa → Personaje**: relación uno a muchos. Una casa no puede eliminarse en cascada arrastrando a sus personajes (se evita evitando `CascadeType.REMOVE`).
- **Personaje → Varita**: relación uno a muchos opcional. Una varita puede existir sin dueño y puede reasignarse a otro personaje.
- **Personaje ↔ Hechizo**: relación muchos a muchos mediante tabla intermedia `personaje_hechizo`.

## Funcionalidades y endpoints principales

### **Funcionalidades**

- **CRUD completo** de Casas, Personajes, Varitas y Hechizos.
- **Creación combinada**: alta de un personaje junto con su varita en una sola petición transaccional.
- **Asignación de varitas** a personajes, con validaciones de negocio:
    - No se puede asignar una varita que ya está en uso por otro personaje.
    - No se puede asignar una varita rota.
- **Romper una varita** (marcarla como `rota`) sin perder el resto de sus datos.
- **Búsquedas y filtros**:
    - Personajes por nombre (coincidencia parcial) o por casa.
    - Varitas por núcleo, por estado (rotas / no rotas), o si están asignadas a un personaje o no.
- **Paginación y ordenación** de hechizos (dos variantes: paginado simple y paginado + orden ascendente/descendente).
- **Alta masiva de hechizos**, evitando duplicados por nombre.
- **Manejo centralizado de errores** con `@ControllerAdvice`, devolviendo mensajes claros para:
    - Recurso no encontrado
    - Relación ya existente / varita ya asignada
    - Varita rota no asignable
    - Recurso duplicado
    - Errores de validación de los DTOs (`@Valid`)
- **Datos de ejemplo precargados** (`data.sql`) con las 4 casas, varios personajes canónicos (Harry Potter, Hermione, Draco, etc.) y varitas de ejemplo.

### **Principales endpoints**

#### **Casas — `/api/casas`**

| Método | Ruta          | Descripción             |
| ------ | ------------- | ----------------------- |
| GET    | `/casas`      | Lista todas las casas   |
| POST   | `/casas`      | Crea una casa           |
| DELETE | `/casas/{id}` | Elimina una casa por id |

#### **Personajes — `/api/personajes`**

| Método | Ruta                                          | Descripción                                      |
| ------ | --------------------------------------------- | ------------------------------------------------ |
| GET    | `/personajes`                                 | Lista todos los personajes                       |
| GET    | `/personajes/{id}`                            | Obtiene un personaje por id                      |
| GET    | `/personajes/nombre/{palabra}`                | Busca personajes cuyo nombre contenga la palabra |
| GET    | `/personajes/casa/{nombreCasa}`               | Lista los personajes de una casa                 |
| POST   | `/personajes`                                 | Crea un personaje                                |
| POST   | `/personajes/crear-con-varita`                | Crea un personaje junto con su varita            |
| PUT    | `/personajes/{idPersonaje}/varita/{idVarita}` | Asigna una varita existente a un personaje       |

#### **Varitas — `/api/varitas`**

| Método | Ruta                                      | Descripción                                     |
| ------ | ----------------------------------------- | ----------------------------------------------- |
| GET    | `/varitas`                                | Lista todas las varitas                         |
| GET    | `/varitas/{id}`                           | Obtiene una varita por id                       |
| GET    | `/varitas/estado?rota=`                   | Filtra varitas rotas / no rotas                 |
| GET    | `/varitas/nucleo?nucleo=`                 | Busca varitas por núcleo                        |
| GET    | `/varitas/resumen`                        | Resumen de todas las varitas                    |
| GET    | `/varitas/ordenadas?descendente=&usadas=` | Varitas ordenadas por longitud, usadas o libres |
| POST   | `/varitas`                                | Crea una varita                                 |
| PUT    | `/varitas/{id}`                           | Actualiza una varita                            |
| PUT    | `/varitas/varita/romper/{id}`             | Marca una varita como rota                      |

#### **Hechizos — `/api/hechizos`**

| Método | Ruta                                        | Descripción                               |
| ------ | ------------------------------------------- | ----------------------------------------- |
| GET    | `/hechizos`                                 | Lista todos los hechizos                  |
| GET    | `/hechizos/paginados`                       | Lista paginada (5 por página por defecto) |
| GET    | `/hechizos/paginados2?ordenacion=asc\|desc` | Paginada y ordenada por nombre            |
| POST   | `/hechizos/crear-masivo`                    | Crea varios hechizos a la vez             |

### **Manejo de errores**

Todos los errores de negocio devuelven una respuesta JSON clara con `Error` y `Message`, gestionados de forma centralizada:

- `ResourceNotFound` → recurso (casa, personaje, varita...) no encontrado.
- `AlreadyExistsException` → el recurso ya existe (p. ej. hechizo duplicado).
- `AlreadyAssignedExcepction` → la varita ya está asignada a otro personaje.
- `BrokenWandException` → se intenta asignar una varita rota.
- Errores de validación (`@Valid`) → devuelve el detalle de los campos inválidos.

## Tecnologías

| Tecnología        | Detalle                       |
| ----------------- | ----------------------------- |
| **Lenguaje**      | Java 17                       |
| **Framework**     | Spring Boot 4.0.1             |
| **Persistencia**  | Spring Data JPA / Hibernate   |
| **Base de datos** | PostgreSQL                    |
| **Validación**    | Jakarta Validation (`@Valid`) |
| **Build tool**    | Gradle (Kotlin DSL)           |
| **Otros**         | DevTools, Jackson (JSON)      |

## Estructura del proyecto

```
src/main/java/lopez/noa/OrmHarryPotterApp/
├── OrmHarryPotterAppApplication.java   # Clase principal (arranque de Spring Boot)
├── Controller/                         # Controladores REST
│   ├── CasaController.java
│   ├── PersonajeController.java
│   ├── VaritaController.java
│   └── HechizoController.java
├── Servicios/                          # Lógica de negocio
│   ├── IModeloService.java             # Interfaz común (getAll, getById, deleteById)
│   ├── CasaService.java
│   ├── PersonajeService.java
│   ├── VaritaService.java
│   └── HechizoService.java
├── Repositorios/                       # Interfaces Spring Data JPA
│   ├── CasaRepository.java
│   ├── PersonajeRepository.java
│   ├── VaritaRepository.java
│   └── HechizoRepository.java
├── Modelos/                            # Entidades JPA
│   ├── Casa.java
│   ├── Personaje.java
│   ├── Varita.java
│   ├── Hechizo.java
│   ├── TipoSangre.java
│   └── TipoHechizo.java
├── DTO/                                 # Objetos de transferencia (entrada/salida)
│   ├── CasaDTO/
│   ├── PersonajeDTO/
│   ├── VaritaDTO/
│   └── HechizoDTO/
├── Mappers/                             # Conversión Entidad <-> DTO
│   ├── CasaMapper.java
│   ├── PersonajeMapper.java
│   ├── VaritaMapper.java
│   ├── VaritaMapperHelper.java
│   ├── HechizoMapper.java
│   └── DataHelper.java
└── Exception/                           # Excepciones personalizadas + handler global
    ├── ResourceNotFound.java
    ├── AlreadyExistsException.java
    ├── AlreadyAssignedExcepction.java
    ├── BrokenWandException.java
    └── HarryPotterExceptionHandler.java

src/main/resources/
├── application.properties               # Configuración de la app y la BD
└── data.sql                             # Datos de ejemplo (casas, personajes, varitas)
```

## Puesta en marcha

### Requisitos previos

- **JDK 17** o superior.
- **PostgreSQL** en ejecución (local o remoto).
- No es necesario instalar Gradle: el proyecto incluye el wrapper (`gradlew` / `gradlew.bat`).

### Configuración de la base de datos

Antes de arrancar, crea una base de datos en PostgreSQL llamada `ad_harry_potter` (o ajusta el nombre en `application.properties`):

```sql
CREATE DATABASE ad_harry_potter;
```

Revisa/edita `src/main/resources/application.properties` con tus credenciales:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ad_harry_potter
spring.datasource.username=postgres
spring.datasource.password=openpgpwd
```

> ⚠️ Por seguridad, en un entorno real conviene mover usuario/contraseña a variables de entorno en lugar de dejarlos en texto plano en el repositorio.

### Ejecución

```bash
# Linux / macOS
./gradlew bootRun

# Windows
gradlew.bat bootRun
```

La API arrancará en:

```
http://localhost:8050/api
```

(puerto `8050` y prefijo `/api`, definidos en `application.properties`).

### Datos iniciales

El proyecto incluye un script `data.sql` con datos de ejemplo. Actualmente está desactivado por defecto (`spring.sql.init.mode=never`) para evitar duplicados al reiniciar la app, ya que `ddl-auto=update` no borra los datos existentes. Si quieres cargar los datos de ejemplo la primera vez, cambia esa propiedad a `always` antes del primer arranque.
