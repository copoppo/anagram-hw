import java.io.*;

public class WordList  {
	public static final int MAXWORDS = 100000;
	public static final int MAXWORDLEN = 30;
	public static final int EOF = -1; //end of file
	
	// shorter alias for I/O streams
	//public static final PrintStream o = System.out;
	public static final PrintStream e = System.err;
	
	static Word[] Dictionary = new Word[MAXWORDS];
	static int totWords=0;

	static void ReadDict (String f) {
		FileInputStream fis;
		try {
			fis = new FileInputStream (f);
		}
		catch (FileNotFoundException fnfe) {
			e.println("Cannot open the file of words '" + f + "'");
			throw new RuntimeException();
		}
		e.println ("reading dictionary..."); 
		
		char buffer[] = new char[MAXWORDLEN];
		String s;
		int r =0;
		while (r!=EOF) {
			int i = 0; 
			try {
				// read a word in from the word file
				while ( (r=fis.read()) != EOF ) { //read() returns the next byte of data, or -1 if the end of the file is reached.
					if ( r == '\n' ) break;
					buffer[i++] = (char) r;
				}
			} catch (IOException ioe) {
				e.println("Cannot read the file of words ");
				throw new RuntimeException();
			}
			
			s = new String(buffer,0,i); //String(char[] value, int offset, int count)
										//Allocates a new String that contains characters from a subarray of the character array argument.
			Dictionary[totWords] = new Word(s);
			totWords++;
		}
		
		e.println("main dictionary has " + totWords + " entries.");
	}
}
