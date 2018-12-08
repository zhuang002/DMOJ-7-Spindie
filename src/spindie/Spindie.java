/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spindie;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author zhuan
 */
public class Spindie {

    static Scanner sc = new Scanner(System.in);
    static int[] spins ;
    static HashMap<Integer,Boolean> possible2SumOrProdCache=new HashMap();
    static HashMap<Integer,Boolean> possible2SumCache=new HashMap();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        for (int i = 0; i < 5; i++) {
            doTestCase();
        }
    }

    private static void doTestCase() {
        int n = sc.nextInt();
        
        spins=new int[n];
        for (int i = 0; i < n; i++) {
            spins[i] = sc.nextInt();
        }
        Arrays.sort(spins);
        for (int i = 0; i < 5; i++) {
            System.out.print(possible(sc.nextInt(), spins, 3) ? 'T' : 'F');
        }
        System.out.println();
    }

    private static boolean possible(int result, int[] spins, int leftRound) {
        if (leftRound < 0) {
            return false;
        }
        if (leftRound == 0 && result == 0) {
            return true;
        }
        if (leftRound == 1) {
            if (Arrays.binarySearch(spins, result)>=0)
                return true;
        }
        int prod = 1;
        int sum = 0;
        for (int i = 0; i < leftRound; i++) {
            sum += spins[0];
            prod *= spins[spins.length - 1];
        }
        if (result < sum || result > prod) {
            return false;
        }
        
        int i=0;
        boolean rt=false;
        while (i<spins.length && spins[i]<=result) {
            int r=result % spins[i];
            int d=result/spins[i];
            if (r==0) {
                rt=possible2SumOrProd(spins,d);
                if (rt) return true;
            } else {
                int idx=Arrays.binarySearch(spins, r);
                if (idx>=0) {
                    idx=Arrays.binarySearch(spins, d);
                    if (idx>=0) return true;
                }
            }
            rt=possible2Sum(spins,result-spins[i]);
            if (rt) return true;
            i++;
        }

        return rt;
    }

    private static boolean possible2SumOrProd(int[] spins, int result) {
        int i=0;
        if (possible2SumOrProdCache.containsKey(result))
            return possible2SumOrProdCache.get(result);
        while (i<spins.length && spins[i]<=result) {
            int idx=-1;
            int r=result%spins[i];
            if (r==0) {
                idx=Arrays.binarySearch(spins, result/spins[i]);
                if (idx>=0) {
                    possible2SumOrProdCache.put(result, Boolean.TRUE);
                    return true;
                }
            } 
            r = result - spins[i];
            idx=Arrays.binarySearch(spins, r);
            if (idx>=0) {
                possible2SumOrProdCache.put(result, Boolean.TRUE);
                return true;
            }
            i++;
        }
        possible2SumOrProdCache.put(result, Boolean.FALSE);
        return false;
    }

    private static boolean possible2Sum(int[] spins, int result) {
        int i=0;
        if (possible2SumCache.containsKey(result))
            return possible2SumCache.get(result);
        while (i<spins.length && spins[i]<=result) {
            int r = result - spins[i];
            int idx=Arrays.binarySearch(spins, r);
            if (idx>=0) {
                possible2SumCache.put(result,true);
                return true;
            }
            i++;
        }
        possible2SumCache.put(result,false);
        return false;
    }
}
