# Professor, Read Me Please!
 

## Structure of our Code
There are two major parts to our code:
1. The front-end (html and JavaScript)
2. The back-end (.java files)
 
### Front-End
#### Description
For the front-end, we currently have the UI for the Request Generation. We are able to pass a basic request generation request to the backend (including caseNumber and suspectUserName). However, full support for all fields still needs to be implemented. Also, note that this is the first basic thing we came up with. UX has not yet been greatly considered. For example, when you try to generate a request, there is a “Submit” button where you might expect a “Next” button. It immediately performs all currently existing validations instead of taking you to the next part of the request form. To switch between the different parts of the request form, use the links on the left.
#### Relevant code
- RequestForm.html
  - Location: privacyTemplates/src/main/resources/assets/Request/RequestForm.html
  - Description: This file contains the UI for the request form.

- requestFormFunctions.js
    - Location: privacyTemplates/src/main/resources/assets/Request/requestFormFunctions.js
    - Description: This file contains the JavaScript functions specific to request form

- globalFunctions.js
    - Location: privacyTemplates/src/main/resources/assets/globalFunctions.js
    - Description: This file contains the JavaScript functions which will be shared by multiple java files.
 
### Back-End
#### Description
Currently, we have the code written to handle the simple request data as described above. We are also able to persist the generated request in the database. Support for other request parameters and complete mediation still need to be implemented.

#### Relevant code
- GenerateRequestInput.java
    - Location: privacyTemplates/src/main/java/edu/cmu/resources/interaction/GenerateRequestInput.java
    - Description: This file represents the request object that the frontend passes to the backend. It contains all the fields which should be there in the request object. Currently, we are only supporting two fields. More fields will be added in the future.
- RequestResource.java
    - Location: privacyTemplates/src/main/java/edu/cmu/resources/RequestResource.java
    - Description: This file is the entry point of the ‘Request calls’ from the UI. This is using the class in the above file to parse incoming JSON into a Java object.
- Request.java
    - Location: privacyTemplates/src/main/java/edu/cmu/db/entities/Request.java
    - Description: This represents the corresponding table in the database. It will be generated from the incoming request data. This entity will be used to filter the input uploaded by the social media.
- RequestDAO.java
    - Location: privacyTemplates/src/main/java/edu/cmu/db/dao/RequestDAO.java
    - Description: An object of this class is used to perform database accesses regarding requests. DAOs are the only classes that can interact with the database.

### Other things:
- Other files like EmployeeResource.java or SayingOutput.java etc. are for exemplary purpose only obtained from tutorials. They will be removed as soon as everyone feels fully comfortable with the framework.
- We've got a sample test running, but in general, there has not been effort put into it so far.
