public class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
		
		//StringBuffer ransomNotebB = new StringBuffer(ransomNote);
		StringBuffer magazineB = new StringBuffer(magazine);
		int slength = ransomNote.length();
		String temp;
		int index;
		
		for(int i = 0; i< slength; i++){
			temp = String.valueOf(ransomNote.charAt(i));
			index = magazineB.indexOf(temp);
			if(index == -1) return false;
			else magazineB.deleteCharAt(index);
		}
		
		return true;
        
		
    }
}

//TODO None