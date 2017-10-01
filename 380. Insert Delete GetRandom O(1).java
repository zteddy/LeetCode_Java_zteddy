class RandomizedSet {
    Set<Integer> s;
    Random r;

    /** Initialize your data structure here. */
    public RandomizedSet() {
        this.s = new HashSet<>();
        this.r = new Random();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        return this.s.add(val);
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        return this.s.remove(val);
    }

    /** Get a random element from the set. */
    public int getRandom() {
        return (int)this.s.toArray()[(this.r.nextInt(this.s.size()))];

    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */




/*Using ArrayList and Hashmap
I got a similar question for my phone interview. The difference is that the duplicated number is allowed. So, think that is a follow-up of this question.
How do you modify your code to allow duplicated number?

public class RandomizedSet {
    ArrayList<Integer> nums;
    HashMap<Integer, Integer> locs;
    java.util.Random rand = new java.util.Random();
    // Initialize your data structure here.
    public RandomizedSet() {
        nums = new ArrayList<Integer>();
        locs = new HashMap<Integer, Integer>();
    }

    // Inserts a value to the set. Returns true if the set did not already contain the specified element.
    public boolean insert(int val) {
        boolean contain = locs.containsKey(val);
        if ( contain ) return false;
        locs.put( val, nums.size());
        nums.add(val);
        return true;
    }

    // Removes a value from the set. Returns true if the set contained the specified element.
    public boolean remove(int val) {
        boolean contain = locs.containsKey(val);
        if ( ! contain ) return false;
        int loc = locs.get(val);
        if (loc < nums.size() - 1 ) { // not the last one than swap the last one with this val
            int lastone = nums.get(nums.size() - 1 );
            nums.set( loc , lastone );
            locs.put(lastone, loc);
        }
        locs.remove(val);
        nums.remove(nums.size() - 1);
        return true;
    }

    // Get a random element from the set.
    public int getRandom() {
        return nums.get( rand.nextInt(nums.size()) );
    }
}

Same as the remove(int index) call, it removes the element at the specified position in this list and shifts any subsequent elements to the left (subtracts one from their indices). When removing the last element at the end of this list, it is O(1).

The follow-up: allowing duplications.
For example, after insert(1), insert(1), insert(2), getRandom() should have 2/3 chance return 1 and 1/3 chance return 2.
Then, remove(1), 1 and 2 should have an equal chance of being selected by getRandom().

The idea is to add a set to the hashMap to remember all the locations of a duplicated number.

public class RandomizedSet {
    ArrayList<Integer> nums;
    HashMap<Integer, Set<Integer>> locs;
    java.util.Random rand = new java.util.Random();
    // Initialize your data structure here.
    public RandomizedSet() {
        nums = new ArrayList<Integer>();
        locs = new HashMap<Integer, Set<Integer>>();
    }

    // Inserts a value to the set. Returns true if the set did not already contain the specified element.
    public boolean insert(int val) {
        boolean contain = locs.containsKey(val);
        if ( ! contain ) locs.put( val, new HashSet<Integer>() );
        locs.get(val).add(nums.size());
        nums.add(val);
        return ! contain ;
    }

    // Removes a value from the set. Returns true if the set contained the specified element.
    public boolean remove(int val) {
        boolean contain = locs.containsKey(val);
        if ( ! contain ) return false;
        int loc = locs.get(val).iterator().next();
        locs.get(val).remove(loc);
        if (loc < nums.size() - 1 ) {
            int lastone = nums.get(nums.size() - 1 );
            nums.set( loc , lastone );
            locs.get(lastone).remove(nums.size() - 1);
            locs.get(lastone).add(loc);
        }
        nums.remove(nums.size() - 1);
        if (locs.get(val).isEmpty()) locs.remove(val);
        return true;
    }

    // Get a random element from the set.
    public int getRandom() {
        return nums.get( rand.nextInt(nums.size()) );
    }
}
*/




/*Using two HashMap
If we assume the operation of HashMap is O(1), then we have the following solution.

import java.util.Random;

public class RandomizedSet {

    private HashMap<Integer, Integer> keyMap = null;
    private HashMap<Integer, Integer> valueMap = null;
    int count;

    //Initialize your data structure here.
    public RandomizedSet() {
        keyMap = new HashMap<Integer, Integer>();
        valueMap = new HashMap<Integer, Integer>();
    }

    //Inserts a value to the set. Returns true if the set did not already contain the specified element.
    public boolean insert(int val) {
        if(keyMap.containsKey(val)) {
            return false;
        } else {
            keyMap.put(val, count);
            valueMap.put(count, val);
            count = keyMap.size();
            return true;
        }
    }

    //Removes a value from the set. Returns true if the set contained the specified element.
    public boolean remove(int val) {
        if(!keyMap.containsKey(val)) {
            return false;
        } else {
            int valueKey = keyMap.get(val);
            keyMap.remove(val);
            if(valueKey != valueMap.size() - 1) {
                valueMap.put(valueKey, valueMap.get(valueMap.size() - 1));
                keyMap.put(valueMap.get(valueMap.size() - 1), valueKey);
                valueMap.remove(valueMap.size() - 1);
            } else {
                valueMap.remove(valueKey);
            }
            count = keyMap.size();
            return true;
        }
    }

    //Get a random element from the set.
    public int getRandom() {
        Random random = new Random();
        int n = random.nextInt(keyMap.size());
        return valueMap.get(n);
    }
}
*/
