package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    static User user1;
    static User user2;
    static User user3;
    static User user4;
    static User user5;
    static User user6;

    @BeforeAll
    static void beforeAll() {
        user1 = new User("A098", "Sara", "Obialero", "Via Susa 43");
        user2 = new User("A078", "Sara", "Rosso", "Via Susa 43");
        user3 = new User("A058", "Sara", "Obialero", "Via Susa 43");
        user4 = new User("A088", "Anna", "Rossi", "Other");
        user5 = new User("A098", "Sara", "Obialero", "Via Susa 43");
        user6 = new User("A008", Collections.singleton("sara.obia99@gmail.com"));
    }

    @Test
    void addMail() {
        String newEmail = "anna.rossi@gmail.com";
        user4.addEmail(newEmail);
        user6.addEmail(newEmail);
        String[] emailsUser4 = user4.getEmails();
        String[] emailsUser6 = user6.getEmails();
        boolean email1Found = false;
        for (String e : emailsUser4) {
            if (e.equals(newEmail)) {
                email1Found = true;
                break;
            }
        }
        boolean email2Found = false;
        for (String e : emailsUser6) {
            if (e.equals(newEmail)) {
                email2Found = true;
                break;
            }
        }
        assertTrue(email1Found);
        assertTrue(email2Found);
    }

    @Test
    void testEquals() {
        assertEquals(user1, user5);
        assertNotEquals(user1, user2);
    }

    @Test
    void testHashCode() {
        assertEquals(user1.hashCode(), user5.hashCode());
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void compare() {
        assertTrue(user1.compareTo(user2) < 0);
        assertTrue(user1.compareTo(user4) > 0);
        assertEquals(0, user1.compareTo(user3));
    }
}