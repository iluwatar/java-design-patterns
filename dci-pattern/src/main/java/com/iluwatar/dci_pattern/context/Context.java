package com.iluwatar.dci_pattern.context;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.iluwatar.dci_pattern.data.Account;
import com.iluwatar.dci_pattern.data.Bank;

import java.util.Hashtable;

public class Context {
    private Account Source;
    private Account Destination;
    private Integer Amount;
    Hashtable<String, Object> transactionMap;

    // conduct transfer operation
    public void transfer(Integer money, Integer from_account, Integer des_account, Bank bank ){
        // map each role
        Amount = money;
        Source = bank.getAccount(from_account);
        Destination = bank.getAccount(des_account);
        transactionMap = new Hashtable<String, Object>();
        transactionMap.put("source account", Source);
        transactionMap.put("destination account",Destination);
        transactionMap.put("money", Amount);

        // conduct operation
        request("Source","sendTo");
    }

    // The execution engine
    protected Object request(String roleName, String method){
        Object[] objects = {};
        return (request(roleName,method,objects));
    }

    protected Object request(String roleName, String method, Object[] obj){
        String roleNameClass = "Context-"+roleName +"class";
        Class roleClassName = null;
        Class dClass = null;
        Method roleMethodName = null;
        Method dataMethodName = null;
        Object instance = null;
        Object dObject = null;

        // attempt to execute role methods.
        try{
            roleClassName = Class.forName(roleNameClass);
            Method[] Methods = roleClassName.getDeclaredMethods();
            for (Method m: Methods){
                if (m.getName() .equals(method)){
                    roleMethodName =m;
                }
            }
            if (roleMethodName != null){
                roleMethodName.setAccessible(true);
                Constructor constructor = roleClassName.getDeclaredConstructor(Context.class);
                constructor.setAccessible(true);
                instance = constructor.newInstance(this);
                Object outcome = roleMethodName.invoke(instance,new Object[]{});
                return outcome;
            }
            // try to execute instance method when there is no role method
            dObject = transactionMap.get(roleName);
            dClass = dObject.getClass();
            Method[] dMethods = dClass.getDeclaredMethods();
            for (Method m1 : dMethods){
                if (m1.getName() .equals(method)){
                    dataMethodName = m1;
                }
            }
            if (dataMethodName == null){
                System.out.println("request " + method + "does not found");
                return (null);
            }
            Object outcome = dataMethodName.invoke(dObject,obj);
            return outcome;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | IllegalArgumentException |InvocationTargetException e) {
            e.printStackTrace();
        }
        return (null);
    }


}
