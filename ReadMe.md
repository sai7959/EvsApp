# Database Setup Instructions for EvsDB:

## Step 1: Create the Database

1.Open your PostgreSQL query tool or terminal.
2.Run the following command to create the database:

CREATE DATABASE evsdb;

3.This will create a new database named EvsDB.

## Step 2: Run the Application

1.With EvsDB created, start your application.
2.On startup, the application should connect to EvsDB and automatically create the required tables based on your application's entity classes and configuration.
3.Navigate to the directory where your Spring Boot project is located. And run this command on the terminal

> ./mvnw clean spring-boot:run

After running the command, you should see logs in the terminal indicating that the application is starting.

By default, Spring Boot applications run on port 8080. You can access your application by navigating to http://localhost:8080 in your web browser.

## Step 3: Insert Initial Roles

1.After the application has successfully created the tables, run the following SQL query to add the default roles:
2.evsdb databse query tool and run the following query

INSERT INTO public.evs_role (
active, created_at, deleted, name, updated_at
) VALUES
(true, CURRENT_TIMESTAMP, false, 'ADMIN', CURRENT_TIMESTAMP),
(true, CURRENT_TIMESTAMP, false, 'VOTER', CURRENT_TIMESTAMP),
(true, CURRENT_TIMESTAMP, false, 'ELECTORIAL-OFFICER', CURRENT_TIMESTAMP);

This query will insert the following roles into the evs_role table:ADMIN,VOTER,ELECTORIAL-OFFICER

## Step 4: Create the Admin User

Run the following SQL query to create an initial ADMIN user in the database:

    INSERT INTO public.evs_user (
    active, created_at, deleted, eligible_to_vote, email, password, updated_at
    ) VALUES
    (true, CURRENT_TIMESTAMP, false, false, 'admin@gmail.com',
    '$2a$12$J.l8B5/l4OmOP93xibjoN.5r0o363atWPiOfSrD2bU7HtCWfNh/pK', CURRENT_TIMESTAMP);

This query creates an admin user with the following details:
Email: admin@gmail.com
Password:admin (bcrypt hashed)
Active: true
Eligible to Vote: false

## Step 5: Assign the ADMIN Role to the Admin User

Verify User ID and Role ID
Before running the following query, ensure that the role_id for the ADMIN role is 1 and the user_id for the admin user is 1. If they are not the same, please adjust the values accordingly based on your database's current state.

INSERT INTO public.evs_user_role (
active, created_at, deleted, updated_at, role_id, user_id
) VALUES
(true, CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 1, 1);

This query assigns the ADMIN role to the admin user by linking their user_id (1) to the role_id (1).

## Step 6: Create the Electoral Officer User

Run the following SQL query to create an ELECTORIAL-OFFICER user in the database:

INSERT INTO public.evs_user (
active, created_at, deleted, eligible_to_vote, email, password, updated_at
) VALUES
(true, CURRENT_TIMESTAMP, false, false, 'electorial-officer@gmail.com',
'$2a$12$EiH3XhLHI9HkDK7J2vesseVp30MCwDSHi1WW8FRd1M0GgPD4xAgga', CURRENT_TIMESTAMP);

This query creates an electoral officer user with the following details:

Email: electorial-officer@gmail.com
Password:electorial-officer (bcrypt hashed)
Active: true
Eligible to Vote: false

## Step 7: Assign the ELECTORIAL-OFFICER Role to the Electoral Officer User

Verify User ID and Role ID
Before running the following query, ensure that the role_id for the ELECTORIAL-OFFICER role is 3 and the user_id for the electoral officer user is 2. If they are not the same, please adjust the values accordingly based on your database's current state.

INSERT INTO public.evs_user_role (
active, created_at, deleted, updated_at, role_id, user_id
) VALUES
(true, CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 3, 2);

This query assigns the ELECTORIAL-OFFICER role to the electoral officer user by linking their user_id (2) to the role_id (3).
