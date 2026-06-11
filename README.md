# futbol5-api

# Futbol 5 API

API REST en Spring Boot 3.5.14 para gestión de entrenamientos de fútbol 5 y selección de titulares.

## Tecnologías

- **Spring Boot** 3.5.14
- **Java** 21
- **MySQL** 8.0
- **JWT** (jjwt 0.12.6)
- **JPA/Hibernate**

## Requisitos

- Java 21+
- MySQL 8.0+
- Maven 3.6+

## Instalación

1. **Clonar el repositorio:**
```bash
git clone https://github.com/mentuscap777-lang/futbol5-api.git
cd futbol5-api
```

2. **Crear la base de datos:**
```sql
CREATE DATABASE futbol5_db;
```

3. **Configurar `application.yaml`:**
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/futbol5_db
    username: root
    password: TU_PASSWORD
```

4. **Compilar y ejecutar:**
```bash
mvn clean install
mvn spring-boot:run
```

La API estará en: `http://localhost:8080`

## Endpoints

### Autenticación

- **POST** `/api/v1/auth/register` — Registrar usuario
- **POST** `/api/v1/auth/login` — Login (obtener JWT)

### Entrenamientos

- **POST** `/api/v1/trainings` — Registrar entrenamiento (requiere rol ADMIN)
- **GET** `/api/v1/trainings/starters?weekNumber=1` — Obtener titulares

## Ejemplo de Uso

### Registrar Usuario
```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"user1","password":"pass123"}'
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Login
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user1","password":"pass123"}'
```

## Estructura del Proyecto
futbol5-api/
├── src/main/java/com/futbol_5/api/
│   ├── controller/
│   ├── service/
│   ├── entity/
│   ├── repository/
│   ├── security/
│   └── exception/
├── src/main/resources/
│   └── application.yaml
└── pom.xml

## Estado Actual

✅ Autenticación con JWT  
✅ Registro de usuarios  
⏳ Login completo  
⏳ Endpoints de entrenamientos  
⏳ Tests unitarios  

## Próximos Pasos

1. Frontend Vue.js
2. Docker & Docker Compose
3. Despliegue en servidor

## Autor

Sebastian Castro - SENA ADSO (Ficha 3292107)
