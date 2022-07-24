package handong.whynot.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.RandomUtils;

public final class NickNameMaker {

    private NickNameMaker() {}

    private static final List<String> PREFIX_LIST = List.of("귀여운", "시무룩한", "정신없는");

    public static String make(String nickname) {
        return PREFIX_LIST.get(RandomUtils.nextInt(0, PREFIX_LIST.size() - 1)) + nickname;
    }

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        int threshold = 100;

        for (int i = 0; i < 100000; i++) {
            int currentKey = RandomUtils.nextInt(0, threshold);
            if (map.containsKey(currentKey)) {
                map.put(currentKey, map.get(currentKey) + 1);
            } else {
                map.put(currentKey, 1);
            }
        }

        int sum = 0;
        for (Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
            sum += entry.getValue();
        }
        System.out.println(sum);
    }
}
