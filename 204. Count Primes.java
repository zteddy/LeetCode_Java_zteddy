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