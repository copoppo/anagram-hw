/*
 * Usage: java Anagram string [[min-len] wordfile] Java Anagram program, Peter
 * van der Linden Jan 7, 1996. Feel free to pass this program around, as long
 * as this header stays intact.
 */

/**
 * This is the main class. It gets the phrase to make an anagram of from the
 * command line. It then reads a dictionary from a file and finds a list of
 * anagrams based on that dictionary.
 * 
 * @author
 *
 */
public class Anagram {
	/** The maximum numbers of words to be stored in a Word array **/
	private static final int MAX_NUM = 100000;

	/** The Word array that holds the candidates of the anagrams **/
	private static Word[] candidates = new Word[MAX_NUM];

	/** The total of the candidates of the anagrams **/
	private static int totalCandidates = 0;

	/** The minimum length of a candidate word **/
	private static int minimumLength = 3;

	/** The base word that this program finds anagrams for **/
	private static Word base;

	/** The WordList that holds all the Words in the dictionary **/
	private static WordList list = new WordList();

	/**
	 * Test the program. Since this is a test method to make sure that the
	 * output of our modified code matches with the original output, the
	 * argument is not needed since we predetermined the output we wanted.
	 * However, we wanted to make sure that the possibility that our program
	 * would still function the same way if we did pass in an argument so we
	 * kept the initial code and just put in the predetermined argument
	 * 
	 */
	private static void testOutput() {
		String testString = "holyoke";

		String[] candidateHolyoke = { "elk", "hey", "hoe", "hoke", "hole", "holk", "holy", "hook", "hoy", "key", "koel",
				"kohl", "kolo", "lek", "ley", "loo", "look", "lye", "oho", "oke", "okeh", "ole", "oleo", "ooh", "yeh",
				"yelk", "yok", "yoke", "yolk" };

		System.out.println("Candidate words for " + testString);

		for (int i = 0; i < candidateHolyoke.length; i++) {
			System.out.print(candidateHolyoke[i] + ", " + ((i % 4 == 3) ? "\n" : " "));
		}

		System.out.println("\n");

		String[] anagramHolyoke = { "hook ley", "hook lye", "koel hoy", "kolo yeh", "kolo hey", "hole yok", "look yeh",
				"look hey", "oke holy", "hoe yolk", "ooh yelk", "oho yelk" };

		System.out.println("and anagrams for " + testString);

		for (int i = 0; i < anagramHolyoke.length; i++) {
			System.out.println(anagramHolyoke[i]);
		}

		System.out.println("\n");

		String[] arg = { "holyoke", "3", "words.txt" };

		if (arg.length < 1 || arg.length > 3) {
			System.err.println(
					"The length of the argument has to be at least 1 letter and can't contain more than 3 words");
			return;
		}

		if (arg.length >= 2) {
			try {
				minimumLength = Integer.parseInt(arg[1]);
			} catch (NumberFormatException e) {
				System.err.println("Please pass in a number as the second argument");
			}
		}

		String doc;

		if (arg.length == 3) {
			doc = arg[2];
		} else {
			doc = "words.txt";
		}

		list.readDict(doc);
		findResult(arg[0]);
	}

	/**
	 * The main method that is used to test the program finds the correct
	 * candidates and anagrams for "holyoke"
	 * 
	 * @param arg
	 */
	public static void main(String[] arg) {
		testOutput();
	}

	/**
	 * Get the candidates and the anagrams of the word passed in
	 * 
	 * @param the
	 *            base word
	 */
	private static void findResult(String str) {
		base = new Word(str);
		assert wellFormed();

		findCandidates();
		printCandidates();

		System.out.println("Anagrams of " + str + ":");

		findAnagrams(base, new String[50], 0, 0, getFirstCandidateIndexWithLeastCommonLetter());

		System.out.println("----" + str + "----");
	}

	/**
	 * Get the candidates of the word passed in
	 * 
	 */
	private static void findCandidates() {

		for (int i = 0; i < list.totalWords; i++) {
			// numLetter should be larger or equal to the minimumLength (3)
			if (list.dictionary[i].isCandidate(base, minimumLength)) {
				candidates[totalCandidates++] = list.dictionary[i];
			}
		}
	}

