# MessageSender

# KafkaSender

A web application that allows users to send messages to predefined Kafka topics through a simple graphical interface. The system is completely containerized using Docker and orchestrated via `docker-compose.yml`.

### Frontend (React)
- User-friendly interface
- Text input for messages
- Dropdown to select a Kafka topic
- Send button to dispatch messages to the backend

### Backend (Spring Boot)
- REST API to receive and forward messages
- Kafka producer service that publishes messages to the selected topic

### Kafka (Dockerized)
- Local Kafka broker
- 2–3 predefined topics (e.g., `logs`, `alerts`, `messages`)
- Integrated via Docker Compose
