package contracts;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@AutoConfigureMessageVerifier
@SpringBootTest(
    classes = {RestBase.Config.class},
    webEnvironment = WebEnvironment.NONE
)
public abstract class RestBase {
    @Value("${APPLICATION_BASE_URL}")
    String url;
    @Value("${APPLICATION_USERNAME:}")
    String username;
    @Value("${APPLICATION_PASSWORD:}")
    String password;

    public RestBase() {
    }

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = this.url;
        if (StringUtils.hasText(this.username)) {
            RestAssured.authentication = RestAssured.basic(this.username, this.password);
        }

    }

    @Configuration
    @EnableAutoConfiguration
    protected static class Config {
        protected Config() {
        }
    }
}
