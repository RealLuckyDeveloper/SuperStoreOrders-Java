# Instructions to Run the App

This guide explains how to set up and run the `app.jar` with the required data.

## Prerequisites

- Java Development Kit (JDK) installed on your system to execute Java applications.

## Steps

1. **Download the Application Files**

   - Download `app.jar` and `data.zip` to your local machine.

2. **Extract the Data Archive (`data.zip`)**

   - Locate the downloaded `data.zip` file.
   - Extract its contents to a directory of your choice. This directory will be referred to as `<data_directory>` in the next steps.

3. **Launch the Application**

   - Open a terminal or command prompt.
   - Navigate to the directory where `app.jar` is located and where you extracted `data.zip`.

4. **Run the Application**

   - Use the following command to launch the application:
   
     ```bash
     java -jar app.jar "<data_directory>/path/to/SuperStoreOrders.csv" "<data_directory>/path/to/SuperStoreReturns.csv"
     ```

     Replace `<data_directory>/path/to/file1` and `<data_directory>/path/to/file2` with the actual paths to your input files within the extracted `data.zip`.

     For example:
     ```bash
     java -jar app.jar "/Users/username/data/file1.txt" "/Users/username/data/file2.txt"
     ```

     Here, replace `/Users/username/data/file1.txt` and `/Users/username/data/file2.txt` with the paths to the csv files you want to process using the `app.jar`.

## Notes

- Ensure that you have appropriate permissions to read from the extracted data directory and execute the `app.jar`.
- Replace `<data_directory>` and the file paths in the command with the actual paths relevant to your setup.
- Make sure Java is correctly installed and added to your system's PATH environment variable to execute the `java` command.
- Adjust the command based on your operating system's file path conventions (e.g., using backslashes `\` instead of forward slashes `/` on Windows).

If you encounter any issues or errors during setup or execution, refer to the application documentation or contact the developer for assistance.
## Points
| Feature                                                                                                                                                                                                                                                                                                                                                                                                                           | Your points              | Max       |
| --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------ | --------- |
| User is able to navigate in the UI. <br>UI is easy to use, follows common guidelines either in CLI or GUI app                                                                                                                                                                                                                                                                                                                     | 20         | 20        |
| App has excellent exception handling                                                                                                                                                                                                                                                                                                                                                                                              | 5          | 10        |
| User is able search for customers by name                                                                                                                                                                                                                                                                                                                                                                                         | 10         | 10        |
| User is able to select one of the customers (from search) and view a summary of all the orders made by the customer in a table view (either ascii or gui)                                                                                                                                                                                                                                                                         | 20         | 20        |
| User is able show statistics from the dataset<br><br><br>- What is the average sales amount of the orders? (10p)<br>- Who is the best customer (highest total sales)? (10p)<br>- The amount of customers per state? (10p)<br>- How many Corporate, Consumer and Home Office customers are there? (10p)<br>- What is the total sales per year? (10p)<br>- What is the total sales per region, West, East, Central and South. (10p) | 55         | 60        |
| Unit Testing for Business Logic                                                                                                                                                                                                                                                                                                                                                                                                   | 8           | 20        |
| Javadoc comments                                                                                                                                                                                                                                                                                                                                                                                                                  | 10          | 20        |
| README.md documentation                                                                                                                                                                                                                                                                                                                                                                                                           | 8         | 10        |
| GUI                                                                                                                                                                                                                                                                                                                                                                                                                               | 45         | 50        |
| GUI: does not hang/suspend when handling large dataset                                                                                                                                                                                                                                                                                                                                                                            | 19         | 20        |
| Checkstyle code quality                                                                                                                                                                                                                                                                                                                                                                                                           | 5          | 10        |
| Screencast                                                                                                                                                                                                                                                                                                                                                                                                                        | 10         | 10        |
| Git commits                                                                                                                                                                                                                                                                                                                                                                                                                       | 8         | 10        |
| Proper releases (implementation for each, clear documentation, working .jar file)                                                                                                                                                                                                                                                                                                                                                 | 9         | 10        |
| Bonus: GUI: User is able to view graphical charts from the data                                                                                                                                                                                                                                                                                                                                                                   | 0         | 30        |
| **SUM**                                                                                                                                                                                                                                                                                                                                                                                                                           | **Count sum of your points** | **290 + 30p** |

