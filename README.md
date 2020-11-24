# Personal Library

The application that I will be creating is a basic library application that saves and stores a collection of books. This application will be useful to anyone that enjoys reading and anyone that would benefit from being able to organize their current list of books. 

**Main Features**

* Add information to each book such as:
  * Title
  * Author
  * Genre(s)
  * \# of Pages
  * Rating /10
  * Whether book has been read

I came up with the idea for this project because I have a list of books that I read myself, but I am organizing them all in a note-taking app which isn't the most ideal place. There doesn't seem to be a great deal of applications like this already, so I thought it would be a fun idea to try.

## User Stories

* As a user, I want to be able to view my library where each book displays a title and an author
* As a user, I want to be able to add and remove books from my library
* As a user, I want to be able to view the information about a book
* As a user, I want to be able to edit the information about a book
* As a user, I want to be able to save my library to a file
* As a user, I want to be able to load my library from a file

## Phase 4: Task 2

For task 2, I decided to implement a type hierarchy involving the BookPanel, AddBookPanel, and CurrentBookPanel classes. This was done in order to improve cohesion within the main GUI class and to reduce the duplication of code involving displaying a book panel.

## Phase 4: Task 3

-   In the Book class, I would add exception handling to the constructor and setters for the pages and rating fields to ensure that the value of pages is always greater than 0 and that the rating field is always between 0 and 10.
-   In the Book class, I would change the genres field type from a list to a set since there should never be duplicate genres.