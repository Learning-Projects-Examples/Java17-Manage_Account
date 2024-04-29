# ManageAccount Project

## Overview
The ManageAccount project provides functionality for managing user accounts and their associated emails. It includes methods for adding users, adding emails to existing users, checking for user existence, retrieving user information, sorting users, and handling user data from a file.

## Usage
To use the ManageAccount project, follow these steps:

1. **Include the Project:** Import the ManageAccount project into your Java project or development environment.
    ```java
    ManageAccount manageAccount = new ManageAccount("user_data.txt");
    ```
2. **Add Users:** Add users to the system using the `addUser` method.
    ```java
    manageAccount.addUser("user123", "John", "Doe", "123 Main St");
    ```
3. **Add Emails to Users:** Add emails to existing users using the `addEmail` method.
    ```java
    manageAccount.addEmail("user123", "john@example.com");
    ```
4. **Retrieve User Information:**
   - **Check if a user exists:**
    ```java
    boolean exists = manageAccount.existsUser("user123");
    ```
   - **Retrieve a user's details:**
    ```java
    Optional<User> user = manageAccount.user("user123");
    ```
5. **Retrieve User Emails:**
    ```java
    String[] userEmails = manageAccount.userMails("user123");
    ```
6. **Retrieve Users:**
   - **Retrieve all users:**
    ```java
    User[] users = manageAccount.users(EnumSortType.SORT_ASCENDING);
    ```
   - **Retrieve the first user:**
    ```java
    User firstUser = manageAccount.firstUser();
    ```
   - **Retrieve the last user:**
    ```java
    User lastUser = manageAccount.lastUser();
    ```
   - **Retrieve a specified number of first or last users:**
    ```java
    User[] firstUsers = manageAccount.firstUsers(5);
    User[] lastUsers = manageAccount.lastUsers(5);
    ```
7. **Retrieve Discarded Rows:**
    ```java
    String[] discardedRows = manageAccount.discardedRows();
    ```
8. **Retrieve All Emails:**
    ```java
    String[] allEmails = manageAccount.allEmails();
    ```

## Exceptions
The ManageAccount project may throw the following exceptions:

- `FileIsEmpty`: Thrown when the provided file is empty.
- `FileNotFoundException`: Thrown when the provided file cannot be found.

## Dependencies
The ManageAccount project relies on the following dependencies:

- Java Standard Library

## Contributing
Contributions to the ManageAccount project are welcome. To contribute, follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes.
4. Submit a pull request.

## License
The ManageAccount project is licensed under the MIT License. See the LICENSE file for details.
