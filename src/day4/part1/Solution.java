package day4.part1;

import common.AbstractSolution;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution extends AbstractSolution {
    public Solution() {
        super("src/day4/input");
    }

    private static final int[][] DIRECTIONS = {
            {1, 0}, {0, 1}, {-1, 0}, {0, -1},
            {1, 1}, {-1, -1}, {1, -1}, {-1, 1}
    };

    @Override
    public int solve() {
        List<List<Character>> matrix = data
                .stream()
                .map(this::parseInput)
                .toList();

        int total = 0;

        for (int i = 0; i < matrix.size(); i++) {
            List<Character> row = matrix.get(i);

            for (int j = 0; j < row.size(); j++) {
                if (row.get(j) == 'X') {
                    for (int[] direction : DIRECTIONS) {
                        LinkedList<Character> list = new LinkedList<>();

                        list.add('M');
                        list.add('A');
                        list.add('S');

                        if (dfs(matrix, list, direction, new int[]{j, i})) {
                            total++;
                        }
                    }
                }
            }
        }

        return total;
    }

    private boolean dfs(List<List<Character>> matrix,
                        LinkedList<Character> list,
                        int[] direction,
                        int[] position) {
        if (list.isEmpty()) {
            return true;
        }

        int[] newPosition = {position[0] + direction[0], position[1] + direction[1]};

        int newXPos = newPosition[0];
        int newYPos = newPosition[1];

        if (newYPos < 0 || newYPos >= matrix.size() ||
                newXPos < 0 || newXPos >= matrix
                .getFirst()
                .size()) {
            return false;
        }

        if (matrix
                .get(newYPos)
                .get(newXPos) == list.pop()) {
            return dfs(matrix, list, direction, newPosition);
        }

        return false;
    }

    private List<Character> parseInput(String item) {
        return item
                .chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
    }
}
