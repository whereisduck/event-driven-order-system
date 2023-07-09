## Reactive microservice-based order management system

Create an event driven microservices with Kafka is the main goal.

![Architecture Diagram](/architecture.png)


- Database using MongoDB.
- Eureka for service discovery.
- Micrometer and Zipkin for logging and services tracking.
- Business logic:
- Order Service creates when receive an order request, and updates order satus during order processing.
- Warehouse Service reserves/releases stock(if order rejected or pending for too long) for orders and creates shipment records.
- Finance Service processes payments and creates invoices.
- Catalogue Service contains product-related information (i.e. products, details, prices)
- Notification Service sends notifications to customers informing them about different order stages
- Customer Service contains customer-related information (i.e. name, contact information)
- Each service is decoupled, communicates by receiving and generating events.


## Technologies

- Spring Webflux
- Spring Kafka
- Spring Reactive Mongo
- MongoDB
- Kafka
- Zipkin
- Eureka
- Docker
