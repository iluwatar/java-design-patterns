/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.pageobject.pages;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Page Object encapsulating the Login Page (login.html)
 */
public class LoginPage extends Page {

  private static final String LOGIN_PAGE_HTML_FILE = "login.html";
  private static final String PAGE_URL = "file:" + AUT_PATH + LOGIN_PAGE_HTML_FILE;

  private HtmlPage page;

  private HtmlTextInput usernameInputTextField;
  private HtmlPasswordInput passwordInputPasswordField;
  private HtmlSubmitInput loginButton;


  /**
   * Constructor
   *
   * @param webClient {@link WebClient}
   */
  public LoginPage(WebClient webClient) {
    super(webClient);
    try {
      page = this.webClient.getPage(PAGE_URL);

      usernameInputTextField = (HtmlTextInput) page.getElementById("username");
      passwordInputPasswordField = (HtmlPasswordInput) page.getElementById("password");
      loginButton = (HtmlSubmitInput) page.getElementById("loginButton");

    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAt() {
    return "Login".equals(page.getTitleText());
  }


  /**
   * Enters the username into the username input text field
   *
   * @param username the username to enter
   * @return {@link LoginPage}
   */
  public LoginPage enterUsername(String username) {
    usernameInputTextField.setText(username);
    return this;
  }


  /**
   * Enters the password into the password input password field
   *
   * @param password the password to enter
   * @return {@link LoginPage}
   */
  public LoginPage enterPassword(String password) {
    passwordInputPasswordField.setText(password);
    return this;
  }


  /**
   * Clicking on the login button to 'login'
   *
   * @return {@link AlbumListPage}
   *        - this is the page that user gets navigated to once successfully logged in
   */
  public AlbumListPage login() {
    try {
      loginButton.click();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new AlbumListPage(webClient);
  }


}
