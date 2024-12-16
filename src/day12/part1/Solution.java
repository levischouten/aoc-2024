package day12.part1;

import common.AbstractSolution;

import java.util.*;
import java.util.stream.Collectors;

public class Solution extends AbstractSolution {
    private static final List<Direction> DIRECTIONS = List.of(new Direction(0, 1),
            new Direction(1, 0),
            new Direction(0, -1),
            new Direction(-1, 0));

    public Solution() {
        super("src/day12/input");
    }

    @Override
    public int solve() {
        List<List<Character>> matrix = data
                .stream()
                .map(this::parseInput)
                .toList();

        List<Set<Position>> list = new ArrayList<>();

        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix
                    .getFirst()
                    .size(); j++) {
                char current = matrix
                        .get(i)
                        .get(j);

                Position position = new Position(j, i);

                if (list
                        .stream()
                        .noneMatch(set -> set.contains(position))) {
                    Set<Position> visited = bfs(matrix, position);
                    list.add(visited);
                }
            }
        }

        return list
                .stream()
                .mapToInt(set -> countTouchingSides(set, matrix))
                .sum();
    }

    private Set<Position> bfs(List<List<Character>> matrix, Position position) {
        Queue<Position> queue = new LinkedList<>(Arrays.asList(position));
        Set<Position> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Position pos = queue.remove();

            visited.add(pos);

            for (Direction direction : DIRECTIONS) {
                Position newPos = new Position(pos.x + direction.x, pos.y + direction.y);

                if (visited.contains(newPos)) {
                    continue;
                }

                if (isInbound(newPos, matrix) && matrix
                        .get(newPos.y)
                        .get(newPos.x) == matrix
                        .get(pos.y)
                        .get(pos.x)) {
                    visited.add(newPos);
                    queue.add(newPos);
                }
            }
        }


        return visited;
    }

    private boolean isInbound(Position position, List<List<Character>> matrix) {
        return position.x >= 0 && position.x < matrix.size() && position.y >= 0 && position.y < matrix
                .getFirst()
                .size();
    }

    private List<Character> parseInput(String item) {
        return item
                .chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
    }

    private int countTouchingSides(Set<Position> positions, List<List<Character>> matrix) {
        int count = 0;

        for (Position position : positions) {
            for (Direction direction : DIRECTIONS) {
                Position neighbor = new Position(position.x + direction.x,
                        position.y + direction.y);

                if (!isInbound(neighbor, matrix)) {
                    count++;
                    continue;
                }

                if (matrix
                        .get(neighbor.y)
                        .get(neighbor.x) != matrix
                        .get(position.y)
                        .get(position.x)) {
                    count++;
                }
            }
        }

        return count * positions.size();
    }

    private record Position(int x, int y) {
    }

    private record Direction(int x, int y) {
    }
}
