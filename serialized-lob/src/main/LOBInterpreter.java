package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Interprets the LOB.
public class LOBInterpreter {

    private static ArrayList<String> lobTokenizer = new ArrayList<>();
    private static Map<String,String> columns = new HashMap<>();


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

    public static Map<String,String> getcolumns(ArrayList<String> string){
        int columnCounts = Integer.getInteger( string.get(0));
        for(int i = 0 ;i<columnCounts ; i++){
            columns.put(string.get(i*2+1),string.get(i*2+2));
        }
        return null;
    }


}
