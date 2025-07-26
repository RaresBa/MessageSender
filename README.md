# MessageSender - Backend

# KafkaSender

## Project Overview

This project is a Spring Boot application that provides a backend for a message-sending system. It implements both a Kafka Producer and a Kafka Consumer, exposing RESTful endpoints to send messages to Kafka and retrieve messages from specific topics. The API is fully documented using Swagger/OpenAPI for easy integration and testing.

## Technical Features

### 1. Kafka Producer API

-   **Endpoint**: `POST /api/messages/send`
-   **Functionality**: The `KafkaController` exposes this endpoint to send messages to a specified Kafka topic. It accepts a `MessageRequest` payload containing the topic name and the message content.
-   **API Documentation**: This endpoint is documented using `@Tag`, `@Operation`, and `@ApiResponses` annotations from the `springdoc-openapi` library. This makes the API's purpose, parameters, and possible responses clear and accessible via the Swagger UI.

### 2. Kafka Consumer Service

-   **Implementation**: The `KafkaConsumerService` class is responsible for listening to incoming messages.
-   **Message Listener**: A `@KafkaListener(topics = "${kafka.consumer.topics}")` is configured to continuously poll messages from the topics defined in `application.properties`.
-   **Data Storage**: Consumed messages are stored in-memory within a `Map<String, List<String>>`. The map's key is the topic name, and the value is a list of all messages received for that topic.

### 3. Message Reader API

-   **Endpoint**: `GET /api/consumer/messages`
-   **Functionality**: The `MessageReaderController` provides this endpoint to retrieve messages stored by the consumer. It requires a `topic` query parameter to specify which topic's messages should be returned.
-   **API Documentation**: Similar to the producer endpoint, this functionality is documented in Swagger UI, detailing the required query parameter and the expected JSON response format.

### 4. API Documentation (Swagger UI)

-   The project is integrated with `springdoc-openapi-starter-webmvc-ui`.
-   The full interactive API documentation, including all endpoints, request/response models, and status codes, is automatically generated and available at `http://localhost:8080/swagger-ui.html` when the application is running.

### 5. Configuration and Dependencies

-   **Dependencies**: The `pom.xml` file includes the `springdoc-openapi-starter-webmvc-ui` dependency for Swagger.
-   **CORS**: Cross-Origin Resource Sharing is enabled with `@CrossOrigin` to allow requests from the React frontend application.
