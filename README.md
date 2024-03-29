# # Cinema Web App

## Overview

This project is a web application developed using Spring Boot for the backend, React for the frontend, and Bootstrap for styling. It aims to provide users with functionalities to manage cinema-related data, including movies, projections, genres, and user accounts.

## Features

- **Entity Management**: Users can create, update, delete, and search for movies, projections, and genres.
- **User Management**: User registration, profile update, listing, and deletion functionalities are provided.
- **Advanced Search**: Users can perform advanced searches based on various criteria such as movie title, genre, projection date, and ticket price.
- **User-Friendly Interface**: The application offers an intuitive and easy-to-use interface for seamless interaction.
- **Validation and Alerts**: Input forms are validated, and alerts are displayed for validation errors.
- **Spinner Component**: A spinner component indicates ongoing operations to enhance user experience.
- **Modal Window**: Detailed information about projections is displayed using a modal window.
  
## Project Structure

The project is structured into backend and frontend components, each serving specific purposes:

- **Backend**:
  - Spring Boot is used to implement RESTful APIs and handle backend logic.
  - Service classes manage data entities and implement business logic.
  - Controllers handle HTTP requests and delegate tasks to service classes.
  - Sample data initialization is done using data.sql file.

- **Frontend**:
  - React is used to build the user interface components.
  - Components are organized to display movies, projections, genres, and user-related functionalities.
  - Forms are provided for user input and interaction with the backend.
  - Bootstrap is utilized for styling and layout management.
  


## Task Breakdown

- **Entity Management**:
  - CRUD operations for movies, projections, and genres are implemented.
  - Advanced search functionalities are provided based on user-defined criteria.

- **User Management**:
  - User registration, profile update, listing, and deletion functionalities are implemented.

- **Frontend Development**:
  - Components are created for displaying and interacting with movies, projections, and genres.
  - Forms are developed for adding, updating, and deleting data entities.
  - User-friendly features such as alerts, spinners, and modal windows are implemented.
  
  
## Usage

- Admins have access to all functionalities, including adding, modifying, and deleting performers and performances.
- Users can add new performances and modify existing performances.
- Only logged-in users have access to the application. Users log in via the login page.

- **Admin Credentials:**
    - Username: miroslav
    - Password: miroslav

- **User Credentials:**
    - Username: petar
    - Password: petar


