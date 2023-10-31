## Project Overview

This project is a web application for managing books and authors. It provides functionality to view, add, edit, and delete books and authors. The application follows the Model-View-Controller (MVC) architectural pattern, where:

- **Controller**: Handles HTTP requests, interacts with services, and manages views.
- **Service**: Contains the business logic and interacts with the repository.
- **DTO (Data Transfer Object)**: Represents data transferred between the Controller and Service layers.
- **Repository**: Provides access to the PostgreSQL database.
- **Thymeleaf**: Used for rendering views.
- **Bootstrap CSS**: Used for styling the web pages.

## Project Structure

The project structure is organized as follows:

- `com.example.books.controller`: Contains controllers for handling HTTP requests related to books and authors.
- `com.example.books.domain`: Contains the domain classes for books and authors.
- `com.example.books.domain.dto`: Contains DTOs for data transfer between layers.
- `com.example.books.exceptions`: Contains custom exception classes.
- `com.example.books.repos`: Contains the repository interfaces for books and authors.
- `com.example.books.service`: Contains the service classes for books and authors.
- `com.example.books.util`: Contains utility classes, such as date parsing.
- `resources/templates`: Contains Thymeleaf HTML templates for rendering views.
- `resources/static`: Contains static resources like CSS files.

## Controllers

- `AuthorController`: Handles requests related to authors, including listing authors, adding authors, and displaying author details.
- `BookController`: Handles requests related to books, including listing books, adding books, and displaying book details.

## Service Layer

The service layer contains classes responsible for business logic:

- `AuthorService`: Manages author-related operations.
- `BookService`: Manages book-related operations.

## DTOs

- `AuthorDTO`: Represents author data for data transfer.
- `BookDTO`: Represents book data for data transfer.

## Repository

- `AuthorRepo`: Provides access to the PostgreSQL database for author-related operations.
- `BookRepo`: Provides access to the PostgreSQL database for author-related operations.

## Utility Classes

- `DateParse`: Contains date parsing utility methods.

## Views

Thymeleaf templates are used for rendering views, and Bootstrap CSS is applied for styling. You can find these templates in the `resources/templates` directory.

## How to Run the Application

1. Clone the repository to your local machine.
2. Set up a PostgreSQL database and configure the database connection in the `application.properties` file.
3. Build and run the application using your favorite Java IDE or by using `mvn spring-boot:run` from the command line.
4. Access the application in a web browser at `http://localhost:8080`.

## Features

- View a list of authors and books.
- Add new authors and books.
- View details of individual authors and books.
- Edit author and book details.


## Contributing

If you want to contribute to this project, please fork the repository and create a pull request. We welcome your contributions!



## Contact

If you have any questions or suggestions, please feel free to contact us at kharitonoffps@yandex.com).

Happy coding!
