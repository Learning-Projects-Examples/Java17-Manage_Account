package org.example;

import java.util.Optional;

public interface Account<T> {

    boolean addUser(String idUser, String name, String surname, String address);

    boolean addEmail(String idUser, String email);

    boolean existsUser(String idUser);

    boolean userHasMail(String idUser);

    Optional<T> user(String idUser);

    String[] userMails(String idUser);

    T[] users(EnumSortType sortType);

    T firstUser();

    T lastUser();

    T[] firstUsers(int usersNum);

    T[] lastUsers(int usersNum);

    String[] discardedRows();

    String[] allEmails();
}
