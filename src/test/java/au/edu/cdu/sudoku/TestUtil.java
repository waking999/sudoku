package au.edu.cdu.sudoku;

import java.nio.file.Paths;

public class TestUtil {
	static String getCurrentPath() {
		return Paths.get(".").toAbsolutePath().normalize().toString();
	}
}
