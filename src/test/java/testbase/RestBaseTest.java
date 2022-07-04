package testbase;

import enums.Endpoints;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import net.serenitybdd.rest.SerenityRest;
import org.junit.BeforeClass;

public class RestBaseTest {
    @BeforeClass
    public static void init(){
        RestAssured.baseURI = "https://schqarecruitment.azurewebsites.net/v1";
        SerenityRest.rest().when().header(new Header("Accept", "application/json")).get(RestAssured.baseURI + Endpoints.CLEAR_DB.getPath());
    }
}
