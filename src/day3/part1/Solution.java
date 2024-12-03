package day3.part1;

import common.AbstractSolution;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution extends AbstractSolution {
    public Solution() {
        super("src/day3/input");
    }

    @Override
    public int solve() {
        int total = 0;

        String input = String.join("", data);
        Pattern pattern = Pattern.compile("mul\\(\\d+,\\d+\\)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String match = matcher.group();
            total += Arrays
                    .stream(match.split("\\D+"))
                    .filter(s -> !s.isEmpty())
                    .mapToInt(Integer::parseInt)
                    .reduce(1, (a, b) -> a * b);
        }

        return total;
    }
}
