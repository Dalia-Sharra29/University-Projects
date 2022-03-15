# Students Register Management
## Problem Statements
A faculty stores information about:
- **Student**: `student_id`, `name`
- **Discipline**: `discipline_id`, `name`
- **Grade**: `discipline_id`, `student_id`, `grade_value`

Create an application to:
1. Manage students and disciplines. The user can add, remove, update, and list both students and disciplines.
2. Grade students at a given discipline. Any student may receive one or several grades at any discipline. Deleting a student also removes their grades. Deleting a discipline deletes all grades at that discipline for all students.
3. Search for disciplines/students based on ID or name/title. The search must work using case-insensitive, partial string matching, and must return all matching disciplines/students.
4. Create statistics:
    - All students failing at one or more disciplines (students having an average <5 for a discipline are failing it)
    - Students with the best school situation, sorted in descending order of their aggregated average (the average between their average grades per discipline)
    - All disciplines at which there is at least one grade, sorted in descending order of the average grade received by all students enrolled at that discipline
5. Unlimited undo/redo functionality. Each step will undo/redo the previous operation performed by the user. Undo/redo operations must cascade and have a memory-efficient implementation (no superfluous list copying)

## Requirements
- Use simple feature-driven development
- Your program must provide a menu-driven console-based user interface
- Implementation must employ layered architecture and classes
- Provide specification and tests for all non-UI classes and methods for the first functionality
- Implement and use your own exception classes
- Implement **PyUnit test cases**

## Additional Implementations
1. Persistent storage for all entities using file-based repositories. A `settings.properties` file to configure your application. The program must work the same way using in-memory repositories, text-file repositories and binary file repositories. The decision of which repositories are employed, as well as the location of the repository input files will be made in the program’s `settings.properties` file. An example is below:

    a. `settings.properties` for loading from memory (input files are not required):
    ```
    repository = inmemory
    cars = “”
    clients = “”
    rentals = “”
    ```
    b. `settings.properties` for loading from binary files:
    ```
    repository = binaryfiles
    cars = “cars.pickle”
    clients = “clients.pickle”
    rentals = “rentals.pickle”
    ```
2. Python module that contains an iterable data structure, a sort method and a filter method, together with complete PyUnit unit tests (100% coverage). The module can be reused in other projects. This data structure is used for storing objects in the repository and both functions are used in the repository and service layer.
