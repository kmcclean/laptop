/**
 * Created by Kevin on 4/19/2015.
 */
public class CellPhone {

    protected String make;
    protected String model;
    protected String staff;
    protected int id;
    protected final int NOID = -1;   //Value to represent no ID known

    CellPhone(String make, String model, String staff) {
        this.make = make;
        this.model = model;
        this.staff = staff;
        this.id = NOID; //flag for no ID known
    }

    CellPhone(int id, String make, String model, String staff) {
        this(make, model, staff);
        this.id = id;

    }


    protected String getCellPhoneMake() {
        return make;
    }


    protected String getCellPhoneModel() {
        return model;
    }


    protected String getCellPhoneStaff() {
        return staff;
    }

    public String toString() {

        String idData = (this.id == this.NOID) ? "<No ID assigned>" : Integer.toString(this.id);

        String laptopData = "Cellphone ID: " + idData + " Make, Model: " + this.make + " " + this.model + " Assigned to " + this.staff;
        return laptopData;

    }
}