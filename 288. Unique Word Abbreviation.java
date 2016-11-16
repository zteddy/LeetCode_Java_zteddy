public class ValidWordAbbr {

	Map<String, String> hm;

    public ValidWordAbbr(String[] dictionary) {

    	hm = new HashMap<>();
    	String temp;

    	for(int i = 0; i < dictionary.length; i++){     //Use for(String str: dictionary)
    		if(dictionary[i].length() <=2)
    			continue;
    		else{
    			temp = dictionary[i].substring(0,1) +
    			       (dictionary[i].length()-2 + "") +
    			       dictionary[i].substring(dictionary[i].length()-1,dictionary[i].length());
    			if(!hm.containsKey(temp))
    				hm.put(temp, dictionary[i]);
    			else{
    				hm.remove(temp);    //It is not needed, hm.put can cover the previous
    				hm.put(temp,"");
    			}
    		}
    	}

    }

    public boolean isUnique(String word) {
    	String temp;

    	if(word.length() <= 2)
    		return true;
    	else if(hm.containsValue(word)) return true;
    	else{
    		temp = word.substring(0,1) + (word.length()-2+"")
    			   + word.substring(word.length()-1, word.length());
    		return !hm.containsKey(temp);
    	}
	}


}


// Your ValidWordAbbr object will be instantiated and called as such:
// ValidWordAbbr vwa = new ValidWordAbbr(dictionary);
// vwa.isUnique("Word");
// vwa.isUnique("anotherWord");


//TODO Don't know why so slow
//Answer:应该已经是很好的办法了，慢是因为用了substring而不是charAt
