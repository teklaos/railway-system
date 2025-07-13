# Railway System

**Railway System** is a Java project that simulates trains travelling between stations while managing locomotives and multiple types of railroad cars. It is built to model object-oriented design patterns and multithreaded simulation for railway operations.

## Key Features

- Manages a dynamic network of stations with defined connecting routes
- Configures and assigns locomotives to specific routes
- Supports multiple railroad car types with operational constraints and capacity limits
- Assembles train sets from locomotives and railroad cars
- Simulates concurrent train movements with multithreaded execution
- Logs the entire application state periodically to a file with synchronized access

## Technical Highlights

- **Object-Oriented Programming**: Encapsulation, inheritance, and polymorphism
- **Multithreading**: Thread lifecycle management and synchronization
- **Exception Handling**: Custom exceptions enforcing domain rules and safety constraints
- **Collections**: Java `List` with custom `Comparator` for entity management and sorting
- **I/O Operations**: Thread-safe, periodic state logging to file
- **Design Patterns**: Modular CLI architecture and coordinated thread management

## Installation

Steps to install and run the **Railway System** project:

1. Clone the repository to your local machine:
```
git clone https://github.com/teklaos/railway-system.git
```

2. Navigate to the project directory:
```
cd railway-system
```

3. Compile the code:
```
javac -d out/production/Trains src/cars/*.java src/exceptions/*.java src/models/*.java src/*.java
```

4. Run the program:
```
java -cp out/production/Trains Main
```
