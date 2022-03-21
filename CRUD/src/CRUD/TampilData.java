package CRUD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class TampilData {
    // 1. Method Read / Tampil data
    public static void tampilData() throws IOException {
        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("database.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e) {
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu\n");
            TambahData.tambahData();
            return;
        }
        
        System.out.println("\n| No | Tahun | Penulis                | Penerbit               | Judul Buku");
        System.out.println("===============================================================================");
        
        String data = bufferInput.readLine();
        int nomorData = 0;
        while(data != null) {
            nomorData++;
            StringTokenizer stringToken = new StringTokenizer(data, ",");
            stringToken.nextToken();
            System.out.printf("| %2d ", nomorData);
            System.out.printf("| %4s  ",stringToken.nextToken());
            System.out.printf("| %-20s   ",stringToken.nextToken());
            System.out.printf("| %-20s   ",stringToken.nextToken());
            System.out.printf("| %s   ",stringToken.nextToken());
            System.out.print("\n");

            data = bufferInput.readLine();
        }
        
        System.out.println("===============================================================================");
        fileInput.close();
        bufferInput.close();
    }

}
