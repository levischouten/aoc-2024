package common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class AbstractSolution {
    protected List<String> data;

    public AbstractSolution(String filePath) {
        try {
            data = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract int solve();
}
