package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions
			(
			//This is one way of specifying feature file
					//features=".//Features/Customers.feature",
			//To specify all feature files 
					//features={".//Features/"},
			//Multiple feature files
			features={".//Features/Login.feature",".//Features/Customers.feature"},
			//features=".//Features/Login.feature",	
			glue ="stepDefinitions",
			dryRun=false,
			monochrome=true,
			plugin = {"pretty","html:test-output"},
			tags = {"@sanity"}
			//tags = {"@sanity,@regression"} => executes scenarios sanity or regression
			//tags = {"@sanity","@regression"} => executes scenarios sanity and regression
			)

public class TestRun {

}
