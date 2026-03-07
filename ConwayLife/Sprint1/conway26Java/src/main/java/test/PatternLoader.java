package main.java.test;

import java.nio.file.*;
import java.util.List;

public class PatternLoader {
    public static boolean[][] loadFromResource(String path, int rows, int cols) throws Exception {
        boolean[][] grid = new boolean[rows][cols];
        List<String> lines = Files.readAllLines(Paths.get(path));
        
        int r = 0;
        for (String line : lines) {
            if (line.startsWith("!")) continue; // Salta i commenti
            for (int c = 0; c < line.length() && c < cols; c++) {
                if (line.charAt(c) == 'O') grid[r][c] = true;
            }
            r++;
        }
        return grid;
    }
}
