/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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
package com.iluwatar.saga.orchestration;

import java.util.ArrayList;
import java.util.List;

public class Saga {

    private List<Chapter> chapters;
    private Result result;

    public Saga() {
        this.chapters = new ArrayList<>();
        this.result = Result.INPROCESS;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public boolean isSuccess(){
        return result == Result.FINISHED;
    }

    public boolean isFinished(){
        return result != Result.INPROCESS;
    }

    public Saga chapter(String name, int timeoutInSec) {
        this.chapters.add(new Chapter(name, timeoutInSec));
        return this;
    }

    public Chapter get(int idx) {
        if (chapters.size() < idx || idx < 0) {
            throw new SagaException("idx shoud be less then ch size or more then 0");
        }
        return chapters.get(idx);
    }



    public static Saga create() {
        return new Saga();
    }

    public enum Result{
        FINISHED,CANCELED, INPROCESS;
    }
    private static class Chapter {
        String name;
        int timeoutInSec;

        public Chapter(String name, int timeoutInSec) {
            this.name = name;
            this.timeoutInSec = timeoutInSec;
        }
    }
}
