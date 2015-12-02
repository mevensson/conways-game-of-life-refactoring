package gol;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class World {
	public List<String> world;
	public int heightOffset;
	public int widthOffset;

	public World(String filePath) throws FileNotFoundException {
		heightOffset = 0;
		widthOffset = 0;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(new File(filePath));
		world = new ArrayList<String>();
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

			world.add(line);
			lineNumber++;
		}
		for (int i = 0; i < world.size(); ++i) {
			String line = world.get(i);

			while (line.length() < maxWidth)
				line += '-';

			world.set(i, line);
		}
	}
	
	public World(List<String> world, int heightOffset, int widthOffset) {
		this.world = world;
		this.heightOffset = heightOffset;
		this.widthOffset = widthOffset;
	}

	public int height() {
		return world.size();
	}

	public int width() {
		return world.isEmpty() ? 0 : world.get(0).length();
	}

	String emptyLine() {
		if (world.isEmpty())
			return "";
		String result = "";
		while (result.length() < world.get(0).length())
			result += '-';
		return result;
	}
}
