# Kata Application

This is a simple Library application built using Java 21, Spring Boot, and an in-memory H2 database. 
## Features

- Add a new book to the catalog
- Borrow a book
- Return a book
- List all books borrowed by a user

## Requirements

- Java 21
- Maven

## Getting Started

1. **Clone the repository:**

    ```bash
    git clone https://github.com/yourusername/library-application.git
    cd library-application
    ```

2. **Build and run the application:**

    ```bash
    mvn spring-boot:run
    ```

   The application will start on `http://localhost:8080`.

## Accessing the H2 Database

You can access the H2 database console through your web browser:

- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** (leave this blank)

## Initial Data Inserted by the Application

When the application starts, it automatically inserts the following data into the H2 database:

### Books

| ID | Title                                      | Is Borrowed |
|----|--------------------------------------------|-------------|
| 1  | Harry Potter and the Philosopher's Stone   | false       |
| 2  | Harry Potter and the Chamber of Secrets    | false       |
| 3  | Harry Potter and the Prisoner of Azkaban   | false       |

### Users

| ID | Name  | Borrowed Book IDs |
|----|-------|-------------------|
| 1  | user1 | []                |
| 2  | user2 | []                |

This data is automatically inserted via the `DataInitializer` class using a `CommandLineRunner`.

## API Endpoints

Here are the endpoints exposed by the application:

### 1. Add a New Book

- **Endpoint:** `POST /library/addBook`
- **Request Body:**

    ```json
    {
      "title": "Book Title",
      "author": "Author Name"
    }
    ```

- **Response:**

    ```json
    {
      "id": 4,
      "title": "Book Title",
      "author": "Author Name",
      "isBorrowed": false
    }
    ```

### 2. Borrow a Book

- **Endpoint:** `POST /library/borrowBook`
- **Parameters:**
    - `userId`: The ID of the user borrowing the book.
    - `bookId`: The ID of the book to be borrowed.

- **Example Request:**

    ```
    POST /library/borrowBook?userId=1&bookId=1
    ```

- **Response:**

    ```json
    {
      "id": 1,
      "name": "John Doe",
      "borrowedBookIds": [1]
    }
    ```

### 3. Return a Book

- **Endpoint:** `POST /library/returnBook`
- **Parameters:**
    - `userId`: The ID of the user returning the book.
    - `bookId`: The ID of the book to be returned.

- **Example Request:**

    ```
    POST /library/returnBook?userId=1&bookId=1
    ```

- **Response:**

    ```json
    {
      "id": 1,
      "name": "John Doe",
      "borrowedBookIds": []
    }
    ```

### 4. List All Borrowed Books by a User

- **Endpoint:** `GET /library/borrowedBooks`
- **Parameters:**
    - `userId`: The ID of the user.

- **Example Request:**

    ```
    GET /library/borrowedBooks?userId=1
    ```

- **Response:**

    ```json
    [
      {
        "id": 1,
        "title": "Harry Potter and the Philosopher's Stone",
        "author": "J.K. Rowling",
        "isBorrowed": true
      }
    ]
    ```
## Postman Collection

You can find a Postman collection for testing all the API endpoints in the `src/main/resources` directory.

## Running Tests

To run the tests for this application, use the following command:

```bash
mvn test
