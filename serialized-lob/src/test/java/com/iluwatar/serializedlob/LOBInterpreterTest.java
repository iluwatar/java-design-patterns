package com.iluwatar.serializedlob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LOBInterpreterTest {
    private String lob = "3 first String second Int third String hello 0 bye";
    private ArrayList<String> list = new ArrayList<>();
    private HashMap<String,String> columns = new HashMap<>();
    private HashMap<String,String > object = new HashMap<>();

    @BeforeEach
    void initList(){
        list = new ArrayList<>();
        columns = new HashMap<>();
    }

    @Test
    void testLOBInterpretion(){
        list.add("3");
        list.add("first");
        list.add("String");
        list.add("second");
        list.add("Int");
        list.add("third");
        list.add("String");
        list.add("hello");
        list.add("0");
        list.add("bye");

        assertEquals(LOBInterpreter.readLOB(lob), list);
    }

    @Test
    void testGetColumns(){
        columns.put("first","String");
        columns.put("second","Int");
        columns.put("third","String");
        assertEquals(LOBInterpreter.getColumns(LOBInterpreter.readLOB(lob)), columns);
    }

    @Test
    void testGetObjects(){
        assertEquals(LOBInterpreter.getObjects(LOBInterpreter.readLOB(lob)), "hello 0 bye");
    }

    @Test
    void testGetObject(){
        object.put("first", "hello");
        object.put("second","0");
        object.put("third", "bye");
        assertEquals(LOBInterpreter.getObject(LOBInterpreter.readLOB(lob),0),object);
    }

}