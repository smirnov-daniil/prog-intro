package markup.parser;

import markup.Document;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface Parser {
    Document parse(Path filename) throws FileNotFoundException, IOException, IllegalArgumentException;
}
