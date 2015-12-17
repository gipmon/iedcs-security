package player.security;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Primes {
    protected static int secret;
    //partially used: http://stackoverflow.com/questions/28846174/generating-two-random-prime-numbers-in-java
    public static int generatePrime(){
        int random;
        SecureRandom r =new SecureRandom();
        
        while (true) {
            random = (int) (r.nextInt() * (127 - 2) + 2);
            if(isPrime(random)){
                break;
            }
        }
        return random;
    }
    
    public static int generateN(int g, int p){
        secret = generatePrime();
        BigInteger tmp;
        tmp = BigInteger.valueOf((long) g);
        long n = tmp.modPow(BigInteger.valueOf((long) secret), BigInteger.valueOf((long) p)).longValue();
        return (int) n;
    }
    
    public static int generateFinal(int p, int n){
        BigInteger tmp;
        tmp = BigInteger.valueOf((long) n);
        long fin = tmp.modPow(BigInteger.valueOf((long) secret), BigInteger.valueOf((long) p)).longValue();
 
        return  (int) fin;
    }
    
    private static boolean isPrime(int n) {
       int i;
       for(i=2;i<=Math.sqrt(n);i++){
           if(n % i == 0){
               return false;
           }
       }
       return true;
    }
}
