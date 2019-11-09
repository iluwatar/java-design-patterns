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
package com.iluwatar.saga.choreography;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Saga representation.
 * Saga consists of chapters.
 * Every Chapter is executed a certain service.
 */
public class Saga {

    private List<Chapter> chapters;
    private int pos;
    private boolean forward;
    private boolean finished;


    public static Saga create() {
        return new Saga();
    }
    public SagaResult getResult() {
        return !finished ?
                SagaResult.PROGRESS :
                forward ?
                        SagaResult.FINISHED :
                        SagaResult.ROLLBACKED;
    }
    public Saga chapter(String name) {
        this.chapters.add(new Chapter(name));
        return this;
    }
    public Saga setInValue(Object value){
        if(chapters.isEmpty()){
            return this;
        }
        chapters.get(chapters.size()-1).setInValue(value);
        return this;
    }
    public Object getCurrentValue(){
        return chapters.get(pos).getInValue();
    }
    public void setCurrentValue(Object value){
        chapters.get(pos).setInValue(value);
    }
    public void setCurrentStatus(ChapterResult result){
        chapters.get(pos).setResult(result);
    }

    void setFinished(boolean finished) {
        this.finished = finished;
    }
    boolean isForward() {
        return forward;
    }
    int forward() {
        return ++pos;
    }

    int back() {
        this.forward = false;
        return --pos;
    }


    public Saga() {
        this.chapters = new ArrayList<>();
        this.pos = 0;
        this.forward = true;
        this.finished = false;
    }

    Chapter getCurrent() {
        return chapters.get(pos);
    }


    boolean isPresent() {
        return pos >= 0 && pos < chapters.size();
    }
    boolean isCurrentSuccess(){
        return chapters.get(pos).isSuccess();
    }

    /***
     * Class presents a chapter status and incoming parameters(incoming parameter transforms to outcoming parameter)
     */
    public static class Chapter {
        private String name;
        private ChapterResult result;
        private Object inValue;


        public Chapter(String name) {
            this.name = name;
            this.result = ChapterResult.INIT;
        }

        public Object getInValue() {
            return inValue;
        }

        public void setInValue(Object object) {
            this.inValue = object;
        }

        public String getName() {
            return name;
        }
        public void setResult(ChapterResult result){
            this.result = result;
        }

        public boolean isSuccess(){
            return result == ChapterResult.SUCCESS;
        }
    }


    public enum ChapterResult {
        INIT, SUCCESS, ROLLBACK
    }

    public enum SagaResult {
        PROGRESS, FINISHED, ROLLBACKED
    }

    @Override
    public String toString() {
        return "Saga{" +
                "chapters=" + Arrays.toString(chapters.toArray()) +
                ", pos=" + pos +
                ", forward=" + forward +
                '}';
    }
}
