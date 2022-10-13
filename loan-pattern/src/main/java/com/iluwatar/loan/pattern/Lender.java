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
package com.iluwatar.loan.pattern;

import java.io.*;

/**
 * The loan pattern is a design pattern in which users do not need to focus on how to manage resources,
 * but only need to control how the resources are used. Users implement the WriteBlock or ReadBlock
 * interface to define how to control the resources such as what data is written to a file, how to read data
 * and operate the data after reading it. The Lender class would help users manage resources. For example, it creates
 * a BufferedWriter/BufferedReader instance for a file and closes the resource after using it.
 */
public class Lender {

    /**
     * write data to a given file
     * @param fileName a file path.
     * @param block    an implementation of the WriteBlock interface.
     */
    public static void writeUsing(String fileName, WriteBlock block) throws IOException {
        // helps users create a file and a BufferedWriter instance.
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bufferedWriter = new BufferedWriter(fw);
        try {
            // execute user-defined logic.
            block.call(bufferedWriter);
        } finally {
            // helps users close the resource.
            bufferedWriter.close();
        }
    }

    /**
     * read data from a given file
     * @param fileName a file path.
     * @param block    an implementation of the ReadBlock interface.
     */
    public static void readUsing(String fileName, ReadBlock block) throws IOException {
        // helps users create a BufferedReader instance.
        File file = new File(fileName);
        FileReader fr = new FileReader(file.getAbsoluteFile());
        BufferedReader bufferedReader = new BufferedReader(fr);
        try {
            // execute user-defined logic
            block.call(bufferedReader);
        } finally {
            // helps users close the resource.
            bufferedReader.close();
        }
    }

}