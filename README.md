# CarTechnical  
A Vehicle Maintenance & Service Scheduling Platform

CarTechnical is a web-based platform designed to streamline communication and operations between vehicle owners, mechanics, and administrators.  
It helps users register vehicles, schedule maintenance, track service requests, and manage mechanic workflows efficiently.

This project is built using Java 21, Spring Boot, Thymeleaf, and MariaDB.

---

## Table of Contents  
- [CarTechnical](#cartechnical)
  - [Table of Contents](#table-of-contents)
- [Overview](#overview)
    - [Target Audience](#target-audience)
- [Features](#features)
    - [For Owners](#for-owners)
    - [For Mechanics](#for-mechanics)
    - [For Admins](#for-admins)
    - [Authentication \& Security](#authentication--security)
- [User Roles \& Capabilities](#user-roles--capabilities)
- [System Pages \& Content Plan](#system-pages--content-plan)
    - [Main Pages](#main-pages)
    - [Content Responsibility](#content-responsibility)
- [Technology Stack](#technology-stack)
    - [Backend](#backend)
    - [Frontend](#frontend)
    - [Build](#build)
- [Architecture](#architecture)
    - [Structure](#structure)
  - [Clone the repository](#clone-the-repository)
  - [Run with Maven](#run-with-maven)
  - [Access the app](#access-the-app)
  - [Admin Prioritization Strategies](#admin-prioritization-strategies)
    - [Earliest Available](#earliest-available)
    - [Priority-Based](#priority-based)
  - [Maintenance \& Future Development](#maintenance--future-development)
    - [Multilanguage Support](#multilanguage-support)
    - [Notifications via email and frontend](#notifications-via-email-and-frontend)

---

# Overview

CarTechnical’s primary purpose is to simplify and organize vehicle maintenance workflows:

- Owners can track their vehicles and request maintenance.
- Mechanics can work on assigned jobs, update progress, and submit final service reports.
- Admins coordinate assignments, prioritize jobs, and view statistics.

### Target Audience  
- Car owners looking for an easy way to manage vehicle maintenance  
- Mechanics who need a digital workflow solution  
- Small garages and service shops  
- Administrators managing maintenance workflows  

Potential users may discover the platform through social media groups, car communities, local repair shops, or technical portfolios.

---

# Features

### For Owners  
- Register and manage multiple vehicles  
- Submit maintenance requests  
- View history and statuses  
- Receive notifications  

### For Mechanics  
- View assigned requests  
- Update request progress  
- Submit reports (notes, parts used, cost)  
- Mark requests as completed  

### For Admins  
- See all non-completed requests  
- Assign mechanics  
- Prioritize requests  
- Dashboard with real-time statistics:
  - Total requests  
  - Pending requests  
  - Completed requests  
  - Most common service type  

### Authentication & Security  
- Spring Security with roles: OWNER, MECHANIC, ADMIN  
- Thymeleaf security extras for role-based UI control  

---

# User Roles & Capabilities

| Role | Description | Capabilities |
|------|-------------|--------------|
| Owner | Vehicle owner | Add vehicles, submit maintenance requests, view history |
| Mechanic | Performs maintenance | Update assigned requests, submit reports, mark completed |
| Admin | Oversees workflow | Assign mechanics, prioritize requests, view stats |

---

# System Pages & Content Plan

### Main Pages  
- Login / Register  
- Owner dashboard  
- Mechanic dashboard  
- Admin dashboard  
- Vehicles CRUD  
- Requests CRUD  
- Reports

### Content Responsibility  
- Admins manage service types and system info  
- Mechanics update service logs  
- Owners add vehicle details  
- Developers update UI, logic, and structure  

User-generated content happens daily; admin and developer content is updated weekly or monthly.

---

# Technology Stack

### Backend  
- Java 21  
- Spring Boot 3.5.6  
  - MVC  
  - Security  
  - JPA  
  - Validation  
- MariaDB

### Frontend  
- Thymeleaf  
- Bootstrap 5 (WebJars)  
- Thymeleaf Spring Security Extras  

### Build  
- Maven  

---

# Architecture

### Structure  
- MVC pattern  
- Services and repositories separated  
- DTOs for data transfer  
- Role-based access control  

---

## Clone the repository

```sh
git clone https://github.com/Pica-Eduard-Ionut/ProjectCarTechnical.git
cd ProjectCarTechnical
```

## Run with Maven
```sh
mvn spring-boot:run
```
## Access the app
```sh
http://localhost:8080
```

## Admin Prioritization Strategies
### Earliest Available
Sorts and assigns requests based on request creation time and mechanic availability.
### Priority-Based
Considers severity, vehicle type, and urgency level chosen by the owner.

## Maintenance & Future Development
### Multilanguage Support
Planned via Spring Boot i18n using messages.properties files.
### Notifications via email and frontend
