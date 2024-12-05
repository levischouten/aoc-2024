package day5.part2;

import common.AbstractSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Solution extends AbstractSolution {
    public Solution() {
        super("src/day5/input");
    }

    @Override
    public int solve() {
        int index = data.indexOf("");

        HashMap<Integer, List<Integer>> orderingRules = new HashMap<>();

        data
                .subList(0, index)
                .forEach(s -> {
                    List<Integer> kvPairs = Arrays
                            .stream(s.split("\\|"))
                            .map(Integer::parseInt)
                            .toList();
                    int key = kvPairs.get(1);
                    int value = kvPairs.get(0);

                    List<Integer> values = orderingRules.getOrDefault(key, new ArrayList<>());

                    values.add(value);
                    orderingRules.put(key, values);
                });

        return data
                .subList(index + 1, data.size())
                .stream()
                .mapToInt(s -> {
                    List<Integer> nums = Arrays
                            .stream(s.split(","))
                            .map(Integer::parseInt)
                            .toList();

                    for (int i = 0; i < nums.size(); i++) {
                        for (int j = 0; j < i; j++) {
                            int value = nums.get(i);
                            boolean isInvalid = orderingRules
                                    .getOrDefault(nums.get(j),
                                            new ArrayList<>())
                                    .stream()
                                    .anyMatch(n -> n.equals(value));

                            if (isInvalid) {
                                List<Integer> sortedList = sortList(nums, orderingRules);

                                return sortedList.get((int) Math.floor((double) sortedList.size() / 2));
                            }
                        }
                    }

                    return 0;
                })
                .sum();
    }

    private List<Integer> sortList(List<Integer> list,
                                   HashMap<Integer, List<Integer>> orderingRules) {
        return list
                .stream()
                .sorted((a, b) -> orderingRules
                        .getOrDefault(b, new ArrayList<>())
                        .stream()
                        .anyMatch(n -> n.equals(a)) ? -1 : 0)
                .toList();
    }
}
