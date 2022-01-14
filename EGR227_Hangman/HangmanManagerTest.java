import org.junit.*;     // JUnit tools

import java.util.*;     // Collections
import java.io.*;       // File access


public class HangmanManagerTest {

    /* Loads the words in fileName and returns the set of all words in that file*/
    private Set<String> getDictionary(String fileName) {
        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            Set<String> dictionary = new HashSet<>();
            while(fileScanner.hasNext()) {
                dictionary.add(fileScanner.next());
            }
            return dictionary;
        } catch(FileNotFoundException e) {
            Assert.fail("Something went wrong.");      //Something went wrong
        }
        /* Should never be reached. */
        return new HashSet<>();
    }

    /**
     * This test class tests both positive and negative values of each method, verifying that the correct
     * values are being calculated and stored throughout the entire program.
     *
     * It also has methods that detect and display when an erroneous value has been passed.
     */

    @Test // tests the HangmanManager constructor
    public void test1(){
        HangmanManager.guessesRemaining = 10;
        HangmanManager.wordsConsidered.add("String1");

        Assert.assertEquals(10, HangmanManager.guessesRemaining);
        Assert.assertTrue(HangmanManager.wordsConsidered.contains("String1"));
    }


    @Test // tests the words method
    public void test2(){
        HangmanManager.wordsConsidered.add("String1");

        Assert.assertTrue(HangmanManager.wordsConsidered.contains("String1"));
        Assert.assertFalse(HangmanManager.wordsConsidered.contains("String20"));
        Assert.assertEquals(1, HangmanManager.wordsConsidered.size());
    }

    @Test // tests the guessesLeft method
    public void test3(){
        HangmanManager.guessesRemaining = 4;

        Assert.assertEquals(4,HangmanManager.guessesRemaining);
    }

    @Test // tests the guesses method
    public void test4(){
        HangmanManager.lettersGuessed.add('f');

        Assert.assertTrue(HangmanManager.lettersGuessed.contains('f'));
        Assert.assertFalse(HangmanManager.lettersGuessed.contains('s'));
    }

    @Test // tests the pattern method
    public void test5(){
        HangmanManager.patternString = "-oo-";

        Assert.assertEquals("-oo-", HangmanManager.patternString);
    }

    @Test(expected = IllegalStateException.class) // tests when the pattern method crashes
    public void test6(){
        HangmanManager.wordsConsidered = null;
        Assert.assertEquals("Error", HangmanManager.pattern());
    }

    @Test(expected = IllegalStateException.class) // tests when the record method crashes
    public void test7(){
        HangmanManager.wordsConsidered = null;
        Assert.assertEquals("Error", HangmanManager.pattern());
    }
}