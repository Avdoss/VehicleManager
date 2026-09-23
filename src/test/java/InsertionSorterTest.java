import org.junit.jupiter.api.Test;
import org.team1.app.InsertionSorter;
import org.team1.app.Sorter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;



import static org.junit.jupiter.api.Assertions.*;

public class InsertionSorterTest {
    @Test
    void runTest(){
        Sorter sorter = new InsertionSorter();
        Integer[] arraySort = {3,1,5,7,4,8,2,6};
        List<Integer> sourceList = new ArrayList<>(Arrays.asList(arraySort));
        List<Integer> sortedList = sorter.sort(sourceList,new IntegerComparator());
        Integer[] correctSort = {1,2,3,4,5,6,7,8};
        List<Integer> correctList = new ArrayList<>(Arrays.asList(correctSort));
        assertEquals(correctList, sortedList, "Error while testing class InsertionSorterTest: lists are not equal");
    }
    public static class IntegerComparator implements Comparator<Integer> {


        @Override
        public int compare(Integer o1, Integer o2) {
            return Integer.compare(o1,o2);
        }
    }
}
