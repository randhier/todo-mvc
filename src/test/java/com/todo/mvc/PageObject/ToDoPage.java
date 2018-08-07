package com.todo.mvc.PageObject;

import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;

public class ToDoPage {
    private WebDriver driver;
    private static By header = By.tagName("h1");
    private static By toDoInput = By.className("new-todo");
    private static By items = By.cssSelector("ul[class='todo-list']>li");
    private static By toDoItemCount = By.cssSelector(".todo-count > strong");
    private static By completedButton = By.className("clear-completed");
    private static String itemEdit = "input[value='%s'][class='edit']";
    private static By itemInput = By.cssSelector("input[class='toggle']");

    public ToDoPage(WebDriver driver){
        this.driver = driver;
    }

    public String getHeaderText() {
        WebElement element = getElement(header);
        return element.getText();
    }

    public void enterToDo(String toDo){
        WebElement element = getElement(toDoInput);
        element.sendKeys(toDo);
        element.sendKeys(Keys.ENTER);
    }

    public List<String> getItems(){
        List<String> itemList = new ArrayList<String>();
        List<WebElement> elementList = driver.findElements(items);
        for(WebElement current: elementList){
            WebElement element =  current.findElement(By.tagName("label"));
            itemList.add(element.getText());
        }
        return itemList;
    }

    public String getItemCount(){
        WebElement element = getElement(toDoItemCount);
        return element.getText();
    }

    public void checkItemAsCompleted(String item) {
        WebElement element = getParentElement(By.cssSelector(String.format(itemEdit, item)));
        element = element.findElement(itemInput);
        element.click();
    }

    public void clickCompletedButton(){
        WebElement element = getElement(completedButton);
        element.click();
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException Ex) {
            return false;
        }
    }

    private WebElement getParentElement(By selector){
        WebElement child = getElement(selector);
        return child.findElement(By.xpath(".."));
    }

    private WebElement getElement(By by){
        return driver.findElement(by);
    }

    //for testing
    private void printElement(WebElement element){
        System.out.println(element.getAttribute("outerHTML"));
    }

    //for testing
    private void printElement(By selector){
        WebElement element = getElement(selector);
        printElement(element);
    }
}
