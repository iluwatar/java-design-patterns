package com.iluwatar.abstractfactory;

/**
 * Created by sandromuggli on 12.08.17.
 */
public final class KingdomFactoryProducer {
    public static KingdomFactory getFactory(String type){

        if(type.equalsIgnoreCase("elf")){
            return new ElfKingdomFactory();

        }else if(type.equalsIgnoreCase("ork")){
            return new OrcKingdomFactory();
        }

        return null;
    }
}