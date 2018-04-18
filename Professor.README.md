# Professor, Read Me Please!
 

## Project functionality
There are three different visible aspects of our code:
1. The login functionality
2. The LEO's view
3. The SME's view.
 
### Login and logout functionality
If a user does not yet have a cookie, all her requests will result in a 401 response. She can retrieve a token by logging in through the login form. From that point on, the cookie is set in the browser and used to automatically reauthenticate every access. We are already setting an expiration date of 60 minutes, however not checking that yet. This is intended for the final version.
 
### LEO's View
Once the LEO logs in, the LEO will have all the requests displayed. On clicking any of the requests, he can view the requests in detail. However, though all possibilities of responses (video listing, user photos, etc.) are shown, the LEO can currently only view the conversations. The LEO can also create a new request. The changes for this part from prototype 1 is that in prototype the server was accepting only caseId and username. However, the server currently accepts all the paramters.

#### SME's View
Once the Social Media Employee logs in, the SME can view all the requests. Currently, when the SME clicks on the row, the SME will be taken to a page where he can upload the results. We aim to enhance this functionality by showing the SME the details that LEO is requesting for. 

## Relevant code:
The relevant code is now on the master branch.
- Backend
    - Authentication 
        - package `auth`: Contains code for authenticating users via tokens
        - class LoginResource: Contains endpoints for retrieving and deleting tokens
    - Parsing
        - package: `edu.cmu.db.dao`
        - class Parser: Contains methods unzip, parse and delete the user data html files
    - Filtering
        - package: `edu.cmu.resources`
        - class RequestResource: Contains functions that law enforcement officers can refer to for filtering by participants name
        - The filtering functions are designed to better protect involved people's privacy.
        - This part is still not fully functional.
- Frontend
	- Location: privacyTemplates/src/main/resources/edu/cmu/resources/views
	- Description: This folder contains all the moustache files
- External JavaScript logic
    - Location: privacyTemplates/src/main/resources/assets/js/
    - Description: This folder contains all the external JavaScript files.
- Styling files
	- Location: privacyTemplates/src/main/resources/assets/css
	- Description: This folder contains the external styling files shared by the moustache files
	
## Suggested flow for testing project
1. Log in as a LEO with the credentials SaSm / SS. Generate a request by setting at least the case id, the username, and start/end dates. Go back to the Home site.
1. Click on the request the now appears in the list to verify that there is no information yet.
1. Log out and back in as SME with credentials HaHa / HH. 
1. Click on the newly generated request.
1. Upload the sample data (located in src/test/resources/SomeonesData.zip). Unfortunately, there is no feedback yet when the upload is finished. For now, assume that it should take at most 30 seconds.
1. Go back to Home and see that the request state has changed from PENDING to ANSWERED.
1. Log out and back in as the LEO.
1. Notice how the state of the request has changed here, too.
1. Finally, click on the request and notice the data that is now listed here. However, the details are not yet available (hence clicking on a conversation will result in an error).	

## Limitations
- When uploading data as a SME, we do not yet discard data that lies outside of the selected range date. We are working on it and this will be done in the final version.
- So far, only messages are parsed and stored in the database. We are now familiar with the structure, so one or two more categories should be included in the final version.
- The filtering is working but unstable. We decided not to include it in the prototype in order not to break functionality.
- Details of a conversation are not yet implemented.
- When uploading data, there is still a request id field. However, it is already populated with the right ID and will be hidden in the final version. Notice that even though the id is visible in the URL, this does not pose a vulnerability because a user still needs an authentication token to access it.

## Privacy Notice
We were thinking hard about how to incorporate a suiting privacy notice. Reluctantly, we decided that its implementation was out of scope for the project. However, please have a look at the PrivacyNotice.docx file that is located in the root folder of this project. 