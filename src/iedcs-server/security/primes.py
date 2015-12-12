import random, math

class Primes(object):
    global g
    global p
    global secret

    @staticmethod
    def generatePrimes():
        while True:
            p = random.randrange(11, 10000, 2)
            if all(p % n != 0 for n in range(3, int((p ** 0.5) + 1), 2)):
                return p

    @staticmethod
    def generateN(g, p):
        Primes.secret = Primes.generatePrimes()
        tmp = g ** Primes.secret
        return tmp % p

    @staticmethod
    def changePrimes():
        Primes.g = Primes.generatePrimes()
        Primes.p = Primes.generatePrimes()

    @staticmethod
    def generateFinal(p, n):
        tmp =  int(n) ** Primes.secret
        return tmp % p


