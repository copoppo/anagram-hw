This text file documents the changes that we have made

All the Classes

1. We changed the instance variables of the classes to be either private or protected
    - We changed them because the classes should not have access to every other variable in the other classes since that is
    generally bad programming practice and because we wanted to make sure that all of the classes have exactly the variables
    that they needed and the law of Demeter holds.

2. We changed the methods of the classes to either private or protected
    - We changed the methods in Anagram to private. 
        We changed those methods to private because they were only used and needed in that class and so we felt that it would
        be better to just declare them as private.
    - We changed the methods in Word and WordList to protected
        We changed those methods to protected because they were particular to the classes but they also needed to be accessed 
        by the anagram class so we made them protected. 

UsefulConstants

1. Deleted the UsefulConstants interface
  We got rid of the UsefulConstants interface because it only held constant variables and yet many of those variables were
  particular to one class (either Anagram or WordList) with a few simple constants (MAXWORDS) that could simply be declared in
  both classes. Along with that, it seemed to be a pointless interface because it wasn't providing methods or other
  information for the classes anagram and WordList to use so we got rid of it and moved the constants to the classes that
  needed it. 

Anagram

1. We got rid of the inheritance relationship between the Anagram class and the WordList class
   We decided to get rid of the inheritance relationship between anagram and WordList because it fails to "is-a" relationship
   since an anagram is not a WordList or vice versa. The reasoning for why the original author decided to subclass anagram to
   WordList seems to be so that anagram would be able to have access to the ReadDic method and some of the variables in
   WordList but we decided to use composition instead. By doing so, anagram still has access to WordList methods and variables
   while not using inheritance since it's not necessary.  

2. Then, we declared a WordList variable in Anagram (composition), instead of having it to be a subclass of WordList
(inheritance)
    - We wanted to make the coding more object-oriented, so we decided to declare it as a class variable for the Anagram class
    to use and access the methods for. The other reason for doing that was because we got rid of the inheritance relationship
    between Anagram and WordList with composition and it is generally better to declare an instance of the class that
    we're compositing with rather than just reference the class.
    
3. We change the if statement with "?" in the main method to make it more readable
    We changed this because we found that to be too confusing to read through and we wanted it to look more readable to a user
    and so we modified it to if-else statements so it was more obvious that they were checking if a document was being passed
    in. 
    
4. We changed the parameters of fewerOfEachLetter (from count to Word)
    We changed the parameters of fewerOfEachLetter and moved the method to the Word class because we felt that it looked more elegant that way and that it seemed
    to be better when thinking through the method and what information that it was accessing. The name of the method is more
    suitable with comparing two words because the method is checking if each letter of the first word is less than the second
    word. 
 
5. Renamed the sortCandidates method to the getFirstCandidateIndexWithLeastCommonLetter method and also extracted a findLeastCommonIndex method from it
  The sortCandidates method was misleading because it was not really sorting the candidates but was calculating the
  RootIndexEnd based on the LeastCommonIndex and the sorting from quickSort. Moreover, it was returning an integer which
  doesn't make sense for its naming because why would a sorting algorithm want to return a value when all it's supposed to be
  doing is reorganizing the Candidate array. Thus we changed the name of sortCandidates to getFirstCandidateIndexWithLeastCommonLetter. Along with that, the method also had a for loop to get the LeastCommonIndex which is a different set of logic and so we extracted a findLeastCommonIndex helper method which helped make more sense of the process of the getRootIndex method and also kept getFirstCommonLetterWithLeastCommonLetter from doing too much work. 

