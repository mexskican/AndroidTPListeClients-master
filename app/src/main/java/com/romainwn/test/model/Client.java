package com.romainwn.test.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Client {

    public enum Sexe {
        HOMME,FEMME;
    }

    private static ArrayList<Client> clients = new ArrayList<>();



    protected String lastname;
    protected String firstname;
    protected String email;
    protected Date birthDate;
    protected String level;
    protected Sexe sexe;
    protected boolean active;

    public Client(String lastname, String firstname, String email, Date birthDate, String level, Sexe sexe, boolean active) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.birthDate = birthDate;
        this.level = level;
        this.sexe = sexe;
        this.active = active;
    }

    public Client() {
    }

    public static List<Client> getClients() {return clients;}

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {return birthDate;}

    public void setbirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
