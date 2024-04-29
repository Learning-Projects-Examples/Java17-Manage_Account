package org.example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User implements Comparable<User> {
    private String idUser;
    private String name;
    private String surname;
    private String address;
    private Set<String> emails;

    public User(String idUser, String name, String surname, String address) {
        this.idUser = idUser;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.emails = new HashSet<String>();
    }

    public User(String idUser, Set<String> emails) {
        this.idUser = idUser;
        this.emails = new HashSet<String>(emails);
    }

    public void addEmail(String email) {
        emails.add(email);
    }

    public String[] getEmails() {
        return emails.isEmpty() ? null : emails.toArray(new String[0]);
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(idUser, user.idUser) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(address, user.address) && Objects.equals(emails, user.emails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, name, surname, address, emails);
    }

    @Override
    public int compareTo(User other) {
        int nameResult = this.name.compareTo(other.name);
        if (nameResult != 0) {
            return nameResult;
        }

        int surnameResult = this.surname.compareTo(other.surname);
        if (surnameResult != 0) {
            return surnameResult;
        }

        int addressResult = this.address.compareTo(other.address);
        if (addressResult != 0) {
            return addressResult;
        }


        String[] thisEmails = this.getEmails();
        String[] otherEmails = other.getEmails();
        Arrays.sort(thisEmails);
        Arrays.sort(otherEmails);
        return Arrays.compare(thisEmails, otherEmails);
    }

}


