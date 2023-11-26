package jdbccensus;

import java.util.List;

/**
 * Print info on customers in the FiredUp database
 * @author Domonic Senesi & Cara Tang
 * @version 2017.08.17
 *
 * Version 2017.08.17 - Changes & Additions
 * added parser, stateSearch refs
 * added getStateSearch method
 * main - changed prompt text
 * main - now loops waiting for user input, when input is validated, print requested data to standard output
 *
 */



public class Main {
    private static Parser parser;
    private static String stateSearch = null;

    public static String getStateSearch(){
        return stateSearch;
    }


    public static void main(String[] args) {

        System.out.println("Which state would you like to see customer data from?");
        System.out.println("Enter a 2-letter state or provinces' abbreviation (in Upper case only).");
        parser = new Parser();

        boolean finished = false;

        while (! finished) {
            stateSearch = parser.getInput();
            if (stateSearch != null) { finished = true;}
        }

        if (stateSearch != null) {

            FiredUpDB firedUp = new FiredUpDB();
            List<Customer> customers = firedUp.readCustomers();

                System.out.println("FiredUp Customers:");

                for (Customer cust : customers) {
                    System.out.println("ID: " + cust.getId() +
                            ", Name: " + cust.getName() +
                            ", City: " + cust.getCity() +
                            ", State: " + cust.getState() +
                            ", Email Address(es): " + cust.getEmailAddresses());
                }

        }
    }
}
