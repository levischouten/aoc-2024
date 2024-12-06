package day6.part2;

import common.AbstractSolution;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution extends AbstractSolution {
    public Solution() {
        super("src/day6/input");
    }

    @Override
    public int solve() {
        List<List<Character>> matrix = data
                .stream()
                .map(this::parseInput)
                .toList();

        Position position = getStarterPosition(matrix);
        Direction direction = new Direction(0, -1);
        Set<Position> visited = new HashSet<>();

        int total = 0;


        while (true) {
            Position newPosition = position.move(direction);

            if (newPosition.isOutbound(matrix)) {
                break;
            }

            while (matrix
                    .get(newPosition.y)
                    .get(newPosition.x) == '#') {
                if (newPosition.isOutbound(matrix)) {
                    break;
                }

                direction = direction.turn();
                newPosition = position.move(direction);
            }

            position = newPosition;

            visited.add(position);
        }

        position = getStarterPosition(matrix);
        direction = new Direction(0, -1);

        for (Position visitedPosition : visited) {
            matrix
                    .get(visitedPosition.y)
                    .set(visitedPosition.x, '#');

            if (isCircular(matrix, position, direction)) {
                total++;
            }

            matrix
                    .get(visitedPosition.y)
                    .set(visitedPosition.x, '.');
        }

        return total;
    }

    private boolean isCircular(List<List<Character>> matrix,
                               Position position,
                               Direction direction) {
        Set<Position> turnedPositions = new HashSet<>();

        while (true) {
            Position newPosition = position.move(direction);
            boolean hasTurned = false;

            if (newPosition.isOutbound(matrix)) {
                break;
            }

            while (matrix
                    .get(newPosition.y)
                    .get(newPosition.x) == '#') {
                if (newPosition.isOutbound(matrix)) {
                    break;
                }

                direction = direction.turn();
                newPosition = position.move(direction);

                hasTurned = true;
            }

            if (hasTurned) {
                if (turnedPositions.contains(position)) {
                    return true;
                }

                turnedPositions.add(position);
            }


            position = newPosition;
        }

        return false;
    }

    private Position getStarterPosition(List<List<Character>> matrix) {
        for (int i = 0; i < matrix.size(); i++) {
            List<Character> row = matrix.get(i);

            for (int j = 0; j < row.size(); j++) {
                if (row.get(j) == '^') {
                    return new Position(j, i);
                }
            }
        }
        throw new IllegalArgumentException("Starter position '^' not found in the matrix.");
    }

    private List<Character> parseInput(String item) {
        return item
                .chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
    }

    public record Direction(int x, int y) {
        public Direction turn() {
            if (x == 0 && y == -1) return new Direction(1, 0);
            if (x == 1 && y == 0) return new Direction(0, 1);
            if (x == 0 && y == 1) return new Direction(-1, 0);
            if (x == -1 && y == 0) return new Direction(0, -1);
            return this;
        }
    }

    public record Position(int x, int y) {
        public Position move(Direction direction) {
            return new Position(x + direction.x(), y + direction.y());
        }

        public boolean isOutbound(List<List<Character>> matrix) {
            return matrix.size() <= y || matrix
                    .getFirst()
                    .size() <= x || x < 0 || y < 0;
        }
    }
}
