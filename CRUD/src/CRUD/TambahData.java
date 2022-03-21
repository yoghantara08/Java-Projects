package CRUD;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TambahData {
    public static void tambahData() throws IOException {

        FileWriter fileOutput = new FileWriter("database.txt", true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        // Mengambil input dari user
        Scanner userInput = new Scanner(System.in);
        String penulis, judul, penerbit, tahun;

        System.out.print("Masukkan nama penulis: ");
        penulis = userInput.nextLine();
        System.out.print("Masukkan judul buku: ");
        judul = userInput.nextLine();
        System.out.print("Masukkan nama penerbit: ");
        penerbit = userInput.nextLine();
        System.out.print("Masukkan tahun terbit, format=(YYYY): ");
        tahun = Utility.ambilTahun();
    
        // cek buku di database
        String[] keywords = {tahun+","+penulis+","+penerbit+","+judul};

        boolean isExist = Utility.checkBukuDiDatabase(keywords,false);
        
        // menulis buku di database
        if(!isExist){
            long nomorEntry = Utility.ambilEntryPerTahun(penulis, tahun) + 1;

            String penulisTanpaSpasi = penulis.replaceAll("\\s+", "");
            String primaryKey = penulisTanpaSpasi+"_"+tahun+"_"+nomorEntry;
            System.out.println("\nData yang akan anda masukkan adalah");
            System.out.println("----------------------------------------");
            System.out.println("Primary key   : " + primaryKey);
            System.out.println("Tahun terbit  : " + tahun);
            System.out.println("Penulis       : " + penulis);
            System.out.println("Judul         : " + judul);
            System.out.println("Penerbit      : " + penerbit);

            boolean isTambah = Utility.getYesNo("Apakah anda ingin menambah data");

            if(isTambah){
                bufferOutput.write(primaryKey + "," + tahun + ","+ penulis+ ","+penerbit+ ","+judul);
                bufferOutput.newLine();
                bufferOutput.flush();
            }


        } else {
            System.out.println("Buku yang anda akan masukkan sudah ada");
            Utility.checkBukuDiDatabase(keywords,true);
        }

        bufferOutput.close();
    }

}
