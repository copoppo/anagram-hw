
public class Word {
	/** the array representation of the word **/
	int count[] = new int[26]; // count of each letter in the word
	/**
	 * the total of the numbers of the letters in the word, thus the length of
	 * the word
	 **/
	int total; // number of letters in the word
	/** the word itself **/
	String aword; // the word

	/**
	 * 
	 * @param s
	 */
	public Word(String s) { // construct an entry from a string
		// each letter's difference from 'a'
		int ch;
		// get the string from the parameter
		aword = s;
		// the total is 0 now
		total = 0;
		// change all letters to lower case
		s = s.toLowerCase();
		// get rid of the first for loop
		for (int i = 'a'; i <= 'z'; i++)
			count[i - 'a'] = 0;
		//
		for (int i = s.length() - 1; i >= 0; i--) {
			// calculates the difference between this letter and 'a'
			ch = s.charAt(i) - 'a';
			// so if this is an alphabet
			if (ch >= 0 && ch < 26) {
				// it will add to total
				total++;
				// add to the array that you have one more of this letter
				count[ch]++;
			}
		}
	}

	/**
	 * 
	 * @param j
	 * @return
	 */
	public boolean containsLetter(int j) {
		//
		return count[j] != 0;
	}

	/**
	 * Should not be in the Word class, should be put in the Wordlist class
	 * 
	 * only being called in anagram
	 * 
	 * @param t
	 * @param LeastCommonIndex
	 * @return
	 */
	public int MultiFieldCompare(Word t, int LeastCommonIndex) {
		//
		if ((containsLetter(LeastCommonIndex)) && !(t.containsLetter(LeastCommonIndex)))
			return 1;

		if (!(containsLetter(LeastCommonIndex)) && (t.containsLetter(LeastCommonIndex)))
			return -1;

		if (t.total != total)
			return (t.total - total);

		return (aword).compareTo(t.aword);
	}
}
