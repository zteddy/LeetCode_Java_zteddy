class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        int i = 0;
        List<String> result = new ArrayList<>();
        if(maxWidth == 0){
            result.add("");
            return result;
        }
        while(i < words.length){
            int currLen = 0;
            List<String> currStr = new ArrayList<>();
            String tempResult = "";
            while(i < words.length && currLen+words[i].length() <= maxWidth){
                currLen += words[i].length()+1;
                currStr.add(words[i]);
                i++;
            }
            if(i == words.length || currStr.size() == 1){
                for(String s:currStr){
                    tempResult += s +" ";
                }
                if(tempResult.length() > maxWidth)
                    result.add(tempResult.trim());
                else{
                    int t = tempResult.length();
                    for(int k = 0; k < maxWidth-t; k++){
                        tempResult += " ";
                    }
                    result.add(tempResult);
                }
            }
            else{
                int total = maxWidth-(currLen-currStr.size());
                int gap = total/(currStr.size()-1);
                int add = total%(currStr.size()-1);
                for(int j = 0; j < currStr.size(); j++){
                    tempResult += currStr.get(j);
                    if(j <= add-1){
                        tempResult += " ";
                        // for(int k = 0; k < add; k++){
                        //     tempResult += " ";
                        // }
                    }
                    for(int k = 0; k < gap; k++){
                        tempResult += " ";
                    }
                }
                result.add(tempResult.trim());
            }
        }
        return result;

    }
}



//我觉得OK
