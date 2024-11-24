### Task 3
Build a RESTful API for a simple social media application using Spring Boot, Hibernate, and PostgreSQL. 
The application should allow users to create and view posts, follow other users, and like posts. 
Each post should have a title, body, and author. Use Hibernate to persist the post and user data in the database.

### Instructions on how to run it
- To see available endpoints, run application, go to [http://localhost:8080/swagger-ui.html]().
- JaCoCo: generates a coverage report in [target/site/jacoco/index.html](). To run "mvn clean test", 
after run "mvn jacoco:report".
- Checkstyle: checks coding style and complexity against the rules defined in [checkstyle-result.xml](). 
To run "mvn checkstyle:check".

### Short feedback for each task
- Was it easy to complete the task using AI?<br>
  Answer: AI makes some tasks much easier.
- How long did task take you to complete?<br>
  Answer: around 3 hours.
- Was the code ready to run after generation? What did you have to change to make it usable?<br>
  Answer: The code was ready to run after generation, changed the location of the credential, added Flyway, etc.
- Which challenges did you face during completion of the task?<br>
  Answer: Adding quality checks (coverage, complexity, check style).
- Which specific prompts you learned as a good practice to complete the task?<br>
  Answer: Is it ok to store DB credentials in the source code or properties files? Please fix it. Create JUnit tests.
Add test coverage.