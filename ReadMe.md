# Database Setup Instructions for EvsDatabase:

## Step 1: Create the Database

1. Open your PostgreSQL query tool or terminal.
2. Run the following command to create the database:

CREATE DATABASE EvsDatabase;

3. This will create a new database named EvsDatabase.

## Step 2: Configure Database in Application Properties

spring.datasource.url = jdbc:postgresql://localhost:5432/EvsDatabase
spring.datasource.username = postgres **Replace with your PostgreSQL username**
spring.datasource.password = Kumar@1234 **Replace with your PostgreSQL password**
spring.datasource.testWhileIdle = true
spring.datasource.validationQuery = SELECT 1
spring.jpa.show-sql = true
spring.jpa.properties.hibernate.dialect =org.hibernate.dialect.PostgreSQLDialect
spring.data.rest.basePath=/
spring.datasource.data = classpath:/data.sql
spring.jpa.hibernate.ddl-auto=update
spring.jpa.generate-ddl=true
spring.datasource.initialization-mode=always
spring.sql.init.mode=always
Spring.jpa.defer-datasource-initialization=true

## Step 2: Run the Application

1. With EvsDB created, start your application.
2. On startup, the application should connect to EvsDB and automatically create the required tables based on your application's entity classes and configuration.
3. Navigate to the directory where your Spring Boot project is located. And run this command on the terminal

> ./mvnw clean spring-boot:run

After running the command, you should see logs in the terminal indicating that the application is starting.

By default, Spring Boot applications run on port 8080. You can access your application by navigating to http://localhost:8080 in your web browser.

# Programmatic Initialization of Basic Roles and Users:

On startup, the application will programmatically initialize essential roles and default users if they do not already exist:

1. Admin User: **admin@gmail.com** with password **admin** and role **ADMIN**.
2. Electorial Officer User: **electorial-officer@gmail.com** with password **electorial-officer** and role **ELECTORIAL-OFFICER**.

## Step 4: Manage States and its Elections

You can use the following endpoints to add, view, and delete states, as well as manage elections for each state. Each endpoint's URL starts with /state.

1. Add a new state(Creates a new state)(**ONLY ADMIN**)
   Endpoint:POST [/state]
   Request Body (JSON):
   {
   "name": "State Name",
   "population": 1000000
   }
   Response: "State saved successfully" (201 CREATED), or 400 BAD REQUEST if the state already exists.

2. Get State by ID(Retrieves a specific state by ID)
   Endpoint: GET [/state/{id}]
   Response: State details (200 OK), or 404 NOT FOUND if the state does not exist.

3. Get All States(Retrieves a list of all states)
   Endpoint: GET [/state]
   Response: List of states (200 OK) or "No records" (204 No Content) if no states are found.

4. Delete a State by ID(Deletes a state by ID)(**ONLY ADMIN**)
   Endpoint: DELETE [/state/{id}]
   Response: "State deleted successfully" (200 OK), or 404 NOT FOUND if the state does not exist.

5. Add an Election to a State(Creates an election entity for state)(**ONLY ADMIN**)
   Endpoint: POST [/state/{id}/election]
   Request Body (JSON):
   {
   "name": "Election Name",
   "date": "2024-11-04"//election date
   }
   Response: "Election added successfully" (201 Created), or 400 BAD REQUEST if the election name already exists for the state.

6. Get Elections for a State
   Endpoint: GET [/state/{id}/election]
   Description: Retrieves all elections associated with a specific state.
   Response: List of elections (200 OK) or "No records" (204 No Content) if no elections are found for the state.

## Step 5: Manage Elections

1. Get Election by ID
   Endpoint: GET [/election/{id}]
   Response: Election details (200 OK), or an error if the election does not exist.

2. Delete Election by ID(**ONLY ADMIN**)
   Endpoint: DELETE [/election/{id}]
   Response: "Election deleted successfully" (200 OK), or an error if the state does not exist.

