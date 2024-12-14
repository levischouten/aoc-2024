package day10.part2;


import common.AbstractSolution;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Solution extends AbstractSolution {
    private static final List<Direction> DIRECTIONS = List.of(new Direction(0, 1),
            new Direction(1, 0),
            new Direction(0, -1),
            new Direction(-1, 0));

    public Solution() {
        super("src/day10/input");
    }

    @Override
    public int solve() {
        List<List<Integer>> matrix = data
                .stream()
                .map(this::parseInput)
                .toList();

        int total = 0;

        for (int i = 0; i < matrix.size(); i++) {
            List<Integer> row = matrix.get(i);

            for (int j = 0; j < row.size(); j++) {
                if (matrix
                        .get(i)
                        .get(j) == 0) {
                    total += dfs(matrix, new Position(j, i));
                }
            }
        }

        return total;
    }

    private int dfs(List<List<Integer>> matrix, Position position) {
        if (!isInbound(position, matrix)) {
            return 0;
        }

        int value = matrix
                .get(position.y)
                .get(position.x);


        if (value == 9) {
            return 1;
        }

        int total = 0;

        for (Direction direction : DIRECTIONS) {
            Position newPosition = new Position(position.x + direction.x, position.y + direction.y);

            if (isInbound(newPosition, matrix) && matrix
                    .get(newPosition.y)
                    .get(newPosition.x) == value + 1) {

                total += dfs(matrix,
                        newPosition);
            }
        }

        return total;
    }

    private boolean isInbound(Position position, List<List<Integer>> matrix) {
        return position.x >= 0 && position.x < matrix.size() && position.y >= 0 && position.y < matrix
                .getFirst()
                .size();
    }

    private List<Integer> parseInput(String item) {
        return item
                .chars()
                .mapToObj(Character::getNumericValue)
                .collect(Collectors.toList());
    }

    private record Direction(int x, int y) {
    }

    private record Position(int x, int y) {
    }
}