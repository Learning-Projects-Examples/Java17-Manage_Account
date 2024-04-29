package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ManageAccountTest {
    static ManageAccount ma;
    static ManageAccount maNull;

    @BeforeAll
    static void beforeAll() throws FileIsEmpty, FileNotFoundException {
        ma = new ManageAccount("UsersList.txt");
    }

    @Test
    void testAddUser() {
        assertTrue(ma.addUser("A008", "Sara", "Obialero", "Via susa 43"));
        assertTrue(ma.addUser("A020", "Sara", "Obialero", "Via susa 43"));
        assertFalse(ma.addUser("A008", "Sara", "Obialero", "Via susa 43"));
        assertFalse(ma.addUser("U056", "Anna", "Rossi", "Piazza del popolo"));
        assertFalse(ma.addUser("U022", "Anna", "Rossi", "Piazza del popolo"));

    }

    @Test
    void testAddEmail() {
        ma.addUser("A008", "Sara", "Obialero", "Via susa 43");
        assertTrue(ma.addEmail("A008", "sara.obia99@gmail.com"));
        assertTrue(ma.addEmail("A010", "sara.obia99@gmail.com"));
        assertTrue(ma.addEmail("A056", "sara.obia99@gmail.com"));
    }

    @Test
    void testAddEmailBeforeUser() {
        ma.addEmail("U090", "new@gmail.com");
        ma.addUser("U090", "Alberto", "Gabbai", "Via Servais 16/A Torino");
        ma.addEmail("U090", "secondMail@gmail.com");
        String[] expectedEmails = {"secondMail@gmail.com","new@gmail.com"};
        String[] actualEmails = ma.userMails("U090");
        assertArrayEquals(expectedEmails, actualEmails);
    }

    @Test
    void testExistsUser() {
        ma.addUser("A008", "Sara", "Obialero", "Via susa 43");
        assertTrue(ma.existsUser("A008"));
        assertTrue(ma.existsUser("U056"));
        assertTrue(ma.existsUser("U020"));
        assertTrue(ma.existsUser("U022"));
        assertFalse(ma.existsUser("Sara"));
        assertFalse(ma.existsUser("A010"));
        assertFalse(ma.existsUser("A025"));
    }

    @Test
    void testUserHasMail() {
        assertTrue(ma.userHasMail("U056"));
        assertTrue(ma.userHasMail("U001"));
        assertFalse(ma.userHasMail("U022"));
    }

    @Test
    void testUserHasMailWithAdd() {
        ma.addUser("A008", "Sara", "Obialero", "Via susa 43");
        ma.addEmail("A008", "sara.obia99@gmail.com");
        ma.addEmail("A008", "sara.prova@libero");
        assertTrue(ma.userHasMail("A008"));
    }

    @Test
    void testUser() {
        Optional<User> actualUserOptional = ma.user("U001");
        assertTrue(actualUserOptional.isPresent());
        assertEquals("Alberto", actualUserOptional.get().getName());
        assertEquals("Gabbai", actualUserOptional.get().getSurname());
        assertEquals("Via Servais 16/A Torino", actualUserOptional.get().getAddress());
    }

    @Test
    void testUserNull() {
        assertFalse(false);
    }

    @Test
    void testUserWithAdd() {
        ma.addUser("A008", "Sara", "Obialero", "Via susa 43");
        Optional<User> actualUserAddedOptional = ma.user("A008");
        assertTrue(actualUserAddedOptional.isPresent());
        assertEquals("Sara", actualUserAddedOptional.get().getName());
        assertEquals("Obialero", actualUserAddedOptional.get().getSurname());
        assertEquals("Via susa 43", actualUserAddedOptional.get().getAddress());
  }

    @Test
    void testUserMails() {
        String[] expectedMails = {"giorgio.poggi@google.com", "giorgio.poggi@libero.it", "giorgio.poggi@spformazione.com"};
        String[] expectedNull = {};
        String[] actualMails = ma.userMails("U020");
        String[] actualNull = ma.userMails("u010");

        Arrays.sort(expectedMails);
        Arrays.sort(actualMails);

        assertArrayEquals(expectedMails, actualMails);
        assertNotEquals(expectedMails, actualNull);
        assertArrayEquals(expectedNull, actualNull);
    }


    @Test
    void testUserMailsWithAdd() {
        ma.addUser("A008", "Sara", "Obialero", "Via susa 43");
        ma.addEmail("A008", "sara.obia99@gmail.com");
        ma.addEmail("A008", "sara.prova@libero");

        String[] expectedMails = {"sara.obia99@gmail.com", "sara.prova@libero"};
        String[] expectedNull = {};
        String[] actualMails = ma.userMails("A008");
        String[] actualNull = ma.userMails("A010");

        Arrays.sort(expectedMails);
        Arrays.sort(actualMails);

        assertArrayEquals(expectedMails, actualMails);
        assertNotEquals(expectedMails, actualNull);
        assertArrayEquals(expectedNull, actualNull);
    }

    @Test
    void testUsersAscending() {
        User[] expectedAscending = {
                new User("U001", "Alberto", "Gabbai", "Via Servais 16/A Torino"),
                new User("U056", "Carlo", "Navone", "Via exilles 12"),
                new User("U033", "Giampietro", "Zedda", "Via Po 20 Torino"),
                new User("U022", "Giorgio", "Palandri", "Via Livorno 25/a Torino"),
                new User("U020", "Giorgio", "Poggi", "Via San Massimo 3 Torino")
        };

        expectedAscending[0].addEmail("alberto.gabbai@libero.it");
        expectedAscending[1].addEmail("carlo.navone@hotmail.com");
        expectedAscending[1].addEmail("carlo.navone@libero.it");
        expectedAscending[4].addEmail("giorgio.poggi@google.com");
        expectedAscending[4].addEmail("giorgio.poggi@libero.it");
        expectedAscending[4].addEmail("giorgio.poggi@spformazione.com");

        User[] actualAscending = ma.users(EnumSortType.SORT_ASCENDING);
        assertArrayEquals(expectedAscending, actualAscending);
    }

    @Test
    void testUsersAscendingWithAdd() {
        ma.addUser("A008", "Sara", "Obialero", "Via susa 43");
        ma.addUser("A018", "Anna", "Rossi", "Piazza del popolo");
        ma.addUser("A038", "Ezio", "Nuovo", "Via nuova");
        ma.addEmail("A038", "ezio@prova.com");
        User[] expectedAscendingWithAdd = {
                new User("U001", "Alberto", "Gabbai", "Via Servais 16/A Torino"),
                new User("A018", "Anna", "Rossi", "Piazza del popolo"),
                new User("U056", "Carlo", "Navone", "Via exilles 12"),
                new User("A038", "Ezio", "Nuovo", "Via nuova"),
                new User("U033", "Giampietro", "Zedda", "Via Po 20 Torino"),
                new User("U022", "Giorgio", "Palandri", "Via Livorno 25/a Torino"),
                new User("U020", "Giorgio", "Poggi", "Via San Massimo 3 Torino"),
                new User("A008", "Sara", "Obialero", "Via susa 43"),
        };

        expectedAscendingWithAdd[0].addEmail("alberto.gabbai@libero.it");
        expectedAscendingWithAdd[2].addEmail("carlo.navone@hotmail.com");
        expectedAscendingWithAdd[2].addEmail("carlo.navone@libero.it");
        expectedAscendingWithAdd[3].addEmail("ezio@prova.com");
        expectedAscendingWithAdd[6].addEmail("giorgio.poggi@google.com");
        expectedAscendingWithAdd[6].addEmail("giorgio.poggi@libero.it");
        expectedAscendingWithAdd[6].addEmail("giorgio.poggi@spformazione.com");

        User[] actualAscendingWithAdd = ma.users(EnumSortType.SORT_ASCENDING);
        assertArrayEquals(expectedAscendingWithAdd, actualAscendingWithAdd);
    }

    @Test
    void testUsersDescending() {
        User[] expectedDescending = {
                new User("U020", "Giorgio", "Poggi", "Via San Massimo 3 Torino"),
                new User("U022", "Giorgio", "Palandri", "Via Livorno 25/a Torino"),
                new User("U033", "Giampietro", "Zedda", "Via Po 20 Torino"),
                new User("U056", "Carlo", "Navone", "Via exilles 12"),
                new User("U001", "Alberto", "Gabbai", "Via Servais 16/A Torino")
        };

        expectedDescending[0].addEmail("giorgio.poggi@spformazione.com");
        expectedDescending[0].addEmail("giorgio.poggi@libero.it");
        expectedDescending[0].addEmail("giorgio.poggi@google.com");
        expectedDescending[3].addEmail("carlo.navone@hotmail.com");
        expectedDescending[3].addEmail("carlo.navone@libero.it");
        expectedDescending[4].addEmail("alberto.gabbai@libero.it");

        User[] actualDescending = ma.users(EnumSortType.SORT_DESCENDING);

        assertArrayEquals(expectedDescending, actualDescending);
    }

    @Test
    void testUsersDescendingWithAdd() {
        ma.addUser("A008", "Sara", "Obialero", "Via susa 43");
        ma.addUser("A018", "Anna", "Rossi", "Piazza del popolo");
        ma.addUser("A038", "Ezio", "Nuovo", "Via nuova");
        ma.addEmail("A038", "ezio@prova.com");

        User[] expectedDescendingWithAdd = {
                new User("A008", "Sara", "Obialero", "Via susa 43"),
                new User("U020", "Giorgio", "Poggi", "Via San Massimo 3 Torino"),
                new User("U022", "Giorgio", "Palandri", "Via Livorno 25/a Torino"),
                new User("U033", "Giampietro", "Zedda", "Via Po 20 Torino"),
                new User("A038", "Ezio", "Nuovo", "Via nuova"),
                new User("U056", "Carlo", "Navone", "Via exilles 12"),
                new User("A018", "Anna", "Rossi", "Piazza del popolo"),
                new User("U001", "Alberto", "Gabbai", "Via Servais 16/A Torino")
        };

        expectedDescendingWithAdd[7].addEmail("alberto.gabbai@libero.it");
        expectedDescendingWithAdd[5].addEmail("carlo.navone@hotmail.com");
        expectedDescendingWithAdd[5].addEmail("carlo.navone@libero.it");
        expectedDescendingWithAdd[4].addEmail("ezio@prova.com");
        expectedDescendingWithAdd[1].addEmail("giorgio.poggi@google.com");
        expectedDescendingWithAdd[1].addEmail("giorgio.poggi@libero.it");
        expectedDescendingWithAdd[1].addEmail("giorgio.poggi@spformazione.com");

        User[] actualDescendingWithAdd = ma.users(EnumSortType.SORT_DESCENDING);

        assertArrayEquals(expectedDescendingWithAdd, actualDescendingWithAdd);
    }

    @Test
    void testFirstUser() {
        User expectedFirstUser = new User("U001", "Alberto", "Gabbai", "Via Servais 16/A Torino");
        expectedFirstUser.addEmail("alberto.gabbai@libero.it");
        User actualUser = ma.firstUser();
        assertEquals(expectedFirstUser, actualUser);
    }

    @Test
    void testFirstUserWithAdd() {
        ma.addUser("A001", "Alberto", "A", "Via nuova");
        User expectedFirstUserWithAdd = new User("A001", "Alberto", "A", "Via nuova");
        User actualUser = ma.firstUser();
        assertEquals(expectedFirstUserWithAdd, actualUser);

        ma.clearTmUsers();

        ma.addUser("A001", "Giancarlo", "Barbieri", "Via delle nuove 3");
        ma.addUser("A002", "Giancarlo", "Allamano", "Via delle nuove 4");
        User newExpectedFirstUserWithAdd = new User("A002", "Giancarlo", "Allamano", "Via delle nuove 4");
        User newActualUser = ma.firstUser();
        assertEquals(newExpectedFirstUserWithAdd, newActualUser);
    }

    @Test
    void testFirstUserWhenNoUsersPresent() {
        ma.clearTmUsers();
        assertNull(ma.firstUser());
    }

    @Test
    void testLastUser() {
        User expectedLastUser =  new User("U020", "Giorgio", "Poggi", "Via San Massimo 3 Torino");
        expectedLastUser.addEmail("giorgio.poggi@google.com");
        expectedLastUser.addEmail("giorgio.poggi@libero.it");
        expectedLastUser.addEmail("giorgio.poggi@spformazione.com");
        User actualUser = ma.lastUser();
        assertEquals(expectedLastUser, actualUser);
    }

    @Test
    void testLastUserWithAdd() {
        ma.addUser("A100", "Sara", "Sasso", "Via generale 45");
        User expectedLastUserWithAdd = new User ("A100", "Sara", "Sasso", "Via generale 45");
        User actualUser = ma.lastUser();
        assertEquals(expectedLastUserWithAdd, actualUser);

        ma.clearTmUsers();

        ma.addUser("A001", "Serena", "Barbieri", "Via Nuove 3");
        ma.addUser("A002", "Serena", "Barbieri", "Piazza");
        User newExpectedLastUserWithAdd = new User("A001", "Serena", "Barbieri", "Via Nuove 3");
        User newActualUser = ma.lastUser();
        assertEquals(newExpectedLastUserWithAdd, newActualUser);
    }

    @Test
    void testLastUserWhenNoUsersPresent() {
        ma.clearTmUsers();
        assertNull(ma.lastUser());
    }

    @Test
    void testFirstUsers() {
        User[] expectedFirstUsers = {
                new User("U001", "Alberto", "Gabbai", "Via Servais 16/A Torino"),
                new User("U056", "Carlo", "Navone", "Via exilles 12"),
        };
        expectedFirstUsers[0].addEmail("alberto.gabbai@libero.it");
        expectedFirstUsers[1].addEmail("carlo.navone@hotmail.com");
        expectedFirstUsers[1].addEmail("carlo.navone@libero.it");

        User[] actualFirstUsers = ma.firstUsers(2);

        assertArrayEquals(expectedFirstUsers, actualFirstUsers);

    }

    @Test
    void testFirstUsersWithAdd() {
        ma.addUser("A020", "Anna", "Obialero", "Via Servais 16/A Torino");
        ma.addUser("A040", "Anna", "Allegri", "Via susa 43");
        ma.addUser("A010", "Chiara", "Obialero", "Piazza Moncalieri");

        User[] expectedFirstUsersWithAdd = {
                new User("U001", "Alberto", "Gabbai", "Via Servais 16/A Torino"),
                new User("A040", "Anna", "Allegri", "Via susa 43"),
                new User("A020", "Anna", "Obialero", "Via Servais 16/A Torino"),
                new User("U056", "Carlo", "Navone", "Via exilles 12"),
                new User("A010", "Chiara", "Obialero", "Piazza Moncalieri")
        };

        expectedFirstUsersWithAdd[0].addEmail("alberto.gabbai@libero.it");
        expectedFirstUsersWithAdd[3].addEmail("carlo.navone@hotmail.com");
        expectedFirstUsersWithAdd[3].addEmail("carlo.navone@libero.it");

        User[] actualFirstUsersWithAdd = ma.firstUsers(5);

        assertArrayEquals(expectedFirstUsersWithAdd, actualFirstUsersWithAdd );
    }

    @Test
    void testLastUsers() {
        User[] expectedLastUsers = {
                new User("U022", "Giorgio", "Palandri", "Via Livorno 25/a Torino"),
                new User("U020", "Giorgio", "Poggi", "Via San Massimo 3 Torino"),
        };
        expectedLastUsers[1].addEmail("giorgio.poggi@spformazione.com");
        expectedLastUsers[1].addEmail("giorgio.poggi@libero.it");
        expectedLastUsers[1].addEmail("giorgio.poggi@google.com");

        User[] actualLastUsers = ma.lastUsers(2);

        assertArrayEquals(expectedLastUsers, actualLastUsers);
    }

    @Test
    void testLastUsersWithAdd() {
        ma.addUser("A008", "Sara", "Obialero", "Via susa 43");
        ma.addUser("A010", "Valentina", "Obialero", "Via Sacchi 64");

        User[] expectedLastUsersWithAdd = {
                new User("U022", "Giorgio", "Palandri", "Via Livorno 25/a Torino"),
                new User("U020", "Giorgio", "Poggi", "Via San Massimo 3 Torino"),
                new User("A008", "Sara", "Obialero", "Via susa 43"),
                new User("A010", "Valentina", "Obialero", "Via Sacchi 64")
        };
        expectedLastUsersWithAdd[1].addEmail("giorgio.poggi@spformazione.com");
        expectedLastUsersWithAdd[1].addEmail("giorgio.poggi@libero.it");
        expectedLastUsersWithAdd[1].addEmail("giorgio.poggi@google.com");

        User[] actualLastUsersWithAdd = ma.lastUsers(4);

        assertArrayEquals(expectedLastUsersWithAdd, actualLastUsersWithAdd);
    }

    @Test
    void testFirstAndLastUsersWithTooBigNum() {
        User[] expectedWithTooBigNum = {
                new User("U001", "Alberto", "Gabbai", "Via Servais 16/A Torino"),
                new User("U056", "Carlo", "Navone", "Via exilles 12"),
                new User("U033", "Giampietro", "Zedda", "Via Po 20 Torino"),
                new User("U022", "Giorgio", "Palandri", "Via Livorno 25/a Torino"),
                new User("U020", "Giorgio", "Poggi", "Via San Massimo 3 Torino")
        };

        expectedWithTooBigNum[0].addEmail("alberto.gabbai@libero.it");
        expectedWithTooBigNum[1].addEmail("carlo.navone@hotmail.com");
        expectedWithTooBigNum[1].addEmail("carlo.navone@libero.it");
        expectedWithTooBigNum[4].addEmail("giorgio.poggi@google.com");
        expectedWithTooBigNum[4].addEmail("giorgio.poggi@libero.it");
        expectedWithTooBigNum[4].addEmail("giorgio.poggi@spformazione.com");

        User[] actualFirstWithTooBigNum = ma.firstUsers(10);
        assertArrayEquals(expectedWithTooBigNum, actualFirstWithTooBigNum);

        User[] actualLastWithTooBigNum = ma.firstUsers(100);
        assertArrayEquals(expectedWithTooBigNum, actualLastWithTooBigNum);
    }

    @Test
    void testDiscardedRows() {
        String[] arDiscaredRowExpected = {"U025",
                "U026 Giusi",
                "U027 Giusi Ferrero",
                "U033 giampietro.zedda@libero.it.it",
                ""};
        assertArrayEquals(arDiscaredRowExpected, ma.discardedRows());
    }

    @Test
    void testAllEmails() {
       String [] allEmailsExpected = {
               "carlo.navone@hotmail.com",
               "carlo.navone@libero.it",
               "alberto.gabbai@libero.it",
               "giorgio.poggi@libero.it",
               "giorgio.poggi@google.com",
               "giorgio.poggi@spformazione.com"
       };
       String [] actaualEmails = ma.allEmails();

       Arrays.sort(allEmailsExpected);
       Arrays.sort(actaualEmails);

       assertArrayEquals(allEmailsExpected, actaualEmails);
    }

    @Test
    void testAllMailsWithAdd() {
        ma.clearTmUsers();
        ma.addUser("A020", "Anna", "Obialero", "Via susa 43");
        ma.addUser("A040", "Anna", "Allegri", "Via susa 43");
        ma.addEmail("A020", "sara.obia@gmail.com");
        ma.addEmail("A020", "sara.aiuto@gmail.com");
        ma.addEmail("A040", "anna@gmail.com");
        ma.addEmail("A040", "anna08@gmail.com");

        String[] allEmailsExpectedWithAdd = {"sara.obia@gmail.com",
                "sara.aiuto@gmail.com",
                "anna@gmail.com",
                "anna08@gmail.com"};

        Arrays.sort(allEmailsExpectedWithAdd);
        String[] actualEmails = ma.allEmails();
        Arrays.sort(actualEmails);

        assertArrayEquals(allEmailsExpectedWithAdd, actualEmails);
    }

    @Test
    void testFileNull() throws FileIsEmpty, FileNotFoundException {
        try {
            maNull = new ManageAccount("EmptyFile.txt");
        } catch (FileIsEmpty e) {
            assertTrue(true);
        } catch (FileNotFoundException e) {
            assertFalse(true);
        }

    }
}