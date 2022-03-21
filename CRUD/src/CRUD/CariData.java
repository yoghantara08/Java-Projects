package CRUD;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CariData {
    public static void cariData() throws IOException {
        // membaca database ada atau tidak
        try {
            File file = new File("database.txt");
        } catch (Exception e) {
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu\n");
            TambahData.tambahData();
            return;
        }

        // ambil keyword dari user
        Scanner userInput = new Scanner(System.in);
        System.out.print("Masukkan kata kunci untuk mencari buku: ");
        String cariString = userInput.nextLine();
        
        // check keyword di database
        String[] keywords = cariString.split("\\s+");
        Utility.checkBukuDiDatabase(keywords,true);

    }
}
