/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppala
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

package com.iluwatar.pageobject

// ABOUTME: Page Object encapsulating the Login Page (login.html).
// ABOUTME: Provides methods to enter credentials and submit the login form.

import io.github.oshai.kotlinlogging.KotlinLogging
import org.htmlunit.WebClient
import org.htmlunit.html.HtmlPage
import org.htmlunit.html.HtmlPasswordInput
import org.htmlunit.html.HtmlSubmitInput
import org.htmlunit.html.HtmlTextInput
import java.io.IOException

private val logger = KotlinLogging.logger {}

/**
 * Page Object encapsulating the Login Page (login.html).
 */
class LoginPage(webClient: WebClient) : Page(webClient) {

    private lateinit var page: HtmlPage

    /**
     * Navigates to the Login page.
     *
     * @return this [LoginPage]
     */
    fun navigateToPage(): LoginPage {
        try {
            page = webClient.getPage("file:${AUT_PATH}$LOGIN_PAGE_HTML_FILE")
        } catch (e: IOException) {
            logger.error(e) { "An error occurred on navigateToPage." }
        }
        return this
    }

    override fun isAt(): Boolean = "Login" == page.titleText

    /**
     * Enters the username into the username input text field.
     *
     * @param username the username to enter
     * @return this [LoginPage]
     */
    fun enterUsername(username: String): LoginPage {
        val usernameInputTextField = page.getElementById("username") as HtmlTextInput
        usernameInputTextField.text = username
        return this
    }

    /**
     * Enters the password into the password input password field.
     *
     * @param password the password to enter
     * @return this [LoginPage]
     */
    fun enterPassword(password: String): LoginPage {
        val passwordInputPasswordField = page.getElementById("password") as HtmlPasswordInput
        passwordInputPasswordField.text = password
        return this
    }

    /**
     * Clicking on the login button to 'login'.
     *
     * @return [AlbumListPage] - this is the page that user gets navigated to once successfully
     *     logged in
     */
    fun login(): AlbumListPage {
        val loginButton = page.getElementById("loginButton") as HtmlSubmitInput
        try {
            loginButton.click<HtmlPage>()
        } catch (e: IOException) {
            logger.error(e) { "An error occurred on login." }
        }
        return AlbumListPage(webClient)
    }

    companion object {
        private const val LOGIN_PAGE_HTML_FILE = "login.html"
    }
}
