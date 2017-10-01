class RandomizedCollection {
    Map<Integer,HashSet<Integer>> map;
    List<Integer> arr;
    Random rand;

    /** Initialize your data structure here. */
    public RandomizedCollection() {
        this.map = new HashMap<>();
        this.arr = new ArrayList<>();
        this.rand = new Random();
    }

    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
        arr.add(val);
        if(map.containsKey(val)){
            map.get(val).add(arr.size()-1);
            return false;
        }
        else{
            HashSet<Integer> temp = new HashSet<>();
            temp.add(arr.size()-1);
            map.put(val,temp);
            return true;
        }
    }

    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        if(!map.containsKey(val)) return false;
        HashSet temp = map.get(val);
        int thisIndex = (int)temp.iterator().next();
        int thisKey = arr.get(thisIndex);
        int lastIndex = arr.size()-1;
        int lastKey = arr.get(lastIndex);

        //delete
        if(thisKey == lastKey){
            arr.remove(lastIndex);
            temp.remove(lastIndex);
            if(temp.size() == 0) map.remove(val);
            return true;
        }


        if(thisIndex != lastIndex){
            arr.set(thisIndex, lastKey);
            arr.remove(lastIndex);
            HashSet lastSet = map.get(lastKey);
            lastSet.remove(lastIndex);
            lastSet.add(thisIndex);
        }
        else{
            arr.remove(lastIndex);
        }

        temp.remove(thisIndex);
        if(temp.size() == 0) map.remove(val);

        return true;
    }

    /** Get a random element from the collection. */
    public int getRandom() {
        return arr.get(rand.nextInt(arr.size()));
    }
}

/**
 * Your RandomizedCollection object will be instantiated and called as such:
 * RandomizedCollection obj = new RandomizedCollection();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */



//逻辑太乱！
//复杂逻辑感觉得画个图




/*More Concise and Using LinkedHashSet 好像有bug？
See my previous post here.
I modified the code by replacing HashSet with LinkedHashSet because the set.iterator() might be costly when a number has too many duplicates. Using LinkedHashSet can be considered as O(1) if we only get the first element to remove.

public class RandomizedCollection {
    ArrayList<Integer> nums;
    HashMap<Integer, Set<Integer>> locs;
    java.util.Random rand = new java.util.Random();
    //Initialize your data structure here.
    public RandomizedCollection() {
        nums = new ArrayList<Integer>();
        locs = new HashMap<Integer, Set<Integer>>();
    }

    //Inserts a value to the collection. Returns true if the collection did not already contain the specified element.
    public boolean insert(int val) {
        boolean contain = locs.containsKey(val);
        if ( ! contain ) locs.put( val, new LinkedHashSet<Integer>() );
        locs.get(val).add(nums.size());
        nums.add(val);
        return ! contain ;
    }

    //Removes a value from the collection. Returns true if the collection contained the specified element.
    public boolean remove(int val) {
        boolean contain = locs.containsKey(val);
        if ( ! contain ) return false;
        int loc = locs.get(val).iterator().next();
        locs.get(val).remove(loc);
        if (loc < nums.size() - 1 ) {
           int lastone = nums.get( nums.size()-1 );
           nums.set( loc , lastone );
           locs.get(lastone).remove( nums.size()-1);
           locs.get(lastone).add(loc);
        }
        nums.remove(nums.size() - 1);

        if (locs.get(val).isEmpty()) locs.remove(val);
        return true;
    }

    //Get a random element from the collection.
    public int getRandom() {
        return nums.get( rand.nextInt(nums.size()) );
    }
}


update: no longer AC !!!
Another AC solution using ArrayList

public class RandomizedCollection {
    ArrayList<Integer> nums;
    HashMap<Integer, List<Integer>> locs;
    java.util.Random rand = new java.util.Random();
    //Initialize your data structure here.
    public RandomizedCollection() {
        nums = new ArrayList<Integer>();
        locs = new HashMap<Integer, List<Integer>>();
    }

    //Inserts a value to the collection. Returns true if the collection did not already contain the specified element.
    public boolean insert(int val) {
        boolean contain = locs.containsKey(val);
        if ( ! contain ) locs.put( val, new ArrayList<Integer>() );
        locs.get(val).add(nums.size());
        nums.add(val);
        return ! contain ;
    }

    //Removes a value from the collection. Returns true if the collection contained the specified element.
    public boolean remove(int val) {
        boolean contain = locs.containsKey(val);
        if ( ! contain ) return false;
        int loc = locs.get(val).remove( locs.get(val).size() - 1 );
        if (loc < nums.size() - 1 ) {
           int lastone = nums.get(nums.size() - 1);
           nums.set( loc , lastone );
           locs.get(lastone).remove( locs.get(lastone).size() - 1);
           locs.get(lastone).add(loc);
        }
        nums.remove( nums.size() - 1 );
        if (locs.get(val).isEmpty()) locs.remove(val);
        return true;
    }

    //Get a random element from the collection.
    public int getRandom() {
        return nums.get( rand.nextInt(nums.size()) );
    }
}
*/
