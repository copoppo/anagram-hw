/**
 * This class is used to hold a word or a phrase representing the original word,
 * a word from the dictionary, or a candidate anagram.
 * 
 * @author
 *
 */
public class Word {
	/** the array representation of the word **/
	protected int[] count = new int[26]; // count of each letter in the word
	/**
	 * the total of the numbers of the letters in the word, thus the length of
	 * the word
	 **/
	protected int total; // number of letters in the word
	/** the word itself **/
	protected String myWord; // the word

	/**
	 * Constructor for the Word class
	 * 
	 * @param the
	 *            String representation of a word
	 */
	public Word(String s) {

		int alphabetValue;
		myWord = s;
		total = 0;
		s = s.toLowerCase();

		for (int i = s.length() - 1; i >= 0; i--) {
			alphabetValue = s.charAt(i) - 'a';
			if (alphabetValue >= 0 && alphabetValue < 26) {
				total++;
				count[alphabetValue]++;
			}
		}
	}

	/**
	 * Checks if the Word has a letter at the index passed in
	 * 
	 * @param index
	 *            of the letter array
	 * @return true if there are one or more letters at j
	 */
	protected boolean hasLetter(int j) {
		return count[j] != 0;
	}

	/**
	 * Compares this with the word being passed in at a certain index and also
	 * compares them lexicographically if neither have a letter at that index
	 * 
	 * @param word
	 *            is the Word being passed in to compare with this
	 * @param index
	 *            is the index of the letter array that we're checking
	 * 
	 * @return 1, if this has the letter at the index and word doesn't; -1, if
	 *         this doesn't have the letter at the index but word does;
	 *         word.total-this.total, if they don't have the same total of
	 *         letters; 0, if they are equal; <0, if this is less than word
	 *         passed in; >0 if this is larger than the word passed in
	 * 
	 */
	protected int multiFieldCompare(Word word, int index) {

		if ((hasLetter(index)) && !(word.hasLetter(index)))
			return 1;

		if (!(hasLetter(index)) && (word.hasLetter(index)))
			return -1;

		if (word.total != total)
			return (word.total - total);

		return (myWord).compareTo(word.myWord);
	}
}
