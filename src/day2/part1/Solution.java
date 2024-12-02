package day2.part1;

import common.AbstractSolution;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution extends AbstractSolution {
    public Solution() {
        super("src/day2/input");
    }

    @Override
    public int solve() {
        return data
                .stream()
                .map(this::parseInput)
                .mapToInt(this::evaluateList)
                .sum();
    }

    private List<Integer> parseInput(String item) {
        return Arrays
                .stream(item.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private int evaluateList(List<Integer> nums) {
        boolean isAsc = IntStream
                .range(0, nums.size() - 1)
                .allMatch(i -> nums.get(i) <= nums.get(i + 1));
        boolean isDesc = IntStream
                .range(0, nums.size() - 1)
                .allMatch(i -> nums.get(i) >= nums.get(i + 1));

        if (!isAsc && !isDesc) {
            return 0;
        }

        boolean isValid = IntStream
                .range(0, nums.size() - 1)
                .allMatch(i -> {
                    int diff = Math.abs(nums.get(i) - nums.get(i + 1));
                    return diff <= 3 && diff > 0;
                });

        return isValid ? 1 : 0;
    }
}
