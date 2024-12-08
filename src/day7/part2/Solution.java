package day7.part2;

import common.GenericAbstractSolution;

import java.util.Arrays;
import java.util.List;

public class Solution extends GenericAbstractSolution<Long> {
    public Solution() {
        super("src/day7/input");
    }

    private record Result(long target, List<Long> nums) {
        private boolean solve(int index, long current) {
            if (index == nums.size()) {
                return current == target;
            }

            if (solve(index + 1, current * nums.get(index))) {
                return true;
            }

            if (solve(index + 1, current + nums.get(index))) {
                return true;
            }

            return solve(index + 1, Long.parseLong("" + current + nums.get(index)));
        }

        private boolean solve() {
            return solve(1, nums.getFirst());
        }
    }

    public Long solve() {
        return data
                .stream()
                .map(this::parseInput)
                .mapToLong(this::evaluateResult)
                .sum();
    }

    private Result parseInput(String item) {
        String[] parts = item.split(":");

        long result = Long.parseLong(parts[0].trim());
        List<Long> nums = Arrays
                .stream(parts[1]
                        .trim()
                        .split("\\s+"))
                .map(Long::parseLong)
                .toList();

        return new Result(result, nums);
    }

    private long evaluateResult(Result result) {
        if (result.solve()) {
            return result.target;
        }

        return 0;
    }
}
