package com.todo.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import com.todo.mvc.PageObject.ToDoPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FeatureTest
{
    private static WebDriver driver;

    @BeforeClass
    public static void before(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://todomvc.com/examples/react/#/");
    }

    @AfterClass
    public static void after(){
//        driver.quit();
    }

    @Test
    public void featureTest()
    {
        ToDoPage toDoPage = new ToDoPage(driver);

        // Confirm page load
        assertThat(toDoPage.getHeaderText()).isEqualTo("todos");

        // Enter one item
        toDoPage.enterToDo("purchase shampoo");
        List<String> actualListOfItems = toDoPage.getItems();
        assertThat(actualListOfItems).containsOnly("purchase shampoo");

        // Enter two more items
        toDoPage.enterToDo("wash cat");
        toDoPage.enterToDo("purchase band-aids");
        actualListOfItems = toDoPage.getItems();
        assertThat(actualListOfItems).containsExactly("purchase shampoo", "wash cat", "purchase band-aids");
        assertThat(toDoPage.getItemCount()).isEqualTo("3");

        // Check one item as completed
        toDoPage.checkItemAsCompleted("wash cat");
        assertThat(toDoPage.getItemCount()).isEqualTo("2");

        // Click complete button
        toDoPage.clickCompletedButton();
        actualListOfItems  = toDoPage.getItems();
        assertThat(actualListOfItems).doesNotContain("wash cat");

        // Check if alert pops up via attempted script injection
        toDoPage.enterToDo("<script>alert('Injected!');</script>");
        assertThat(toDoPage.isAlertPresent()).isFalse();
        toDoPage.enterToDo("'<script>alert('Injected!');</script>'");
        assertThat(toDoPage.isAlertPresent()).isFalse();
        toDoPage.enterToDo("\"<script>alert('Injected!');</script>\"");
        assertThat(toDoPage.isAlertPresent()).isFalse();
    }
}
