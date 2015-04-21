/** @author Clara MCTC Java Programming Class */

import java.util.LinkedList;

public class InventoryController {
    

    static InventoryModel db ;


    public static void main(String[] args) {

        //Add a shutdown hook.
        //http://hellotojavaworld.blogspot.com/2010/11/runtimeaddshutdownhook.html
        AddShutdownHook closeDBConnection = new AddShutdownHook();
        closeDBConnection.attachShutdownHook();
        //Can put code in here to try to shut down the DB connection in a tidy manner if possible

        try {
            InventoryController controller = new InventoryController();


            db = new InventoryModel(controller);

            boolean setup = db.setupDatabase(true);
            if (setup == false) {
                System.out.println("Error setting up database, see error messages. Clean up database connections.... Quitting program ");

                db.cleanup();

                System.out.println("Quitting program ");

                System.exit(-1);   //Non-zero exit codes indicate errors.
            }

            new InventoryView(controller).launchUI();
        }

        finally {
            if (db != null) {
                db.cleanup();
            }
        }

    }

    //this requests that the database add a cellphone.
    public String requestAddCellphone(CellPhone c) {
        //This message should arrive from the UI. Send a message to the db to request that this laptop is added.
        //Return error message, if any. Return null if transaction was successful.
        boolean success = db.addCellphone(c);
        if (success == true ) {
            return null;   //Null means all was well.
        }
        else {
            return "Unable to add laptop to database";
        }

    }

    //this requests that the database add a laptop.
    public String requestAddLaptop(Laptop l) {
        //This message should arrive from the UI. Send a message to the db to request that this laptop is added.
        //Return error message, if any. Return null if transaction was successful.
        boolean success = db.addLaptop(l);
        if (success == true ) {
            return null;   //Null means all was well.
        }
        else {
            return "Unable to add laptop to database";
        }

    }

    //this requests that the database reassign a specific cellphone.
    public String requestReassignCellphone(CellPhone c, String newuser){
        boolean success = db.reassignCellphone(c, newuser);
        if (success == true){
            return null;
        }
        else{
            return "Could not delete laptop";
        }
    }

    //this has the database reassign the chosen laptop.
    public String requestReassignLaptop(Laptop l, String newuser){
        boolean success = db.reassignLaptop(l, newuser);
        if (success == true){
            return null;
        }
        else{
            return "Could not delete laptop";
        }
    }

    //this deletes the chosen laptop.
    public String requestDeleteLaptop(Laptop l){
        boolean success = db.deleteLaptop(l);
        if (success == true){
            return null;
        }
        else{
            return "Could not delete laptop";
        }
    }

    //this deletes the cellphone that is selected.
    public String requestDeleteCellphone(CellPhone c){
        boolean success = db.deleteCellphone(c);
        if (success == true){
            return null;
        }
        else{
            return "Could not delete laptop";
        }
    }

    //this hs the database send a specific request to look at the use by an individual user.
    public static String requestStaffSearch(String user){
        String items = db.staffSearch(user);
        if (items.equals("")){
            System.out.println("No cellphones or laptops have been assigned to this staff member.");
            return null;
        }
        else {
            return items;
        }
    }

    //this has the database pull all the informatoin about laptops.
    public static LinkedList<Laptop> requestAllLaptops() {

        LinkedList<Laptop> allLaptops = db.displayAllLaptops();
        if (allLaptops == null ) {
            System.out.println("Controller detected error in fetching laptops from database");
            return null;   //Null means error. View can deal with how to display error to user.
        }
        else {
            return allLaptops;
        }
    }

    //this has the database pull all the information about cellphones.
    public static LinkedList<CellPhone> requestAllCellphones() {

        LinkedList<CellPhone> cellPhones = db.displayAllCellphones();
        if (cellPhones == null ) {
            System.out.println("Controller detected error in fetching laptops from database");
            return null;   //Null means error. View can deal with how to display error to user.
        }
        else {
            return cellPhones;
        }
    }

}

class AddShutdownHook {
    public void attachShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Shutdown hook: program closed, attempting to shut database connection");
                //Unfortunately this doesn't seem to be called when a program is restarted in eclipse.
                //Avoid restarting your programs. If you do, and you get an existing connection error you can either
                // 1. restart eclipse - Menu > Restart
                // 2. Delete your database folder. In this project it's a folder called laptopinventoryDB (or similar) in the root directory of your project.
                InventoryController.db.cleanup();
            }
        });
    }
}
