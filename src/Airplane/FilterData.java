package Airplane;

import javax.naming.event.ObjectChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/*  TO DO LATER */
public class FilterData {
    /* This function deletes all "dirty" data
    * */
    public static List<Records> removeErrors(List<Records> list){

        // not canceled but wu=ith cancellation code
        List<Records> result = list.stream().filter(s -> Objects.equals(s.getCancelled(), "1") ||
                        !(Objects.equals(s.getCancellationCode(), "0"))).toList();
        System.out.println("filter 1: " + result.size());

        //cancelled but with departure or arrival time
        result = result.stream().filter(s -> !Objects.equals(s.getCancelled(), "1") ||
                (!(Objects.equals(s.getArrTime(), null) && Objects.equals(s.getDepTime(), null))
                || (!Objects.equals(s.getArrTime(), null) && !Objects.equals(s.getDepTime(), null)))).toList();
        System.out.println("filter 2: " + result.size());

        //no dep time or arr time
        return result.stream().filter(s -> (Objects.equals(s.getDepTime(), null) && Objects.equals(s.getArrTime(), null)) ||
                !(Objects.equals(s.getArrTime(), null)) || Objects.equals(s.getDepTime(), null)).toList();

    }

}
