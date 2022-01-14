import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * This JUnit testing program tests both positive and negative possibilities for all eight methods. It uses varying
 * inputs and modifications and checks whether the expected output is matched by the AssassinManager's methods.
 * When there are errors to be thrown, the test method verifies that each error is correctly accounted for,
 * including it in the testing outputs.
 */

public class AssassinManagerTest {

    @Test // positively tests the AssassinManager constructor
    public void AssassinManagerPositive() {
        List<String> list = new ArrayList<>();
        list.add("Nick");
        list.add("David");
        list.add("John");
        AssassinManager manager = new AssassinManager(list);
        Assert.assertTrue(manager.killRingContains("Nick"));
    }

    @Test // negatively tests the AssassinManager constructor, catching the error if the list is empty
    public void AssassinManagerNegative() {
        try {
            List<String> list = new ArrayList<>();
            AssassinManager manager = new AssassinManager(list);
            Assert.fail("List should not be empty.");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test // positively tests the printKillRing method
    public void printKillRingPositive() {
        List<String> list = new ArrayList<>();
        list.add("Nick");
        list.add("David");
        list.add("John");
        AssassinManager manager = new AssassinManager(list);
        manager.printKillRing();
        Assert.assertTrue(manager.killRingContains("Nick"));
    }

    @Test // negatively tests the printKillRing method
    public void printKillRingNegative() {
        List<String> list = new ArrayList<>();
        list.add("Nick");
        list.add("David");
        list.add("John");
        AssassinManager manager = new AssassinManager(list);
        manager.printKillRing();
        Assert.assertFalse(manager.killRingContains("Mark"));
    }

    @Test // positively tests the printGraveyard method
    public void printGraveyardPositive() {
        List<String> list = new ArrayList<>();
        list.add("Nick");
        list.add("David");
        list.add("John");
        AssassinManager manager = new AssassinManager(list);
        manager.kill("Nick");
        manager.printGraveyard();
        Assert.assertTrue(manager.graveyardContains("Nick"));
    }

    @Test // negatively tests the printGraveyard method
    public void printGraveyardNegative() {
        List<String> list = new ArrayList<>();
        list.add("Nick");
        list.add("David");
        list.add("John");
        AssassinManager manager = new AssassinManager(list);
        manager.kill("Nick");
        manager.printGraveyard();
        Assert.assertFalse(manager.graveyardContains("John"));
    }

    @Test // positively tests the killRingContains method
    public void killRingContainsPositive() {
        List<String> list = new ArrayList<>();
        list.add("Nick");
        list.add("David");
        list.add("John");
        AssassinManager manager = new AssassinManager(list);
        Assert.assertTrue(manager.killRingContains("Nick"));
    }

    @Test // negatively tests the killRingContains method
    public void killRingContainsNegative() {
        List<String> list = new ArrayList<>();
        list.add("Nick");
        list.add("David");
        list.add("John");
        AssassinManager manager = new AssassinManager(list);
        manager.kill("Nick");
        Assert.assertFalse(manager.killRingContains("Nick"));
    }

    @Test // positively tests the graveyardContains method
    public void graveyardContainsPositive() {
        List<String> list = new ArrayList<>();
        list.add("Nick");
        list.add("David");
        list.add("John");
        AssassinManager manager = new AssassinManager(list);
        manager.kill("Nick");
        Assert.assertTrue(manager.graveyardContains("Nick"));
    }

    @Test // negatively tests the graveyardContains method
    public void graveyardContainsNegative() {
        List<String> list = new ArrayList<>();
        list.add("Nick");
        list.add("David");
        list.add("John");
        AssassinManager manager = new AssassinManager(list);
        manager.kill("Nick");
        Assert.assertFalse(manager.graveyardContains("John"));
    }

    @Test // positively tests the isGameOver method
    public void isGameOverPositive() {
        List<String> list = new ArrayList<>();
        list.add("Nick");
        list.add("David");
        list.add("John");
        AssassinManager manager = new AssassinManager(list);
        manager.kill("Nick");
        manager.kill("David");
        Assert.assertTrue(manager.isGameOver());
    }

    @Test // positively tests the isGameOver method
    public void isGameOverNegative() {
        List<String> list = new ArrayList<>();
        list.add("Nick");
        list.add("David");
        list.add("John");
        AssassinManager manager = new AssassinManager(list);
        Assert.assertFalse(manager.isGameOver());
    }

    @Test // positively tests the winner method
    public void winnerPositive() {
        List<String> list = new ArrayList<>();
        list.add("Nick");
        list.add("David");
        list.add("John");
        AssassinManager manager = new AssassinManager(list);
        manager.kill("Nick");
        manager.kill("David");
        Assert.assertEquals("John", manager.winner());
    }

    @Test // negatively tests the winner method
    public void winnerNegative() {
        List<String> list = new ArrayList<>();
        list.add("Nick");
        list.add("David");
        list.add("John");
        AssassinManager manager = new AssassinManager(list);
        Assert.assertFalse(manager.winner() == "Nick");
    }

    @Test // positively tests the kill method
    public void killPositive() {
        List<String> list = new ArrayList<>();
        list.add("Nick");
        list.add("David");
        list.add("John");
        AssassinManager manager = new AssassinManager(list);
        manager.kill("John");
        Assert.assertFalse(manager.killRingContains("John"));
    }

    @Test // negatively tests the kill method, catching the error if the game ends
    public void killNegative() {
        try {
            List<String> list = new ArrayList<>();
            list.add("Nick");
            list.add("David");
            list.add("John");
            AssassinManager manager = new AssassinManager(list);
            manager.kill("David");
            manager.kill("John");
            manager.kill("Nick");
            Assert.fail("Cannot run: Game is over.");
        } catch (IllegalStateException e) {
        }
    }
}