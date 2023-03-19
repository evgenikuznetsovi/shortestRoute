# The Shortest possible route calculation project

## General info
- **Runs on** 
  - [http://localhost:8080/](http://localhost:8080/)
- **H2 inmemory DB** 
  -  Username: h2user 
  - Password: h2user
## Tasks
- [x] Persist the Graph into an in-memory database
- [x] Read the file and import it into the DB
- [x] Expose the database using a RESTful Webservice
- [x] Implement the algorithm
- [ ] Expose the algorithm using a Document Literal Web service
- [x] Create a front end to capture the source and destination and then print the shortest path
- [x] Optional: Overlay the following data and re-compute

## Used algorithm
Given a positively weighted graph and a starting node in it, the application uses Dijkstra algorithm to determine the shortest path and distance from the source to all destinations in the graph.

## JAR file location
The jar file is located in the **/target** directory


