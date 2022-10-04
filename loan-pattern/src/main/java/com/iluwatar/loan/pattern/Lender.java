package com.iluwatar.loan.pattern;

import java.io.*;

public class Lender {

    public static void writeUsing(String fileName, WriteBlock block) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bufferedWriter = new BufferedWriter(fw);
        try {
            block.call(bufferedWriter);
        } finally {
            bufferedWriter.close();
        }
    }

    public static void readUsing(String fileName, ReadBlock block) throws IOException {
        File inputFile = new File(fileName);
        FileReader fileReader = new FileReader(inputFile.getAbsoluteFile());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        try {
            block.call(bufferedReader);
        } finally {
            bufferedReader.close();
        }
    }

}