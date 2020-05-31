package com.iluwater.federatedIdentity;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.net.ConnectException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * act as consumer.
 * <p>
 * @author jasciiz
 */
public class Consumer {
    String name;
    int age;
    Date data;
    String others;
    static String IdP = "http://localhost:7001/IdP";
    static String Server = "http://localhost:7003/Server";
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    boolean isRegister;
    boolean isLand;
    String claim;

    /**
     * construct with parameters.
     * <p>
     * @param name the name of consumer
     * @param age the age of consumer
     * @param data the date of birth of consumer
     * @param others the other information of consumer
     * @throws IOException throw
     *  when input name with "//".
     */
    public Consumer(String name, int age, String data, String others) throws IOException {
        if(name.contains("//")){
            throw new IOException("wrong name! Do not contain \"//\"!");
        }
        this.name = name;
        try{
            this.data = sdf.parse(data);
        }catch (ParseException e){
            e.printStackTrace();
        }
        this.others = others;
        this.age = age;
        this.isRegister = false;
        this.claim = null;
        this.isLand = false;
    }

    /**
     * construct without parameter.
     * <p>
     */
    public Consumer(){
        this.name = "iluwater";
        this.age = 2020-1990;
        this.data = new Date(1990-1900, Calendar.DECEMBER,8);
        this.others = "hello world";
        this.isRegister = false;
        this.claim = null;
        this.isLand = false;
    }

    /**
     * get IdP link.
     * <p>
     * @return
     */
    public String getIdP() {
        return IdP;
    }

    /**
     * set new IdP link.
     * <p>
     * @param IdP the new link
     */
    public void setIdP(String IdP) {
        Consumer.IdP = IdP;
    }

    /**
     * resister self in IdP.
     * <P>
     * @exception IOException throw
     * when no get response from IdP.
     */
    public void register() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name+"//");
        stringBuilder.append(this.age+"//");
        stringBuilder.append(sdf.format(this.data)+"//");
        stringBuilder.append(this.others);
        byte[] postBytes = stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
        try {
            String responseString = Request.Post(IdP+"/register").bodyByteArray(postBytes).execute().returnContent().asString();
            if(responseString.equals("success")){
                isRegister = true;
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * get claim from IdP.
     * <P>
     * @return if get claim.
     * @exception IOException throw
     * when no get response from IdP.
     */
    public boolean getClaim(){
        try {
            byte[] postBytes = this.name.getBytes(StandardCharsets.UTF_8);
            this.claim = Request.Post(IdP+"/getClaim").bodyByteArray(postBytes).execute().returnContent().asString();
            if(this.claim.equals("fail")){
                return false;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }

    /**
     * log in server.
     * <p>
     * @return if log in server
     * @exception IOException throw
     * when no get response from server.
     */
    public boolean logIn(){
        if(!this.isRegister || this.claim == null) return false;
        try {
            byte[] postBytes =this.claim.getBytes(StandardCharsets.UTF_8);
            String response = Request.Post(Server+"/logIn").bodyByteArray(postBytes).execute().returnContent().asString();
            if(response.equals("success")){
                this.isLand = true;
                return true;
            }else {
                return false;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * using server function.
     * <p>
     * @exception IOException throw
     * when no get response from server.
     */
    public String run(){
        if(!isLand){
            return "no log in";
        }
        try {
            String response = Request.Get(Server+"/function").execute().returnContent().asString();
            System.out.println(response);
            return response;
        }catch (IOException e){
            e.printStackTrace();
        }
        return "error";
    }

}
