/*
 * Usage: java Anagram string [[min-len] wordfile] Java Anagram program, Peter
 * van der Linden Jan 7, 1996. Feel free to pass this program around, as long
 * as this header stays intact.
 */

public class anagram{
	static Word[] Candidate = new Word[100000];
	static int totCandidates=0;
	static int MinimumLength = 3;
	static WordList wordDictionary = new WordList();
	
	public static void main(String[] argv) 
	{
		if (argv.length < 1 || argv.length > 3) 
		{
			System.err.println("The length of the argument has to be at least 1 letter and can't contain more than 3 words");
			return;
		}
		
		if (argv.length >= 2)
			MinimumLength = Integer.parseInt(argv[1]);
		
		// word filename is optional 3rd argument
		String doc;
		//so the user can also input a file name as the 3rd argument
		if(argv.length == 3)
		{
			doc = argv[2];
		}
		else
		{
			doc = "words.txt";
		}
		wordDictionary.ReadDict( doc );
		
		//okay so doanagram starts the candidates
		DoAnagrams(argv[0]);
	}
	
	static void DoAnagrams(String anag)
	{
		Word myAnagram = new Word(anag);

		
		getCandidates(myAnagram);
		PrintCandidate();
		
		int RootIndexEnd = calcRootIndexEnd(myAnagram);
		
		System.out.println("Anagrams of " + anag + ":");
		FindAnagram(myAnagram, new String[50],  0, 0, RootIndexEnd);
		
		System.out.println("----" + anag + "----");
	}

	static void getCandidates(Word d) 
	{
		for (int i = 0; i < wordDictionary.totWords; i++)
			if (   (    wordDictionary.Dictionary[i].total >= MinimumLength   )
				&& (    wordDictionary.Dictionary[i].total + MinimumLength <= d.total
					||  wordDictionary.Dictionary[i].total == d.total)
				&& ( fewerOfEachLetter(d.count, wordDictionary.Dictionary[i].count) )  )
				Candidate[totCandidates++]=wordDictionary.Dictionary[i];
		
	}
	
	static boolean fewerOfEachLetter(int anagCount[], int entryCount[])
	{
		for (int i = 25; i >=0; i--)
			if (entryCount[i] > anagCount[i]) return false;
		return true;
	}
	
	static void PrintCandidate()
	{
		System.out.println("Candiate words:");
		for (int i=0; i < totCandidates; i++)
			System.out.print( Candidate[i].aword + ", " + ((i%4 ==3) ?"\n":" " ) );
		System.out.println("");
		System.out.println();
	}

	static void FindAnagram(Word d, String WordArray[], int Level, int StartAt, int EndAt) 
	{
		boolean enoughCommonLetters;
		Word WordToPass = new Word("");
		
		for (int i = StartAt; i < EndAt; i++) {
			enoughCommonLetters = true;
			for (int j = 25; j >= 0 && enoughCommonLetters; j--)
				if (d.count[j] < Candidate[i].count[j])
					enoughCommonLetters = false;
			
			if (enoughCommonLetters) {
				WordArray[Level] = Candidate[i].aword;
				WordToPass.total = findMissingLetters(d, WordToPass, i);
				determineAnagram(WordArray, Level, WordToPass, i);
			}
		}
	}

	private static void determineAnagram(String[] WordArray, int Level,
			Word WordToPass, int candIndex) {
		if (WordToPass.total == 0) {
			/* Found a series of words! */
			for (int j = 0; j <= Level; j++)
				System.out.print(WordArray[j] + " ");
			System.out.println();
		} else if (WordToPass.total < MinimumLength) {
			; /* Don't call again */
		} else {
			FindAnagram(WordToPass, WordArray, Level+1,candIndex, totCandidates);
		}
	}

	private static int findMissingLetters(Word findWord, Word WordToPass, int candIndex) {
		int total = 0;
		for (int j = 25; j >= 0; j--) {
			WordToPass.count[j] = (byte) (findWord.count[j] - Candidate[candIndex].count[j] );
			if ( WordToPass.count[j] != 0 ) {
				total += WordToPass.count[j];
			}
		}
		return total;
	}

	static int calcRootIndexEnd(Word anagWord)
	{
		int LeastCommonIndex = findLeastCommonIndex(anagWord);
		
		quickSort(0, totCandidates-1, LeastCommonIndex );
		
		int i = 0;
		for (i = 0; i < totCandidates; i++)
			if (Candidate[i].containsLetter(LeastCommonIndex))
				break;
		
		return i;
	}

	private static int findLeastCommonIndex(Word anagWord) {
		int[] MasterCount=new int[26];
		int LeastCommonIndex=0, LeastCommonCount;
		
		for (int j = 25; j >= 0; j--) MasterCount[j] = 0;
		for (int i = totCandidates-1; i >=0; i--)
			for (int j = 25; j >=0; j--)
				MasterCount[j] += Candidate[i].count[j];
		
		LeastCommonCount = 100000 * 5;
		for (int j = 25; j >= 0; j--)
			if (    MasterCount[j] != 0
				 && MasterCount[j] < LeastCommonCount
				 && anagWord.containsLetter(j)  ) {
				LeastCommonCount = MasterCount[j];
				LeastCommonIndex = j;
			}
		return LeastCommonIndex;
	}

	static void quickSort(int left, int right, int LeastCommonIndex)
	{
		// standard quicksort from any algorithm book
		if (left >= right) return;
		swap(left, (left+right)/2);
		int last = left;
		for (int i=left+1; i <=right; i++)  /* partition */
			if (Candidate[i].MultiFieldCompare ( Candidate[left], LeastCommonIndex ) ==  -1 )
				swap( ++last, i);
		
		swap(last, left);
		quickSort(left, last-1, LeastCommonIndex);
		quickSort(last+1,right, LeastCommonIndex);
	}
	
	static void swap(int d1, int d2) {
		Word tmp = Candidate[d1];
		Candidate[d1] = Candidate[d2];
		Candidate[d2] = tmp;
	}
}
