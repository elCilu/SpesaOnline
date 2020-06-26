package models;

import enums.Status;
import java.util.Date;
import java.util.Calendar;

public class StockManModel {

    private final int matriculationNumber;
    private final String name;
    private final String surname;
    //private final String role;  esiste un magazziniere unico che gestisce tutto il magazzino
    private final String username;
    //non mi ricordo come avevamo deciso di gestire il password..

    public StockManModel(int matriculationNumber, String name, String surname, String username){
        this.matriculationNumber = matriculationNumber;
        this.name = name;
        this.surname = surname;
        this.username = username;
    }

    public int getMatriculationNumber(){
        return matriculationNumber;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

}
