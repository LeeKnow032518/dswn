package Airplane;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

public class AirportSolver {
    public static void main(String[] args) {
        String fileName = "src/flights.csv";

        FormattedOutput answers = new FormattedOutput();

        // read all the data and the data of cancelled flights
        List<Records> allFlights = DataReader.readAllData(fileName);
//        allFlights = FilterData.removeErrors(allFlights);

        List<Records> cancelledFlights = DataReader.readCancelledData(fileName);
//        cancelledFlights = FilterData.removeErrors(cancelledFlights);


        //first question
        Map<String, Integer> allFlightsForCarriers = allFlights.stream()
                .collect(Collectors.toMap(Records::getUniqueCarrier, i -> 1, (i1, i2) -> i1 + i2));

        Map<String, Integer> cancellationsForCarriers = cancelledFlights.stream()
                .collect(Collectors.toMap(Records::getUniqueCarrier, i -> 1, (i1, i2) -> i1 + i2));

        Map<String, Double> percentage = cancellationsForCarriers.keySet().stream()
                .collect(Collectors.toMap(s -> s, s -> (double) cancellationsForCarriers.get(s) / allFlightsForCarriers.get(s) * 100));

        Optional<String> keyWithMaxPercent = percentage.keySet().stream()
                .max(Comparator.comparing(percentage::get));

        keyWithMaxPercent.ifPresent(s -> answers.addAnswer(1, s
                .concat(", ").concat(percentage.get(s).toString())
                .concat("%")));

        //second question
        Map<String, Integer> cancellationReasons = cancelledFlights.stream()
                .collect((Collectors.toMap(Records::getCancellationCode, i -> 1, Integer::sum)));

        Optional<String> cancellationCode = cancellationReasons.keySet().stream()
                .max(Comparator.comparing(cancellationReasons::get));

        cancellationCode.ifPresent(s -> answers.addAnswer(2, s));

        //third question
        Map<String, Integer> mostFlights = allFlights.stream().filter(s -> Objects.equals(s.getCancelled(), "0"))
                        .collect(Collectors.toMap(Records::getTailNum, s -> Integer.parseInt(s.getDistance()), (sum, i) -> sum + i));

        Optional<String> tailNumMax = mostFlights.keySet().stream()
                        .max(Comparator.comparing(mostFlights::get));

        tailNumMax.ifPresent(s -> answers.addAnswer(3, s));

        //fourth question
        Map<String, Integer> busyAirportsOrigin = allFlights.stream().filter(s -> s.getCancelled().equals("0"))
                        .collect(Collectors.toMap(s->s.getOriginAirportID(), s -> 1, (s1, s2) -> s1+s2));

        Set<String> destinyAirports = allFlights.stream().map(Records::getDestAirportID).collect(Collectors.toSet()); // set of destiny airports
        destinyAirports.stream().filter(s -> busyAirportsOrigin.keySet().contains(s))
            .forEach(s -> busyAirportsOrigin.replace(s, busyAirportsOrigin.get(s) + 1));

        Optional<String> airportID = busyAirportsOrigin.keySet().stream().max(Comparator.comparing(busyAirportsOrigin::get));

        airportID.ifPresent(s -> answers.addAnswer(4, s));

        // fifth question
        Map<String, Integer> destinyCount = allFlights.stream().filter(s -> s.getCancelled().equals("0"))
                        .collect(Collectors.toMap(s -> s.getDestAirportID(), s -> 1, (i1, i2) -> i1+i2));
        Map<String, Integer> originCount = allFlights.stream().filter(s -> s.getCancelled().equals("0"))
                        .collect(Collectors.toMap(s -> s.getOriginAirportID(), s -> 1, (i1, i2) -> i1+i2));

        Optional<String> source = originCount.keySet().stream().filter(s -> destinyCount.keySet().contains(s))
                .max(Comparator.comparing(s -> originCount.get(s) - destinyCount.get(s)));

        source.ifPresent(s -> answers.addAnswer(5, s));

        // sixth question
        Optional<String> sink = destinyCount.keySet().stream().filter(s -> originCount.keySet().contains(s))
                .max(Comparator.comparing(s -> destinyCount.get(s) - originCount.get(s)));

        sink.ifPresent(s -> answers.addAnswer(6, s));

        // seventh question
        long delayedAA = allFlights.stream().filter(s -> s.getCancelled().equals("0"))
                        .filter(s -> s.getUniqueCarrier().equals("AA"))
                        .filter(s -> !s.getDepDelay().equals(""))
                        .filter(s -> !s.getArrDelay().equals(""))
                        .filter(s -> (Integer.parseInt(s.getDepDelay()) >= 60) ||
                                ((Integer.parseInt(s.getArrDelay()) > 0) && (Integer.parseInt(s.getDepDelay()) + Integer.parseInt(s.getArrDelay()) >= 60))).count();

        answers.addAnswer(7, Long.toString(delayedAA));

        // eighth question
        Optional<Records> theFastest = allFlights.stream().filter(s -> s.getCancelled().equals("0"))
                        .filter(s -> !s.getDepDelay().equals(""))
                        .filter(s -> !s.getArrDelay().equals(""))
                        .filter(s ->Integer.parseInt(s.getDepDelay()) > 0)
                        .filter(s -> Integer.parseInt(s.getArrDelay()) <= 0)
                        .max(Comparator.comparing(s -> Integer.parseInt(s.getDepDelay())));

        theFastest.ifPresent(s -> answers.addAnswer(8, s.getDayOfMonth().concat(",").concat(s.getDepDelay())
                .concat(",").concat(s.getTailNum())));

        answers.writeAnswers();
    }

}
