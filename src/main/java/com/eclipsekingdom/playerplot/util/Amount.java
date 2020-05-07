package com.eclipsekingdom.playerplot.util;

public class Amount {

    public static int parse(String string){

        int amt;
        try{
            amt = Integer.parseInt(string);
            if(amt < 0){
                amt = 0;
            }
            return amt;
        }catch (Exception e){
            return 1;
        }

    }


}