	/**
	 * Gets the index of the first word that contains the least common letter
	 * 
	 * @return the index in the candidates array of the first word that contains
	 *         the least common letter
	 */
	private static int getFirstCandidateIndexWithLeastCommonLetter() {

		int firstCandidateIndexWithLeastCommonLetter;
		int leastCommonIndex = getLeastCommonIndex();

		quickSort(0, totalCandidates - 1, leastCommonIndex);

		// return the first candidate that has the least counted letter
		// from the back of the alphabet
		// for example, the first candidate that has "y" if the base word is
		// "holyoke"
		for (firstCandidateIndexWithLeastCommonLetter = 0; firstCandidateIndexWithLeastCommonLetter < totalCandidates; firstCandidateIndexWithLeastCommonLetter++) {
			if (candidates[firstCandidateIndexWithLeastCommonLetter].hasLetter(leastCommonIndex)) {
				break;
			}
		}

		assert wellFormedFirstCandidateIndex(leastCommonIndex, firstCandidateIndexWithLeastCommonLetter);
		return firstCandidateIndexWithLeastCommonLetter;
	}

	/**
	 * Gets the index of the letter in the base word that has the least counts
	 * 
	 * @return the index of the letter in the base word that has the least
	 *         counts
	 */
	private static int getLeastCommonIndex() {
		// the array that holds the total of each letter counts from all
		// candidate words
		int[] totalCounts = new int[26];

		for (int i = totalCandidates - 1; i >= 0; i--) {
			for (int j = 25; j >= 0; j--) {
				totalCounts[j] += candidates[i].count[j];
			}
		}

		int leastCommonIndex = 0;
		int leastCommonCount = MAX_NUM;

		for (int j = 25; j >= 0; j--) {
			// if we found this letter
			if (totalCounts[j] != 0
					// the counts of this letter is less than the counts of the
					// current least letter
					// and if the base word has this letter
					&& totalCounts[j] < leastCommonCount && base.hasLetter(j)) {
				// update leastLetter
				leastCommonCount = totalCounts[j];
				leastCommonIndex = j;
			}
		}

		assert wellFormedLeastCommonIndex(leastCommonIndex, totalCounts);

		return leastCommonIndex;
	}

	/**
	 * Sort the candidates so that the least common letter appears in the latter
	 * part of the candidate array
	 * 
	 * @param left
	 *            is the first index of the candidate array
	 * @param right
	 *            is the last index of the cadidate array
	 * @param leastCommonIndex
	 *            is the index in the count array of a word
	 */
	private static void quickSort(int left, int right, int leastCommonIndex) {
		if (left >= right) {
			return;
		}
		swap(left, (left + right) / 2);
		int last = left;

		for (int i = left + 1; i <= right; i++) {
			if (candidates[i].compare(candidates[left], leastCommonIndex) == -1) {
				swap(++last, i);
			}
		}

		swap(last, left);
		quickSort(left, last - 1, leastCommonIndex);
		quickSort(last + 1, right, leastCommonIndex);
	}

	/**
	 * Finds the anagrams
	 * 
	 * @param comparedWord
	 *            is the current word to be compared with the other candidates
	 * @param anagrams
	 *            is the string that holds possible candidates that makes up an
	 *            anagram
	 * @param levelIndex
	 *            is the last place that you would put in a candidate into the
	 *            anagrams array
	 * @param start
	 *            is the index that you want to start at the candidates array
	 * @param end
	 *            is the index that you want to end at the candidates array
	 */
	private static void findAnagrams(Word comparedWord, String anagrams[], int levelIndex, int start, int end) {
		for (int i = start; i < end; i++) {
			if (comparedWord.fewerOfEachLetter(candidates[i])) {
				anagrams[levelIndex] = candidates[i].myWord;
				Word wordToPass = new Word("");
				comparedWord.findMissingLetters(i, wordToPass, candidates);
				determineAnagram(anagrams, levelIndex, i, wordToPass);
			}
		}
	}

