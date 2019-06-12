package image_handlers;

import java.util.Arrays;

public class MathWrapper {

    //TODO
    public static int leastCommonMultiple(int[] numbers){

        int lcm = 0;

        for(int n : numbers) {
            if (lcm < n) {
                lcm = n;
            }
        }

        boolean resultGot = true;

        while(true){
            for(int n : numbers){
                if((lcm % n) != 0){
                    resultGot = false;
                }
            }

            if(resultGot){
                System.out.println("LCM: "+lcm);
                return lcm;
            }

            lcm++;
            resultGot = true;
        }
    }
}
