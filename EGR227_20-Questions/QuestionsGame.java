/**
 * By Nick DiSanto, #689997
 * For Professor Knisley, EGR227-A, SP21
 *
 * The QuestionsGame class (along with its private inner class QuestionNode), initialize and monitor
 * a game of 20 Questions, tracking the user's progress along the way and making guesses to try
 * and find the user's object. In the case that the user has an object that is not stored in the text files
 * of the program, it asks for the object and a distinguishing property, then implementing the object
 * in future games.
 **/

import java.io.PrintStream;
import java.util.Scanner;

public class QuestionsGame {

    private final QuestionNode node = new QuestionNode();
    private QuestionNode guess = new QuestionNode();

    // creates fields to be used for each node object
    private static class QuestionNode {
        public QuestionNode left;
        public QuestionNode right;
        public String question;
        public String answer;
    }

    // initializes the individual node with a given "answer" string
    public QuestionsGame(String object) {
        this.node.answer = object;
    }

    // builds a new tree to initialize the game
    public QuestionsGame(Scanner input) {
        newTree(input, node);
    }

    // helper method that uses the given input from the user and the specific node to build a new tree
    public void newTree(Scanner input, QuestionNode node) {
        String line = input.nextLine();

        boolean isQuestion = line.charAt(0) == 'Q'; // checks whether the input is a question or an answer
        line = input.nextLine();

        if (isQuestion) { // if it's a question, creates new objects for yes or no cases
            node.question = line;
            QuestionNode yes = new QuestionNode();
            QuestionNode no = new QuestionNode();
            node.left = yes;
            node.right = no;

            newTree(input, yes); // recursively calls itself, trying again with both its yes and no cases
            newTree(input, no);
        } else
            node.answer = line; // if it's an answer, it assigns it to the answer variable
    }

    // called when user must provide its own question to be saved for future games
    public void saveQuestions(PrintStream output) {
        if (output == null) // throws exception if user didn't provide new question
            throw new IllegalArgumentException("Output is null.");
        getQuestions(output, node);
    }

    // helper method that distinguishes new question
    public void getQuestions(PrintStream output, QuestionNode node) {
        if (node.question == null) { // base case: finds final answer
            output.println("A:");
            output.println(node.answer);
        } else {
            output.println("Q:");
            output.println(node.question);

            getQuestions(output, node.left); // recursively calls itself, looking for answer for yes and no cases
            getQuestions(output, node.right);
        }
    }

    // simulates the entire game, first calling a helper method, and then displaying final messages to user
    public void play(Scanner input) {
        askQuestion(input, node);

        System.out.print("Am I right? (y/n)? ");

        if (input.nextLine().toLowerCase().charAt(0) == ('y')) // checks if it found the correct answer
            System.out.println("Awesome! I win!");
        else {
            System.out.println("Boo! I Lose. Please help me get better!");
            System.out.print("What is your object? ");
            String correct = input.nextLine(); // takes correct answer as input
            System.out.println("Please give me a yes/no question that distinguishes between "
                    + correct + " and " + guess.answer + ".");
            System.out.print("Q: ");
            String newQuestion = input.nextLine(); // takes new question to distinguish new answer
            System.out.print("Is the answer \"yes\" for " + correct + "? (y/n)? ");
            char isCorrect = input.nextLine().toLowerCase().charAt(0); // checks answer for this question

            guess.question = newQuestion;
            QuestionNode yes = new QuestionNode();
            QuestionNode no = new QuestionNode();
            guess.left = yes;
            guess.right = no;

            if (isCorrect == 'y') { // assigns values to this new question and answer
                yes.answer = correct;
                no.answer = guess.answer;
            } else {
                yes.answer = guess.answer;
                no.answer = correct;
            }
        }
    }

    // helper method that recursively runs through the entire tree trying to find the user's object
    public void askQuestion(Scanner input, QuestionNode currentNode) {
        if (currentNode.question != null) { // recursive case: continues to run until an answer is reached
            System.out.print(currentNode.question + " (y/n)? ");
            if (input.nextLine().toLowerCase().charAt(0) == 'y') // calls itself for respective answers
                askQuestion(input, currentNode.left);
            else
                askQuestion(input, currentNode.right);
        } else { // base case: an answer has been reached, and a guess is made
            guess = currentNode;
            System.out.println("I guess that your object is " + currentNode.answer + "!");
        }
    }
}