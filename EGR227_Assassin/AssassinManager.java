import java.util.List;
import java.util.Locale;

/**
 * The AssassinManager class, when given a list of names, adds them to the "kill ring", beginning the game.
 * The job of the AssassinManager is to modify the components and structure of the list depending on what information
 * is passed into the program. It has a constructor to initialize each of the global variables, methods that
 * return the state of the game (who is still alive and who has been killed), methods that check individual names,
 * returning whether or not they are still alive, methods that determine when the game is over and who the winner is,
 * and a method that kills a given player, eliminating them from the list and assigning their target to the person
 * who killed them.
 */

public class AssassinManager {

    private AssassinNode frontOfKillRing; // global nodes of people still alive
    private AssassinNode frontOfGraveyard; // global nodes of people who have been killed

    // throws error if parameters are not met and initializes kill ring
    public AssassinManager(List<String> names) {
        if (names == null || names.isEmpty()) { // if names passed are inadequate, error is thrown
            throw new IllegalArgumentException();
        }
        for (int i = names.size() - 1; i >= 0; i--) { // initializes kill ring in descending order
            frontOfKillRing = new AssassinNode(names.get(i), frontOfKillRing);
        }
    }

    // prints names of those who are still alive, along with who they are stalking
    public void printKillRing() {
        AssassinNode current = frontOfKillRing;
        while (current.next != null) { // iterates through the copy of kill ring, printing each person's info
            System.out.println("    " + current.name + " is stalking " + current.next.name);
            current = current.next;
        }
        System.out.println("    " + current.name + " is stalking " + frontOfKillRing.name); // separately prints last
    }                                                                                       // and first names

    // prints names of those who have already been killed, along with who killed them
    public void printGraveyard() {
        AssassinNode current = frontOfGraveyard;
        while (current != null) { // iterates through the copy of the graveyard, printing each person's info
            System.out.println("    " + current.name + " was killed by " + current.killer);
            current = current.next;
        }
    }

    // returns whether the kill ring contains a given name
    public boolean killRingContains(String name) {
        AssassinNode current = frontOfKillRing;
        while (current != null) { // iterates through the copy of the kill ring, checking if the name is there
            if (name.toUpperCase(Locale.ROOT).equals(current.name.toUpperCase())) // toUpperCase, so not case sensitive
                return true;
            current = current.next;
        }
        return false;
    }

    // returns whether the graveyard contains a given name
    public boolean graveyardContains(String name) {
        AssassinNode current = frontOfGraveyard;
        while (current != null) { // iterates through the copy of the graveyard, checking if the name is there
            if (name.toUpperCase(Locale.ROOT).equals(current.name.toUpperCase())) // toUpperCase, so not case sensitive
                return true;
            current = current.next;
        }
        return false;
    }

    // returns whether the game is over
    public boolean isGameOver() {
        return (frontOfKillRing.next == null); // game is over if there is only one name remaining
    }

    // returns the name of the winner if the game is over, otherwise returning null
    public String winner() {
        if (isGameOver())
            return frontOfKillRing.name;
        else
            return null;
    }

    // eliminates the given name from the kill ring, instead placing it in the graveyard
    public void kill(String name) {
        if (isGameOver()) // throws exception if the game is over
            throw new IllegalStateException();
        if (!killRingContains(name)) // throws exception if the kill ring does not contain the given name
            throw new IllegalArgumentException();

        AssassinNode dead = null;
        AssassinNode current = frontOfKillRing;
        if (frontOfKillRing.name.toUpperCase(Locale.ROOT).equals(name.toUpperCase(Locale.ROOT))) {
            while (current.next != null) { // if first name in the ring is the name, iterates through copy of kill ring
                current = current.next;
            }
            dead = frontOfKillRing; // the name of the dead person and killer are stored
            dead.killer = current.name;
            frontOfKillRing = frontOfKillRing.next; // dead person's node is cut out
        } else { // if the name is not the first person in the kill ring
            while (current.next != null) { // iterates through the copy of the kill ring
                if (current.next.name.toUpperCase(Locale.ROOT).equals(name.toUpperCase(Locale.ROOT))) {
                    dead = current.next; // if the name of the next node is who will be killed
                    dead.killer = current.name; // killer is the current name
                    if (current.next.next != null) { // if there are more names following, the node is cut out
                        current.next = current.next.next;
                        break;
                    }
                    current.next = null; // otherwise, the node is assigned to be null
                    break;
                }
                current = current.next;
            }
        }
        if (frontOfGraveyard != null) { // if there are already dead people, it stores the new name in order
            dead.next = frontOfGraveyard;
        } else {
            dead.next = null;
        }
        frontOfGraveyard = dead;
    }





    //////// DO NOT MODIFY AssassinNode.  You will lose points if you do. ////////
    /**
     * Each AssassinNode object represents a single node in a linked list
     * for a game of Assassin.
     */
    private static class AssassinNode {
        public final String name;  // this person's name
        public String killer;      // name of who killed this person (null if alive)
        public AssassinNode next;  // next node in the list (null if none)
        
        /**
         * Constructs a new node to store the given name and no next node.
         */
        public AssassinNode(String name) {
            this(name, null);
        }

        /**
         * Constructs a new node to store the given name and a reference
         * to the given next node.
         */
        public AssassinNode(String name, AssassinNode next) {
            this.name = name;
            this.killer = null;
            this.next = next;
        }
    }
}
