package player.security;

import java.math.BigInteger;

public class Primes {
    protected static int secret;
    public static int generatePrime(){
        int random;
        while (true) {
            random = (int) (Math.random() * (127 - 2) + 2);
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
