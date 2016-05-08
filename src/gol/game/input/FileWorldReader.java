package gol.game.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gol.game.world.BitSetWorld;
import gol.game.world.World;

public class FileWorldReader {
	private int width;
	private int height;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public World read(final String filePath) throws FileNotFoundException {
		final Scanner scanner = new Scanner(new File(filePath));
		final World world = new BitSetWorld();
		int lineNumber = 1;
		int maxWidth = 0;
		while (scanner.hasNextLine()) {
			final String line = scanner.nextLine();
			final Pattern pattern = Pattern.compile("[^#-]");
			final Matcher matcher = pattern.matcher(line);
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

	private void setLine(final World world, final int lineNumber, final String line) {
		for (int x = 0; x < line.length(); ++x) {
			if (line.charAt(x) == '#') {
				world.setAlive(x, lineNumber - 1);
			}
		}
	}
}