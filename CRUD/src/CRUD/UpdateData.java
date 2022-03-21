package CRUD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class UpdateData {
    public static void updateData() throws IOException {
        // ambil database original
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferInput = new BufferedReader(fileInput);

        // buat database temporary
        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        // tampilkan data
        TampilData.tampilData();

        // ambil user input untuk update data
        Scanner userInput = new Scanner(System.in);
        System.out.print("\nMasukkan nomor buku yang akan diupdate: ");
        int updateNum = userInput.nextInt();

        // tampilkan data yang ingin diupdate
        String data = bufferInput.readLine();
        int dataCounts = 0;

        while(data != null){
            dataCounts++;
            StringTokenizer stringToken = new StringTokenizer(data, ",");
            
            // tampilkan data yang ingin diupdate
            if(updateNum == dataCounts){
                System.out.println("\nData yang ingin anda update:");
                System.out.println("--------------------------------");
                System.out.println("Referensi      : " + stringToken.nextToken());
                System.out.println("Tahun          : " + stringToken.nextToken());
                System.out.println("Penulis        : " + stringToken.nextToken());
                System.out.println("Penerbit       : " + stringToken.nextToken());
                System.out.println("Judul          : " + stringToken.nextToken());
                
                // update data
                
                // mengambil input dari user
                String[] fieldData = {"tahun","penulis","penerbit","judul"};
                String[] tempData = new String[4];

                // Refresh Token
                stringToken = new StringTokenizer(data, ",");
                String originalData = stringToken.nextToken();

                for(int i = 0; i < fieldData.length; i++){
                    boolean isUpdate = Utility.getYesNo("Apakah anda ingin merubah " + fieldData[i]);
                    
                    originalData = stringToken.nextToken();
                    if(isUpdate){
                        // user input
                      
                        if(fieldData[i].equalsIgnoreCase("tahun")){
                          System.out.print("\nMasukkan tahun terbit, format=(YYYY): ");
                          tempData[i] = Utility.ambilTahun();
                        } else {
                          userInput = new Scanner(System.in);
                          System.out.print("\nMasukkan " + fieldData[i] + " baru: ");
                          tempData[i] = userInput.nextLine();
                        }

                    } else {
                        tempData[i] = originalData;
                    }
                }

                // Tampil data baru
                stringToken = new StringTokenizer(data, ",");
                stringToken.nextToken();
                System.out.println("\nData baru yang ingin anda update:");
                System.out.println("--------------------------------");
                System.out.println("Tahun          : " + stringToken.nextToken() + " --> " + tempData[0]);
                System.out.println("Penulis        : " + stringToken.nextToken() + " --> " + tempData[1]);
                System.out.println("Penerbit       : " + stringToken.nextToken() + " --> " + tempData[2]);
                System.out.println("Judul          : " + stringToken.nextToken() + " --> " + tempData[3]);

                boolean isUpdate = Utility.getYesNo("Apakah anda yakin untuk update data");

                if(isUpdate){
                    // cek data baru di database
                    boolean isExist = Utility.checkBukuDiDatabase(tempData, false);

                    if(isExist){
                        System.err.println("Data buku sudah ada di database");
                    } else {
                        // format data baru kedalam database
                        String tahun = tempData[0];
                        String penulis = tempData[1];
                        String penerbit = tempData[2];
                        String judul = tempData[3];

                        // buat primary key
                        long nomorEntry = Utility.ambilEntryPerTahun(penulis, tahun) + 1;

                        String penulisTanpaSpasi = penulis.replaceAll("\\s+", "");
                        String primaryKey = penulisTanpaSpasi+"_"+tahun+"_"+nomorEntry;

                        // tulis data ke database
                        bufferOutput.write(primaryKey + "," + tahun + ","+ penulis+ ","+penerbit+ ","+judul);
                    }
                } else {
                    bufferOutput.write(data);
                }

            } else {
                // copy data
                bufferOutput.write(data);
            }
            
            bufferOutput.newLine();
            data = bufferInput.readLine();
        }

        // menulis data ke file
        bufferOutput.flush();

        // close file
        fileInput.close();
        bufferInput.close();
        fileOutput.close();
        bufferOutput.close();
        System.gc();

        // delete database original
        database.delete();

        // rename temporary file 
        tempDB.renameTo(database);
    }
}
