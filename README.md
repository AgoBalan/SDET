
# SDET required skills and sample code

This project demonstrates 
1.how to connect to a MySQL database using JDBC (Java Database Connectivity) and perform various database operations. It includes examples of working with JDBC components like DriverManager, Connection, Statement, PreparedStatement, and ResultSet.
2. Converting resultSet to POJO object using lambok
3. Converting POJO OBJECT to JSON using jackson
4. Converting POJO OBJECT to JSON using gson
5. Constructing nested json using simple Json
6. Constructing nested json using Map 
7. Using Apache commons text easily remove escape characters added along with double quotes(During POJO object to json conversion)
8. converting json to class object using jackson
9. Apache commons library to remove /before double quotes (AFter json conversion)
*********************
9.Execution using docker,images pulled from docker hub or selenium hub or git SeleniumHQ
docker-selenium
10. Selenum Grid execution
11. Executing from bacth File - docker compose (locally)
12. Scan Server logs for succesfull server start then start Test execution. redirect to logs >>output.txt
13.Scale up the chrome node.
14. Monitor server logs before starting teh execution.
********* Docker based Jenkins: port 8080
15. Build jdk 17 image by ourself (jenkins-jdk17.dockerfile) and push the img
15. run it from Docker jenkins(Refer screenshot attached local-JenkinsRunSampleImage).

***** Jenkins installed in windows port 7777 for view CICD**********
After login in to Jenkins.
 >Install build pipeline plugin first  <<<Genrally for small scale applciations>>>
 > Create new view "Demo view" Setup Base job -> BUild
 > Have another new job "Deploy to QA" this has to run after Build job completes
 >Another job "Testing" this has to run after Buil"Deploy to QA" job compeltes
 >Another job "Deploy to prod" this has to run after Buil"Testing" job compeltes
  ***Refere screenshot udner the folder "Screenshot"Jenkins-Pipeline for CI-CD *******

<<Enterprise applciations>>
 New Item->Pipeline Project and Name it "pipelineScriptDemo"
Pipeline is created by executing Jenkisn file(Which stores entire work flow).
File writtred using GROOVY DSL(Domain Specifc language).
 2 Ways:
  >Declarative pipleine syntax --> Famous and trending one
  >Descriptive pipleine Syntax
REfer Jenkinsfile in teh root directory, this is refered to the pipeline job in jenkins
  Refre screenshot folder descriptivePipeline screenshot
  ************************************

  16. Data providers TestNG and Excel
    


# JDBC Database Connection Demo
## JDBC Components Explained

### 1. JDBC Driver
- The JDBC driver provides the connection to the database
- In this project, we use MySQL Connector/J:
```java
Class.forName("com.mysql.cj.jdbc.Driver");
```

### 2. DriverManager
- `DriverManager` is the basic service for managing JDBC drivers
- It creates the connection to the database using URL, username, and password:
```java
Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
```
- Connection URL format for MySQL: `jdbc:mysql://hostname:port/databasename`

### 3. Connection
- Represents a connection session with a specific database
- Used to create statements and manage transactions
- Best practice is to close connections when done:
```java
try (Connection conn = DBUtil.getConnection()) {
    // database operations
} catch (SQLException e) {
    // handle exception
}
```

### 4. Statement Types

#### Statement
- Used for executing simple SQL queries without parameters
```java
Statement stmt = connection.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM CustomerInfo");
```

#### PreparedStatement
- Used for SQL queries with input parameters
- Prevents SQL injection by automatically escaping special characters
- Better performance for repeatedly executed SQL
```java
PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM CustomerInfo WHERE Location = ?");
pstmt.setString(1, "Asia");
ResultSet rs = pstmt.executeQuery();
```

### 5. ResultSet
- Contains the results of a SQL query
- Provides methods to iterate through and retrieve data from results
```java
while (rs.next()) {
    String courseName = rs.getString("CourseName");
    Date purchaseDate = rs.getDate("PurchasedDate");
    int amount = rs.getInt("Amount");
    String location = rs.getString("Location");
}
```

## Project Structure

```
jdbc-demo/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── example/
│                   ├── model/
│                   │   ├── CustomerInfo.java      # POJO class
│                   │   └── CustomerInfoDAO.java    # Data Access Object
│                   ├── util/
│                   │   └── DBUtil.java            # Database utility class
│                   └── Main.java                  # Main application class
└── pom.xml                                        # Maven configuration
```

## Common JDBC Operations

### 1. Executing a Query (SELECT)
```java
String sql = "SELECT * FROM CustomerInfo WHERE Location = ?";
try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    pstmt.setString(1, location);
    try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
            // Process results
        }
    }
}
```

