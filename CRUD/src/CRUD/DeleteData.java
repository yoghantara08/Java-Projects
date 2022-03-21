package CRUD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DeleteData {
     public static void deleteData() throws IOException {
        // Ambil database
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferInput = new BufferedReader(fileInput);

        // Buat database sementara
        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        // tampilkan data
        TampilData.tampilData();

        // ambil user input untuk mendelete data
        Scanner inputUser = new Scanner(System.in);
        System.out.print("\nMasukkan nomor buku yang akan dihapus: ");
        int deleteNum = inputUser.nextInt();

        // looping untuk membaca tiap data baris dan skip data yg akan di delete
        int dataCounts = 0;
        boolean isFound = false;
        
        String data = bufferInput.readLine();
        while(data != null){
            dataCounts++;
            boolean isDelete = false;

            StringTokenizer stringToken = new StringTokenizer(data, ",");
            // tampilkan data yang ingin dihapus
            if(deleteNum == dataCounts){
                System.out.println("\nData yang ingin anda hapus:");
                System.out.println("--------------------------------");
                System.out.println("Referensi      : " + stringToken.nextToken());
                System.out.println("Tahun          : " + stringToken.nextToken());
                System.out.println("Penulis        : " + stringToken.nextToken());
                System.out.println("Penerbit       : " + stringToken.nextToken());
                System.out.println("Judul          : " + stringToken.nextToken());

                isDelete = Utility.getYesNo("Apakah anda yakin akan menghapus data buku ini");
                isFound = true;
            }

            if(isDelete){
                // skip pindahkan data dari original ke temporary
                System.out.println("Data berhasil dihapus");
            } else {
                // pindahkan data dari original ke temporary
                bufferOutput.write(data);
                bufferOutput.newLine();
            }
            data = bufferInput.readLine();
        }

        if(!isFound){
            System.err.println("Buku tidak ditemukan");
        }

        // menulis data ke file temporary
        bufferOutput.flush();

        // close file
        fileInput.close();
        bufferInput.close();
        fileOutput.close();
        bufferOutput.close();
        System.gc();

        // delete original file
        database.delete();

        // rename file temporary ke database(original)
        tempDB.renameTo(database);

    }
}
