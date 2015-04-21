/** @author Clara MCTC Java Programming Class */


public class Laptop {


    protected String make;
    protected String model;
    protected String staff;
    protected int id;
    protected final int NOID = -1;   //Value to represent no ID known

    Laptop (String make, String model, String staff) {
        this.make = make;
        this.model = model;
        this.staff = staff;
        this.id = NOID ; //flag for no ID known
    }

    Laptop (int id, String make, String model, String staff) {
        this(make, model, staff);
        this.id = id;

    }


    protected String getLapTopMake() {
        return make;
    }


    protected String getLapTopModel() {
        return model;
    }


    protected String getLapTopStaff() {
        return staff;
    }

    public String toString() {

        String idData = (this.id == this.NOID) ? "<No ID assigned>" : Integer.toString(this.id) ;

        String laptopData = "Laptop ID: " +  idData + " Make, Model: " + this.make + " " + this.model + " Assigned to " + this.staff;
        return laptopData;

    }

}
