import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException{

        Scanner userInput = new Scanner(System.in);
        String pilihanUser;
        boolean isLanjutkan = true;

        while(isLanjutkan){
            clearScreen();
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
                    tampilData();
                    break;
                case "2":
                    System.out.println("\nCARI BUKU");
                    // Cari data
                    break;
                case "3":
                    System.out.println("\nTAMBAH DATA BUKU");
                    // Tambah data
                    break;
                case "4":
                    System.out.println("\nUBAH DATA BUKU");
                    // Ubah data
                    break;
                case "5":
                    System.out.println("\nHAPUS DATA BUKU");
                    // Hapus/delete data
                    break;
                default:
                    System.err.println("\nInput Anda Tidak Valid\nSilahkan Memilih Angka 1 sampai 5.");
                    break;
            }
            isLanjutkan = getYesNo("Apakah anda ingin melanjutkan");

            // Tutup scanner jika user menginputkan y/n dengan benar
            if(pilihanUser.equalsIgnoreCase("y") && pilihanUser.equalsIgnoreCase("n")) userInput.close();
        }
    }

    private static void tampilData() throws IOException{
        System.out.println("Tampilkan Data");

        boolean isTambah = getYesNo("Tambahkan Data");
    }

    // Method untuk mengolah inputan Y/N dari user input
    private static boolean getYesNo(String message) {
        Scanner userInput = new Scanner(System.in);
        System.out.print("\n"+message+" (y/n)?");
        String pilihanUser = userInput.next();

        while(!pilihanUser.equalsIgnoreCase("y") && !pilihanUser.equalsIgnoreCase("n")) {
            System.err.println("Silahkan Memilih y/n");
            System.out.print(message);
            pilihanUser = userInput.next();
        }

        // Tutup scanner jika user menginputkan y/n dengan benar
        if(pilihanUser.equalsIgnoreCase("y") && pilihanUser.equalsIgnoreCase("n")) userInput.close();

        return pilihanUser.equalsIgnoreCase("y");
    }

    // Fungsi / Method untuk refresh console
    private static void clearScreen() {
        try {
            if(System.getProperty("os.name").contains("windows")){
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (Exception ex){
            System.err.println("Tidak bisa clear screen");
        }
    }
}