	/**
	 * Determine if there is an anagram in the anagrams array and prints the
	 * anagrams
	 * 
	 * @param anagrams
	 *            is the array that holds one possible anagram at a point of
	 *            time
	 * @param levelIndex
	 *            is the last index that has a word in the anagrams array
	 * @param i
	 *            the index of the candidates array
	 * @param wordToPass
	 *            is the word that holds the missing letters
	 */
	private static void determineAnagram(String[] anagrams, int levelIndex, int i, Word wordToPass) {
		if (wordToPass.total == 0) {
			/* Found a series of words! */
			assert wellFormedAnagram(anagrams, levelIndex);
			for (int j = 0; j <= levelIndex; j++) {
				System.out.print(anagrams[j] + " ");
			}
			System.out.println();
		} else if (wordToPass.total < minimumLength) {
			; /* Don't call again */
		} else {
			findAnagrams(wordToPass, anagrams, levelIndex + 1, i, totalCandidates);
		}
	}

	/**
	 * Prints out the candidates
	 */
	private static void printCandidates() {
		System.out.println("Candiate words:");
		for (int i = 0; i < totalCandidates; i++) {
			System.out.print(candidates[i].myWord + ", " + ((i % 4 == 3) ? "\n" : " "));
		}
		System.out.println("\n");
	}

	/**
	 * Swap candidates at indexOne and indexTwo
	 * 
	 * @param indexOne
	 *            is the first index of the candidates array that is passed in
	 * @param indexTwo
	 *            is the second index of the candidates array that is passed in
	 */
	private static void swap(int indexOne, int indexTwo) {
		Word tmp = candidates[indexOne];
		candidates[indexOne] = candidates[indexTwo];
		candidates[indexTwo] = tmp;
	}

	/**
	 * Method to check for class invariants
	 * 
	 * @return true if the class invariants are upheld and false if not
	 */
	private static boolean wellFormed() {
		if (base.myWord == null) {
			return false;
		}
		// the dictionary has to have values
		if (list.dictionary == null) {
			return false;
		}

		// can't have less than 0 candidates
		if (totalCandidates < 0) {
			return false;
		}
		// all of the candidate words can't have different letters than the
		// anagram word
		for (int i = 0; i < totalCandidates - 1; i++) {
			for (int j = 0; j < 26; j++) {
				if (candidates[i].count[j] > base.count[j]) {
					return false;
				}
			}
		}

		return true;

	}

	/**
	 * A separate wellFormed method to check if the anagram word matches with
	 * myAnagram
	 * 
	 * @return false if the anagram created does not match with myAnagram
	 */
	private static boolean wellFormedAnagram(String[] theAnagram, int anagEnd) {
		String checkString = "";
		for (int j = 0; j <= anagEnd; j++) {
			checkString += theAnagram[j];
		}

		Word checkAnagram = new Word(checkString);

		// if the anagram is longer than myAnagram, it's not a true anagram
		if (checkString.length() != base.myWord.length()) {
			return false;
		}

		// then check the count
		for (int i = 0; i < 26; i++) {
			if (checkAnagram.count[i] != base.count[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if rootIndexEnd is well formed
	 * 
	 * @param leastCommonIndex
	 * @param rootIndexEnd
	 * @return true if rootIndexEnd is well formed
	 */
	private static boolean wellFormedFirstCandidateIndex(int leastCommonIndex, int rootIndexEnd) {

		// index should never be negative or greater or equal to the
		// totalCandidates
		if (rootIndexEnd < 0 || rootIndexEnd >= totalCandidates) {
			return false;
		}

		// the candidates at and after the rootIndexEnd should contain the least
		// common letter
		for (int i = rootIndexEnd; i < totalCandidates; i++) {
			if (!candidates[i].hasLetter(leastCommonIndex)) {
				return false;
			}
		}

		// the candidates before the rootIndexEnd should not contain the least
		// common letter
		for (int i = 0; i < rootIndexEnd; i++) {
			if (candidates[i].hasLetter(leastCommonIndex)) {
				return false;
			}
		}

		return true;

	}

	/**
	 * Check if leastCommonIndex is well formed
	 * 
	 * @param leastCommonIndex
	 * @return true if leastCommonIndex is well formed
	 */
	private static boolean wellFormedLeastCommonIndex(int leastCommonIndex, int[] totalCounts) {

		// index should never be negative or greater than 25
		if (leastCommonIndex < 0 || leastCommonIndex > 25) {
			return false;
		}

		// the count of letters at the leastCommonIndex should always be the
		// smallest count larger than 0
		for (int i = 0; i < totalCounts.length; i++) {

			if (totalCounts[i] > 0) {
				if (totalCounts[i] < totalCounts[leastCommonIndex]) {
					return false;
				}
			}

		}

		return true;

	}

}