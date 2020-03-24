package contracts.consumer1;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.toomuchcoding.jsonassert.JsonAssertion;
import contracts.RestBase;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessage;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierObjectMapper;
import org.springframework.cloud.contract.verifier.messaging.util.ContractVerifierMessagingUtil;

public class MessagingTest extends RestBase {
    @Inject
    ContractVerifierMessaging contractVerifierMessaging;
    @Inject
    ContractVerifierObjectMapper contractVerifierObjectMapper;

    public MessagingTest() {
    }

    @Test
    public void validate_pingPong() throws Exception {
        ContractVerifierMessage inputMessage = this.contractVerifierMessaging.create("{\"message\":\"ping\"}", ContractVerifierMessagingUtil.headers());
        this.contractVerifierMessaging.send(inputMessage, "input");
        ContractVerifierMessage response = this.contractVerifierMessaging.receive("output");
        SpringCloudContractAssertions.assertThat(response).isNotNull();
        DocumentContext parsedJson = JsonPath.parse(this.contractVerifierObjectMapper.writeValueAsString(response.getPayload()));
        JsonAssertion.assertThatJson(parsedJson).field("['message']").isEqualTo("pong");
    }
}
