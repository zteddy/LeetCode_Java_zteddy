public class Solution {
    public boolean wordPattern(String pattern, String str) {

    	HashMap<String, String> hm= new HashMap<String, String>();
 
    	String[] str_a = str.split(" ");

    	if(pattern.length() != str_a.length) return false;

    	for(int i = 0; i < pattern.length(); i++){
    		if(hm.containsKey(pattern.substring(i,i+1))){
    			if(!hm.get(pattern.substring(i,i+1)).equals(str_a[i])) return false;
    		}
    		else if(hm.containsValue(str_a[i])) return false;
    		else
    			hm.put(pattern.substring(i,i+1), str_a[i]);
    	}

    	return true;
        
    }
}

/*More concise solution
public boolean wordPattern(String pattern, String str) {
    String[] words = str.split(" ");
    if (words.length != pattern.length())
        return false;
    Map index = new HashMap();
    for (Integer i=0; i<words.length; ++i)
        if (index.put(pattern.charAt(i), i) != index.put(words[i], i))
            return false;
    return true;
}

I go through the pattern letters and words in parallel and compare the indexes where they last appeared.

Edit 1: Originally I compared the first indexes where they appeared, using putIfAbsent instead of put. 
That was based on mathsam's solution for the old Isomorphic Strings problem. But then czonzhu's answer below made me realize that put works as well and why.

Edit 2: Switched from

    for (int i=0; i<words.length; ++i)
        if (!Objects.equals(index.put(pattern.charAt(i), i),
                            index.put(words[i], i)))
            return false;
to the current version with i being an Integer object, which allows to compare with just != because there's no autoboxing-same-value-to-different-objects-problem anymore. 
Thanks to lap_218 for somewhat pointing that out in the comments.
*/