# Portfolio Website Backend

Spring Boot backend for the Ravi Saini portfolio website. It provides admin authentication, portfolio content APIs, contact message storage, certificate management, and file uploads for the Next.js frontend.

## What Was Added / Updated

- Added JWT-based admin login.
- Added seeded default admin user.
- Added profile API for hero/about/contact/social information.
- Added project CRUD APIs.
- Added certificate CRUD and certificate file upload APIs.
- Added general upload API for profile images, project images, and other admin uploads.
- Added contact message APIs so admin can view and delete form messages.
- Added dynamic About Section items with add, edit/update, and delete support.
- Added dynamic Work Experience entries with add, edit/update, and delete support.
- Added dynamic Skills entries with category, skill name, level, add, edit/update, and delete support.
- Added default seed data for projects, certificates, about cards, work experience, and skills.
- Added static serving for uploaded files from `/uploads/**`.
- Fixed local upload path handling to avoid upload API errors.
- Added H2 database support for local development.
- Updated security rules so public GET APIs work and admin write/delete APIs require authentication.
- Updated Maven wrapper command file and backend tests.

## Tech Stack

- Java 21
- Spring Boot 3.5
- Spring Web
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- H2 database for local development
- MySQL-compatible configuration support
- Maven

## Default Local Configuration

Backend runs on:

```text
http://localhost:8081
```

Local database:

```text
jdbc:h2:file:./data/portfolio;MODE=MySQL;AUTO_SERVER=TRUE
```

Uploaded files are stored in:

```text
uploads/
```

Uploaded files are served from:

```text
http://localhost:8081/uploads/file-name
```


## Getting Started

Run the backend:

```bash
mvn spring-boot:run
```

Or using the Maven wrapper:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Run tests:

```bash
mvn test
```

## Authentication

### Admin Login

```http
POST /auth/login
Content-Type: application/json
```

Request:

```json
{
  "username": "****",
  "password": "*******"
}
```

Response:

```json
{
  "message": "Login successful",
  "token": "jwt-token"
}
```

For protected APIs, send:

```http
Authorization: Bearer jwt-token
```

## API Endpoints

### Profile

Public read:

```http
GET /api/profile
```

Admin update:

```http
PUT /api/profile
Authorization: Bearer jwt-token
```

Profile controls:

- Full name
- Role
- Bio
- Email
- Phone
- Location
- GitHub URL
- LinkedIn URL
- Profile image
- About title and description
- Skills title and description
- Legacy skill text fields

### Uploads

Admin upload:

```http
POST /api/uploads
Authorization: Bearer jwt-token
Content-Type: multipart/form-data
```

Form field:

```text
file
```

Response:

```json
{
  "url": "/uploads/file-name.png"
}
```

### Projects

Public read:

```http
GET /api/projects
```

Admin add/update:

```http
POST /api/projects
Authorization: Bearer jwt-token
```

Admin delete:

```http
DELETE /api/projects/{id}
Authorization: Bearer jwt-token
```

Project fields:

- Title
- Description
- Technologies
- GitHub URL
- Live URL
- Image URL or uploaded image path

### About Section Items

Public read:

```http
GET /api/about-items
```

Admin add/update:

```http
POST /api/about-items
Authorization: Bearer jwt-token
```

Admin delete:

```http
DELETE /api/about-items/{id}
Authorization: Bearer jwt-token
```

About item fields:

- Title
- Description
- Icon label

### Work Experience

Public read:

```http
GET /api/experiences
```

Admin add/update:

```http
POST /api/experiences
Authorization: Bearer jwt-token
```

Admin delete:

```http
DELETE /api/experiences/{id}
Authorization: Bearer jwt-token
```

Experience fields:

- Title
- Company and period
- Description

### Skills

Public read:

```http
GET /api/skills
```

Admin add/update:

```http
POST /api/skills
Authorization: Bearer jwt-token
```

Admin delete:

```http
DELETE /api/skills/{id}
Authorization: Bearer jwt-token
```

Skill fields:

- Category
- Name
- Level

Example categories:

- Frontend
- Backend
- Database & Tools

### Certificates

Public read:

```http
GET /api/certificates
```

Admin add/update with JSON:

```http
POST /api/certificates
Authorization: Bearer jwt-token
```

Admin upload certificate file:

```http
POST /api/certificates/upload
Authorization: Bearer jwt-token
Content-Type: multipart/form-data
```

Admin delete:

```http
DELETE /api/certificates/{id}
Authorization: Bearer jwt-token
```

Certificate fields:

- Name / title
- Issuer
- Date
- Duration
- Location
- Type
- Description
- Image URL / uploaded file path

### Contact Messages

Public submit:

```http
POST /api/contact
Content-Type: application/json
```

Request:

```json
{
  "name": "Visitor Name",
  "email": "visitor@example.com",
  "phone": "9999999999",
  "subject": "Project inquiry",
  "message": "Message text"
}
```

Admin list messages:

```http
GET /api/contact
Authorization: Bearer jwt-token
```

Admin delete message:

```http
DELETE /api/contact/{id}
Authorization: Bearer jwt-token
```

## Seeded Default Data

When the app starts, it creates default data if missing:

- Admin user
- 3 default projects
- 3 default certificates
- 4 about cards
- 1 work experience entry
- 15 skill items

This keeps the portfolio populated on a fresh local database.

## Security Notes

- Public users can read portfolio content and submit the contact form.
- Admin login returns a JWT token.
- Admin write, upload, and delete operations require `Authorization: Bearer token`.
- Production deployments should set a strong `JWT_SECRET` and private admin credentials.

## Files and Runtime Folders

These runtime folders are ignored by Git:

```text
data/
uploads/
```

`data/` contains the local H2 database.

`uploads/` contains uploaded profile, project, and certificate files.

## Frontend Pairing

This backend is designed to run with the frontend project:

```text
../Portfolio-website
```

Start backend first:

```bash
mvn spring-boot:run
```

Then start frontend:

```bash
npm run dev
```

Frontend local URL:

```text
http://localhost:3000
```
