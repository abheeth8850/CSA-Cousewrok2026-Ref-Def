# CSA-Cousewrok2026-Ref-Def
# MLOps Pipeline Management API

## Student Information

**Student Name:** T.G. Abheeth Indumine

**Module:** 5COSC022C.2 Client-Server Architectures

**University of Westminster ID:** w2120667

**IIT Student ID:** 20232169

---

## GitHub Repository

https://github.com/abheeth8850/CSA-Cousewrok2026-Ref-Def


## Video Demonstration

Video Link:

TO BE ADDED

---

## Project Overview

This project implements a RESTful MLOps Pipeline Management API using JAX-RS (Jersey) and an embedded Grizzly HTTP server.

The API allows clients to:

- Discover API metadata
- Create and manage Machine Learning Workspaces
- Register Machine Learning Models
- Filter Models by status
- Record historical Evaluation Metrics
- Enforce business constraints using custom exception handling
- Log API requests and responses

The implementation follows RESTful architectural principles and uses in-memory data structures (HashMap and ArrayList) as required by the coursework specification.

No database technology has been used.

---

## Technology Stack

- Java 17
- Maven
- JAX-RS (Jersey)
- Grizzly HTTP Server
- Jackson JSON Provider

---

## Project Structure

src/main/java/com/abheeth/mlops

- Main.java
- config/
- resource/
- model/
- store/
- exception/
- mapper/
- filter/

---

## Build Instructions

1. Clone the repository

git clone https://github.com/abheeth8850/CSA-Cousewrok2026-Ref-Def.git

2. Open the project in NetBeans or another Java IDE.

3. Ensure Java 17 is installed.

4. Install Maven if not already available.

5. Build the project:

mvn clean install

---

## Running the Server

Start the application:

mvn exec:java

The API will start on:

http://localhost:8080/api/v1

---

## API Endpoints

### Discovery

GET /api/v1

### Workspaces

GET /api/v1/workspaces

POST /api/v1/workspaces

GET /api/v1/workspaces/{workspaceId}

DELETE /api/v1/workspaces/{workspaceId}

### Models

GET /api/v1/models

GET /api/v1/models?status=TRAINING

POST /api/v1/models

### Metrics

GET /api/v1/models/{modelId}/metrics

POST /api/v1/models/{modelId}/metrics

---

## Sample Curl Commands

### 1. Discovery

curl http://localhost:8080/api/v1

### 2. Create Workspace

curl -X POST http://localhost:8080/api/v1/workspaces \
-H "Content-Type: application/json" \
-d "{\"teamName\":\"Research Team Alpha\",\"storageQuotaGb\":100}"

### 3. Get All Workspaces

curl http://localhost:8080/api/v1/workspaces

### 4. Create Model

curl -X POST http://localhost:8080/api/v1/models \
-H "Content-Type: application/json" \
-d "{\"framework\":\"TensorFlow\",\"status\":\"TRAINING\",\"workspaceId\":\"WORKSPACE_ID\"}"

### 5. Get Models

curl http://localhost:8080/api/v1/models

### 6. Filter Models

curl "http://localhost:8080/api/v1/models?status=TRAINING"

### 7. Add Metric

curl -X POST http://localhost:8080/api/v1/models/MODEL_ID/metrics \
-H "Content-Type: application/json" \
-d "{\"accuracyScore\":0.95}"

### 8. Get Metrics

curl http://localhost:8080/api/v1/models/MODEL_ID/metrics

---

## Postman Collection

A complete Postman collection is included in the repository.

File:

MLOpsAPI.postman_collection.json

The collection demonstrates:

- Discovery endpoint
- Workspace creation
- Workspace retrieval
- Model creation
- Model filtering
- Metric creation
- Metric retrieval
- Exception handling scenarios

Run the collection from top to bottom to execute the full workflow.



# Part 1 – API Setup & Discovery

## Question 1 – Explain how the Discovery Endpoint was implemented and how it supports API discoverability.

The Discovery Endpoint was implemented using a JAX-RS resource class named `DiscoveryResource`. This resource is exposed through the root endpoint of the API and allows clients to obtain metadata about the service without needing prior knowledge of the available resources.

The endpoint returns a JSON response containing the API version, administrative contact information, and links to the primary resources available within the system. These resources include workspace management and model management endpoints.

Providing a discovery endpoint improves API usability because clients can dynamically identify available functionality. This approach follows REST principles by exposing navigational information through resource representations rather than requiring hardcoded endpoint knowledge.

The endpoint is registered through the `ApiApplication` configuration class and is accessible through the API base URL.

---

## Question 2 – Explain the purpose of the ApiApplication class and how resources are registered.

The `ApiApplication` class extends Jersey's `ResourceConfig` class and acts as the central configuration point for the API.

Its primary responsibility is to register all resources, exception mappers, and filters used throughout the application. Resource package scanning is configured to automatically discover REST resources located within the resource package.

This configuration ensures that incoming HTTP requests can be routed correctly to the appropriate resource classes.

The class also registers custom exception mappers and logging filters, allowing error handling and observability functionality to be applied globally across the API.

By centralising configuration within a single class, the application becomes easier to maintain and extend.

---

# Part 2 – Workspace Management

## Question 1 – Explain how workspace management was implemented.

Workspace management was implemented through the `WorkspaceResource` class.

The API supports creating, retrieving, listing, and deleting machine learning workspaces. Each workspace represents an isolated environment used to organise machine learning models.

Workspace data is stored in memory using a HashMap located within the `DataStore` class. The workspace identifier acts as the key, while the workspace object acts as the value.

The following operations are supported:

