/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
package com.iluwatar.composite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * Messenger
 *
 */
public class Messenger {

  LetterComposite messageFromOrcs() {

    List<Word> words = new ArrayList<>();

    words.add(new Word(Arrays.asList(new Letter('W'), new Letter('h'), new Letter('e'), new Letter(
        'r'), new Letter('e'))));
    words.add(new Word(Arrays.asList(new Letter('t'), new Letter('h'), new Letter('e'), new Letter(
        'r'), new Letter('e'))));
    words.add(new Word(Arrays.asList(new Letter('i'), new Letter('s'))));
    words.add(new Word(Arrays.asList(new Letter('a'))));
    words.add(new Word(Arrays.asList(new Letter('w'), new Letter('h'), new Letter('i'), new Letter(
        'p'))));
    words.add(new Word(Arrays.asList(new Letter('t'), new Letter('h'), new Letter('e'), new Letter(
        'r'), new Letter('e'))));
    words.add(new Word(Arrays.asList(new Letter('i'), new Letter('s'))));
    words.add(new Word(Arrays.asList(new Letter('a'))));
    words.add(new Word(Arrays.asList(new Letter('w'), new Letter('a'), new Letter('y'))));

    return new Sentence(words);

  }

  LetterComposite messageFromElves() {

    List<Word> words = new ArrayList<>();

    words.add(new Word(Arrays.asList(new Letter('M'), new Letter('u'), new Letter('c'), new Letter(
        'h'))));
    words.add(new Word(Arrays.asList(new Letter('w'), new Letter('i'), new Letter('n'), new Letter(
        'd'))));
    words.add(new Word(Arrays.asList(new Letter('p'), new Letter('o'), new Letter('u'), new Letter(
        'r'), new Letter('s'))));
    words.add(new Word(Arrays.asList(new Letter('f'), new Letter('r'), new Letter('o'), new Letter(
        'm'))));
    words.add(new Word(Arrays.asList(new Letter('y'), new Letter('o'), new Letter('u'), new Letter(
        'r'))));
    words.add(new Word(Arrays.asList(new Letter('m'), new Letter('o'), new Letter('u'), new Letter(
        't'), new Letter('h'))));

    return new Sentence(words);

  }

}
