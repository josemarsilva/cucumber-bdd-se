package org.gnu.automation.cucumber_bdd_se;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		monochrome = true, snippets = SnippetType.CAMELCASE, 
		plugin = { "json:target/surefire-reports/cucumber.json" }, 
		features = { 
				".\\src\\main\\resources\\features\\", 
				".\\src\\main\\resources\\features\\crmpro\\" 
				}, 
		glue = { 
				"org.gnu.automation.cucumber_bdd_se.steps",
				"org.gnu.automation.cucumber_bdd_se.steps.crmpro"
				}, 
		tags = { 
				"@CrmproTransacFinancPlanVsArq"
				}
		)

public class RunTest {

}
