# PrivacyTemplates
## Goals and Purpose
The purpose of our project is to help streamline the process for law enforcement officers requesting and reviewing a suspect’s social media data that may be used as evidence during the investigative process. Our goal is to create a website that will be used by law enforcement officials such as police officers and analysts; and social media employees.  The website’s functionality can be divided into three sections,  a form used to submit the requests, an area for social media employees to receive, review, and reply to the requests; and a filtering functionality on the raw data returned.  As these requests will contain very sensitive personal data and specific details about crimes, the requests could have an impact on legal proceedings. Therefore, our goal is to reduce the potential privacy harms that occur between the sharing of data between companies and enforcement agencies. We strive to create a template that ensures the request is lawful, provides details in hopes of uniquely identifying the suspect, specifies the time period, location, type of data and to avoid generic or overly broad requests.

## Used libraries and frameworks
Our project uses Dropwizard as a bundle of well established web frameworks such as Jackson, Jersey, Jetty, and Hibernate.
For our frontend, we leverage the well-known Bootstrap CSS library.
The project is built with Maven.

## Structure of the repository
- java.edu.cmu:
    - The api, cli, and client packages will most likely remain.
    - The core package will contain business logic in the future.
    - The db package contains data access objects (DAOs) for database access and entities for Hibernate's mapping of objects to tables.
    - The health package might contain application health checks in the future.
    - The resources package contains classes for registering endpoints of the application.
- resources:
    - The assets package contains static contents to be served
    - The db.migration package contains SQL migrations for evolving our DB schema over time.   

## General Remarks
Static assets are served from the resources/assets folder.
API endpoints are implemented through resources which should be added in resources package. 

## First time startup
1. Get Maven (https://maven.apache.org/)
2. Set up the database:
    1. Run a MySQL server. The following assumes it runs at `localhost:3306` with a user `root` and password `root`. If that is not the case, you need to change the according lines in the `pom.xml` file and in the `privacyTemplates.yml` file.
    2. Run `mvn clean compile flyway:clean flyway:migrate`

## How to start the PrivacyTemplates application
1. Run `mvn clean package` in your project directory to build your application
1. Start application with `java -jar target/privacyTemplates-1.0-SNAPSHOT.jar server privacyTemplates.yml` (you can gracefully terminate by `Ctrl+C`). Note that this assumes Java 8. Prior or later Java releases are currently not supported.
1. To check that your application is running, go to `http://localhost:8080`