6. Broke up the findAnagrams method into a smaller findAnagrams method, a findMissingLetters method, and a determineAnagram
method
  The findAnagrams method in the anagram class was much too complicated and we had a hard time going through it and
  findAnagrams is doing too much. Thus we knew that we had to break up the method into smaller methods with better names to
  determine their role in the class. There were many for loops and if-else statements in the class that caused a lot of
  confusion so we broke up the method based on where the algorithm in the method switched to a different one (such as the
  first for loop in the if(enoughCommonLetters) loop) and extracted a method there.
  The two methods that we extracted were both found in the if(enoughCommonLetters) loop. findMissingLetters has the first for
  loop in the if loop and determineAnagram contains the if-else statemens that follow that for loop. 
  As such the findMissingLetters will find any missing letters for the anagram that we want to create for one of the candidate
  words and will return an integer of the amount of letters that are missing. The determineAnagram method will determine
  whether or not there is an anagram and then decide what to do from there based on whether that statement is true or false. 

7. Moved down declaration of variables such as i and j into the for loops
  A lot of the variables such as i and j were only used in for loops yet the original author declared them outside of the for 
  loops where they were used in so we got rid of the declarations and declared them in the for loop that they were
  controlling. 

8. Got rid of enoughCommonLetters and the algorithm for determining whether enoughCommonLetters is true or false and 
replaced enoughCommonLetters with fewerOfEachLetter method
  The logic in the for loop to determine whether enoughCommonLetters should be true or false is pretty similar to what the 
  fewerOfEachLetter method was doing so we decided to replace enoughCommonLetters with fewerOfEachLetter. By doing so, we cut
  down on the duplication of code in the class and also made the method FindAnagrams easier to read. 

9. Extracted the if statement in getCandidates into a isCandidate method
  The if statement in getCandidates was really complicated and hard to read through. Thus we extracted an isCandidate
  method so that getCandidates can simply call isCandidate to determine whether or not a dictionary entry is a possible
  candidate rather than using such a complicated if-else statement. We also moved the isCandidate method to the Word class.

10. Changed the import java.io.* in WordList to be more specific
  It's generally bad programming practice to import everything when the class only needs to import several packages so we
  decided to declare the packages that we were importing at the top of the class. 

11. Made 'base' a class instance variable instead of a local variable
  Since base is passed around in a lot of methods in the anagram class, we felt that it might be better to just declare it as 
  an instance variable for the class so that all of the methods can refer to it instead of needing to pass it as a parameter
  each time a method needs to access it. 

Word Class

1. got rid of the first for loop in constructor 
    We got rid of the first for loop in the constructor of Word because we felt that it was not needed since an array of ints
    already initializes to 0 so we got rid of it to avoid unneccessary code. 
2. We added the method fewerOfEachLetter, findMissingLetters and isCandidate to this class
    We moved the method over from the Anagram class because this method was only referencing Word properties and so it would be better if we moved it to the Word class because it belongs better. Because of that, we also changed how it was being called so the method is now non-static and called on Word objects.
    
2. Method/Variable name changes

   (Anagram)
  -Changed doAnagrams to getResults
  -Changed Candidate to candidates
  -Changed anagram to base
  
  (WordList and Anagram)
  -o; e to System.out; System.err
  
  (WordList)
  -r to letterValue 
    We changed it because letterValue is more explanatory.
  -buffer to currentWord
    We changed these names because we thought thaty they would look more elegant and made it more clear as to what they were
    doing and what information that they were using.    
    
Testing

1. We wrote a test outout class to replace the original main method.
    We replaced the main method with a testOutput method to ensure that our program would run the same as the original
    program.
    While it is replacing the main method, the anagram class should still be operating in the same way and should be able to 
    take in arguments from the command line hence we created an argument with a String[] to demonstrate that fact.

2. Four wellFormed methods
    There are four wellFormed methods in order to check that the class invariants of the original anagram class were being 
    upheld in the anagram class that we were improving. One of them checks the Word and candidates array and the candidates 
    themselves to make sure that they were accurate and usable. Another of these methods is to check if the rootIndexEnd is 
    correct and valid to how the original program was using it. There is also another method that checks the leastCommonIndex
    and makes sure that it also consistent with how the original program was using it. Finally, there is a wellFormed method
    to check that the anagram that we've made is actually an anagram of the word we want to find an anagram of.
    
3. One wellFormed method for Word
    We did this because we wanted to check that the word is being formed correctly and that the letters and counts match up        with the string being given. 






