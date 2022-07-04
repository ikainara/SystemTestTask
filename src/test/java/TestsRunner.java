
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;
import testbase.RestBaseTest;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = {"src/test/resources/features"})
public class TestsRunner extends RestBaseTest {

}
