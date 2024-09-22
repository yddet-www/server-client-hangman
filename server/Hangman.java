import java.util.ArrayList;
import java.util.Random;

public class Hangman {
    private static final Random rand = new Random();    // randomizer for words
    private ArrayList<String> word_list;
    private String word;
    private char[] guess_state;
    private int lives = -1;                             // represent initializng game

    public Hangman(ArrayList<String> word_list) {
        this.word_list = word_list;
    }

    public String start() {
        word = word_list.get(rand.nextInt(word_list.size()));   // grab a word random word from list
        guess_state = new char[word.length()];                  // create new char array for state of game
        lives = 10;                                             // set lives to 10
        return String.format("%s\n%s", check_lives(), word.replaceAll("[a-z]", "_"));
    }

    public String play(String input) {
        String usr_input = input.toLowerCase();

        if (usr_input.length() != 1 || !Character.isLetter(usr_input.charAt(0))) {
            return "Invalid input!\nPlease enter a single alphabetic character.";
        }

        if (word.contains(usr_input)) {
            boolean null_found = false; // boolean flag to check completed guess

            for (int i = 0; i < guess_state.length; i++) {
                if (word.charAt(i) == usr_input.charAt(0)   // insert correct guess to char array
                    && word.charAt(i) != '\0')                    // if and only if it is empty 
                {
                    guess_state[i] = usr_input.charAt(0);
                }

                if (guess_state[i] == '\0') {
                    null_found = true;          // flag that guess is still incomplete
                }
            }

            if (!null_found) {
                return String.format("You win, the word was %s", word);
            } else {
                return check_lives() + "\n" + get_state();
            }
            
        } else {
            lives--;
            if (lives == 0) {
                return String.format("You lose, the word was %s", word);
            }
            return "WRONG! " + check_lives() + "\n" + get_state();
        }
    }

    public String get_state() {
        String state = "";

        for (int i = 0; i < guess_state.length; i++) {
            if (guess_state[i] == '\0') {
                state = state + "-";
            } else {
                state = state + guess_state[i];
            }
        }

        return state;
    }

    public String check_lives() {
        if (lives == 1) {
            return "Last chance!";
        }
        return "Number of lives left: " + lives;
    }
}
