import org.junit.Test;

import java.util.ArrayList;

public class test {
    public static void main(String[] args) {

    }
    @Test
    void test1(){
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        for (Integer integer : integers) {
            System.out.println(integer);
        }
    }
}
