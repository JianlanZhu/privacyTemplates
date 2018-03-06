# PrivacyTemplates

## First time startup
1. Get Maven (https://maven.apache.org/)
2. Set up the database
    1. Run a MySQL server (the following assumes it runs at `localhost:3306` with username `root` and password `root`)
    2. Either
        1. Run SQL scripts in alphabetical order (FIrst V1__..., then V2__..., ...) with a command line tool or the MySQL workbench (or however you like it) OR
        2. Get Flyway and run `flyway migrate` in the project folder

## How to start the PrivacyTemplates application
1. Run `mvn clean package` in your project directory to build your application
1. Start application with `java -jar target/privacyTemplates-1.0-SNAPSHOT.jar server privacyTemplates.yml` (you can gracefully terminate by `Ctrl+C`)
1. To check that your application is running, go to `http://localhost:8080`
