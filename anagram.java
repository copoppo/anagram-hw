/*
 * Usage: java Anagram string [[min-len] wordfile] Java Anagram program, Peter
 * van der Linden Jan 7, 1996. Feel free to pass this program around, as long
 * as this header stays intact.
 * 
 * The main class. It gets the phrase to make an anagram of from the command
 * line. It then reads a dictionary from a file and finds a list of anagrams based on that dictionary
 */

public class anagram{	
	private static Word[] Candidate = new Word[100000]; //a collection of possible anagrams
	private static int totalCandidates=0;
	private static int MinimumLength = 3;
	private static WordList wordDictionary = new WordList();
	private static int LeastCommonIndex=0;
	
	public static void main(String[] argv) 
	{
		if (argv.length < 1 || argv.length > 3) 
		{
			//System.err.println("Usage: java anagram  string-to-anagram " + "[min-len [word-file]]");
			//System.err.println("lengt")
			return;
		}
		
		if (argv.length >= 2)
		{
			MinimumLength = Integer.parseInt(argv[1]);
		}
		
		// word filename is optional 3rd argument
		wordDictionary.ReadDict( argv.length==3? argv[2] : "words.txt" );
		
		//okay so doanagram starts the candidates
		DoAnagrams(argv[0]);
	}
	
	private static void DoAnagrams(String anag)
	{
		Word myAnagram = new Word(anag);

		
		getCandidates(myAnagram); //so this prints out the first half
		PrintCandidate();
		
		//splitting that in half since sortCandidates wasn't only sorting candidates
		sortCandidates(myAnagram);
		int RootIndexEnd = calcRootIndex();
		System.out.println("Anagrams of " + anag + ":");
		FindAnagram(myAnagram, new String[50],  0, 0, RootIndexEnd);
		
		System.out.println("----" + anag + "----");
	}

	private static void getCandidates(Word entryWord) {
	//	for (int i = totCandidates = 0; i < totWords; i++)
		//i just changed the method signature because i felt that it was unnecessary since toCandidates is already declared as 0 in the beginning
		for(int i = 0; i < wordDictionary.totalWords; i++) //oh okay so totalWords first comes from the wordlist which reads through the dictionary alright
		{
			// the if checking is too long and overly complicated
			//really all it would need is better naming conventions and also more obvious naming
			if (   (wordDictionary.Dictionary[i].total >= MinimumLength   ) //so it has to be at least the minimum length (which is 3)
				&& (wordDictionary.Dictionary[i].total + MinimumLength <= entryWord.total //and the total amount of words in the dictionary + 3 either has to be
				//less than or equal to the total number of words of the word given in the parameter
				//so it can't be too much longer from the word
				//so Dictionary[i] is just looking through the dictionary to check
					||  wordDictionary.Dictionary[i].total == entryWord.total)
					//or the dictionary word has the same amount of letters as the currentWord
				&& ( fewerOfEachLetter(entryWord.count, wordDictionary.Dictionary[i].count) )  ) //and this checks if the letters are the same
				//but also checks if the anagramWord has lesser letters or equal letters to the current dictionary entry
			{
				Candidate[totalCandidates] = wordDictionary.Dictionary[i];
				totalCandidates++; //moving the increment afterwards because i felt that it made it look cleaner but that's just a personal taste
				//Candidate[totalCandidates++]=Dictionary[i]; //then it inserts the dictionary entry into the possible candidates
			}
		}
	}
	
	private static boolean fewerOfEachLetter(int anagCount[], int entryCount[])
	{
		for (int i = 25; i >=0; i--)
		{
			//so if the letter in that place is greater than the possible anagram 
			//as in it has more of the letters than the actual word we want the anagrams of
			//or that it has different letters than the anagrams
			if (entryCount[i] > anagCount[i]) 
			{
				return false;
			}
		}
		return true;
	}
	
	private static void PrintCandidate()
	{
		System.out.println("Candiate words:");
		for (int i=0; i < totalCandidates; i++)
		{
			//this ensures that there are only 4 candidate words per line
			System.out.print( Candidate[i].aword + ", " + ((i%4 ==3) ?"\n":" "));
		}
		System.out.println("");
		System.out.println();
	}

	private static void FindAnagram(Word enterWord, String WordArray[], int Level, int StartAt, int EndAt) 
	{
		boolean enoughCommonLetters;
		Word WordToPass = new Word("");
		
		for (int i = StartAt; i < EndAt; i++) 
		{
			enoughCommonLetters = true;
			//this is to check that they share the same common letters
			for (int j = 25; j >= 0 && enoughCommonLetters; j--)
			{
				if (enterWord.count[j] < Candidate[i].count[j])
				{
					enoughCommonLetters = false;
				}
			}
			
			
			//there's too many if statements so it would probably be better to separate them
			if (enoughCommonLetters) 
			{
				WordArray[Level] = Candidate[i].aword;
				WordToPass.total = 0;
				for (int j = 25; j >= 0; j--) {
					WordToPass.count[j] = (byte) (enterWord.count[j] - Candidate[i].count[j] );
					if ( WordToPass.count[j] != 0 ) {
						WordToPass.total += WordToPass.count[j];
					}
				}
				//so then that's an anagram
				if (WordToPass.total == 0) 
				{
					/* Found a series of words! */
					for (int j = 0; j <= Level; j++)
					{
						System.out.print(WordArray[j] + " ");
					}
					System.out.println();
				} 
				else if (WordToPass.total < MinimumLength) 
				{
					 ;/* Don't call again */
				} 
				else 
				{
					FindAnagram(WordToPass, WordArray, Level+1,i, totalCandidates);
				}
			}
		}
	}

