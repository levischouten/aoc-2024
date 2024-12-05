package day4.part2;

import common.AbstractSolution;

import java.util.List;
import java.util.stream.Collectors;

public class Solution extends AbstractSolution {
    public Solution() {
        super("src/day4/input");
    }

    @Override
    public int solve() {
        List<List<Character>> matrix = data
                .stream()
                .map(this::parseInput)
                .toList();

        int total = 0;

        for (int i = 1; i < matrix.size() - 1; i++) {
            List<Character> row = matrix.get(i);

            for (int j = 1; j < row.size() - 1; j++) {
                if (row.get(j) == 'A') {
                    String a = buildString(matrix, i - 1, j - 1, i + 1, j + 1);
                    String b = buildString(matrix, i - 1, j + 1, i + 1, j - 1);

                    if (isValidString(a) && isValidString(b)) {
                        total++;
                    }
                }
            }
        }

        return total;
    }

    private List<Character> parseInput(String item) {
        return item
                .chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
    }

    private String buildString(List<List<Character>> matrix, int x1, int y1, int x2, int y2) {
        return "" + matrix
                .get(x1)
                .get(y1) + 'A' + matrix
                .get(x2)
                .get(y2);
    }

    private boolean isValidString(String str) {
        return "MAS".equals(str) || "SAM".equals(str);
    }
}
