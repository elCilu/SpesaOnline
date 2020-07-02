package models;

import enums.Status;

public class AdminModel {
    private final int matriculationNumber;
    private final String name;
    private final String surname;
    private final String role; //potremmo fare anche un Enum dei ruoli..
    private final String username;
    //non mi ricordo come avevamo deciso di gestire il password..

    public AdminModel(int matriculationNumber, String name, String surname, String role, String username){
        this.matriculationNumber = matriculationNumber;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.username = username;
    }

    public int getMatriculationNumber() {
        return matriculationNumber;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

}
