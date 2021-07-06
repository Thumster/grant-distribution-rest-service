# Grant Distribution Rest Service
RESTful API that would help users decide on groups of people who are eligible for various upcoming government grants.

*Disclaimer: All grants mentioned here are fictitious and do not reflect actual grants that are being worked on or implemented by any government ministries.*

## Getting Started
#### Requirements
1. Install [PostgreSQL](https://www.postgresql.org/download/)
2. Install [IntelliJ Community](https://www.jetbrains.com/idea/download/#section=windows)
3. Install [Postman](https://www.postman.com/)

#### Setup
1. Clone the repository
2. Setup PostgreSQL. Tables and schemes get setup automatically. Use following settings:
      
        postgresql://localhost:5432/db_grant_distribution  
        username=postgres
        password=password
        
3. Maven clean and install (either through IDE or mvn cmd)
4. Run application

## Spring Boot Framework [dependencies]
* spring-boot-starter-data-jpa
* spring-boot-starter-web
* spring-boot-devtools
* postgresql
* spring-boot-configuration-processor
* lombok
* spring-boot-starter-test
* spring-boot-starter-validation

## End Points
### With Postman
### create-household
### retreive-all-households
### retrieve-household
### delete-household
### create-family-member
### delete-family-member
### search-households

### Assumptions
* Family Member cannot be created without a Household
* Deleting Household will delete all Family Members
* You can create a spouse under a different Household
