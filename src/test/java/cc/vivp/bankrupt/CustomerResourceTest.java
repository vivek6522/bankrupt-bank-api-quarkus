package cc.vivp.bankrupt;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import cc.vivp.bankrupt.util.MessageKeys;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
/*
@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class CustomerResourceTest {

    @Test
    public void shouldDenyAccessGivenNotLoggedIn() {
        given()
            .when().get("/customers/1")
            .then().assertThat()
            .statusCode(HttpStatus.SC_FORBIDDEN)
            .body("message", equalTo(MessageKeys.CUSTOMER_ID_MISMATCH));
    }

}
*/