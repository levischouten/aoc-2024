package day1.part2;

import resources.Solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Solution extends Solver {

    public Solution() {
        super("src/day1/input");
    }

    @Override
    public int solve() {
        List<Integer> list = new ArrayList<>();
        HashMap<String, Integer> hash_map = new HashMap<String, Integer>();

        data
                .stream()
                .map(item -> item
                        .trim()
                        .split("\\s+"))
                .forEach(parts -> {
                    list.add(Integer.parseInt(parts[0]));

                    String key = parts[1];
                    hash_map.put(key, hash_map.getOrDefault(key, 0) + 1);
                });

        return list
                .stream()
                .mapToInt(n -> n * hash_map.getOrDefault(n.toString(), 0))
                .sum();
    }
}

