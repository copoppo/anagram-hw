import java.io.PrintStream;

/*
 * Usage: java Anagram string [[min-len] wordfile] Java Anagram program, Peter
 * van der Linden Jan 7, 1996. Feel free to pass this program around, as long
 * as this header stays intact.
 */

public class Anagram {

	private static Word[] Candidate = new Word[WordList.MAXWORDS];
	private static int totCandidates=0;

	private static int MIN_WORD_LENGTH = 3;

	private static int MIN_ARG_LENGTH = 1;

	private static int MAX_ARG_LENGTH = 3;


	public static void main(String[] arg) {

		if (arg.length < MIN_ARG_LENGTH || arg.length > MAX_ARG_LENGTH) {
			System.err.println("Usage: java anagram  string-to-anagram " + "[min-len [word-file]]");
			return;
		}

		if (arg.length > MIN_ARG_LENGTH){
			try{
				MIN_WORD_LENGTH = Integer.parseInt(arg[1]);
			}
			catch(NumberFormatException e){
				System.err.println("Please pass in a number as the second argument");
			}


		}

		if (arg.length == MAX_ARG_LENGTH) {	
			WordList.ReadDict(arg[2]);
		}
		else{
			WordList.ReadDict("words.txt");
		}


		doAnagrams(arg[0]);

	}

	static void doAnagrams(String anag)
	{
		Word myAnagram = new Word(anag);

		getCandidates(myAnagram);

		printCandidate();

		int rootIndexEnd = findRootIndex(myAnagram);

		//PrintCandidate();

		//System.out.println("RootIndexEnd: " + rootIndexEnd);

		System.out.println("Anagrams of " + anag + ":");

		findAnagram(myAnagram, new String[50],  0, 0, rootIndexEnd);

		System.out.println("----" + anag + "----");
	}

	static void getCandidates(Word d) {
		for (int i = 0; i < WordList.totWords; i++){

			if ( isCandidate (WordList.Dictionary[i], d) ){
				Candidate[totCandidates++]=WordList.Dictionary[i];
			}

		}

	} 

	/**
	 * the number of each letter in the argument should equal to the number of those letters in candidate
	 * @param anagCount
	 * @param entryCount
	 * @return
	 */
	static boolean fewerOfEachLetter(int anagCount[], int entryCount[])
	{
		for (int i = 25; i >=0; i--) {
			if (entryCount[i] > anagCount[i]) {
				return false;
			}
		}
		return true;
	}

	private static boolean isCandidate(Word potentialCandi, Word anan){
		if (potentialCandi.total < MIN_WORD_LENGTH ) {
			return false;
		}
		if ((potentialCandi.total + MIN_WORD_LENGTH > anan.total) && (potentialCandi.total != anan.total)){
			return false;
		}
		if (!fewerOfEachLetter(anan.count, potentialCandi.count) ){
			return false;
		}

		return true; 
	}

	static void printCandidate()
	{
		System.out.println("Candiate words:");
		for (int i=0; i < totCandidates; i++) {
			System.out.print( Candidate[i].aword + ", " + ((i%4 ==3) ?"\n":" " ));
		}
		System.out.println("");
		System.out.println();
	}

	static void findAnagram(Word d, String wordArray[],int level, int startAt, int endAt) {
		Word wordToPass = new Word("");
		for (int i = startAt; i < endAt; i++) {

			if (fewerOfEachLetter(d.count ,Candidate[i].count )) {
				wordArray[level] = Candidate[i].aword;
				wordToPass.total = findMissingLetters(d, i, wordToPass);;

				wordFound(wordArray, level, i, wordToPass);
			}
		}
	}

	private static void wordFound(String[] wordArray, int level, int i, Word wordToPass) {
		if (wordToPass.total == 0) {
			/* Found a series of words! */
			for (int j = 0; j <= level; j++) {
				System.out.print(wordArray[j] + " ");
			}
			System.out.println();
		} else if (wordToPass.total < MIN_WORD_LENGTH) {
			; /* Don't call again */
		} else {
			findAnagram(wordToPass, wordArray, level+1,i, totCandidates);
		}
	}

	private static int findMissingLetters(Word d, int i, Word wordToPass) {
		int total = 0;
		for (int j = 25; j >= 0; j--) {
			wordToPass.count[j] = (byte) (d.count[j] - Candidate[i].count[j] );
			if ( wordToPass.count[j] != 0 ) {
				total += wordToPass.count[j];
			}
		}
		return total;
	}

