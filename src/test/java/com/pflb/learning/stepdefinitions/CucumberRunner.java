package com.pflb.learning.stepdefinitions;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;


@CucumberOptions(
        strict = true,
        monochrome = true,
        features = ("src/test/resources/features"),
        glue = "com.pflb.learning.stepdefinitions"
)

public class CucumberRunner extends AbstractTestNGCucumberTests {
}
