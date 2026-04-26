# 📚 College Library Management System
> A full-featured console-based Java application using OOP principles.

---

## 📁 Project Structure

```
LibraryManagement/
├── src/
│   └── library/
│       ├── Main.java                    # Entry point & console menus
│       ├── model/
│       │   ├── Book.java                # Book entity
│       │   ├── Student.java             # Student entity
│       │   └── IssuedRecord.java        # Book issue/return record
│       ├── service/
│       │   └── LibraryService.java      # All business logic
│       └── util/
│           ├── DataStore.java           # File-based persistence
│           └── ConsoleHelper.java       # Colored console output
└── README.md
```

---

## ✨ Features

### 📖 Book Management
- Add / Remove books
- View all books with availability count
- Search by title, author, genre, or ID

### 🎓 Student Management
- Register / Remove students
- View all students with issued-book count
- Search by name, ID, or branch

### 🔄 Issue / Return System
- Issue book to student (max 3 books per student)
- Return book with automatic fine calculation (₹2/day overdue)
- 14-day loan period
- Prevents duplicate issues

### 📊 Reports
- All issue records (active + returned)
- Overdue books with fine amounts
- Per-student issue history

### 💾 Data Persistence
- All data auto-saved to `data/` folder using Java Serialization
- Data persists between program runs

---

## 🚀 How to Run

### Prerequisites
- Java 11 or higher

### Linux / macOS
```bash
cd LibraryManagement
chmod +x compile_and_run.sh
./compile_and_run.sh
```

### Windows
```cmd
cd LibraryManagement
mkdir out
javac -d out src/library/model/*.java src/library/util/*.java src/library/service/*.java src/library/Main.java
java -cp out library.Main
```

---

## 🧪 Sample Data (Pre-loaded)

### Books
| ID   | Title                         | Author           | Genre       |
|------|-------------------------------|------------------|-------------|
| B001 | Clean Code                    | Robert C. Martin | Programming |
| B002 | The Pragmatic Programmer      | David Thomas     | Programming |
| B003 | Introduction to Algorithms    | Cormen et al.    | CS Theory   |
| B004 | Design Patterns               | Gang of Four     | Software Eng|
| B005 | Discrete Mathematics          | Kenneth Rosen    | Mathematics |
| B006 | Operating System Concepts     | Silberschatz     | OS          |
| B007 | Computer Networks             | Tanenbaum        | Networking  |
| B008 | Database System Concepts      | Silberschatz     | Database    |

### Students
| ID   | Name          | Branch | Year |
|------|---------------|--------|------|
| S001 | Arjun Sharma  | CSE    | 2    |
| S002 | Priya Reddy   | ECE    | 3    |
| S003 | Rahul Kumar   | MECH   | 1    |
| S004 | Ananya Singh  | CSE    | 4    |

---

## 🏗️ OOP Concepts Used
- **Encapsulation** — private fields with getters/setters in model classes
- **Abstraction** — service layer hides persistence details from UI
- **Separation of Concerns** — model / service / util / UI layers
- **Serialization** — `Serializable` models for file-based storage
- **Stream API** — filtering/searching collections
- **Optional** — null-safe lookups
