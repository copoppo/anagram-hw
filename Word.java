
public class Word {
	/** the array representation of the word **/
	int[] letter = new int[26]; // count of each letter in the word
	/**
	 * the total of the numbers of the letters in the word, thus the length of
	 * the word
	 **/
	int total; // number of letters in the word
	/** the word itself **/
	String myWord; // the word

	/**
	 * 
	 * @param s
	 */
	public Word(String s) { // construct an entry from a string
		// each letter's difference from 'a'
		int ch;
		// get the string from the parameter
		myWord = s;
		// the total is 0 now
		total = 0;
		// change all letters to lower case
		s = s.toLowerCase();
		// // get rid of the first for loop
		// for (int i = 'a'; i <= 'z'; i++)
		// count[i - 'a'] = 0;
		//
		for (int i = s.length() - 1; i >= 0; i--) {
			// calculates the difference between this letter and 'a'
			ch = s.charAt(i) - 'a';
			// so if this is an alphabet
			if (ch >= 0 && ch < 26) {
				// it will add to total
				total++;
				// add to the array that you have one more of this letter
				letter[ch]++;
			}
		}
	}

	/**
	 * 
	 * @param j
	 * @return
	 */
	public boolean hasLetter(int j) {
		//
		return letter[j] != 0;
	}

	/**
	 * Should not be in the Word class, should be put in the Anagram class
	 * 
	 * only being called in anagram
	 * 
	 * @param word
	 * @param index
	 * @return
	 */
	public int multiFieldCompare(Word word, int index) {
		//
		if ((hasLetter(index)) && !(word.hasLetter(index)))
			return 1;

		// if the next candidate doesn't have this letter
		// but this candidate does
		if (!(hasLetter(index)) && (word.hasLetter(index)))
			return -1;

		// if they both don't have this letter or both have this letter
		// if the next candidate has 1 less letter than this candidate
		if (word.total != total)
			return (word.total - total);

		// if the next candidate is not that same with this candidate
		return (myWord).compareTo(word.myWord);
	}
}
