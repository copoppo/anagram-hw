import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Former WordList
 * 
 * @author christine
 *
 */
public class WordList {

	public static final int MAXWORDS = 100000;
	public static final int MAXWORDLEN = 30;
	public static final PrintStream e = System.err;

	// shorter alias for I/O streams

	static Word[] dictionary = new Word[MAXWORDS];
	//
	static int totalWords = 0;

	public static int getTotalWords() {
		return totalWords;
	}

	public static Word[] getDictionary() {
		return dictionary;
	}

	static void ReadDict(String f) {
		//
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException fnfe) {
			e.println("Cannot open the file of words '" + f + "'");
			throw new RuntimeException();
		}
		e.println("reading dictionary...");

		char buffer[] = new char[MAXWORDLEN];

		String s;

		int r = 0;

		// when r is not EOF -1
		while (r != -1) {

			int i = 0;

			try {
				// read a word in from the word file

				while ((r = fis.read()) != -1) {

					if (r == '\n')
						break;

					buffer[i++] = (char) r;

				}

			} catch (IOException ioe) {

				e.println("Cannot read the file of words ");
				throw new RuntimeException();

			}

			s = new String(buffer, 0, i);

			dictionary[totalWords] = new Word(s);

			totalWords++;
		}

		e.println("main dictionary has " + totalWords + " entries.");
	}
}
