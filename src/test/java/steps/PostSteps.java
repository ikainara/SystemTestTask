package steps;


import enums.Endpoints;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import model.RequestProduct;
import model.ResponseProduct;
import net.serenitybdd.rest.SerenityRest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.Assert;

public class PostSteps {
    RequestProduct requestProduct;
    ResponseProduct responseProduct;
    int expectingResultCode;
    Response getProductResponse;
    Response postProductResponse;

    Header acceptHeader = new Header("Accept", "application/json");
    Headers headers = new Headers(acceptHeader, new Header("Content-Type", "application/json"));

    public PostSteps() {
        RestAssured.basePath = Endpoints.ADD_PRODUCT.getPath();
    }

    @Given("^User has product with \"(.*)\", \"(.*)\", \"(.*)\" and \"(.*)\" expecting \"(.*)\"$")
    public void userHasProduct(String name, Double price, Integer count, Boolean isActive, int expectingResultCode) {
        this.expectingResultCode = expectingResultCode;
        requestProduct = RequestProduct.builder().name(name).price(price).itemCount(count).active(isActive).build();
    }

    @Given("^User post new product with name > 50 symbols$")
    public void userHasProductWithTooLongName() {
        requestProduct = RequestProduct.builder().name(RandomStringUtils.randomAlphabetic( 51)).price(2d).itemCount(3).active(true).build();
    }

    @Given("^User post new product without product name$")
    public void userHasProductWithoutName() {
        requestProduct = RequestProduct.builder().name(null).price(2d).itemCount(3).active(true).build();
    }

    @When("^User post this product to server$")
    @SneakyThrows
    public void userPostProductToServer() {
        postProductResponse = RestAssured.given().headers(headers).body(requestProduct).post();
    }

    @And("^Checks post method response status$")
    @SneakyThrows
    public void checkPostMethodResponse() {
        Assert.assertEquals(String.format("Verify response status is %d", expectingResultCode), postProductResponse.getStatusCode(), expectingResultCode);
        if(expectingResultCode == 200) {
            responseProduct = postProductResponse.as(ResponseProduct.class);
            Assert.assertTrue(responseProduct.isSameAsRequest(requestProduct));
        }
    }

    @And("^User making a get call for posted product by ID$")
    @SneakyThrows
    public void userMakingGetCallForProduct() {
        if(expectingResultCode == 200) {
            getProductResponse = SerenityRest.rest().when().header(acceptHeader).get(RestAssured.baseURI + RestAssured.basePath + "/" + responseProduct.getId());
        }
    }

    @Then("^Server response product correspond to posted one$")
    public void serverResponseCorrespondToExpectedOne() {
        if(expectingResultCode == 200) {
            Assert.assertEquals(String.format("Verify product \n%s\n returned from service", requestProduct.toString()), HttpStatus.SC_OK,  getProductResponse.getStatusCode());
            ResponseProduct rProduct = getProductResponse.as(ResponseProduct.class);
            Assert.assertTrue("Verify returned product equals to posted one", rProduct.isSameAsRequest(requestProduct));
        }
    }

    @Then("^Server response error 400$")
    public void serverResponseError400() {
        Assert.assertEquals("Verify server response is Error 400", HttpStatus.SC_BAD_REQUEST, postProductResponse.getStatusCode());
    }
}
