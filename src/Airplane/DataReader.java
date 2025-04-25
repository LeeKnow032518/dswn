package Airplane;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//считывание конкретных данных
public class DataReader {
    public static List<Records> readAllData(String fileName){
        List<Records> allFlights = new ArrayList<>();
        try(BufferedReader br = new BufferedReader((new FileReader(fileName)))){
            String line;
            line = br.readLine();
            while((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Records newRecord = new Records(values[0], values[1], values[2], values[3],
                        values[4], values[5], values[6], values[7], values[8], values[9], values[10], values[11],
                        values[12], values[13], values[14], values[15], values[16], values[17], values[18], values[19],
                        values[20], values[21]);
                allFlights.add(newRecord);
            }
        }catch (IOException e){
            System.out.println(e);
        }

        return allFlights;
    }

    public static List<Records> readCancelledData(String fileName){
        List<Records> cancelledFlights = new ArrayList<>();

        try(BufferedReader br = new BufferedReader((new FileReader(fileName)))){
            String line;
            line = br.readLine();
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                Records newRecord = new Records(values[0], values[1], values[2], values[3],
                        values[4], values[5], values[6], values[7], values[8], values[9], values[10], values[11],
                        values[12], values[13], values[14], values[15], values[16], values[17], values[18], values[19],
                        values[20], values[21]);
                if(Integer.parseInt(newRecord.getCancelled()) == 1){
                    cancelledFlights.add(newRecord);
                }
            }

        } catch (IOException e){
            System.out.println(e);
        }
        return cancelledFlights;
    }
}
