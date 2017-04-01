import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * This class holds a list of words or candidate anagrams
 * @author carol
 *
 */
public class WordList
{
	protected Word[] Dictionary = new Word[100000];
	protected int totalWords=0;
	private final int MAXWORDLEN = 30;
	//private static final int EOF = -1;

	//got rid of the static references throughout the whole thing
	protected void ReadDict (String f) 
	{
		FileInputStream fis;
		try {
			fis = new FileInputStream (f);
		}
		catch (FileNotFoundException fnfe) 
		{
			System.err.println("Cannot open the file of words '" + f + "'");
			throw new RuntimeException();
		}
		System.err.println ("reading dictionary...");
		
		char buffer[] = new char[MAXWORDLEN];
		String s;
		int r =0;
		//there are two while loops along with a try-catch block 
		//might be better to break it up
		//while (r!=EOF) 
		while (r!=-1)
		{
			int i = 0;
			try 
			{
				// read a word in from the word file
				//while ( (r=fis.read()) != EOF ) 
				while( (r=fis.read()) != -1)
				{
					//if the word is on another line then break from it and set it to whatever needs to be set
					if ( r == '\n' ) 
					{
						break;
					}
					buffer[i++] = (char) r;
				}
			} 
			catch (IOException ioe) 
			{
				System.err.println("Cannot read the file of words ");
				throw new RuntimeException();
			}
			
			s = new String(buffer,0,i);
			//okay and then add the words to the dictionary
			Dictionary[totalWords] = new Word(s);
			totalWords++;
		}
		
		System.err.println("main dictionary has " + totalWords + " entries.");
	}
}
