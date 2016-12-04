package net.arcanemc.ninjas.Util;

import java.util.Optional;

/**
 * Created by eric on 12/2/16.
 */
public class Null {
    public static void argsNull(Object...obj){
        int x=0;
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
        for(Object o:obj){
            Optional<Object> oobj=Optional.ofNullable(o);
            if(!oobj.isPresent()){
                System.out.println("Param "+x+": null");
            }else{
                if(oobj.get() instanceof String&&oobj.get().equals("")){
                    System.out.println("Param "+x+": empty");
                }else if(oobj.get() instanceof Integer&&(int)oobj.get()==0){
                    System.out.println("Param "+x+" 0");
                }
            }
            x++;
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
    }
}