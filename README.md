### 📦 Box Dispatch Service ###

A Spring Boot implementation of the Box Dispatch Service assessment.
The service manages boxes, their lifecycle, items they carry, and operational constraints such as weight and battery levels.




### 🚀 Features ###

The service provides the following functionality:

✅ Create a new box

✅ Load a box with items (with validation)

✅ Check loaded items for a given box

✅ Check available boxes for loading

✅ Check battery level for a given box

✅ Unified API response wrapper (ApiResponse<T>)

✅ Global exception handling

✅ H2 in-memory database with sample seed data (data.sql)

✅ Unit tests with MockMvc




### 🏗️ Design Assumptions ###

The following assumptions guided the design approach:

Box lifecycle is state-driven

States: IDLE, LOADED, RETURNING.

Only IDLE and RETURNING boxes are available for new loading.

Battery threshold matters

Loading is only allowed if battery level ≥ 25%.

Weight limit enforced

Each box has a configurable weight limit (0–500g).

The system validates total weight before adding items.

Strict item validation

name: alphanumeric + _ or -.

code: uppercase letters/numbers + _.

Box identification

Business-friendly unique reference (txref) instead of database ID.

Entity relationships

One Box → many Items.

An Item belongs to exactly one Box.

Consistent API contracts

All endpoints return a wrapped ApiResponse with responseCode, responseMsg, and responseDesc.

Errors are handled globally for clean client responses.




### 📂 Project Structure ###
src/main/java/com/example/box
│── controller      # REST controllers
│── dto             # DTOs for Box & Item
│── entity          # JPA entities (Box, Item, BoxState)
│── repository      # Spring Data JPA repositories
│── service         # Interfaces and implementations
│── exception       # Global exception handling
│── payload         # ApiResponse wrapper




### ⚡ Endpoints ###
>> 1. Create a new box
>>
POST /api/boxes

{
  "txref": "BOX-001",
  "weightLimit": 400,
  "batteryCapacity": 80
}

2. Load items into a box

POST /api/boxes/{txref}/items

[
  { "name": "item_one", "weight": 200, "code": "ITEM_001" },
  { "name": "item_two", "weight": 100, "code": "ITEM_002" }
]

3. Get loaded items for a box

GET /api/boxes/{txref}/items

4. Get available boxes for loading

GET /api/boxes/available

5. Check battery level of a box

GET /api/boxes/{txref}/battery




### 🛠️ Build & Run ###

Requirements:

Java 17+

Maven

Run locally
git clone <repo-url>
cd box-dispatch-service
mvn clean package
mvn spring-boot:run

Run tests
mvn test

Access H2 console

http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:boxdb
User: sa
Password: (empty)

✅ Sample Responses

Success:

{
  "responseCode": "00",
  "responseMsg": "Success",
  "data": {
    "txref": "BOX-001",
    "weightLimit": 400,
    "batteryCapacity": 80,
    "state": "IDLE"
  }
}


Failure:

{
  "responseCode": "99",
  "responseMsg": "Failed",
  "responseDesc": "Weight exceeds box capacity"
}




### 🧪 Testing with Postman ###

Import the collection (or manually create requests using the endpoints above).

Create a box → Load items → Query items → Check availability → Check battery.

Validate different states (e.g., low battery, overweight, duplicate codes).

📌 Notes

Data is seeded automatically from data.sql.

Database resets on restart since it uses in-memory H2.

DTOs are used to prevent infinite recursion between Box ↔ Item.