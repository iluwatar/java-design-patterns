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
import java.util.Map;


/**
 * Interprets the LOB generated from serializers for each database software.
 */

public class LOBInterpreter {

    private static ArrayList<String> lobTokenizer = new ArrayList<>();
    private static HashMap<String,String> columns = new HashMap<>();
    private static HashMap<String,String> object = new HashMap<>();

    public static ArrayList<String> readLOB(String lob){
        boolean hasNext = true;

        while(hasNext){
            int next = lob.indexOf(" ");
            if(next == -1){
                lobTokenizer.add(lob);
                hasNext = false;
            } else{
                lobTokenizer.add(lob.substring(0,next));
                lob = lob.substring(next);
            }
        };
        return lobTokenizer;
    }

    public static HashMap<String,String> getColumns(ArrayList<String> clob){
        int columnCounts = Integer.getInteger( clob.get(0));
        for(int i = 0 ;i<columnCounts ; i++){
            columns.put(clob.get(i*2+1),clob.get(i*2+2));
        }
        return null;
    }

    // This is after Columns part of the CLOB.
    public static String getObjects(ArrayList<String> clob){
        StringBuilder objects = new StringBuilder();
        int columnCounts = Integer.getInteger(clob.get(0));
        for(int i = columnCounts*2+2;i<clob.size();i++){
            if(i < clob.size() -  1){
                objects.append(clob.get(i)).append(" ");
            } else{
                objects.append(clob.get(i));
            }
        }

        return objects.toString();
    }

    // Retrieve a single object from the CLOB.
    public static HashMap<String, String> getObject(ArrayList<String> clob, int index){
        int columnCounts = Integer.getInteger(clob.get(0));
        int startOfObject = (columnCounts*(2+index)) +2 ;
        for(int i = 0;i<columnCounts;i++){
            object.put(clob.get((i*2)+1),clob.get(startOfObject+i));
        }

        return object;
    }

}
