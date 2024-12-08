package day8.part1;

import common.AbstractSolution;

import java.util.*;
import java.util.stream.Collectors;

public class Solution extends AbstractSolution {
    public Solution() {
        super("src/day8/input");
    }

    @Override
    public int solve() {
        List<List<Character>> matrix = data
                .stream()
                .map(this::parseInput)
                .toList();

        Map<Character, List<Position>> antennaMap = new HashMap<>();
        List<Antenna> antennas = new ArrayList<>();

        for (int i = 0; i < matrix.size(); i++) {
            List<Character> row = matrix.get(i);

            for (int j = 0; j < row.size(); j++) {
                char c = row.get(j);

                if (c == '.') {
                    continue;
                }

                List<Position> list = antennaMap.getOrDefault(c, new ArrayList<>());
                Position position = new Position(j, i);
                list.add(position);

                antennaMap.put(c, list);
                antennas.add(new Antenna(c, position));
            }
        }

        Set<Position> antinodes = new HashSet<>();

        for (Antenna antenna : antennas) {
            List<Position> positions = antennaMap.get(antenna.type);

            antinodes.addAll(positions
                    .stream()
                    .filter(antenna::isDifferent)
                    .map(antenna::getAntinode)
                    .filter(a -> isInbound(a, matrix))
                    .collect(Collectors.toSet()));
        }

        return antinodes.size();
    }

    private List<Character> parseInput(String item) {
        return item
                .chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
    }

    private boolean isInbound(Position position, List<List<Character>> matrix) {
        return position.x >= 0 && position.x < matrix.size() && position.y >= 0 && position.y < matrix
                .getFirst()
                .size();
    }

    public record Position(int x, int y) {
    }

    public record Antenna(char type, Position position) {
        private boolean isDifferent(Position otherPosition) {
            return otherPosition.y != position.y || otherPosition.x != position.x;
        }

        private Position getAntinode(Position otherPosition) {
            int x = position.x - otherPosition.x;
            int y = position.y - otherPosition.y;

            int newX = position.x + x;
            int newY = position.y + y;

            return new Position(newX, newY);
        }
    }
}
