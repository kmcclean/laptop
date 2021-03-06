/** @author Clara MCTC Java Programming Class */

import java.util.LinkedList;
import java.util.Scanner;

public class InventoryView {

    private final int QUIT = 9;   //Modify if you add more menu items.
    //Can you think of a more robust way of handling menu options which would be easy to modify with a varying number of menu choices?

    InventoryController myController;
    Scanner s;

    InventoryView(InventoryController c) {
        myController = c;
        s = new Scanner(System.in);
    }


    public void launchUI(){//This is a text-based UI. Probably a GUI in a real program


        while (true) {
            int userChoice = displayMenuGetUserChoice();
            if (userChoice == QUIT ) {
                break;
            }
            doTask(userChoice);
        }

    }

    private void doTask(int userChoice) {

        switch (userChoice) {

            case 1:  {
                displayAllLaptops();
                displayAllCellphones();
                break;
            }
            case 2: {
                addNewLaptop();
                break;
            }
            case 3 : {
                reassignLaptop();
                break;
            }
            case 4 : {
                retireLaptops();
                break;
            }
            case 5: {
                addNewCellphones();
                break;
            }
            case 6: {
                reassignCellphones();
            }
            case 7: {
                retireCellphones();
            }
            case 8: {
                searchByStaff();
            }
            }
        }


    private void addNewCellphones() {

        //Get data about new laptop from user

        System.out.println("Please enter make of Cell Phone: ");
        String make = s.nextLine();

        System.out.println("Please enter model of Cell Phone: ");
        String model = s.nextLine();

        System.out.println("Please enter name of staff member Cell Phone is assigned to : ");
        String staff = s.nextLine();

        CellPhone c = new CellPhone(make, model, staff);


        String errorMessage = myController.requestAddCellphone(c);

        if (errorMessage == null ) {
            System.out.println("New laptop added to database");
        } else {
            System.out.println("New laptop could not be added to database");
            System.out.println(errorMessage);
        }

    }

    private void addNewLaptop() {

        //Get data about new laptop from user

        System.out.println("Please enter make of laptop (e.g. Toshiba, Sony) : ");
        String make = s.nextLine();

        System.out.println("Please enter make of laptop (e.g. Macbook, X-123) : ");
        String model = s.nextLine();

        System.out.println("Please enter name of staff member laptop is assigned to : ");
        String staff = s.nextLine();

        Laptop l = new Laptop(make, model, staff);


        String errorMessage = myController.requestAddLaptop(l);

        if (errorMessage == null ) {
            System.out.println("New laptop added to database");
        } else {
            System.out.println("New laptop could not be added to database");
            System.out.println(errorMessage);
        }

    }

