package day9.part2;

import common.GenericAbstractSolution;

import java.util.*;
import java.util.stream.IntStream;

public class Solution extends GenericAbstractSolution<Long> {
    public Solution() {
        super("src/day9/input");
    }

    @Override
    public Long solve() {
        LinkedList<Pair> filesQueue = new LinkedList<>();
        LinkedList<Integer> freeQueue = new LinkedList<>();

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

        List<String> list = new ArrayList<>();

        while (!filesQueue.isEmpty() && !freeQueue.isEmpty()) {
            Pair file = filesQueue.removeFirst();
            int free = freeQueue.removeFirst();

            for (int i = 0; i < file.count; i++) {
                list.add(String.valueOf(file.value));
            }

            for (int i = 0; i < free; i++) {
                list.add(".");
            }
        }

        Pair file = filesQueue.removeFirst();

        for (int i = 0; i < file.count; i++) {
            list.add(String.valueOf(file.value));
        }

        List<Integer> indexes = new ArrayList<>(List.of(list.size() - 1));
        String current = list.getLast();

        for (int i = list.size() - 2; i >= 0; i--) {
            String item = list.get(i);
            if (item.equals(".")) {
                continue;
            }

            if (item.equals(current)) {
                indexes.add(i);
                continue;
            }

            int index = findSublistIndex(list.subList(0, indexes.getLast()),
                    new ArrayList<>(Collections.nCopies(indexes.size(), ".")));

            if (index >= 0) {
                for (int j = index; j < indexes.size() + index; j++) {
                    list.set(j, current);
                }

                for (int j : indexes) {
                    list.set(j, ".");
                }
            }

            indexes = new ArrayList<>(List.of(i));
            current = item;
        }

        long result = 0;


        for (int i = 0; i < list.size(); i++) {
            try {
                long n = (long) i * Integer.parseInt(list.get(i));
                result += n;
            } catch (NumberFormatException _) {
            }
        }

        return result;
    }

    private int findSublistIndex(List<String> list, List<String> sublist) {
        int n = list.size();
        int m = sublist.size();

        if (m > n) {
            return -1;
        }

        for (int i = 0; i <= n - m; i++) {
            boolean match = true;

            for (int j = 0; j < m; j++) {
                if (!list
                        .get(i + j)
                        .equals(sublist.get(j))) {
                    match = false;
                    break;
                }
            }

            if (match) {
                return i;
            }
        }

        return -1;
    }

    private record Pair(int value, int count) {
    }
}
