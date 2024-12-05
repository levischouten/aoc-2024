package day4.part1;

import common.AbstractSolution;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution extends AbstractSolution {
    public Solution() {
        super("src/day4/input");
    }

    public record Direction(int x, int y) {
    }

    public record Position(int x, int y) {
        public Position move(Direction direction) {
            return new Position(x + direction.x(), y + direction.y());
        }
    }

    private static final List<Direction> DIRECTIONS = List.of(
            new Direction(1, 0), new Direction(0, 1),
            new Direction(-1, 0), new Direction(0, -1),
            new Direction(1, 1), new Direction(-1, -1),
            new Direction(1, -1), new Direction(-1, 1)
    );

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
                    for (Direction direction : DIRECTIONS) {
                        LinkedList<Character> list = new LinkedList<>();

                        list.add('M');
                        list.add('A');
                        list.add('S');

                        if (dfs(matrix, list, direction, new Position(j, i))) {
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
                        Direction direction,
                        Position position) {
        if (list.isEmpty()) {
            return true;
        }

        Position newPosition = position.move(direction);

        if (newPosition.y() < 0 || newPosition.y() >= matrix.size() ||
                newPosition.x() < 0 || newPosition.x() >= matrix
                .getFirst()
                .size()) {
            return false;
        }

        if (matrix
                .get(newPosition.y())
                .get(newPosition.x()) == list.pop()) {
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