	private static int findRootIndex(Word d)
	{
		int rootEndIndex;
		int leastCommonIndex = findLeastCommonIndex(d);
		//System.out.println("LeastCommonIndex " + LeastCommonIndex);
		//System.out.println("totCandidates " + totCandidates);

		quickSort(0, totCandidates-1, leastCommonIndex );

		for (rootEndIndex = 0; rootEndIndex < totCandidates; rootEndIndex++) {
			if (Candidate[rootEndIndex].containsLetter(leastCommonIndex)){
				break;
			}

		}

		return rootEndIndex;
	}

	private static  int findLeastCommonIndex(Word d) {
		int[] masterCount = new int[26];
		int leastCommonIndex = 0;
		int leastCommonCount = 0;


		for (int i = totCandidates-1; i >=0; i--) {
			for (int j = 25; j >=0; j--) {
				masterCount[j] += Candidate[i].count[j];
			}
		}

		//find the least common index
		leastCommonCount = WordList.MAXWORDS * 5;
		for (int j = 25; j >= 0; j--) {
			if (masterCount[j] != 0 && masterCount[j] < leastCommonCount && d.containsLetter(j)) {
				leastCommonCount = masterCount[j];
				leastCommonIndex = j;
			}
		}
		return leastCommonIndex;
	}

	private static void quickSort(int left, int right, int leastCommonIndex)
	{
		// standard quicksort from any algorithm book
		int last;
		if (left >= right){
			return;
		}
		swap(left, (left+right)/2);
		last = left;
		for (int i=left+1; i <=right; i++) {
			if (MultiFieldCompare ( Candidate[i], Candidate[left], leastCommonIndex ) ==  -1 ) {
				swap( ++last, i);
			}
		}

		swap(last, left);
		quickSort(left, last-1, leastCommonIndex);
		quickSort(last+1,right, leastCommonIndex);
	}

	private static int MultiFieldCompare(Word firstWord, Word secondWord, int LeastCommonIndex)
	{
		if ( (firstWord.containsLetter(LeastCommonIndex) ) &&  !(secondWord.containsLetter(LeastCommonIndex)) )
			return 1;

		if ( !(firstWord.containsLetter(LeastCommonIndex) ) &&  (secondWord.containsLetter(LeastCommonIndex)) )
			return -1;

		if ( secondWord.total != firstWord.total )
			return (secondWord.total - firstWord.total);

		return (firstWord.aword).compareTo(secondWord.aword);
	}

	private static void swap(int d1, int d2) {
		Word tmp = Candidate[d1];
		Candidate[d1] = Candidate[d2];
		Candidate[d2] = tmp;
	}
	
	static void testOutput()
	{
		String testString = "holyoke";
		String [] candidateHolyoke = {"elk",  "hey",  "hoe",  "hoke", 
				"hole",  "holk",  "holy",  "hook", 
				"hoy",  "key",  "koel",  "kohl", 
				"kolo",  "lek",  "ley",  "loo", 
				"look",  "lye",  "oho",  "oke", 
				"okeh",  "ole",  "oleo",  "ooh", 
				"yeh",  "yelk",  "yok",  "yoke", 
				"yolk"};
		System.out.println("Candidate words for " + testString);
		for(int i = 0; i < candidateHolyoke.length; i ++)
		{
			System.out.print( candidateHolyoke[i] + ", " + ((i%4 ==3) ?"\n":" " ) );
		}
		System.out.println("\n");
		
		String[] anagramHolyoke = {"hook ley", "hook lye", "koel hoy",
				"kolo yeh",  "kolo hey", "hole yok", "look yeh", "look hey", 
				"oke holy", "hoe yolk", "ooh yelk", "oho yelk"};
		System.out.println("and anagrams for " + testString);
		for(int i = 0; i < anagramHolyoke.length; i ++)
		{
			System.out.println( anagramHolyoke[i] );
		}
		System.out.println("\n");
		
		String[] arg = {"holyoke", "3", "words.txt"};
		if (arg.length < 1 || arg.length > 3) 
			{
				System.err.println("The length of the argument has to be at least 1 letter and can't contain more than 3 words");
				return;
			}
			
			if (arg.length >= 2)
				MIN_WORD_LENGTH = Integer.parseInt(arg[1]);
		String doc;
		if(arg.length == 3)
		{
			doc = arg[2];
		}
		else
		{
			doc = "words.txt";
		}
		
		WordList.ReadDict(doc);
		doAnagrams(testString);	
	}
}