package org.example;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String filename = "UsersList.txt";
        File file = new File(filename);
        String absolutePath = file.getAbsolutePath();
        System.out.println("Percorso del file: " + absolutePath);
    }
}
