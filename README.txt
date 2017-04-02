This text file documents the changes that we've made

-Got rid of the inheritance relationship between the anagram class and the WordList class
  We decided to get rid of the inheritance relationship between anagram and WordList because it fails to "is-a" relationship since an anagram is not a WordList or vice versa. The reasoning for why the original author decided to subclass anagram to WordList seems to be so that anagram would be able to have access to the ReadDic method and some of the variables in WordList but we decided instead to use composition. By doing so, anagram still has access to WordList methods and variables while not using inheritance when it's not needed.  

-Got rid of the UsefulConstants interface
  We got rid of the UsefulConstants interface because it only held constant variables and yet many of those variables were particular to one class (either anagram or WordList) with several other simple constants (MAXWORDS, o, and e) that could simply be declared in both classes. Along with that, it seemed to be a pointless interface because it wasn't providing methods or other information for the classes anagram and WordList to use so we got rid of it and moved the constants to the classes that needed it. 

-Broke up the FindAnagrams method into a smaller FindAnagrams method, a findMissingLetters method, and a determineAnagram method
  The FindAnagrams method in the anagram class was much too complicated and we had a hard time going through it so we knew that we had to break up the method into smaller methods with better names to determine their role in the class. There were many for loops and if-else statements in the class that caused a lot of confusion so we broke up the method based on where the algorithm in the method switched to a different one (such as the first for loop in the if(enoughCommonLetters) loop) and extracted a method there. The two methods that we extracted were both found in the if(enoughCommonLetters) loop, findMissingLetters has the first for loop we encounter in the if loop and determineAnagram contains the if-else statements that follow that for loop. We broke it up like that because we were really confused as to what was going on in that loop and felt that there were FindAnagrams to do too much. As such the findMissingLetters will find any missing letters for the anagram that we want to create for one of the candidate words and will return an integer of the amount of letters that are missing. The determineAnagram method will determine whether or not there is an anagram and then decide what to do from there based on whether that statement is true or false. 

-Renamed the sortCandidates method to the getRootIndex method and also extracted a getLeastCommonIndex method from it

-Moved down declaration of variables such as i and j into the for loops

-Got rid of enoughCommonLetters and the algorithm for determining whether enoughCommonLetters is true or false

-Extracted the if statement in getCandidates into a isCandidate method



