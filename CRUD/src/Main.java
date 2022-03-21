import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;
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
                    tambahData();
                    tampilData();
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

    // 1. Method Read / Tampil data
    private static void tampilData() throws IOException {
        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("database.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e) {
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu\n");
            tambahData();
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

    // 2. Method find / Cari data
    private static void cariData() throws IOException {
        // membaca database ada atau tidak
        try {
            File file = new File("database.txt");
            System.out.println(file);
        } catch (Exception e) {
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu\n");
            tambahData();
            return;
        }

        // ambil keyword dari user
        Scanner userInput = new Scanner(System.in);
        System.out.print("Masukkan kata kunci untuk mencari buku: ");
        String cariString = userInput.nextLine();
        
        // check keyword di database
        String[] keywords = cariString.split("\\s+");
        checkBukuDiDatabase(keywords,true);

    }

    // 3. Method tambah data
    private static void tambahData() throws IOException {

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
        tahun = ambilTahun();
    
        // cek buku di database
        String[] keywords = {tahun+","+penulis+","+penerbit+","+judul};

        boolean isExist = checkBukuDiDatabase(keywords,false);
        
        // menulis buku di database
        if(!isExist){
            long nomorEntry = ambilEntryPerTahun(penulis, tahun) + 1;

            String penulisTanpaSpasi = penulis.replaceAll("\\s+", "");
            String primaryKey = penulisTanpaSpasi+"_"+tahun+"_"+nomorEntry;
            System.out.println("\nData yang akan anda masukkan adalah");
            System.out.println("----------------------------------------");
            System.out.println("Primary key   : " + primaryKey);
            System.out.println("Tahun terbit  : " + tahun);
            System.out.println("Penulis       : " + penulis);
            System.out.println("Judul         : " + judul);
            System.out.println("Penerbit      : " + penerbit);

            boolean isTambah = getYesNo("Apakah anda ingin menambah data");

            if(isTambah){
                bufferOutput.write(primaryKey + "," + tahun + ","+ penulis+ ","+penerbit+ ","+judul);
                bufferOutput.newLine();
                bufferOutput.flush();
            }


        } else {
            System.out.println("Buku yang anda akan masukkan sudah ada");
            checkBukuDiDatabase(keywords,true);
        }

        bufferOutput.close();
    }

    // Method ambil entry per tahun untuk primary key
    private static long ambilEntryPerTahun(String penulis, String tahun) throws IOException{
        FileReader fileInput = new FileReader("database.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        long entry = 0;
        String data = bufferInput.readLine();
        Scanner dataScanner;
        String primaryKey;

        while(data != null){
            dataScanner = new Scanner(data);
            dataScanner.useDelimiter(",");
            primaryKey = dataScanner.next();
            dataScanner = new Scanner(primaryKey);
            dataScanner.useDelimiter("_");

            penulis = penulis.replaceAll("\\s+", "");
            
            if(penulis.equalsIgnoreCase(dataScanner.next()) && tahun.equalsIgnoreCase(dataScanner.next())) {
                entry = dataScanner.nextInt();
            }

            data = bufferInput.readLine();
        }
        bufferInput.close();
        return entry;
    }

    // Method Cek Buku di Database
    private static boolean checkBukuDiDatabase(String[] keywords, boolean isDisplay) throws IOException{
        FileReader fileInput = new FileReader("database.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine();
        boolean isExist = false;
        int nomorData = 0;

        if(isDisplay){
        System.out.println("\n| No | Tahun | Penulis                | Penerbit               | Judul Buku");
        System.out.println("===============================================================================");
        }

        while(data != null) {  
            // cek keywords di dalam baris
            isExist = true;
            for(String keyword: keywords){
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }

            // jika keywords cocok maka tampilkan data
            if(isExist){
                if(isDisplay) {
                nomorData++;
                StringTokenizer stringToken = new StringTokenizer(data, ",");
                stringToken.nextToken();
                System.out.printf("| %2d ", nomorData);
                System.out.printf("| %4s  ",stringToken.nextToken());
                System.out.printf("| %-20s   ",stringToken.nextToken());
                System.out.printf("| %-20s   ",stringToken.nextToken());
                System.out.printf("| %s   ",stringToken.nextToken());
                System.out.print("\n");
                } else {
                    break;
                }
            }  

            data = bufferInput.readLine();
        }

        if(isDisplay){
        System.out.println("===============================================================================");
        }
        bufferInput.close();
        return isExist;
    }
    
    // Method validasi tahun
    private static String ambilTahun() throws IOException {
    boolean tahunValid = false;
    
    Scanner userInput = new Scanner(System.in);
    String tahunInput = userInput.nextLine();
    
    while(!tahunValid){    
            try {
                Year.parse(tahunInput);
                tahunValid = true;
            } catch (Exception e){
                System.err.println("Format Tahun salah, format=(YYYY)");
                System.out.print("Masukkan tahun terbit: ");
                tahunValid = false;
                tahunInput = userInput.nextLine();
            }
        }
        return tahunInput;
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
