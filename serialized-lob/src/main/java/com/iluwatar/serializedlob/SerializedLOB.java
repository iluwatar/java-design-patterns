/*
 * This project is licensed under the MIT license.
 * Module model-view-viewmodel is using ZK framework
 * licensed under LGPL (see lgpl-3.0.txt).
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

package com.iluwatar.serializedlob;

import java.util.HashMap;
import java.util.Map;

/**
 * Serialized CLOB should be a single String.
 * This class can store interpretations for the CLOB.
 */
public class SerializedLOB {
    // This is the original CLOB
    private String clob;

    // This stores columns' names and their types
    private HashMap<String, String> key;

    // This stores information about objects
    private String objects;

    public SerializedLOB(String clob, HashMap<String,String> key, String objects){
        this.clob = clob;
        this.key = key;
        this.objects = objects;
    }

    public SerializedLOB(){}

    public String getClob() {return clob;}

    public void setClob(String clob) {this.clob = clob;}

    public HashMap<String, String> getKey() {return key;}

    public void setKey(HashMap<String, String> key) {
        this.key = key;
    }

    public String getObjects() {return objects;}

    public void setObjects(String objects) {this.objects = objects;}
}

