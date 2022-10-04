package com.iluwatar.loan.pattern;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        writeFile("loan-pattern/src/main/resources/data.txt");
        readFile("loan-pattern/src/main/resources/data.txt");
    }

    public static void writeFile(String fileName) throws IOException {
        Lender.writeUsing(fileName,
                new WriteBlock() {
                    public void call(BufferedWriter out) throws IOException {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < 10; i++) {
                            sb.append(i);
                            sb.append(",");
                        }
                        sb.deleteCharAt(sb.length() - 1);
                        out.append(sb.toString());
                        out.newLine();
                    }
                });
    }

    public static void readFile(String fileName) throws IOException {
        Lender.readUsing(fileName,
                new ReadBlock() {
                    @Override
                    public void call(BufferedReader reader) throws IOException {
                        String s = reader.readLine();
                        System.out.println(s);
                    }
                });
    }

}