- Create Workspace (POST)
- Retrieve All Workspaces (GET)
- Retrieve Workspace by ID (GET)
- Delete Workspace (DELETE)

When a workspace is created, a unique identifier is generated and assigned to the workspace before storing it within the DataStore.

Using RESTful endpoints allows workspace resources to be managed through standard HTTP methods.

---

## Question 2 – Why was HashMap chosen for workspace storage?

The coursework specification requires the use of in-memory data structures such as HashMap and ArrayList instead of database technologies.

HashMap was selected because it provides efficient key-based access to workspace objects. Since workspaces are frequently retrieved using their unique identifier, HashMap allows near constant-time lookup performance.

Using HashMap also simplifies implementation because workspaces can be stored and retrieved directly using their IDs without requiring database queries.

This design satisfies the coursework requirement while maintaining simplicity and performance.

---

# Part 3 – Models & Filtering

## Question 1 – Explain how model management was implemented.

Model management was implemented through the `ModelResource` class.

Each machine learning model belongs to a workspace and contains information such as:

- Model ID
- Framework
- Status
- Latest Accuracy
- Workspace ID

Models are stored in memory using a HashMap within the `DataStore` class.

The API supports:

- Creating models
- Listing models
- Filtering models by status

Before a model can be created, the system verifies that the specified workspace exists. If the workspace cannot be found, a custom exception is thrown.

This validation prevents orphaned models from being created.

The implementation follows RESTful design principles by exposing model resources through dedicated endpoints and HTTP methods.

---

## Question 2 – Explain how model filtering was implemented.

Model filtering was implemented using the `@QueryParam` annotation.

Clients can supply a status query parameter when requesting models.

Example:

GET /models?status=TRAINING

When a status value is provided, the API filters the collection of stored models and returns only those matching the requested status.

If no status parameter is supplied, all models are returned.

This approach provides a flexible mechanism for retrieving subsets of resources without creating additional endpoints.

Using query parameters aligns with REST conventions and improves API usability.

---

# Part 4 – Sub-Resources & Evaluation Metrics

## Question 1 – Explain how the sub-resource locator pattern was implemented.

The sub-resource locator pattern was implemented to manage evaluation metrics associated with individual machine learning models.

Within the `ModelResource` class, requests targeting model metrics are delegated to the `EvaluationMetricResource` class.

This allows URLs to follow a hierarchical structure:

/models/{modelId}/metrics

The sub-resource locator returns an instance of the metrics resource responsible for handling requests related to evaluation metrics.

This design improves modularity because model functionality and metric functionality remain separated into dedicated resource classes.

The approach also reflects the natural relationship between models and their evaluation metrics.

Using sub-resource locators is a common JAX-RS design pattern for representing nested resources.

---

# Part 5 – Error Handling & Logging

## Question 1 – Explain the custom exception handling strategy.

Several custom exceptions were implemented to enforce business rules and provide meaningful error responses.

### WorkspaceNotEmptyException

This exception is thrown when a client attempts to delete a workspace that still contains models.

The exception is mapped to HTTP Status Code:

409 Conflict

### LinkedWorkspaceNotFoundException

This exception is thrown when a client attempts to create a model using a workspace identifier that does not exist.

The exception is mapped to HTTP Status Code:

422 Unprocessable Entity

### ModelDeprecatedException

This exception is thrown when a client attempts to add evaluation metrics to a model that is marked as deprecated.

The exception is mapped to HTTP Status Code:

403 Forbidden

Each exception is processed by a dedicated ExceptionMapper that converts the exception into a structured JSON error response.

This provides consistent and user-friendly error messages while preventing internal implementation details from being exposed.

---

## Question 2 – Explain the purpose of the GlobalExceptionMapper.

The `GlobalExceptionMapper` acts as a fallback error handler.

If an unexpected exception occurs that is not handled by a specific exception mapper, the GlobalExceptionMapper intercepts the exception and returns a standardised JSON error response.

The mapper returns HTTP Status Code:

500 Internal Server Error

This prevents stack traces and sensitive implementation details from being exposed to API consumers.

Using a global exception handler improves robustness and provides consistent error formatting across the application.

---

## Question 3 – Explain how API request and response logging was implemented.

API observability was implemented using a custom logging filter named `ApiLoggingFilter`.

The filter implements:

- ContainerRequestFilter
- ContainerResponseFilter

For incoming requests, the filter logs information such as:

- HTTP method
- Request URI

For outgoing responses, the filter logs:

- HTTP status code

Logging is performed using Java's built-in logging framework.

This information is valuable when diagnosing API issues because developers can observe request flow, identify failing endpoints, and verify response outcomes.

The logging filter provides visibility into API behaviour without modifying individual resource classes.

---

## Question 4 – What useful information can be extracted from ContainerRequestContext and ContainerResponseContext?

From `ContainerRequestContext`, useful information includes:

1. Request URI

The URI identifies which endpoint the client is attempting to access and is useful when tracing requests.

2. HTTP Method

The HTTP method indicates the operation being performed, such as GET, POST, PUT, or DELETE.

From `ContainerResponseContext`, useful information includes:

1. HTTP Status Code

The status code indicates whether the request was successful or resulted in an error.

2. Response Headers

Headers provide additional information regarding the response and can assist with debugging communication issues.

These details are particularly valuable when diagnosing API server problems and understanding request-response behaviour.

---

# Conclusion

The completed implementation demonstrates the use of JAX-RS to develop a RESTful MLOps Pipeline Management API. The system supports workspace management, model management, status-based filtering, evaluation metric tracking, custom exception handling, and request-response logging while complying with the coursework requirement to use in-memory data structures instead of a database.


