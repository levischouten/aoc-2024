package common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class GenericAbstractSolution<T> {
    protected List<String> data;

    public GenericAbstractSolution(String filePath) {
        try {
            data = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract T solve();
}
