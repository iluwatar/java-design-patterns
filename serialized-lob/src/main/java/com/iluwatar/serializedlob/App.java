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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Example of how an LOB Interpreter and SerializedLOB class may be used
 */
public class App {

    private static String lob = "3 first String second Int third String hello 0 bye";
    private static ArrayList<String> list = new ArrayList<>();
    private static HashMap<String,String> columns = new HashMap<>();
    public static void main(String[] args) {
        list = LOBInterpreter.readLOB(lob);
        SerializedLOB slob = new SerializedLOB(lob, LOBInterpreter.getColumns(list),LOBInterpreter.getObjects(list));
        System.out.println("lob is " + slob.getClob());
        System.out.println("Its columns are : " + slob.getKey());
        System.out.println("And Objects are represented as " + slob.getObjects());
    }


}
