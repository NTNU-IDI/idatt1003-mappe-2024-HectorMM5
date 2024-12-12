# Food Registry Application

## Description
The **Food Registry Application** is a Java-based project designed to help users manage their groceries and recipes effectively. Users can:

- Add and manage items in their fridge.
- Search, update, and remove expired items.
- Plan and manage recipes using the available groceries.
- Automatically check and consume groceries needed for a recipe.

This project is built as part of a university assignment, following best practices for object-oriented programming, clean code, and modular design.

---

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [File Structure](#file-structure)
- [How to Run](#how-to-run)
- [Usage](#usage)
- [Author](#author)

---

## Features

### Fridge Management
- Add new groceries with properties like name, unit, amount, cost, and expiry date.
- Display a full list of groceries with their details.
- Search for specific items.
- View expired items and optionally delete them.
- Check the total value of groceries in the fridge.

### Recipe Management
- Create recipes with ingredients, portions, and instructions.
- View recipes that can be made with the current groceries.
- Cook recipes, which consumes the required ingredients.
- List all saved recipes and their details.
- Delete recipes.

---

## Technologies Used

- **Java**: Programming language.
- **Maven**: Build and dependency management tool.
- **JUnit**: Unit testing framework.
- **IntelliJ IDEA**: IDE for development.

---

## File Structure

```plaintext
src/
 ├── main/
 │   ├── java/
 │   │   ├── edu.ntnu.idi.idatt/
 │   │   │   ├── controller/    # Handles user interaction and input.
 │   │   │   ├── model/         # Data models like Grocery and Recipe.
 │   │   │   ├── service/       # Business logic for Fridge and CookBook.
 │   │   │   ├── storage/       # Data storage for recipes and groceries.
 │   │   │   ├── util/          # Utility classes for validation and common tasks.
 │   │   │   └── Main.java      # Entry point of the application.
 │   └── resources/             # Resources such as configuration files.
 └── test/                      # Unit tests for all major functionalities.
```

---

## How to Run

### Prerequisites

1. Ensure **Java 17** or later is installed.
2. Install **Maven** (https://maven.apache.org/download.cgi).
3. Clone the repository:

   ```bash
   git clone https://github.com/NTNU-IDI/idatt1003-mappe-2024-HectorMM5.git
   cd idatt1003-mappe-2024-HectorMM5
   ```

### Running the Application

1. Compile and package the project using Maven:
   ```bash
   mvn clean package
   ```

2. Run the application:
   ```bash
   java -cp target/idatt1003-mappe-2024-HectorMM5-1.0-SNAPSHOT.jar edu.ntnu.idi.idatt.Main
   ```

---

## Usage

### Main Menu
Once the application starts, you will see the following options:

1. **Fridge Management**: Add, manage, and view groceries.
2. **Cookbook Management**: Create, view, and manage recipes.
3. **Exit**: Terminate the program.

### Commands
Each menu offers detailed commands such as:

- `/newItem`: Add a new grocery.
- `/search`: Search for a grocery.
- `/createRecipe`: Create a new recipe.
- `/listRecipes`: List all recipes.

Type `/help` in any menu for a full list of available commands.

---

## Author

- **HectorMM5** - Developed as part of the NTNU IDATT1003 course.
- **GitHub Repository**: [idatt1003-mappe-2024-HectorMM5](https://github.com/NTNU-IDI/idatt1003-mappe-2024-HectorMM5)
