import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class holds a list of words or candidate anagrams.
 * 
 * 
 * @author
 *
 */
public class WordList {
	/** the maximum number of words that can be stored in our dictionary **/
	private static final int MAXWORDS = 100000;
	/** the maximum length of a word **/
	private static final int MAXWORDLEN = 30;
	/** Word array that serves as our dictionary **/
	protected static Word[] dictionary = new Word[MAXWORDS];
	/** total number of words in the dictionary **/
	protected static int totalWords = 0;

	/**
	 * Reads words from a txt file and parse them into a word array
	 * 
	 * @param name
	 *            of the txt file
	 */
	protected static void ReadDict(String f) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException fnfe) {
			System.err.println("Cannot open the file of words '" + f + "'");
			throw new RuntimeException();
		}
		System.err.println("reading dictionary...");

		char currentWord[] = new char[MAXWORDLEN];

		String s;

		int letterValue = 0;

		// while we have not reached the end of the file
		while (letterValue != -1) {

			int i = 0;

			try {
				// read a word in from the word file
				while ((letterValue = fis.read()) != -1) {

					if (letterValue == '\n')
						break;

					currentWord[i++] = (char) letterValue;

				}

			} catch (IOException ioe) {

				System.err.println("Cannot read the file of words ");
				throw new RuntimeException();

			}

			s = new String(currentWord, 0, i);

			dictionary[totalWords] = new Word(s);

			totalWords++;
		}

		System.err.println("main dictionary has " + totalWords + " entries.");
	}

}
