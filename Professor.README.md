# Professor, Read Me Please!
 

## Project functionality
There are three different aspects of our code:
1. The login functionality
2. The LEO's view
3. The SME's view.
 
### Login and logout functionality
#### Description
The login functionality has been achieved by setting a cookie.
 
### LEO's View
#### Description
Once the LEO logs in, the LEO will have all the requests displayed. On clicking any of the requests, he can view the requests in detail. However, though all possibilities of responses (video listing, user photos, etc.) are shown, the LEO can currently only view the conversations. The LEO can also create a new request. The changes for this part from prototype 1 is that in prototype the server was accepting only caseId and username. However, the server currently accepts all the paramters.

#### SME's View
### Description:
Once the Social Media Employee logs in, the SME can view all the requests. Currently, when the SME clicks on the row, the SME will be taken to a page where he can upload the results. We aim to enhance this functionality by showing the SME the details that LEO is requesting for. 

### Relevant code:
- Frontend
	- Location: privacyTemplates/src/main/resources/edu/cmu/resources/views
	- Description: This folder contains all the moustache files
- External JavaScript logic
    - Location: privacyTemplates/src/main/resources/assets/js/
    - Description: This folder contains all the external JavaScript files.
- Styling files
	- Location: privacyTemplates/src/main/resources/assets/css
	- Description: This folder contains the external styling files shared by the moustache files
	