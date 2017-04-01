
/**
 * The class is used to hold a word or a phrase representing the 
 * original word, a word from the dictionary, or a candidate anagram
 * @author carol
 *
 */
public class Word  
{
	protected int count[] = new int[26];  // count of each letter in the word
	protected int total;  // number of letters in the word
	protected String aword;  // the word

	public Word(String s) { // construct an entry from a string
		int ch;
		aword = s;
		total = 0; //calculating the number of letters in the word
		s = s.toLowerCase();
		
		for (int i = s.length()-1; i >= 0; i--) 
		{
			//so ch is checking s
			ch = s.charAt(i) - 'a';
			//if it has a word at that character that's bigger than 0 as in there's actually a letter
			if (ch >= 0 && ch < 26) 
			{
				//the total increases
				total++;
				//and the count at that letter increases
				//i'm not actually sure what this does tho
				//probably just says that that the word has that letter then
				count[ch]++; //saying that there is a letter at that spot
			}
		}
	}

	//this one is fine
	protected boolean containsLetter(int j)
	{
		return count[j] != 0;
	}

	//i literally have no idea what this is doing
	//okay so it takes a word
	protected int MultiFieldCompare(Word compareWord, int LeastCommonIndex)
	{
		//this could probably be helped if there were better naming conventions so i know what each is doing
		
		//so what i'm getting is that it the word contains a letter at the leastcommonindex as in the smallest index that they share in common
		//and the word being given also has at least has a letter at that index
		if ( (containsLetter(LeastCommonIndex) ) &&  !(compareWord.containsLetter(LeastCommonIndex)) )
		{
			//say yes we do
			return 1;
		}
		
		//this is if the word does not have a letter
		if ( !(containsLetter(LeastCommonIndex) ) &&  (compareWord.containsLetter(LeastCommonIndex)) )
		{
			return -1;	
		}
		
		//sees if this and compareWord does not have the same amount of letters and return that difference
		if ( compareWord.total != total )
		{
			return (compareWord.total - total);
		}
		
		return (aword).compareTo(compareWord.aword); //i don't know what this compareto is for
		//i guess this is comparing the strings
		//so if it is smaller than 0 than it's less than the current word??
		//and if it is 0 then the total letters are technically equal
		//and if it is more than 0 then it is greater than the current word??
	}
}
}

