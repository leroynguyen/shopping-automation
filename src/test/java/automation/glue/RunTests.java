package automation.glue;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-reports"},
        features = "src/main/resources/features"
//        glue = "src/test/java/automation/glue"
)
public class RunTests {
    @Test
    public void tests(){}
}
