public class Protocol {
    private String word;
    private char[] guess_it;

    public Protocol(String chosen_word){
        guess_it = new char[chosen_word.length()];
        word = chosen_word;
    }

    public void start(){
        
    }
}
