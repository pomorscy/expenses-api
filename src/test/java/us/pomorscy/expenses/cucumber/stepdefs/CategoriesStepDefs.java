package us.pomorscy.expenses.cucumber.stepdefs;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static us.pomorscy.expenses.TestDataHelper.SAMPLE_CATEGORIES;


public class CategoriesStepDefs extends AbstractStepDefs {

    @Before
    public void beforeScenario() {
        beginTransaction();
    }

    @After
    public void afterScenario() {
        rollbackChanges();
    }

    @Given("^There are categories$")
    public void iHaveTwoCategories() throws Throwable {
        int rowCount = this.jdbcTemplate.queryForObject("SELECT count(*) FROM categories", Integer.class);
        System.out.println("Categories count: " + rowCount);
        SAMPLE_CATEGORIES.forEach(category -> {
            jdbcTemplate.update("INSERT INTO categories (id, name) VALUES (?, ?)",
                    category.getId(), category.getName());
        });
    }

    @When("^the client calls /categories$")
    public void theClientCallsCategories() throws Throwable {
        int rowCount = this.jdbcTemplate.queryForObject("SELECT count(*) FROM categories", Integer.class);
        System.out.println("Categories count: " + rowCount);
    }

    @Then("^the client receives status code of (\\d+)$")
    public void theClientReceivesStatusCodeOf(int arg0) throws Throwable {
        int rowCount = this.jdbcTemplate.queryForObject("SELECT count(*) FROM categories", Integer.class);
        System.out.println("Categories count: " + rowCount);
    }

    @And("^the client receives a list containing these categories$")
    public void theClientReceivesAListContainingTheseCategories() throws Throwable {
        int rowCount = this.jdbcTemplate.queryForObject("SELECT count(*) FROM categories", Integer.class);
        System.out.println("Categories count: " + rowCount);
    }
}
