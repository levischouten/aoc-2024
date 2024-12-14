package day9.part1;

import common.GenericAbstractSolution;

import java.util.*;
import java.util.stream.IntStream;

public class Solution extends GenericAbstractSolution<Long> {
    public Solution() {
        super("src/day9/input");
    }

    @Override
    public Long solve() {
        Deque<Pair> filesQueue = new LinkedList<>();
        Deque<Integer> freeQueue = new LinkedList<>();

        String input = data.getFirst();

        IntStream
                .range(0, input.length())
                .forEach(i -> {
                    int digit = Character.getNumericValue(input.charAt(i));
                    Pair pair = new Pair(filesQueue.size(), digit);

                    if (i % 2 == 0) {
                        filesQueue.add(pair);
                        return;
                    }
                    freeQueue.add(digit);
                });

        List<Integer> numbers = new ArrayList<>();

        while (filesQueue.size() >= 2) {
            Pair file = filesQueue.removeFirst();

            for (int i = 0; i < file.value; i++) {
                numbers.add(file.index);
            }

            int free = freeQueue.removeFirst();

            while (!filesQueue.isEmpty() && free > 0) {
                Pair last = filesQueue.removeLast();

                if (last.value > free) {
                    int value = last.value - free;

                    filesQueue.addLast(new Pair(last.index, value));
                    for (int i = 0; i < free; i++) {
                        numbers.add(last.index);
                    }

                    break;
                }

                free = free - last.value;
                for (int i = 0; i < last.value; i++) {
                    numbers.add(last.index);
                }
            }
        }

        while (!filesQueue.isEmpty()) {
            Pair file = filesQueue.removeLast();

            for (int i = 0; i < file.value; i++) {
                numbers.add(file.index);
            }
        }

        return IntStream
                .range(0, numbers.size())
                .mapToLong(i -> (long) numbers.get(i) * i)
                .sum();
    }

    private record Pair(int index, int value) {
    }
}