	static void sortCandidates(Word enterWord)
	{
		int[] MasterCount=new int[26]; 
		
		setMasterCount(MasterCount);
		int LeastCommonCount = 100000 * 5;
		
		for (int j = 25; j >= 0; j--)
		{
			//if statement should probably be better worded
			//the if statement is ultimately checking that yes a letter matches up
			//and also that there aren't too many of the same word such that it is ultimately unable to make an anagram
			//and also that it matches with the givenword
			if (    MasterCount[j] != 0 //checking that there is a letter at that spot
				 && MasterCount[j] < LeastCommonCount //so as long as the word isn't ridiculously long
				 && enterWord.containsLetter(j)  ) //so if the word that you want to make an anagram of
				{
				//then the leastCommoncount is set equal to the amount of words at that point
				LeastCommonCount = MasterCount[j];
				//and the index is set equal to j
				LeastCommonIndex = j; //so i guess the leastcommonindex is really where they share the first letter
			}
		}
		
		//so this is called once we reach to the end of the letters that match up
		quickSort(0, totalCandidates-1, LeastCommonIndex ); //so then this is organizing the candidates array
	}

	//this method is actually pretty small because it's just getting the count at a certain index
	//in this case it's how mnay words have 'e' in them
	private static int calcRootIndex() {
		//for the time being i'll just create a different variable
		int returnCount = totalCandidates-1;
		for (int i = 0; i < totalCandidates; i++)
		{
			//if any of the candidate words has a letter at the so called leastcommonindex
			if (Candidate[i].containsLetter(LeastCommonIndex))
			{
				returnCount = i;
				break;
			}
		}
		
		//this decides how many possible candidate words are there so maybe it should just be a local variable
		System.out.println("whatever this is " + returnCount);
		return returnCount;
	}

	private static void setMasterCount(int[] MasterCount) {
		//like the only reason for this is to set all of it equal to 0 
		//and then the next for loop is to put in values so what's the point of this
		//might as well as just set the values of j when i is being counted
		for (int j = 25; j >= 0; j--) 
		{
			MasterCount[j] = 0;
		}
		
		//so there's a double for loop
		for (int i = totalCandidates-1; i >=0; i--)
		{	//and then this is putting in the counts at that
			//so the mastercount only has 26 slots so it starts there
			for (int j = 25; j >=0; j--)
			{
				MasterCount[j] += Candidate[i].count[j]; //and what is the current amount at 25
			}
		}
	}

	private static void quickSort(int left, int right, int LeastCommonIndex)
	{
		// standard quicksort from any algorithm book
		//int i; //this needs a better naming convention
		//i'll probably just push it down
		int last;
		if (left >= right)
		{
			return;
		}
		swap(left, (left+right)/2);
		last = left;
		for (int i=left+1; i <=right; i++)  /* partition */
		{
			//so this means that the current candidate on the left has a letter at the leastcommonindex
			//but the word at candidate slot i does not
			if (Candidate[i].MultiFieldCompare ( Candidate[left], LeastCommonIndex ) ==  -1 )
			{
				//so then you swap i with the current left
				last++; //moving the increment earlier
				//so we're not really switching left with i but the increment of last before the for loop
				swap(last, i);	
			}
		}
		
		//then switch last with left
		//i'm looking at this and it seems that last will always be more than left it seems
		swap(last, left);
		//and then some more quicksorting
		//i'm going to draw out some memory of this because this is really confusing me
		//at least i know that it's sorting out the candidates array
		quickSort(left, last-1, LeastCommonIndex);
		quickSort(last+1,right, LeastCommonIndex);
	}
	
	private static void swap(int d1, int d2) 
	{
		Word tmp = Candidate[d1];
		Candidate[d1] = Candidate[d2];
		Candidate[d2] = tmp;
	}
	
	//well-formed method to make sure that the program is working just fine
	private static boolean wellFormed()
	{
		//it needs to make sure that it pulls out only words that are at least length 3
		//and has enoughcommonletters with the actual anagram so that there aren't any duplicate letters
		
		//the other thing is to make sure that the anagrams that it creates all have the same letters as the word
		//we want the anagram of
		
		//the other thing is to make sure that the letters are being pulled out alphabetically
		
		//the anagrams are being printed alphabetically
		
		//i don't know i feel like the best way to test it is to just put in all the answers
		//that we currently have for holyoke when running the cadidate words
		
		//there's also the rootindexend which i still don't fully understand but it would be good 
		//to make sure that it is correct even as we're changing parts of the program
		return true;
	}
}
