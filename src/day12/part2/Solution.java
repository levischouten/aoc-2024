package day12.part2;

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
                .mapToInt(set -> countCorners(set, matrix))
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

    private int countCorners(Set<Position> positions, List<List<Character>> matrix) {
        int corners = 0;

        Direction left = new Direction(-1, 0);
        Direction right = new Direction(1, 0);
        Direction up = new Direction(0, 1);
        Direction down = new Direction(0, -1);
        Direction upLeft = new Direction(-1, 1);
        Direction upRight = new Direction(1, 1);
        Direction downLeft = new Direction(-1, -1);
        Direction downRight = new Direction(1, -1);

        for (Position position : positions) {
            boolean containsLeft = positions.contains(new Position(position.x + left.x,
                    position.y + left.y));
            boolean containsRight = positions.contains(new Position(position.x + right.x,
                    position.y + right.y));
            boolean containsUp = positions.contains(new Position(position.x + up.x,
                    position.y + up.y));
            boolean containsDown = positions.contains(new Position(position.x + down.x,
                    position.y + down.y));
            boolean containsUpLeft = positions.contains(new Position(position.x + upLeft.x,
                    position.y + upLeft.y));
            boolean containsUpRight = positions.contains(new Position(position.x + upRight.x,
                    position.y + upRight.y));
            boolean containsDownLeft = positions.contains(new Position(position.x + downLeft.x,
                    position.y + downLeft.y));
            boolean containsDownRight = positions.contains(new Position(position.x + downRight.x,
                    position.y + downRight.y));


            if (!containsLeft && !containsUp) {
                corners++;
            }

            if (!containsLeft && !containsDown) {
                corners++;
            }

            if (!containsRight && !containsUp) {
                corners++;
            }

            if (!containsRight && !containsDown) {
                corners++;
            }

            if (containsUp && containsLeft && !containsUpLeft) {
                corners++;
            }

            if (containsUp && containsRight && !containsUpRight) {
                corners++;
            }

            if (containsDown && containsRight && !containsDownRight) {
                corners++;
            }

            if (containsDown && containsLeft && !containsDownLeft) {
                corners++;
            }
        }

        return corners * positions.size();
    }

    private record Position(int x, int y) {
    }

    private record Direction(int x, int y) {
    }
}
