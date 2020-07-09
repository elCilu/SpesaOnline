package models;

public class StockManModel {

    private final int matriculationNumber;
    private final String name;
    private final String surname;
    private final String username;

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
