import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class WordList{
	protected Word[] Dictionary = new Word[100000];
	protected int totWords=0;
	protected int EOF = -1;

	protected void ReadDict (String f) {
		FileInputStream fis;
		try {
			fis = new FileInputStream (f);
		}
		catch (FileNotFoundException fnfe) {
			System.err.println("Cannot open the file of words '" + f + "'");
			throw new RuntimeException();
		}
		System.err.println ("reading dictionary...");
		
		char buffer[] = new char[30];
		String s;
		int r =0;
		while (r!= EOF) {
			int i = 0;
			try {
				// read a word in from the word file
				while ( (r=fis.read()) != EOF ) {
					if ( r == '\n' ) break;
					buffer[i++] = (char) r;
				}
			} catch (IOException ioe) {
				System.err.println("Cannot read the file of words ");
				throw new RuntimeException();
			}
			
			s = new String(buffer,0,i);
			Dictionary[totWords] = new Word(s);
			totWords++;
		}
		
		System.err.println("main dictionary has " + totWords + " entries.");
	}
}
