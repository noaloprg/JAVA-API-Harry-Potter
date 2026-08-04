# ORM Harry Potter API

REST API built with **Java and Spring Boot**, set in the Harry Potter universe, using **JPA/Hibernate** on top of a relational **PostgreSQL** database.

## **Table of contents**

- [Technologies](#technologies)

- [Data model and relationships](#data-model-and-relationships)

- [Features and main endpoints](#features-and-main-endpoints)
  - [Features](#features)
  - [Endpoints](#main-endpoints)

- [Project structure](#project-structure)
- [Running app in local](#run-application-in-local)
- [Running with Docker](#running-with-docker)

## Technologies

| Technology      | Detail                        |
| --------------- | ----------------------------- |
| **Language**    | Java 17                       |
| **Framework**   | Spring Boot 4.0.1             |
| **Persistence** | Spring Data JPA / Hibernate   |
| **Database**    | PostgreSQL                    |
| **Validation**  | Jakarta Validation (`@Valid`) |
| **Build tool**  | Gradle (Kotlin DSL)           |
| **Other**       | DevTools, Jackson (JSON)      |

## Data model and relationships

```
House (1) ──────< (N) Character
Character (1) ──────< (N) Wand        (a wand belongs to a single character, optional)
Character (N) ──────< (N) Spell       (join table: character_spell)
```

- **House → Character**: one-to-many relationship. A house cannot be deleted while cascading the deletion of its characters (`CascadeType.REMOVE` is avoided on purpose).
- **Character → Wand**: optional one-to-many relationship. A wand can exist without an owner and can be reassigned to another character.
- **Character ↔ Spell**: many-to-many relationship through the join table `character_spell`.

## Features and main endpoints

### **Features**

- **Full CRUD** for Houses, Characters, Wands, and Spells.
- **Combined creation**: create a character together with its wand in a single transactional request.
- **Wand assignment** to characters, with business rule validations:
  - A wand that is already in use by another character cannot be assigned.
  - A broken wand cannot be assigned.
- **Breaking a wand** (marking it as `broken`) without losing the rest of its data.
- **Search and filters**:
  - Characters by name (partial match) or by house.
  - Wands by core, by status (broken / not broken), or whether they're assigned to a character or not.
- **Pagination and sorting** for spells (two variants: simple pagination and pagination + ascending/descending order).
- **Bulk spell creation**, avoiding duplicates by name.
- **Centralized error handling** with `@ControllerAdvice`, returning clear messages for:
  - Resource not found
  - Relationship already exists / wand already assigned
  - Broken wand cannot be assigned
  - Duplicate resource
  - DTO validation errors (`@Valid`)
- **Preloaded sample data** (`data.sql`) with the 4 houses, several canonical characters (Harry Potter, Hermione, Draco, etc.), and sample wands.

### **Main endpoints**

- Base URL: `http://localhost:8050/api`

#### **Houses — `/casas`**

| Method | Route         | Description           |
| ------ | ------------- | --------------------- |
| GET    | `/casas`      | Lists all houses      |
| POST   | `/casas`      | Creates a house       |
| DELETE | `/casas/{id}` | Deletes a house by id |

#### **Characters — `/personajes`**

| Method | Route                                         | Description                                      |
| ------ | --------------------------------------------- | ------------------------------------------------ |
| GET    | `/personajes`                                 | Lists all characters                             |
| GET    | `/personajes/{id}`                            | Gets a character by id                           |
| GET    | `/personajes/nombre/{palabra}`                | Searches characters whose name contains the word |
| GET    | `/personajes/casa/{nombreCasa}`               | Lists the characters of a house                  |
| POST   | `/personajes`                                 | Creates a character                              |
| POST   | `/personajes/crear-con-varita`                | Creates a character together with its wand       |
| PUT    | `/personajes/{idPersonaje}/varita/{idVarita}` | Assigns an existing wand to a character          |

#### **Wands — `/varitas`**

| Method | Route                                     | Description                              |
| ------ | ----------------------------------------- | ---------------------------------------- |
| GET    | `/varitas`                                | Lists all wands                          |
| GET    | `/varitas/{id}`                           | Gets a wand by id                        |
| GET    | `/varitas/estado?rota=`                   | Filters broken / not broken wands        |
| GET    | `/varitas/nucleo?nucleo=`                 | Searches wands by core                   |
| GET    | `/varitas/resumen`                        | Summary of all wands                     |
| GET    | `/varitas/ordenadas?descendente=&usadas=` | Wands sorted by length, assigned or free |
| POST   | `/varitas`                                | Creates a wand                           |
| PUT    | `/varitas/{id}`                           | Updates a wand                           |
| PUT    | `/varitas/varita/romper/{id}`             | Marks a wand as broken                   |

#### **Spells — `/hechizos`**

| Method | Route                                       | Description                            |
| ------ | ------------------------------------------- | -------------------------------------- |
| GET    | `/hechizos`                                 | Lists all spells                       |
| GET    | `/hechizos/paginados`                       | Paginated list (5 per page by default) |
| GET    | `/hechizos/paginados2?ordenacion=asc\|desc` | Paginated and sorted by name           |
| POST   | `/hechizos/crear-masivo`                    | Creates several spells at once         |

### **Error handling**

All business errors return a clear JSON response with `Error` and `Message`, handled centrally:

- `ResourceNotFound` → resource (house, character, wand...) not found.
- `AlreadyExistsException` → the resource already exists (e.g. duplicate spell).
- `AlreadyAssignedExcepction` → the wand is already assigned to another character.
- `BrokenWandException` → trying to assign a broken wand.
- Validation errors (`@Valid`) → returns the details of the invalid fields.

## Project structure

```
src/main/java/lopez/noa/OrmHarryPotterApp/
├── OrmHarryPotterAppApplication.java   # Main class (Spring Boot bootstrap)
├── Controller/                         # REST controllers
│   ├── CasaController.java
│   ├── PersonajeController.java
│   ├── VaritaController.java
│   └── HechizoController.java
├── Servicios/                          # Business logic
│   ├── IModeloService.java             # Common interface (getAll, getById, deleteById)
│   ├── CasaService.java
│   ├── PersonajeService.java
│   ├── VaritaService.java
│   └── HechizoService.java
├── Repositorios/                       # Spring Data JPA interfaces
│   ├── CasaRepository.java
│   ├── PersonajeRepository.java
│   ├── VaritaRepository.java
│   └── HechizoRepository.java
├── Modelos/                            # JPA entities
│   ├── Casa.java
│   ├── Personaje.java
│   ├── Varita.java
│   ├── Hechizo.java
│   ├── TipoSangre.java
│   └── TipoHechizo.java
├── DTO/                                 # Data transfer objects (input/output)
│   ├── CasaDTO/
│   ├── PersonajeDTO/
│   ├── VaritaDTO/
│   └── HechizoDTO/
├── Mappers/                             # Entity <-> DTO conversion
│   ├── CasaMapper.java
│   ├── PersonajeMapper.java
│   ├── VaritaMapper.java
│   ├── VaritaMapperHelper.java
│   ├── HechizoMapper.java
│   └── DataHelper.java
└── Exception/                           # Custom exceptions + global handler
    ├── ResourceNotFound.java
    ├── AlreadyExistsException.java
    ├── AlreadyAssignedExcepction.java
    ├── BrokenWandException.java
    └── HarryPotterExceptionHandler.java

src/main/resources/
├── application.properties               # App and database configuration
└── data.sql                             # Sample data (houses, characters, wands)
```

## Run application in local

### Prerequisites

- **JDK 17** or higher.
- **PostgreSQL** running / installed (local or remote).
- No need to install Gradle: the project includes the wrapper (`gradlew` / `gradlew.bat`).

### Database setup

Before starting, create a PostgreSQL database named `ad_harry_potter` (or adjust the name in `application.properties`):

```sql
CREATE DATABASE ad_harry_potter;
```

Review/edit `src/main/resources/application.properties` with your credentials.

> [!NOTE]
> the two `spring.datasource.url` options above: use the `host.docker.internal` one when the **application** runs inside a Docker container (see [Running with Docker](#running-with-docker)) and needs to reach a PostgreSQL instance running on your host machine; use `localhost` when running the app directly on your machine with Gradle.

### Running

```bash
# Linux / macOS
./gradlew bootRun

# Windows
./gradlew.bat bootRun
```

The API will start at:

```
http://localhost:8050/api
```

(port `8050` and prefix `/api`, defined in `application.properties`).

### Initial data

The project includes a `data.sql` script with sample data. It is disabled by default (`spring.sql.init.mode=never`) to avoid duplicates on restart, since `ddl-auto=update` does not delete existing data. If you want to load the sample data on the very first run, set that property to `always` before the first startup.

## Running with Docker

The project includes a `Dockerfile` to build and run the application in a container. **Only the application itself is containerized here** — PostgreSQL is expected to run separately (locally on your host, or in its own container).

What this image does:

1. Starts from an official `gradle:7.2.0-jdk17-alpine` image (from Docker Hub) that already includes Gradle and JDK 17.
2. Copies only the Gradle wrapper/config files first and runs `./gradlew dependencies`, so dependencies get cached in a Docker layer and aren't re-downloaded on every rebuild unless those files change.
3. Copies the actual source code (`src`) and builds the executable jar with `./gradlew bootJar`, skipping tests (`-x test`) to keep the build fast.
4. Exposes port `8050` (must match `server.port` in `application.properties`).
5. Runs the generated jar with `java -jar`.

### Build and run the container

```bash
# Build the image
docker build -t orm-harry-potter-app .

# Run the container
docker run -p 8050:8050 orm-harry-potter-app
```

Since PostgreSQL runs outside this container, `application.properties` points to it via `host.docker.internal`, which lets a container reach services running on your host machine (works out of the box on Docker Desktop for Windows/Mac; on Linux you may need to add `--add-host=host.docker.internal:host-gateway` to the `docker run` command).

> [!TIP]
> If you'd rather containerize PostgreSQL as well (so both the app and the database run in Docker), you'd need a `docker-compose.yml` with both services and change the datasource URL to point to the Postgres service name instead of `host.docker.internal`. Let me know if you want that set up too.
