package gol;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileWorldReader {
	private int width;
	private int height;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public World read(String filePath) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(filePath));
		World world = new SetWorld();
		int lineNumber = 1;
		int maxWidth = 0;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			Pattern pattern = Pattern.compile("[^#-]");
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				scanner.close();
				throw new RuntimeException("Invalid character '"
						+ matcher.group() + "' on line "
						+ lineNumber + " in file " + filePath);
			}

			maxWidth = Math.max(maxWidth, line.length());

			setLine(world, lineNumber, line);
			lineNumber++;
		}
		scanner.close();

		width = maxWidth;
		height = lineNumber - 1;

		return world;
	}

	private void setLine(World world, int lineNumber, String line) {
		for (int x = 0; x < line.length(); ++x) {
			if (line.charAt(x) == '#') {
				world.setAlive(x, lineNumber - 1);
			}
		}
	}
}