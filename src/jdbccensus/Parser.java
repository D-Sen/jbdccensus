package jdbccensus;
import java.util.Scanner;

/*
 * 
 * Author: Domonic Senesi
 * Version: 2017.08.17
 *
 * Version 2017.08.17 - Changes & Additions
 * Method - added - getInput. See Method description
 *
 */

public class Parser {
    private Scanner reader;

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser()
    {
        reader = new Scanner(System.in);
    }

    /**
     * getInput
     * Gets and parses a users text input. Prints error to standard output.
     * Checks for length and actual character type.
     * @return The next command from the user.
     */
    public String getInput() {
        String inputLine = null;   // will hold the full input line

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();
        int inputLength = inputLine.length();


        if (inputLength == 2){
            for (int index = 0; index < inputLength; index++) {
                if (! Character.isLetter(inputLine.charAt(index))) {
                    System.out.println("Input cannot be numbers or symbols.");
                    inputLine = null;
                    break;
                }
            }
        } else {
            System.out.println("Use only 2 upper case letters for a State/Province, and no Symbols or Numbers.");
            inputLine = null;

        }

        return inputLine;

    }
}
