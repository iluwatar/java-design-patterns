/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.pageobject.pages;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlPasswordInput;
import org.htmlunit.html.HtmlSubmitInput;
import org.htmlunit.html.HtmlTextInput;
import java.io.IOException;

/**
 * Page Object encapsulating the Login Page (login.html)
 */
public class LoginPage extends Page {

  private static final String LOGIN_PAGE_HTML_FILE = "login.html";
  private static final String PAGE_URL = "file:" + AUT_PATH + LOGIN_PAGE_HTML_FILE;

  private HtmlPage page;

  /**
   * Constructor
   *
   * @param webClient {@link WebClient}
   */
  public LoginPage(WebClient webClient) {
    super(webClient);
  }

  /**
   * Navigates to the Login page
   *
   * @return {@link LoginPage}
   */
  public LoginPage navigateToPage() {
    try {
      page = this.webClient.getPage(PAGE_URL);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return this;
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
    var usernameInputTextField = (HtmlTextInput) page.getElementById("username");
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
    var passwordInputPasswordField = (HtmlPasswordInput) page.getElementById("password");
    passwordInputPasswordField.setText(password);
    return this;
  }


  /**
   * Clicking on the login button to 'login'
   *
   * @return {@link AlbumListPage} - this is the page that user gets navigated to once successfully
   *     logged in
   */
  public AlbumListPage login() {
    var loginButton = (HtmlSubmitInput) page.getElementById("loginButton");
    try {
      loginButton.click();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new AlbumListPage(webClient);
  }

}
