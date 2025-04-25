import Airplane.DataReader;
import Airplane.FilterData;
import Airplane.FormattedOutput;
import Airplane.Records;

import java.util.*;
import java.util.stream.Collectors;

class Main{
    public static void main(String[] args) {
        String fileName = "src/flights.csv";

        FormattedOutput answers = new FormattedOutput();

        List<Records> allFlights = DataReader.readAllData(fileName);
        System.out.println("before filter: " + allFlights.size());

        allFlights = FilterData.removeErrors(allFlights);
        System.out.println("end filter: " + allFlights.size());

        List<Records> cancelledFlights = DataReader.readCancelledData(fileName);




    }
}