3. Add Party to Election(**ONLY ADMIN**)
   Endpoint: POST [/election/{electionId}/party]
   Request Format:
   {
   "name": "Janasena", //party name
   "candidateName": "PSPK"
   }
   Response: "Party added successfully" (200 OK), or 400 BAD REQUEST if the state does not exist.

4. Get Parties of Election
   Endpoint: GET [/election/{electionId}/party]
   Response: List of parties (200 OK) or "No records" (204 No Content) if no elections are found for the election.

## Step 6: Manage party

1. Get Party by ID
   Endpoint: GET [/party/{id}]
   Response: Party details (200 OK), 404 NOT FOUND if the party does not exist.

2. Delete Party by ID(**ONLY ADMIN**)
   Endpoint: DELETE [/party/{id}]
   Response: "Party deleted successfully" (200 OK), or an error if the party does not exist.

3. Get Votes for Party
   Endpoint: GET [/party/{partyId}/votes]
   Response: List of votes (200 OK) or "No records" (204 No Content) if no votes are found for the party.

## Step 7: Register voter

1. Create User
   Endpoint: POST [/userdetails]
   RequestBody:
   {
   "user": {
   "email": "ganesh@gmail.com",
   "password": "ganesh"
   },
   "name": "lakshmi",
   "dateOfBirth": "1974-09-12",
   "gender": "f", // or "F" for female, etc.
   "address": "345 Main St, Anytown",
   "mobileNumber": "1234567890", // Exactly 10 digits
   "district": "Some District5"
   }

Respose: 201 Created: UserDetails saved successfully or 400 Bad Request: If userDetails data is invalid, already exists, or if the voter role does not exist.

2. Get User Details by ID
   Endpoint: GET [/userdetails/{id}]
   Response:User details (200 OK), 404 NOT FOUND if the user details does not exist.

3. Get All User Details
   Endpoint: GET /userdetails
   Response: List of user details (200 OK) or "No records" (204 No Content) if no user details are found.

4. Delete User Details
   Endpoint: DELETE [/userdetails/{id}]
   Response: "User details deleted successfully" (200 OK), or 404 NOT FOUND an error if the user details does not exist.

## Step 8: Voter Requests and approvals

1. Request Vote
   Endpoint: POST [/voter-request/user/{userId}]
   Response:200 or 400 BAD REQUEST if user doesn't exist.

2. Get All Voter Requests
   Endpoint: GET [/voter-request]
   Response:List of requests(200 OK) or "No records"(204 NO CONTENT) if no requests are found

3. Get Voter Request by ID
   Endpoint: GET [/voter-request/{id}]
   Response:Voter request (200 OK) or 404 NOT FOUND if no request exists

4. Approve Voter Request(**ONLY ELECTORIAL-OFFICER**)
   Endpoint: PUT [/voter-request/{id}/approve]
   Response:"Approved"(200 OK) or 400 BAD REQUEST if voter request doesn't exist.

5. Reject Voter Request(**ONLY ELECTORIAL-OFFICER**)
   Endpoint: PUT [/voter-request/{id}/reject]
   Response:"Rejected"(200 OK) or 400 BAD REQUEST if voter request doesn't exist.

## Step 9: Cast vote

1. Cast Vote
   Endpoint: POST [/vote/election/{electionId}/party/{partyId}/user/{userId}]
   electionId: ID of the election.
   partyId: ID of the party.
   userId: ID of the user.

   Response:"casted vote"(200 OK) OR
   400 BAD REQUEST if electionId or partyId or userId is doesn't exist

2. Get Vote by Election, Party, and User
   Endpoint: GET [/vote/election/{electionId}/party/{partyId}/user/{userId}]
   electionId: ID of the election.
   partyId: ID of the party.
   userId: ID of the user.

   Response:"casted vote"(200 OK) OR
   404 NOT FOUND if electionId or partyId or userId is doesn't exist

## Step 10: Declare result

1. Declare Election Result
   Endpoint: POST [/result/election/{electionId}]
   Response:Declared (200 ok)
