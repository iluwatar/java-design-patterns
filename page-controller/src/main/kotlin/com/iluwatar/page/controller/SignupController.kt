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

// ABOUTME: Controller for the signup page that handles GET and POST HTTP requests.
// ABOUTME: Routes requests to the appropriate view and manages form data via SignupModel.
package com.iluwatar.page.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

private val logger = KotlinLogging.logger {}

/** Signup Controller. */
@Controller
@Component
class SignupController {
    internal var view = SignupView()

    /** Handle http GET request. */
    @GetMapping("/signup")
    fun getSignup(): String {
        return view.display()
    }

    /** Handle http POST request and access model and view. */
    @PostMapping("/signup")
    fun create(form: SignupModel, redirectAttributes: RedirectAttributes): String {
        logger.info { form.name }
        logger.info { form.email }
        redirectAttributes.addAttribute("name", form.name)
        redirectAttributes.addAttribute("email", form.email)
        redirectAttributes.addFlashAttribute("userInfo", form)
        return view.redirect(form)
    }
}
