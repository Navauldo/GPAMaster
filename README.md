cat > README.md << 'EOF'
# GPA Master

A comprehensive academic management system with role-based access for Administrators, Teachers, and Students. Built with JavaFX and Maven.

## 🎯 Features

### 👑 Admin
- Manage students, teachers, and courses (CRUD operations)
- Input grades for any student
- View system statistics

### 👨‍🏫 Teacher
- View assigned students
- Input grades for students in their courses
- Track courses taught

### 👨‍🎓 Student
- View personal grades
- Calculate GPA (4.0 scale)
- Track academic standing
- View enrolled courses

## 📋 Prerequisites

| Requirement | Version | macOS Installation |
|-------------|---------|-------------------|
| Java JDK | 23 | `brew install openjdk@23` |
| Maven | 3.6+ | `brew install maven` |

## 📦 Dependencies

The project uses the following key dependencies (automatically managed by Maven):

| Dependency | Version | Purpose |
|------------|---------|---------|
| JavaFX Controls | 25.0.2 | JavaFX UI components |
| JavaFX FXML | 25.0.2 | FXML layout loading |
| AtlantaFX | 2.1.0 | Modern themes and styling |

## 🚀 How to Run

### Method 1: Maven (Recommended)

'''bash
# Navigate to project directory
cd GPAMaster1.2

# Clean and run the JavaFX application
mvn clean javafx:run

Method 2: Using IntelliJ IDEA

    Open IntelliJ IDEA

    Click File → Open and select the GPAMaster1.2 folder

    Wait for Maven to import dependencies

    Locate Frontend/MainApp.java

    Right-click and select Run 'MainApp.main()'
