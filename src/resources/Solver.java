package resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

// TODO: research naming convention for this:
public abstract class Solver {
    protected List<String> data;

    public Solver(String filePath) {
        try {
            data = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract int solve();
}
