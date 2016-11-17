public class Solution {
    public int countPrimes(int n) {

    	int count = 0;
    	Map<Integer, Integer> primes = new HashMap<>();
    	Iterator iter = primes.entrySet().iterator();
    	boolean flag;

    	for(Integer i = 2; i < n; i++){

    		flag = true;

    		iter = primes.entrySet().iterator();
    		while(iter.hasNext()){
    			Map.Entry entry = (Map.Entry) iter.next();
    			Object key = entry.getKey();
    			int k = Integer.valueOf(String.valueOf(key)).intValue();
    			if(k <= Math.sqrt(i))
    				if(i % k == 0) flag = false;
    		}

    		if(flag) primes.put(i, i);
    	}

    	return primes.size();


    }
}

//TLE cheared by the tag! Use hashmap will be slow!

//Using math
It is called the Sieve of Eratosthenes algorithm. In the boolean array m, m[n] means the number n. Thus for each time, if m[n] is a prime, we need to delete all the multiple of m[n]. And finally, the remaining numbers are primes.

public class Solution {
    public int countPrimes(int n) {
        boolean[] notPrime = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (notPrime[i] == false) {
                count++;
                for (int j = 2; i*j < n; j++) {
                    notPrime[i*j] = true;
                }
            }
        }

        return count;
    }
}
