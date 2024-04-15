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
     java -jar app.jar "<data_directory>/path/to/file1" "<data_directory>/path/to/file2"
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
