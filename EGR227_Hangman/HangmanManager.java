/**
 * Nick DiSanto
 * Professor Knisley
 * EGR227-A
 * 5 March 2021
 *
 * The HangmanManager class receives the information the user passed to HangmanMain and keeps track of
 * the state of the Hangman game, returning information regarding how accurate the user's guesses were,
 * how many guesses they have remaining, and the pattern of dashes and correct letters.
 *
 * This class keeps track of the letters guessed in alphabetical order by using a TreeSet, which automatically
 * stores all of the values in the set in alphabetical order for the ease of the user.
 *
 * The trick of the HangmanManager class is that it will always make the game as hard as possible for the
 * user, keeping a collection of every possible word that fits the narrative, and hand-picking the categories
 * with the most amount of elements left so it takes the user longer to guess the "correct word".
 */

import java.util.*; // imported for access to Sets, Collections, Maps, and Argument Exceptions

public class HangmanManager {

    // these global variables are what will be modified in the program and returned ot HangmanMain
    public static int guessesRemaining;
    public static String patternString;
    public static Set<String> wordsConsidered = new TreeSet<>();
    public static SortedSet<Character> lettersGuessed = new TreeSet<>();

    // constructor initializes values of global variables and sets up the Hangman game
    public HangmanManager(Collection<String> dictionary, int length, int max) {
        guessesRemaining = max;
        if (length < 1 || max < 0) {
            throw new IllegalArgumentException("Error"); // throws exception if input passed by the user is illogical
        }

        for (String word : dictionary) { // runs through every word in the dictionary and selects
            if (word.length() == length) { // which ones are the correct length
                wordsConsidered.add(word);
            }
        }
        patternString = "";
        for (int i = 0; i < length; i++) { // pattern starts as all dashes since nothing has been guessed yet
            patternString += "- ";
        }
    }

    // returns the words that meet the current criteria
    public static Set<String> words() {
        return wordsConsidered;
    }

    // returns how many guesses the user has remaining
    public static int guessesLeft() {
        return guessesRemaining;
    }

    // returns which letters the user has already guessed
    public static SortedSet<Character> guesses() {
        return lettersGuessed;
    }

    // returns the current pattern of dashes and correct letters after each guess
    public static String pattern() {
        if (wordsConsidered == null) {
            throw new IllegalStateException("Error"); // throws exception if no words meet the criteria
        }
        return patternString;
    }

    // finds which group of words has the most remaining elements and returns that pattern
    public static int record(char guess) {
        if (guessesLeft() < 1 || wordsConsidered == null) {
            throw new IllegalStateException("Error"); // throws exception if the user passes values that don't work
        }

        lettersGuessed.add(guess); // adds users guessed letter to the TreeSet of letters guessed
        Map<String, Set<String>> wordPatterns = new TreeMap<>(); // maps each pattern to its associated words

        for (String word : wordsConsidered) { // runs through every word that still meets the criteria
            String individualWordPattern = ""; // creates a unique pattern for the given word

            int patternIndex = 0;
            for (int i = 0; i < word.length(); i++) { // runs through every index in the word
                if (word.charAt(i) != guess) { // if the character doesn't match the guess, it adds "- "
                    individualWordPattern += patternString.substring(patternIndex, patternIndex + 2);
                } else { // if the character matches the guess, it adds the guess to the string
                    individualWordPattern += guess + " ";
                }
                patternIndex += 2; // updates the index of the string that will be modified
            }

            Set<String> wordGroup = new TreeSet<>(); // the set of words that the pattern maps to
            if (!wordPatterns.containsKey(individualWordPattern)) { // if the pattern hasn't been seen before,
                wordPatterns.put(individualWordPattern, wordGroup); // it creates a new key
            }
            wordPatterns.get(individualWordPattern).add(word); // adds the word to the correct map
        }

        int max = 0; // keeps track of which map has the most elements
        for (String patternWithMax : wordPatterns.keySet()) { // runs through every map
            if (wordPatterns.get(patternWithMax).size() > max) { // determines if the current map has max elements
                wordsConsidered.clear(); // if so, the words considered set is wiped and replaced
                wordsConsidered.addAll(wordPatterns.get(patternWithMax));
                patternString = patternWithMax; // the pattern is updated to match the new optimized wordsConsidered
                max = wordPatterns.get(patternWithMax).size(); // the max is set to the new map's size
            }
        }

        int count = 0; // counts how many letters the user guessed correctly
        for (int i = 0; i < patternString.length(); i++) { // runs through every character in the pattern
            if (patternString.charAt(i) == guess) { // increments count if there are any updated characters
                count++;
            }
        }
        if (count == 0) { // if the user guessed none correctly, they lose a guess
            guessesRemaining--;
        }
        return count; // returns how many letters the user guessed correctly
    }
}
