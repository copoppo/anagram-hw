/*
 * Usage: java Anagram string [[min-len] wordfile] Java Anagram program, Peter
 * van der Linden Jan 7, 1996. Feel free to pass this program around, as long
 * as this header stays intact.
 */

/**
 * This class finds and prints the candidates for anagrams and the finalized
 * anagrams
 * 
 * @author Kuan-Chi Chen
 *
 */
public class Anagram {
	/** The maximum numbers of words to be stored in a Word array **/
	public static final int MAX_NUM = 100000;

	/** The Word array that holds the candidates of the anagrams **/
	private static Word[] candidate = new Word[MAX_NUM];

	/** The total of the candidates of the anagrams **/
	private static int totalCandidates = 0;

	/** The minimum length of a candidate word **/
	private static int minimumLength = 3;

	private static Word base;

	static void testOutput() {
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

		if (arg.length >= 2)
			minimumLength = Integer.parseInt(arg[1]);
		String doc;
		if (arg.length == 3) {
			doc = arg[2];
		} else {
			doc = "words.txt";
		}

		WordList.ReadDict(doc);
		getResult(testString);
	}

	/**
	 * The main method that is used to test the program finds the correct
	 * candidates and anagrams for "holyoke"
	 * 
	 * @param arg
	 */
	public static void main(String[] arg) {
		testOutput();

		// // It accepts 1 - 3 inputs
		// // The first input is the base word for which you want to find its
		// // anagrams
		// // thus if there are 0 or 4 or more inputs
		// // the program throws an error
		// if (arg.length < 1 || arg.length > 3) {
		// System.err.println("The length of input should be >=1 and <=3. Please
		// try again.");
		// }
		//
		// // if there are 2 or 3 words in the argument
		// // the program sets the minimum length to what the second input
		// // designates
		// // thus the second input can only be an int
		// if (arg.length >= 2) {
		// minimumLength = Integer.parseInt(arg[1]);
		// }
		//
		// // The optional third input is the filename, such as words.txt
		// // if arrgv length is 3 then pass in the second word in argument to
		// // ReadDictionay
		// if (arg.length == 3) {
		// WordList.ReadDict(arg[2]);
		// // if argv length is 1 or 2 then pass in words.txt
		// } else {
		// WordList.ReadDict("words.txt");
		// }
		// // get the anagram of the first word in the argument
		// getResult(arg[0]);
	}

	/**
	 * Get the anagrams of the word passed in
	 * 
	 * @param str
	 */
	static void getResult(String str) {
		// the word that would be the base of all anagrams
		base = new Word(str);
		// get the candidates that could be anagrams of the base
		getCandidates();
		printCandidates();

		System.out.println("Anagrams of " + str + ":");
		getAnagrams(base, new String[50], 0, 0, getEndIndex());
		System.out.println("----" + str + "----");
	}

	/**
	 * Get the candidates of the word passed in
	 * 
	 * @param base
	 */
	static void getCandidates() {
		// get the dictionary, which is an array of words in the WordList class
		Word[] dictionary = WordList.getDictionary();

		for (int i = 0; i < WordList.getTotalWords(); i++) {
			// numLetter should be larger or equal to the minimumLength (3)
			if ((dictionary[i].total >= minimumLength)
					// numLetter + 3 is smaller or equal to the length of word
					// numLetter = wordLength
					// change condition here??
					&& (dictionary[i].total + minimumLength <= base.total || dictionary[i].total == base.total)
					// ok
					&& (hasFewerLetter(base, dictionary[i]))) {
				// then it is a candidate
				candidate[totalCandidates++] = dictionary[i];
			}
		}
	}

	static int getEndIndex() {

		int endIndex;
		int leastLetterIndex = getLeastLetterIndex();

		quickSort(0, totalCandidates - 1, leastLetterIndex);

		// return the first candidate that has the least counted letter
		// from the back of the alphabet
		// for example, the first candidate that has "y" if the base word is
		// "holyoke"
		for (endIndex = 0; endIndex < totalCandidates; endIndex++) {
			if (candidate[endIndex].hasLetter(leastLetterIndex)) {
				break;
			}
		}

		return endIndex;
	}

	private static int getLeastLetterIndex() {
		// the total of each letter counts from each candidate word
		int[] totalLetter = new int[26];

		// calculate totalCandidateLetterCounts
		for (int candidateIndex = totalCandidates - 1; candidateIndex >= 0; candidateIndex--) {
			for (int letterIndex = 25; letterIndex >= 0; letterIndex--) {
				totalLetter[letterIndex] += candidate[candidateIndex].letter[letterIndex];
			}
		}

		int leastLetterIndex = 0;
		int leastLetter = MAX_NUM;

		for (int letterIndex = 25; letterIndex >= 0; letterIndex--) {
			// if we found this letter
			if (totalLetter[letterIndex] != 0
					// the counts of this letter is less than the counts of the
					// current least letter
					&& totalLetter[letterIndex] < leastLetter && base.hasLetter(letterIndex)) {
				// update leastLetter
				leastLetter = totalLetter[letterIndex];
				leastLetterIndex = letterIndex;
			}
		}
		return leastLetterIndex;
	}

	static void quickSort(int left, int right, int leastLetterIndex) {
		if (left >= right) {
			return;
		}

		swap(left, (left + right) / 2);

		int pivot = left;

		for (int i = left + 1; i <= right; i++) {

			// left + 1 compare with left
			if (candidate[i].multiFieldCompare(candidate[left], leastLetterIndex) == -1) {
				// swap left and left + 1, update last
				swap(++pivot, i);
			}

		}

		swap(pivot, left);
		quickSort(left, pivot - 1, leastLetterIndex);
		quickSort(pivot + 1, right, leastLetterIndex);
	}

	static void getAnagrams(Word anaOne, String anagrams[], int levelIndex, int start, int end) {

		for (int i = start; i < end; i++) {

			if (hasFewerLetter(anaOne, candidate[i])) {

				anagrams[levelIndex] = candidate[i].myWord;

				Word anaTwo = new Word("");

				for (int j = 25; j >= 0; j--) {

					anaTwo.letter[j] = (byte) (anaOne.letter[j] - candidate[i].letter[j]);

					if (anaTwo.letter[j] != 0) {

						anaTwo.total += anaTwo.letter[j];

					}
				}

				printAnagrams(anagrams, levelIndex, i, anaTwo);
			}
		}
	}

	private static void printAnagrams(String[] anagrams, int levelIndex, int i, Word anaTwo) {
		if (anaTwo.total == 0) {
			/* Found a series of words! */
			for (int j = 0; j <= levelIndex; j++) {
				System.out.print(anagrams[j] + " ");
			}
			System.out.println();
		} else if (anaTwo.total < minimumLength) {
			; /* Don't call again */
		} else {
			getAnagrams(anaTwo, anagrams, levelIndex + 1, i, totalCandidates);
		}
	}

	static boolean hasFewerLetter(Word wordOne, Word wordTwo) {
		for (int i = 25; i >= 0; i--) {
			if (wordOne.letter[i] < wordTwo.letter[i]) {
				return false;
			}
		}
		return true;
	}

	static void printCandidates() {
		System.out.println("Candiate words:");
		for (int i = 0; i < totalCandidates; i++) {
			System.out.print(candidate[i].myWord + ", " + ((i % 4 == 3) ? "\n" : " "));
		}
		System.out.println("\n");
	}

	static void swap(int d1, int d2) {
		Word tmp = candidate[d1];
		candidate[d1] = candidate[d2];
		candidate[d2] = tmp;
	}

}