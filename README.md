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
[Import Collection](Grant-Distribution.postman_collection.json) You can directly import all endpoints to a Postman Collection to view the API structure clearer. Ensure that the project has been setup locally before running the requests on Postman.
### create-household
Example Request

```json
Request Method: POST /create-household
Request Body:
{
      "housingType": "CONDOMIUM"
}
```
Example Response (Success)

```json
{
      "householdId": 1,
      "housingType": "CONDOMINIUM",
      "familyMembers": []
}
```

Example Response (Failure)
```json
{
      "housingType": "Please provide either LANDED, CONDOMINIUM, HDB"
}
```
### retreive-all-households
Example Request

      Request Method: GET /retreive-all-households
Example Response (Success)

```json
[
      {
            "householdId": 1,
            "housingType": "CONDOMINIUM",
            "familyMembers": []
      }
]
```
### retrieve-household
Example Request

      Request Method: GET /retrieve-household/1        
Example Response (Success)
```json
{
      "householdId": 1,
      "housingType": "CONDOMINIUM",
      "familyMembers": []
}
```
Example Response (Failure)

      Household with Id: 10 not found
### delete-household
Example Request

      Request Method: DELETE /delete-household/1
Example Response (Success)
```json
{
      "householdId": 1,
      "housingType": "CONDOMINIUM",
      "familyMembers": []
}
```
Example Response (Failure)

      Household with Id: 10 not found
### create-family-member
Example Request
```json
Request Method: POST /create-family-member
Request Body:
{
      "familyMemberToCreate": {
            "dateOfBirth": "1996-12-30",
            "gender": "male",
            "name": "Bryan",
            "annualIncome": 100,
            "maritalStatus": "single",
            "occupationType": "STUDENT"
      },
      "householdId": 1,
      "spouseId": null
}
```
Example Response (Success)
```json
{
      "familyMemberId": 2,
      "name": "Bryan",
      "gender": "male",
      "maritalStatus": "single",
      "occupationType": "STUDENT",
      "annualIncome": 100.0,
      "dateOfBirth": "1996-12-30",
      "spouse": null,
      "household": 1
}
```
Example Response (Failure)
```json
{
      "householdId": "Household Id is mandatory",
      "familyMemberToCreate.dateOfBirth": "Date of Birth is mandatory",
      "familyMemberToCreate.gender": "Gender is mandatory",
      "familyMemberToCreate.name": "Name is mandatory",
      "familyMemberToCreate.occupationType": "Please provide either UNEMPLOYED, EMPLOYED, STUDENT",
      "familyMemberToCreate.annualIncome": "Annual Income is mandatory",
      "familyMemberToCreate.maritalStatus": "Marital Status is mandatory"
}
```
### delete-family-member
Example Request

      Request Method: DELETE /delete-family-member/1
Example Response (Success)
```json
{
    "familyMemberId": 1,
    "name": "BBB",
    "gender": "MALE",
    "maritalStatus": "single",
    "occupationType": "STUDENT",
    "annualIncome": 100.0,
    "dateOfBirth": "1996-12-30",
    "spouse": null,
    "household": 2
}
```
Example Response (Failure)

      Family Member with Id: 10 not found
### search-households
This endpoint serves as a search filter for households. All search parameters are in the form of query parameters and are optional. Validation is also carried out in some scenarios, for e.g. if householdSizeLT = 1 and householdSizeMT = 5, this will trigger a validation error as household size cannot be more than 5 and less than 1 at the same time.

Query Parameters

| Paramter          | Description                              | Type    | Example |
|-------------------|------------------------------------------|---------|---------|
| householdSizeLT   | Household Size less than                 | Integer | 5       |
| householdSizeMT   | Household Size more than                 | Integer | 1       |
| householdIncomeLT | Household Income less than               | Double  | 1000.00 |
| householdIncomeMT | Household Income more than               | Double  | 500.00  |
| hasAgeLT          | Household has Family Member Younger than | Integer | 5       |
| hasAgeMT          | Household has Family Member Older than   | Integer | 60      |
| hasSpouse         | Household with Husband & Wife            | Boolean | true    |

In-Use
| Grant Schemes               | Criteria                                                                                        | URL                                                   |
|-----------------------------|-------------------------------------------------------------------------------------------------|-------------------------------------------------------|
| Student Encouragement Bonus | ● Households with children of less than 16 years old.<br />● Household income of less than $150,000. | /search-households?hasAgeLT=16&householdIncomeLT=150000 |
| Family Togetherness Scheme  | ● Households with husband & wife<br />● Has child(ren) younger than 18 years old.                    | /search-households?hasSpouse=true&hasAgeLT=18                                                     |
| Elder Bonus                 | ● HDB household with family members above the age of 50.                                        | /search-households?hasAgeMT=50                                                       |
| Baby Sunshine Grant         | ● Household with young children younger than 5.                                                 | /search-households?hasAgeLT=5                                                      |
| YOLO GST Grant              | ● HDB households with annual income of less than $100,000.                                      | /search-households?householdIncomeLT=100000                                                      |

### Assumptions
* Family Member cannot be created without a Household
* Deleting Household will delete all Family Members
* You can create a spouse under a different Household
