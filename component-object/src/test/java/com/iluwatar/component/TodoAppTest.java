/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.component;

import com.iluwatar.component.app.TodoApp;

import io.github.bonigarcia.wdm.ChromeDriverManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests various components.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TodoApp.class)
@WebIntegrationTest
public class TodoAppTest {
  private static WebDriver driver;

  @BeforeClass
  public static void setUp() {
    ChromeDriverManager.getInstance().setup();
    driver = new ChromeDriver();
  }

  @AfterClass
  public static void tearDown() {
    driver.quit();
  }

  @Test
  public void testCreateTodos() {
    // GIVEN
    new TodoPageObject(driver).get()

      // WHEN
      .addTodo("Buy groceries")
      .addTodo("Tidy up")

      // THEN
      .getTodoList()
      .verifyItemShown("Buy groceries", false)
      .verifyItemShown("Tidy up", false);
  }

  @Test
  public void testCompleteTodo() {
    // GIVEN
    new TodoPageObject(driver).get()
      .addTodo("Buy groceries")
      .addTodo("Tidy up")
      .getTodoList()

      // WHEN
      .clickOnItem("Buy groceries")

      // THEN
      .verifyItemShown("Buy groceries", true)
      .verifyItemShown("Tidy up", false);
  }

  @Test
  public void testSelectTodosActive() {
    // GIVEN
    TodoPageObject todoPage = new TodoPageObject(driver).get();

    todoPage
      .addTodo("Buy groceries")
      .addTodo("Tidy up")
      .getTodoList()
      .clickOnItem("Buy groceries");

    // WHEN
    todoPage
      .selectActive()

      // THEN
      .getTodoList()
      .verifyItemNotShown("Buy groceries")
      .verifyItemShown("Tidy up", false);
  }

  @Test
  public void testSelectTodosCompleted() {
    // GIVEN
    TodoPageObject todoPage = new TodoPageObject(driver).get();
    todoPage
      .addTodo("Buy groceries")
      .addTodo("Tidy up")
      .getTodoList()
      .clickOnItem("Buy groceries");

    // WHEN
    todoPage
      .selectCompleted()

      // THEN
      .getTodoList()
      .verifyItemShown("Buy groceries", true)
      .verifyItemNotShown("Tidy up");
  }

  @Test
  public void testSelectTodosAll() {
    // GIVEN
    TodoPageObject todoPage = new TodoPageObject(driver).get();
    todoPage
      .addTodo("Buy groceries")
      .addTodo("Tidy up")
      .getTodoList()
      .clickOnItem("Buy groceries");
    todoPage
      .selectCompleted()

      // WHEN
      .selectAll()

      // THEN
      .getTodoList()
      .verifyItemShown("Buy groceries", true)
      .verifyItemShown("Tidy up", false);
  }

  @Test
  public void testCreateGroceryItems() {
    // GIVEN
    new TodoPageObject(driver).get()

      // WHEN
      .addGroceryItem("avocados")
      .addGroceryItem("tomatoes")

      // THEN
      .getGroceryList()
      .verifyItemShown("avocados", false)
      .verifyItemShown("tomatoes", false);
  }

  @Test
  public void testCompleteGroceryItem() {
    // GIVEN
    new TodoPageObject(driver).get()
      .addGroceryItem("avocados")
      .addGroceryItem("tomatoes")
      .getGroceryList()

      // WHEN
      .clickOnItem("avocados")

      // THEN
      .verifyItemShown("avocados", true)
      .verifyItemShown("tomatoes", false);
  }

  @Test
  public void testSelectGroceryItemsActive() {
    // GIVEN
    TodoPageObject todoPage = new TodoPageObject(driver).get();

    todoPage
      .addGroceryItem("avocados")
      .addGroceryItem("tomatoes")
      .getGroceryList()
      .clickOnItem("avocados");

    // WHEN
    todoPage
      .selectActive()

      // THEN
      .getGroceryList()
      .verifyItemNotShown("avocados")
      .verifyItemShown("tomatoes", false);
  }

  @Test
  public void testSelectGroceryItemsCompleted() {
    // GIVEN
    TodoPageObject todoPage = new TodoPageObject(driver).get();
    todoPage
      .addGroceryItem("avocados")
      .addGroceryItem("tomatoes")
      .getGroceryList()
      .clickOnItem("avocados");

    // WHEN
    todoPage
      .selectCompleted()

      // THEN
      .getGroceryList()
      .verifyItemShown("avocados", true)
      .verifyItemNotShown("tomatoes");
  }

  @Test
  public void testSelectGroceryItemsAll() {
    // GIVEN
    TodoPageObject todoPage = new TodoPageObject(driver).get();
    todoPage
      .addGroceryItem("avocados")
      .addGroceryItem("tomatoes")
      .getGroceryList()
      .clickOnItem("avocados");
    todoPage
      .selectCompleted()

      // WHEN
      .selectAll()

      // THEN
      .getGroceryList()
      .verifyItemShown("avocados", true)
      .verifyItemShown("tomatoes", false);
  }

  @Test
  public void testSelectCombinedItemsActive() {
    // GIVEN
    TodoPageObject todoPage = new TodoPageObject(driver).get();

    todoPage
      .addTodo("Buy groceries")
      .addTodo("Tidy up")
      .addGroceryItem("avocados")
      .addGroceryItem("tomatoes");

    todoPage
      .getGroceryList()
      .clickOnItem("avocados");

    todoPage
      .getTodoList()
      .clickOnItem("Tidy up");

    // WHEN
    todoPage
      .selectActive();

    // THEN
    todoPage
      .getTodoList()
      .verifyItemShown("Buy groceries", false)
      .verifyItemNotShown("Tidy up");

    todoPage
      .getGroceryList()
      .verifyItemNotShown("avocados")
      .verifyItemShown("tomatoes", false);
  }
}
