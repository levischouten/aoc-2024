package day1.part1;

import common.AbstractSolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Solution extends AbstractSolution {

    public Solution() {
        super("src/day1/input");
    }

    @Override
    public int solve() {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        data
                .stream()
                .map(item -> item
                        .trim()
                        .split("\\s+"))
                .forEach(parts -> {
                    list1.add(Integer.parseInt(parts[0]));
                    list2.add(Integer.parseInt(parts[1]));
                });

        Collections.sort(list1);
        Collections.sort(list2);

        return IntStream
                .range(0, list1.size())
                .map(i -> Math.abs(list1.get(i) - list2.get(i)))
                .sum();
    }
}

