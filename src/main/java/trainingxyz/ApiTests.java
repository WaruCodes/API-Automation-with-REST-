package trainingxyz;

import org.junit.Test;
import models.Product;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTests {

    // Test to fetch all categories from the API
    @Test
    public void getCategories() {
        // Define the API endpoint for retrieving categories
        String endpoint = "https://localhost:8888/api_testing/category/read.php";

        // Make a GET request to the API and store the response
        var response = given()
                .when()
                .get(endpoint)
                .then();

        // Log the response body to the console
        response.log().body();
    }

    // Test to fetch a specific product using an ID parameter
    @Test
    public void getProduct() {
        // Define the API endpoint for retrieving a specific product
        String endpoint = "https://localhost:8888/api_testing/product/read_one.php";

        // Make a GET request with a query parameter (ID = 2)
        var response =
                given()
                        .queryParam("id", 2)  // Add query parameter for product ID
                        .when()
                        .get(endpoint)       // Make GET request to the endpoint
                        .then();

        // Log the response body to the console
        response.log().body();
    }

    // verify status of code responses
    @Test  // Marks this method as a test case for JUnit
    public void getProduct() {

        // Define the API endpoint URL for retrieving a specific product
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";

        // Use Rest-Assured to make an API request
        given().  // "given" sets up the request's preconditions
                queryParam("id", 2).  // Adds a query parameter "id=2" to specify the product to retrieve
                when().  // "when" defines the action to be performed
                get(endpoint).  // Sends a GET request to the specified endpoint
                then().  // "then" defines the expected outcome of the request
                assertThat().  // Used to validate the response
                statusCode(200); // Asserts that the response status code should be 200 (OK)
                        body("id", equalTo("2")).
                        body("name", equalTo("Cross-Back Training Tank")).
                        body("description", equalTo("The most awesome phone of 2013!")).
                        body("price", equalTo("299.00")).
                        body("category_id", equalTo("2")).
                        body("category_name", equalTo("Active Wear - Women"));

    }


    // Test to create a new product in the system
    @Test
    public void createProduct() {
        // Define the API endpoint for creating a product
        String endpoint = "http://localhost:8888/api_testing/product/create.php";

        // JSON body containing the product details
        String body = """
                {
                "name": "Water Bottle",
                "description": "Blue water bottle. Holds 64 ounces",
                "price": 12,
                "category_id": 3
                }
                """;

        // Send a POST request with the request body
        var response = given()
                .body(body)  // Attach the request body (JSON payload)
                .when()
                .post(endpoint)  // Send POST request to the specified endpoint
                .then();

        // Log the response body to see the output
        response.log().body();
    }

    @Test
    public void updateProduct(){
        String endpoint = "http://localhost:8888/api_testing/product/update.php";
        String body = """
                {
                "id": 19,
                "name": "Water Bottle",
                "description": "Blue water bottle. Holds 64 ounces",
                "price": 15,
                "category_id": 3
                }
                """;
        var response = given().body(body).when().put(endpoint).then();
        response.log().body();
    }

    @Test
    public void deleteProduct(){
        String endpoint = "http://localhost:8888/api_testing/product/delete.php";
        String body = """
                {
                "id": 19
                }
                """;
        var response = given().body(body).when().delete(endpoint).then();
        response.log().body();
    }

    @Test
    public void createSweatband()
    {
        String endpoint = "http://localhost:8888/api_testing/product/create.php";
        String body = """
                    {             
                        "name" : "Sweatband",           
                        "description" : "White sweatband. One size fits all.",                
                        "price" : 5,               
                        "category_id" : 3          
                    }
                      """;
        var response = given().body(body).when().post(endpoint).then();
    }

    @Test
    public void updateSweatBand()
    {
        String endpoint = "http://localhost:8888/api_testing/product/update.php";
        String body = """
                    {   
                        "id": 26,          
                        "price" : 6                      
                    }
                   """;
        var response = given().body(body).put(endpoint).then();
        response.log().body();
    }

    @Test
    public void getSweatband() {
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
        var response =
                given().
                        queryParam("id", 26).
                        when().
                        get(endpoint).
                        then();
        response.log().body();
    }

    @Test
    public void deleteSweatband(){
        String endpoint = "http://localhost:8888/api_testing/product/delete.php";
        String body = """
                {         
                    "id" : 26       
                }
                """;
        var response = given().body(body).when().delete(endpoint).then();
        response.log().body();
    }

    @Test
    public void getProducts(){
        String endpoint = "http://localhost:8888/api_testing/product/read.php";
        given().
                when().
                get(endpoint).
                then().
                log().
                body(). // if we put headers().
                assertThat().
                statusCode(200).
                //header("Content-Type", equalTo("application/json; charset=UTF-8")).
                          body("records.size()", greaterThan(0)).
                          body("records.id", everyItem(notNullValue())). // every item should be passed
                          body("records.name", everyItem(notNullValue())).
                          body("records.description", everyItem(notNullValue())).
                          body("records.price", everyItem(notNullValue())).
                          body("records.category_id", everyItem(notNullValue())).
                          body("records.category_name", everyItem(notNullValue())).
                          body("records.id[0]", equalTo("25")); // If needed, verify a specific item in the array using its index.
    }

    @Test
    public void createSerializedProduct(){
        String endpoint = "http://localhost:8888/api_testing/product/create.php";
        Product product = new Product(
                "Water Bottle",
                "Blue water bottle. Holds 64 ounces",
                12,
                3
        );
        var response = given().body(product).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void getDeserializedProduct(){
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
        //"id":"2","name":"Cross-Back Training Tank","description":"The most awesome phone of 2013!","price":"299.00","category_id":"2","category_name":"Active Wear - Women"}
        Product expectedProduct = new Product(
                2,
                "Cross-Back Training Tank",
                "The most awesome phone of 2013!",
                299.00,
                2,
                "Active Wear - Women"
        );

        Product actualProduct =
                given().
                        queryParam("id", "2").
                        when().
                        get(endpoint).
                        as(Product.class);// get output as java object

        assertThat(actualProduct, samePropertyValuesAs(expectedProduct)); //compare two
    }
    @Test
    public void getMultiVitamins(){ // create new method
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php"; // add endpoint
        given().
                queryParam("id", 18). // given the ID is 18
                when().
                get(endpoint). // made the GET request
                then().
                assertThat().
                statusCode(200). // get request successfull
                    header("Content-Type", equalTo("application/json")).
                    body("id", equalTo("18")).
                    body("name", equalTo("Multi-Vitamin (90 capsules)")).
                    body("description", equalTo("A daily dose of our Multi-Vitamins fulfills a day’s nutritional needs " +
                            "for over 12 vitamins and minerals.")).
                    body("price", equalTo("10.00")).
                    body("category_id", equalTo("4")).
                    body("category_name", equalTo("Supplements"));
    }
}

