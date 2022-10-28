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

import java.sql.Connection;
import  java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static com.iluwatar.serializedlob.LOBInterpreter.getColumns;
import static com.iluwatar.serializedlob.LOBInterpreter.readLOB;

/**
 * This class contains operations related to PostgreSQL databse.
 */
public class PostGresDatabase {
    private static Map<String , String> columns = new HashMap<>();

    public static Connection connection(){
        Connection c = null;
        try{
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/serialized-lob",
                            "postgres", "123");
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return c;
    }

    public static void toSerializedLOB(Connection c, String tableName, String newTableName){
        connection();
        Statement create = null;
        Statement insert = null;
        StringBuilder sql = new StringBuilder();
        try{
            String lob = PostGresSerializer.graphToLOB(c,tableName);
            columns = getColumns(readLOB(lob));
            sql.append("Create TABLE ").append(newTableName).append(" ");
            create = c.createStatement();

            sql.append("(").append(newTableName).append(")");

            create.executeUpdate(sql.toString());
            create.close();

            insert = c.createStatement();
            sql = new StringBuilder();
            sql.append("INSERT INTO ").append(newTableName).append(" (").
                    append(newTableName).append(") ").
                    append("VALUES (").append(lob).append(")");
            insert.executeUpdate(sql.toString());
            c.close();

        } catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }
}