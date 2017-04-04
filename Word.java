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

		assert wellFormed();
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
	 *         Compare
	 * 
	 */
	protected int compare(Word word, int index) {

		if ((hasLetter(index)) && !(word.hasLetter(index))) {
			return 1;
		}

		if (!(hasLetter(index)) && (word.hasLetter(index))) {
			return -1;
		}

		if (word.total != total) {
			return (word.total - total);
		}

		return (myWord).compareTo(word.myWord);
	}

	/**
	 * Compares two words to see if the first one has fewer letters than the
	 * second one
	 * 
	 * @param wordOne
	 *            is the first word that is passed in
	 * @param wordTwo
	 *            is the second word that is passed in
	 * 
	 * @return true if wordOne has more or the same amount of letters than
	 *         wordTwo
	 */
	boolean fewerOfEachLetter(Word wordTwo) {
		for (int i = 25; i >= 0; i--) {
			if (this.count[i] < wordTwo.count[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * A wellFormed method to check that the Word is formed correctly and has
	 * the right values
	 * 
	 * @return false if not wellFormed and true if it is
	 */
	boolean wellFormed() {
		// if the string is null then it's not wellFormed
		if (myWord == null) {
			return false;
		}

		// the second test is to make sure that the length of the string is the
		// total for the counts in the count[]
		int totalLetters = 0;
		for (int i = 0; i < count.length; i++) {
			totalLetters += count[i];
		}

		if (totalLetters != myWord.length()) {
			return false;
		}

		// the final test is to checks that the letters appear correctly in the
		// count array
		int alphabetValue = 0;
		for (int i = myWord.length() - 1; i >= 0; i--) {
			alphabetValue = myWord.charAt(i) - 'a';
			if (alphabetValue >= 0) {
				if (count[alphabetValue] == 0) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Finds the missing letters that are needed to make up a full anagram based
	 * on a list of candidates
	 * 
	 * @param i
	 *            is where we are in the candidate array
	 * @param wordToPass
	 *            is the word holding the missing letters
	 * @param candidates
	 *            is the array of Words that we're using to find if there is a
	 *            word that completes an anagram with this
	 */
	protected void findMissingLetters(int i, Word wordToPass, Word[] candidates) {
		for (int j = 25; j >= 0; j--) {
			wordToPass.count[j] = (byte) (count[j] - candidates[i].count[j]);
			if (wordToPass.count[j] != 0) {
				wordToPass.total += wordToPass.count[j];
			}
		}
	}

	/**
	 * Check whether this could be a potential candidate for base
	 * 
	 * @param base
	 *            is the base word that this could be a candidate for
	 * @param minimumLength
	 *            is the smallest length this could be
	 * @return true if potential candidate is a candidate
	 */
	protected boolean isCandidate(Word base, int minimumLength) {
		if (this.total < minimumLength) {
			return false;
		}
		if ((this.total + minimumLength > base.total) && (this.total != base.total)) {
			return false;
		}
		if (!base.fewerOfEachLetter(this)) {
			return false;
		}
		return true;
	}
}
