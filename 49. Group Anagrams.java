public class Solution {
	public List<List<String>> groupAnagrams(String[] strs) {
		Map<String, List> hm = new HashMap<>();
		for(int i = 0; i < strs.length; i++){
			String temp = strs[i];
			char[] chartemp = temp.toCharArray();
			Arrays.sort(chartemp);
			String sorted = new String(chartemp);  //java sort 一个string的方法
			if(hm.containsKey(sorted)){
				List<String> v = hm.get(sorted);
				v.add(temp);
				hm.put(sorted, v);  //不用这么麻烦，get到是一个reference，直接add
			}
			else{
				List<String> v = new ArrayList<>();
				v.add(temp);
				hm.put(sorted, v);
			}
		}
		List<List<String>> list = new ArrayList<>();
		for(String key : hm.keySet()){
			list.add((List)hm.get(key));
		}
		return list;
	}
}


/*More concise
public class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<List<String>>();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String s : strs) {
            char[] ca = s.toCharArray();
            Arrays.sort(ca);
            String keyStr = String.valueOf(ca);
            if (!map.containsKey(keyStr)) map.put(keyStr, new ArrayList<String>());
            map.get(keyStr).add(s);
        }
        return new ArrayList<List<String>>(map.values());
    }
}
*/

/*Using primes
private static final int[] PRIMES = new int[]{2, 3, 5, 7, 11 ,13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 107};

public List<String> anagrams(String[] strs) {
    List<String> list = new LinkedList<>();
    Map<Integer, List<String>> mapString = new HashMap<>();
    int result = -1;
    for (int i = 0; i < strs.length; i++){
        int mapping = 1;
        for (int j = 0, max = strs[i].length(); j < max; j++) {
            mapping *= PRIMES[strs[i].charAt(j) - 'a'];
        }
        List<String> strings = mapString.get(mapping);
        if (strings == null) {
            strings = new LinkedList<>();
            mapString.put(mapping, strings);
        }
        strings.add(strs[i]);
    }
    for (List<String> mapList : mapString.values()){
        if (mapList.size() > 1)
            list.addAll(mapList);
    }
    return list;
}
*/
