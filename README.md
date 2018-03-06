# PrivacyTemplates

## First time startup
1. Get Maven (https://maven.apache.org/)
2. Set up the database:
    1. Run a MySQL server. The following assumes it runs at `localhost:3306` with a user `root` and password `root`. If that is not the case, you need to change the according lines in the `pom.xml` file and in the `privacyTemplates.yml` file.
    2. Run `mvn compile flyway:migrate`

## How to start the PrivacyTemplates application
1. Run `mvn clean package` in your project directory to build your application
1. Start application with `java -jar target/privacyTemplates-1.0-SNAPSHOT.jar server privacyTemplates.yml` (you can gracefully terminate by `Ctrl+C`)
1. To check that your application is running, go to `http://localhost:8080`
