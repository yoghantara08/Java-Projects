import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

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
                    cariData();
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

    // Method find / Cari data
    private static void cariData() throws IOException {
        // membaca database ada atau tidak
        try {
            File file = new File("database.txt");
            System.out.println(file);
        } catch (Exception e) {
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            return;
        }

        // ambil keyword dari user
        Scanner userInput = new Scanner(System.in);
        System.out.print("Masukkan kata kunci untuk mencari buku: ");
        String cariString = userInput.nextLine();
        
        // check keyword di database
        String[] keywords = cariString.split("\\s+");
        checkBukuDiDatabase(keywords);

        userInput.close();
    }

    // Method Cek Buku di Database
    private static void checkBukuDiDatabase(String[] keywords) throws IOException{
        
        FileReader fileInput = new FileReader("database.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine();
        boolean isExist;
        int nomorData = 0;

        System.out.println("\n| No | Tahun | Penulis                | Penerbit               | Judul Buku");
        System.out.println("===============================================================================");

        while(data != null) {  
            // cek keywords di dalam baris
            isExist = true;
            for(String keyword: keywords){
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }

            // jika keywords cocok maka tampilkan data
            if(isExist){
                nomorData++;
                StringTokenizer stringToken = new StringTokenizer(data, ",");
                stringToken.nextToken();
                System.out.printf("| %2d ", nomorData);
                System.out.printf("| %4s  ",stringToken.nextToken());
                System.out.printf("| %-20s   ",stringToken.nextToken());
                System.out.printf("| %-20s   ",stringToken.nextToken());
                System.out.printf("| %s   ",stringToken.nextToken());
                System.out.print("\n");
            }  

            data = bufferInput.readLine();
        }

        System.out.println("===============================================================================");
        fileInput.close();
        bufferInput.close();
    }

    // Method Read / Tampil data
    private static void tampilData() throws IOException {
        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("database.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e) {
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
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

    // Method untuk mengolah inputan Y/N dari user input
    private static boolean getYesNo(String message) {
        Scanner userInput = new Scanner(System.in);
        System.out.print("\n"+message+" (y/n)?");
        String pilihanUser = userInput.next();

        while(!pilihanUser.equalsIgnoreCase("y") && !pilihanUser.equalsIgnoreCase("n")) {
            System.err.println("Silahkan Memilih y/n");
            System.out.print("\n"+message+" (y/n)?");
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
