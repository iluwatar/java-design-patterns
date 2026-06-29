/*
 *
 *  * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *  *
 *  * The MIT License
 *  * Copyright © 2014-2022 Ilkka Seppälä
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  * THE SOFTWARE.
 *
 */
package com.iluwatar.onion.infrastructure.web;

import com.iluwatar.onion.application.dto.PersonResponse;
import com.iluwatar.onion.application.dto.SavePersonCommand;
import com.iluwatar.onion.application.usecase.GetPersonUseCase;
import com.iluwatar.onion.application.usecase.SavePersonUseCase;
import com.iluwatar.onion.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    private final SavePersonUseCase savePersonUseCase;
    private final GetPersonUseCase getPersonUseCase;

    public PersonController(SavePersonUseCase savePersonUseCase, GetPersonUseCase getPersonUseCase) {
        this.savePersonUseCase = savePersonUseCase;
        this.getPersonUseCase = getPersonUseCase;
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<PersonResponse> getPerson(@PathVariable("id") Long id) {
        var person = getPersonUseCase.execute(id);
        return ResponseEntity.ok(person);
    }

    @GetMapping("/persons")
    public ResponseEntity<List<PersonResponse>> getAllPersons() {
        var persons = getPersonUseCase.executeAll();
        return ResponseEntity.ok(persons);
    }

    @PostMapping("/persons")
    public ResponseEntity<PersonResponse> savePerson(@RequestBody SavePersonCommand command) {
        try {
            var savedPerson = savePersonUseCase.execute(command);
            return ResponseEntity.status(HttpStatus.OK).body(savedPerson);
        } catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
