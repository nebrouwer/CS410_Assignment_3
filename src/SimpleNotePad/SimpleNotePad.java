// Nick Brouwer
// CS 410
// Assignment 3

package SimpleNotePad;

public class SimpleNotePad {

    private SimpleNotePadGUI notePadGUI;

    public SimpleNotePad() {
        notePadGUI = new SimpleNotePadGUI();
    }

    public static void main(String[] args) {
        SimpleNotePad app = new SimpleNotePad();
    }
}