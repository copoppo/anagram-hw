
public class Word  {
	int count[] = new int[26];  // count of each letter in the word
	int total;  // number of letters in the word
	String aword;  // the word

	public Word(String s) { // construct an entry from a string
		int ch; //meaning? 
		aword = s;
		total = 0;
		s = s.toLowerCase();//why not use "aword.toLowerCase"
		
		//initiate count[] with 0s
//		for (int i = 'a'; i <= 'z'; i++) {
//			count[i-'a'] = 0; //number:'a' = 97 'z' = 122 
//		}
		//count total number of letters in the word
		//count number of a specific letter in the word and put the result into the corresponding index of count array
		for (int i = s.length()-1; i >= 0; i--) {
			ch = s.charAt(i) - 'a';
			if (ch >= 0 && ch < 26) {
				total++;
				count[ch]++;
			}
		}
	}

	public boolean containsLetter(int j){
		return count[j] != 0;
	}
	
	//should not be in the word class? 
	//what does this method mean? 
	//only be called in anagram 
	
	
	//new add method
	public String toString(){
		return aword;
	}
}

