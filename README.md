Setup
-----

### Available Api

 * GET `/api/products` (get a list of products) 				
 * GET `/api/products/{id}` (get one product from the list)
 * PUT `/api/products/{id}` (update a single product)
 * POST `/api/products` (create a product)	
 
 The swagger-ui is accessible via `[host]:[port]/rabobank/swagger-ui.html`

### Running the application

Either run this in maven using `mvn spring-boot:run` or using the run features from your IDE of choice.

### Embedded H2 database

During application startup an embedded h2 database is started and loaded with product data.   
You can view the database through the h2 webconsole.   

Url: [http://localhost:8080/h2-console]   
Database url: `jdbc:h2:mem:rabobank`   
Username: `sa` (no password required)   
