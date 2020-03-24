package contracts.consumer1;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.toomuchcoding.jsonassert.JsonAssertion;
import contracts.RestBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions;

public class RestTest extends RestBase {
    public RestTest() {
    }

    @Test
    public void validate_getHelloWorld() throws Exception {
        RequestSpecification request = RestAssured.given();
        Response response = (Response)RestAssured.given().spec(request).get("/", new Object[0]);
        SpringCloudContractAssertions.assertThat(response.statusCode()).isEqualTo(200);
        DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
        JsonAssertion.assertThatJson(parsedJson).field("['message']").matches("Hello, [A-z]*!");
    }
}
