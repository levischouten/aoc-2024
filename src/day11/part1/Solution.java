package day11.part1;

import common.AbstractSolution;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Solution extends AbstractSolution {
    public Solution() {
        super("src/day11/input");
    }

    @Override
    public int solve() {
        List<Long> stones = Arrays
                .stream(data
                        .getFirst()
                        .split(" "))
                .map(Long::parseLong)
                .map(n -> recurse(n, 0))
                .flatMap(List::stream)
                .toList();

        return stones.size();
    }

    private List<Long> recurse(long stone, int count) {
        if (count == 25) {
            return List.of(stone);
        }

        if (stone == 0) {
            return recurse(1, count + 1);
        }

        int digits = (int) Math.log10(Math.abs(stone)) + 1;

        if (digits % 2 == 0) {
            return splitInt(stone)
                    .stream()
                    .map(s -> this.recurse(s, count + 1))
                    .flatMap(List::stream)
                    .toList();
        }

        return recurse(stone * 2024, count + 1);
    }

    private List<Long> splitInt(long number) {
        String input = String.valueOf(number);
        int mid = input.length() / 2;

        long firstHalf = Long.parseLong(input.substring(0, mid));
        long secondHalf = Long.parseLong(input.substring(mid));

        return List.of(firstHalf, secondHalf);
    }
}
