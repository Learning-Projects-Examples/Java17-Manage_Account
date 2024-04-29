package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ManageAccount implements Account<User> {
    private Map<String, User> tmUsers;
    private User[] arUsers;
    private List<String> lsUsersWrong;

    public ManageAccount(String fileName) throws FileIsEmpty, FileNotFoundException {
        loadMap(fileName);
    }

    private void loadMap(String fileName) throws FileIsEmpty, FileNotFoundException {
        String[] lines = readFile(fileName);
        loadMapFromLines(lines);
    }

    @Override
    public boolean addUser(String idUser, String name, String surname, String address) {
        return !existsUser(idUser)
                && (tmUsers.put(idUser, new User(idUser, name, surname, address)) == null);
    }

    @Override
    public boolean addEmail(String idUser, String email) {
        Optional.ofNullable(tmUsers.get(idUser))
                .map(user -> {
                    user.addEmail(email);
                    return true;
                })
                .orElseGet(() -> {
                    User newUser = new User(idUser, new HashSet<>(Collections.singletonList(email)));
                    tmUsers.put(idUser, newUser);
                    return true;
                });
        return true;
    }


    @Override
    public boolean existsUser(String idUser) {
        return tmUsers.containsKey(idUser);
    }

    @Override
    public boolean userHasMail(String idUser) {
        return existsUser(idUser) && tmUsers.get(idUser).getEmails() != null && tmUsers.get(idUser).getEmails().length != 0;
    }

    @Override
    public Optional<User> user(String idUser) {
        return existsUser(idUser)
                ? Optional.ofNullable(tmUsers.get(idUser))
                : Optional.empty();
    }

    @Override
    public String[] userMails(String idUser) {
        return existsUser(idUser)
                ? tmUsers.get(idUser).getEmails()
                : new String[0];
    }

    @Override
    public User[] users(EnumSortType sortType) {
        arUsers = tmUsers.values().toArray(new User[0]);
        if (sortType == EnumSortType.SORT_ASCENDING) {
            Arrays.sort(arUsers);
        }
        if (sortType == EnumSortType.SORT_DESCENDING) {
            Arrays.sort(arUsers, Collections.reverseOrder());
        }
        return arUsers;
    }

    @Override
    public User firstUser() {
        return (arUsers = users(EnumSortType.SORT_ASCENDING)) != null
                && arUsers.length > 0
                ? arUsers[0]
                : null;
    }

    @Override
    public User lastUser() {
        return (arUsers = users(EnumSortType.SORT_DESCENDING)) != null
                && arUsers.length > 0
                ? arUsers[0]
                : null;
    }

    @Override
    public User[] firstUsers(int usersNum) {
        arUsers = users(EnumSortType.SORT_ASCENDING);
        return usersNum >= arUsers.length
               ? arUsers.clone()
               : Arrays.copyOfRange(arUsers, 0, usersNum);
    }

    @Override
    public User[] lastUsers(int usersNum) {
        arUsers = users(EnumSortType.SORT_ASCENDING);
        return usersNum >= arUsers.length
                ? arUsers.clone()
                : Arrays.copyOfRange(arUsers, arUsers.length - usersNum, arUsers.length);
    }

    @Override
    public String[] discardedRows() {
        return lsUsersWrong.toArray(lsUsersWrong.toArray(new String[0]));
    }

    @Override
    public String[] allEmails() {
        List<String> allEmailsList = new ArrayList<>();
        for (User user : tmUsers.values()) {
            String[] userMails = user.getEmails();
            if (userMails != null) {
                allEmailsList.addAll(Arrays.asList(userMails));
            }
        }
        return allEmailsList.toArray(new String[0]);
    }

    private String[] readFile(String fileName) throws FileIsEmpty, FileNotFoundException {
        List<String> lines = new ArrayList<>();
        File file = new File(fileName);
        Scanner sc = new Scanner(file);

        if (!sc.hasNext()) {
            throw new FileIsEmpty("File is empty: " + fileName);
        }

        while (sc.hasNext()) {
            lines.add(sc.nextLine());
        }

        sc.close();

        return lines.toArray(new String[0]);
    }

    private void loadMapFromLines(String[] lines) {
        tmUsers = new TreeMap<>();
        lsUsersWrong = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(" ");

            if (parts.length >= 4) {
                String idUser = parts[0];
                String name = parts[1];
                String surname = parts[2];
                String address = String.join(" ", Arrays.copyOfRange(parts, 3, parts.length));
                addUser(idUser, name, surname, address);
            } else if (parts.length == 2 && mailChecker(parts[1])) {
                String idUser = parts[0];
                String email = parts[1];
                addEmail(idUser, email);
            } else {
                if (lsUsersWrong != null) {
                    lsUsersWrong.add(line);
                }
            }
        }
    }

    private boolean mailChecker(String email) {
        String regex = "^[a-zA-Z0-9.!#$%&'+/=?^_`{|}~-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-]+$";
        return email.matches(regex);
    }

    public void clearTmUsers() {
        tmUsers.clear();
    }
}
