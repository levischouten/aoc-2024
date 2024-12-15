package day11.part2;

import common.GenericAbstractSolution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution extends GenericAbstractSolution<Long> {
    private final Map<String, Long> cache = new HashMap<>();

    public Solution() {
        super("src/day11/input");
    }

    @Override
    public Long solve() {
        return Arrays
                .stream(data
                        .getFirst()
                        .split(" "))
                .mapToLong(n -> recurse(Long.parseLong(n), 0))
                .sum();
    }

    private long recurse(long stone, int count) {
        String key = stone + "," + count;

        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        if (count == 75) {
            return 1;
        }

        if (stone == 0L) {
            long result = recurse(1, count + 1);
            cache.put(key, result);
            return result;
        }

        int digits = (int) Math.log10(Math.abs(stone)) + 1;

        if (digits % 2 == 0) {
            long result = splitInt(stone)
                    .stream()
                    .mapToLong(s -> this.recurse(s, count + 1))
                    .sum();

            cache.put(key, result);
            return result;
        }

        long result = recurse(stone * 2024, count + 1);

        cache.put(key, result);
        return result;
    }

    private List<Long> splitInt(long number) {
        String input = String.valueOf(number);
        int mid = input.length() / 2;

        long firstHalf = Long.parseLong(input.substring(0, mid));
        long secondHalf = Long.parseLong(input.substring(mid));

        return List.of(firstHalf, secondHalf);
    }
}
