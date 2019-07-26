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
package com.iluwatar.indextable;

import com.iluwatar.indextable.Data;
import com.iluwatar.indextable.Table;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


/**
 * 
 * The Index Table pattern...
 */
public class App {

    //private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private final Table table;

    public App() {
        table = new Table();
    }

    public void add(int key, Data data) {
        table.add(key, data);
    }

    public void printTable(){
        System.out.println(table);
    }

    public static void main(String[] args) {
        App app = new App();
        app.add(1, new Data("Smith", "Redmond"));
        app.add(2, new Data("Jones", "Seattle"));
        app.add(3, new Data("Robinson", "Portland"));
        app.add(4, new Data("Brown", "Redmond"));
        app.add(5, new Data("Smith", "Chicago"));
        app.add(6, new Data("Green", "Redmond"));
        app.add(7, new Data("Clarke", "Portland"));
        app.add(8, new Data("Smith", "Redmond"));
        app.add(9, new Data("Jones", "Chicago"));
        app.printTable();
        System.out.println(app.table.findByKey(1).getTown()+" "+app.table.findByKey(1).getName()+"测试");
        for (int a:app.table.findByTown("Redmond")) {
            System.out.println(a);
        }
    }
}