public class Solution {
    public List<List<String>> groupStrings(String[] strings) {

		List<List<String>> result = new ArrayList<List<String>>();
		int flag = 0;

		for(int i = 0; i < strings.length; i++){
			flag = 0;
			 for(int j = 0; j < result.size(); j++){
				 String temp = result.get(j).get(0);
				 if(temp.length() == strings[i].length() && flag == 0){

					 int tlength = strings[i].length();
					 int count = 0;
					 for(int k = 0; k < tlength-1; k++){
						 int a = temp.charAt(k+1) - temp.charAt(k);
						 int b = strings[i].charAt(k+1) - strings[i].charAt(k);
						 a = (a < 0)? a+26 : a;
						 b = (b < 0)? b+26 : b;
						 if(a == b) count++;

					 }

					 if(count == tlength-1){
						 result.get(j).add(strings[i]);
						 flag = 1;
					 }
				 }
			 }
			 if(flag == 0){
				 List<String> nl = new ArrayList<String>();
				 nl.add(strings[i]);
				 result.add(nl);
			 }
		}

		return result;

    }
}

/*Using hash table
public class Solution {
    public List<List<String>> groupStrings(String[] strings) {
        List<List<String>> result = new ArrayList<List<String>>();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strings) {
            int offset = str.charAt(0) - 'a';
            String key = "";
            for (int i = 0; i < str.length(); i++) {
                char c = (char) (str.charAt(i) - offset);
                if (c < 'a') {
                    c += 26;
                }
                key += c;
            }
            if (!map.containsKey(key)) {
                List<String> list = new ArrayList<String>();
                map.put(key, list);
            }
            map.get(key).add(str);
        }
        for (String key : map.keySet()) {
            List<String> list = map.get(key);
            Collections.sort(list);
            result.add(list);
        }
        return result;
    }
}
*/
