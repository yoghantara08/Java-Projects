package Main;

import java.io.IOException;
import java.util.Scanner;
import CRUD.TampilData;
import CRUD.CariData;
import CRUD.TambahData;
import CRUD.UpdateData;
import CRUD.DeleteData;
import CRUD.Utility;

public class Main {
    public static void main(String[] args) throws IOException{

        Scanner userInput = new Scanner(System.in);
        String pilihanUser;
        boolean isLanjutkan = true;

        while(isLanjutkan){
            Utility.clearScreen();
            System.out.println("DATABASE PERPUSTAKAAN");
            System.out.println("1. Lihat seluruh buku");
            System.out.println("2. Cari data buku");
            System.out.println("3. Tambah data buku");
            System.out.println("4. Ubah data buku");
            System.out.println("5. Hapus data buku");

            System.out.print("\nPilihan Anda: ");
            pilihanUser = userInput.next();

            switch(pilihanUser) {
                case "1":
                    System.out.println("\nLIST SELURUH BUKU");
                    TampilData.tampilData();
                    break;
                case "2":
                    System.out.println("\nCARI BUKU");
                    CariData.cariData();
                    break;
                case "3":
                    System.out.println("\nTAMBAH DATA BUKU");
                    TambahData.tambahData();
                    TampilData.tampilData();
                    break;
                case "4":
                    System.out.println("\nUBAH DATA BUKU");
                    UpdateData.updateData();
                    break;
                case "5":
                    System.out.println("\nHAPUS DATA BUKU");
                    DeleteData.deleteData();
                    break;
                default:
                    System.err.println("\nInput Anda Tidak Valid\nSilahkan Memilih Angka 1 sampai 5.");
                    break;
            }
            isLanjutkan = Utility.getYesNo("Apakah anda ingin melanjutkan");

            // Tutup scanner jika user menginputkan y/n dengan benar
            if(pilihanUser.equalsIgnoreCase("y") && pilihanUser.equalsIgnoreCase("n")) userInput.close();
        }
    }
}
    
