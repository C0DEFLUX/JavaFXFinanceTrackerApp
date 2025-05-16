# JavaFX Finance Tracker App

A desktop application built with **JavaFX** that helps users track their personal income and expenses. The app demonstrates the use of multiple FXML scenes, controller separation, shared data via object storage, and clean scene switching for a smooth user experience.

---

## ✨ Features

- Register screen for new users
- Login screen for basic access
- Dashboard overview with total income & expenses
- Add new transactions (income or expense)
- View a full list of transactions
- Edit any existing transaction via a separate view
- Delete transactions directly from the list
- Live updates to totals when adding/editing/deleting

---

## 🛠️ Technologies Used

- Java 17+
- JavaFX 20+
- FXML (SceneBuilder for layout)
- MVC pattern
- MySQL

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- JavaFX SDK 20+
- Any IDE (IntelliJ IDEA, Eclipse, or VSCode with JavaFX plugin)
- SceneBuilder (optional but recommended for editing FXML)
- MySQL server

### Running the App

1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/javafx-finance-tracker.git
   cd javafx-finance-tracker
   ```
   
2. Open in your IDE and ensure:
   JavaFX libraries are properly linked.
   VM options are set (e.g., --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml if running from terminal).
   You're using Main.java as your entry point.


3. Open **utils** folder, then in the file **DBUtils** change the MySQL credentials from your MySQL server credentials.
   ```java
      package utils;
      
      import java.sql.Connection;
      import java.sql.DriverManager;
      import java.sql.SQLException;
      
      public class DBUtil
      {
      //Change to your host
      private static final String URL = "jdbc:mysql:your-host/finance_schema";
      private static final String USER = "your-user";
      private static final String PASSWORD = "your-password";
      
          public static Connection connect() throws SQLException
          {
              return DriverManager.getConnection(URL, USER, PASSWORD);
          }
      }
   ```
   After that import the SQL file for the tables.
   
3.Run the app!



