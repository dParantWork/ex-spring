# ex-spring

## Run the project

To run the project's tests, run the command "mvn surefire:test".
To run the project, execute the command mvn "spring-boot:run".
Once the application is started, the documentation is available here : "http://localhost:8080/swagger-ui.html#/".
Once the application is started, the h2 embedded database can be manipulated in a UI here "http://localhost:8080/h2-console/".
A postman collection is available at the root of the project.

## Application details : 
	
### Post : 
 - Informations :
 	Creation of a user.
	The post call doesn't return any information in the body, it just return the uri to get the user in the location header.
 - Code returned : 
   201 Created
   400 Bad Request
  
### Get : 
 - Informations :
	The get call is used to retrieve user's informations.
 - Code returned : 
   200 OK
   404 Not Found
   
   		