    //this is where the user can retire laptops that are no longer in service.
    private void retireLaptops(){
        LinkedList<Laptop> laptopList = myController.requestAllLaptops();
        LinkedList<Integer> laptopIds = new LinkedList<Integer>();
        boolean inputVal = false;
        int userChoice = -1;

        //adds laptops to the list of keys so that they can be checked against.
        for (Laptop l: laptopList){
            laptopIds.add(l.id);
        }

        //runs the input validation for the entries.
        while(!inputVal) {
            try {

                //prints out the laptops.
                for (Laptop l: laptopList){
                    System.out.println(l.toString());
                }

                //user selects a laptop here.
                System.out.println("Select a laptop ID or enter '0' to quit: ");
                String userChoiceStr = s.nextLine();
                userChoice = Integer.parseInt(userChoiceStr);

                //if the laptop is one in the list, it is deleted.
                if (laptopIds.contains(userChoice)) {
                    for (Laptop l: laptopList){
                        if(l.id == userChoice){
                            myController.requestDeleteLaptop(l);
                        }
                    }
                    inputVal = true;
                    return;
                }

                //quits.
                else if (userChoice == 0){
                    return;
                }

                //tells the user that they need to try again.
                else{
                    System.out.println("That is not a valid choice.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a number.");
                continue;
            }

        }
    }

    private void retireCellphones(){
        LinkedList<CellPhone> cellphoneList = myController.requestAllCellphones();
        LinkedList<Integer> cellphoneIDs = new LinkedList<Integer>();
        boolean inputVal = false;
        int userChoice = -1;

        //adds laptops to the list of keys so that they can be checked against.
        for (CellPhone c: cellphoneList){
            cellphoneIDs.add(c.id);
        }

        //runs the input validation for the entries.
        while(!inputVal) {
            try {

                //prints out the laptops.
                for (CellPhone c: cellphoneList){
                    System.out.println(c.toString());
                }

                //user selects a laptop here.
                System.out.println("Select a laptop ID or enter '0' to quit: ");
                String userChoiceStr = s.nextLine();
                userChoice = Integer.parseInt(userChoiceStr);

                //if the laptop is one in the list, it is deleted.
                if (cellphoneIDs.contains(userChoice)) {
                    for (CellPhone c: cellphoneList){
                        if(c.id == userChoice){
                            myController.requestDeleteCellphone(c);
                        }
                    }
                    inputVal = true;
                    return;
                }

                //quits.
                else if (userChoice == 0){
                    return;
                }

                //tells the user that they need to try again.
                else{
                    System.out.println("That is not a valid choice.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a number.");
                continue;
            }

        }
    }

    //has the controller request that the database reassign the cellphones.
    private void reassignCellphones() {
        LinkedList<CellPhone> cellphoneList = myController.requestAllCellphones();
        LinkedList<Integer> cellphoneIDs = new LinkedList<Integer>();
        boolean inputVal = false;
        int userChoice;
        for (CellPhone c: cellphoneList){
            cellphoneIDs.add(c.id);
        }

        //input validation to make sure that only appropriate choices are selected.
        while (!inputVal) {
            try {
                //prints the laptops that have been assigned to users.
                for (CellPhone c: cellphoneList){
                    System.out.println(c.toString());
                }

                //user chooses a laptop ID.
                System.out.println("Select a laptop ID or enter '0' to quit: ");
                String userChoiceStr = s.nextLine();
                userChoice = Integer.parseInt(userChoiceStr);

                //this allows the user to put in the new name, and it will then be uploaded to the database.
                if (cellphoneIDs.contains(userChoice)) {
                    System.out.println("Who is the new user of laptop " + userChoiceStr + ": ");
                    String newuser = s.nextLine();
                    for (CellPhone c : cellphoneList) {
                        if (c.id == userChoice) {
                            myController.requestReassignCellphone(c, newuser);
                        }
                    }
                    inputVal = true;
                    return;
                }

                //quits.
                else if (userChoice == 0){
                    return;
                }

                //lets the user know they did wrong.
                else{
                    System.out.println("That is not a valid choice.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a number.");
                continue;
            }
        }
    }


//this takes the laptop id and reassigns it to a different person, after the prompt allows someone to choose who they want.
    private void reassignLaptop() {
        LinkedList<Laptop> laptopLists = myController.requestAllLaptops();
        LinkedList<Integer> laptopIds = new LinkedList<Integer>();
        boolean inputVal = false;
        int userChoice = -1;
        for (Laptop l: laptopLists){
            laptopIds.add(l.id);
        }

        //input validation to make sure that only appropriate choices are selected.
        while (!inputVal) {
            try {
                //prints the laptops that have been assigned to users.
                for (Laptop l: laptopLists){
                    System.out.println(l.toString());
                }

                //user chooses a laptop ID.
                System.out.println("Select a laptop ID or enter '0' to quit: ");
                String userChoiceStr = s.nextLine();
                userChoice = Integer.parseInt(userChoiceStr);

                //this allows the user to put in the new name, and it will then be uploaded to the database.
                if (laptopIds.contains(userChoice)) {
                    System.out.println("Who is the new user of laptop " + userChoiceStr + ": ");
                    String newuser = s.nextLine();
                    for (Laptop l : laptopLists) {
                        if (l.id == userChoice) {
                            myController.requestReassignLaptop(l, newuser);
                        }
                    }
                    inputVal = true;
                    return;
                }

                //quits.
                else if (userChoice == 0){
                    return;
                }

                //lets the user know they did wrong.
                else{
                    System.out.println("That is not a valid choice.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a number.");
                continue;
            }
        }
    }

    //has the controller pull all the laptops for display.
    private void displayAllLaptops() {

        LinkedList<Laptop> allLaptops = myController.requestAllLaptops();
        if (allLaptops == null) {
            System.out.println("Error fetching all laptops from the database");
        } else if (allLaptops.isEmpty()) {
            System.out.println("No laptops found in database");
        } else {
            for (Laptop l : allLaptops) {
                System.out.println(l);   //Call the toString method in Laptop
            }
        }
    }

    private void displayAllCellphones() {

        LinkedList<CellPhone> allCellphones = myController.requestAllCellphones();
        if (allCellphones == null) {
            System.out.println("Error fetching all laptops from the database");
        } else if (allCellphones.isEmpty()) {
            System.out.println("No laptops found in database");
        } else {
            for (CellPhone c : allCellphones) {
                System.out.println(c);   //Call the toString method in Laptop
            }
        }
    }

    //has the controller search by staff member.
    private void searchByStaff() {
        System.out.println("Who would you like to search? ");
        String user = s.nextLine();

        String list = myController.requestStaffSearch(user);
        try{
            if(!list.equals(null)){
                System.out.println(list);
            }
        }
        catch (NullPointerException npe){
            System.out.println("No information found.");
        }
    }


    //displays the choices.
    private int displayMenuGetUserChoice() {
        boolean inputOK = false;
        int userChoice = -1;

        while (!inputOK) {
            System.out.println("1. View all inventory");
            System.out.println("2. Add a new laptop");
            System.out.println("3. Reassign a laptop to another staff member");
            System.out.println("4. Retire a laptop");
            System.out.println("5. Add a new cellphone");
            System.out.println("6. Reassign a cellphone to another staff member");
            System.out.println("7. Retire a cellphone");
            System.out.println("8. Find equipment assigned to a staff member.");
            System.out.println(QUIT + ". Quit program");

            System.out.println();
            System.out.println("Please enter your selection");

            String userChoiceStr = s.nextLine();
            try {
                userChoice = Integer.parseInt(userChoiceStr);
                if (userChoice < 1  ||  userChoice > 9) {
                    System.out.println("Please enter a number between 1 and 8");
                    continue;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a number");
                continue;
            }
            inputOK = true;

        }

        return userChoice;

    }
}