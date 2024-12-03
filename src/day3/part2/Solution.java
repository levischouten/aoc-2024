package day3.part2;

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
        Pattern pattern = Pattern.compile("mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)");
        Matcher matcher = pattern.matcher(input);

        boolean isEnabled = true;

        while (matcher.find()) {
            String match = matcher.group();

            if (match.equals("don't()")) {
                isEnabled = false;
                continue;
            }

            if (match.equals("do()")) {
                isEnabled = true;
                continue;
            }

            if (isEnabled) {
                total += Arrays
                        .stream(match.split("\\D+"))
                        .filter(s -> !s.isEmpty())
                        .mapToInt(Integer::parseInt)
                        .reduce(1, (a, b) -> a * b);
            }
        }
        return total;
    }
}