### 2. Executing an Update (INSERT, UPDATE, DELETE)
```java
String sql = "INSERT INTO CustomerInfo (CourseName, PurchasedDate, Amount, Location) VALUES (?, ?, ?, ?)";
try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    pstmt.setString(1, courseName);
    pstmt.setDate(2, purchaseDate);
    pstmt.setInt(3, amount);
    pstmt.setString(4, location);
    int rowsAffected = pstmt.executeUpdate();
}
```

## Best Practices

1. **Resource Management**
   - Always use try-with-resources for Connection, Statement, and ResultSet
   - Close resources in the reverse order of creation

2. **SQL Injection Prevention**
   - Use PreparedStatement instead of Statement for parameterized queries
   - Never concatenate strings to build SQL queries

3. **Exception Handling**
   - Properly catch and handle SQLException
   - Consider using custom exceptions for business logic

4. **Connection Pooling**
   - For production applications, use connection pooling (e.g., HikariCP, C3P0)
   - Helps manage and reuse database connections efficiently

## Running the Project

1. Update database configuration in `DBUtil.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/Business";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

2. Build the project:
```bash
mvn clean install
```

3. Run the main class to test database operations.

## Database Schema
```sql
CREATE TABLE CustomerInfo (
  CourseName VARCHAR(50),
  PurchasedDate DATE,
  Amount INT(50),
  Location VARCHAR(50)
);
```


## POJO Class and Lombok
The project uses Plain Old Java Objects (POJO) with Lombok to efficiently map database records to Java objects. This approach significantly reduces boilerplate code and makes the codebase more maintainable.

### POJO (Plain Old Java Object)
- POJOs are used to represent database tables as Java classes
- Each instance variable corresponds to a database column
- Helps in converting database records to Java objects and vice versa

### Lombok Annotations
The project uses Lombok annotations to automatically generate common code patterns:

1. **@Data**
   - Generates all the boilerplate code typically needed for a data class:
   - Generates getters for all fields
   - Generates setters for all non-final fields
   - Generates `toString()` method
   - Generates `equals()` and `hashCode()` methods
   - Example usage:
   ```java
   @Data
   public class CustomerInfo {
       private String courseName;
       // fields...
   }
   ```

2. **@NoArgsConstructor**
   - Generates a constructor with no parameters
   - Useful when creating objects through reflection or when using frameworks
   - Example:
   ```java
   @NoArgsConstructor
   public class CustomerInfo {
       // Generates: public CustomerInfo() {}
   }
   ```

3. **@AllArgsConstructor**
   - Generates a constructor with one parameter for each field
   - Parameters are in the order the fields are declared
   - Example:
   ```java
   @AllArgsConstructor
   public class CustomerInfo {
       private String courseName;
       private Date purchasedDate;
       private int amount;
       private String location;
       // Generates: public CustomerInfo(String courseName, Date purchasedDate, int amount, String location) { ... }
   }
   ```

### Benefits of Using Lombok
1. **Reduced Boilerplate Code**
   - Eliminates need to write repetitive code
   - Makes classes more concise and readable

2. **Maintainability**
   - When fields are added or removed, Lombok automatically updates all generated methods
   - Reduces the chance of errors in getter/setter implementations

3. **Clean Code**
   - Classes focus on business logic rather than boilerplate
   - Improved readability and understanding of the code's purpose



JACKSON:
Jackson automatically serializes all fields using getters generated by Lombok (@Data). No manual JSON mapping is required.

##########################################################
To check any containers running in cmd
> docker ps

To pull image from from docker hub or selenium hub or git SeleniumHQ - https://github.com/SeleniumHQ/docker-selenium
-> >docker pull selenium/standalone-chrome:4.35.0-20250828
> >docker pull selenium/standalone-chrome:latest

To view images
>docker images
REPOSITORY                   TAG               IMAGE ID       CREATED      SIZE
selenium/standalone-chrome   4.35.0-20250828   0330f8b1d561   5 days ago   2.05GB

Deploy image to container
>docker run -d -p 4444:4444 --shm-size 2g selenium/standalone-chrome
-d --> run in background
-p --> selenum run port 4444 will redirect to docker port


#####################Run in grid set up #############################
RUn in grid - refer https://github.com/SeleniumHQ/docker-selenium
cmd to the path where compose yaml present and run the command
PS C:\Users\Ago Ben\Desktop\Learnings\udemy\SDET\SDET\jdbc-demo> docker compose -f compose.yaml up

if you wan to increase the node use scale command
docker-compose scale chrome=5
[+] Running 6/6
 ✔ Container selenium-hub        Running                                                  0.0s 
 ✔ Container jdbc-demo-chrome-1  Running                                                  0.0s 
 ✔ Container jdbc-demo-chrome-5  Started                                                  1.1s 
 ✔ Container jdbc-demo-chrome-2  Started                                                  1.7s 
 ✔ Container jdbc-demo-chrome-3  Started                                                  1.4s 
 ✔ Container jdbc-demo-chrome-4  Started                                                  0.7s

 #######################

 pull jenkins image using
  docker pull jenkins/jenkins:latest