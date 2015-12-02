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

	public void addMargins() {
		world.add(emptyLine());
		world.add(0, emptyLine());
		heightOffset--;
	
		for (int i = 0; i < height(); i++) {
			String line = world.get(i);
			world.set(i, '-' + line + '-');
		}
		widthOffset--;
	}

	private String emptyLine() {
		if (world.isEmpty())
			return "";
		String result = "";
		while (result.length() < world.get(0).length())
			result += '-';
		return result;
	}

	public void stripMargins() {
		while (!world.isEmpty() && world.get(0).equals(emptyLine())) {
			world.remove(0);
			heightOffset++;
		}
		while (!world.isEmpty()
				&& world.get(height() - 1).equals(emptyLine())) {
			world.remove(height() - 1);
		}
	
		while (!world.isEmpty() && world.get(0).length() != 0
				&& isColumnEmpty(0)) {
			for (int i = 0; i < height(); i++) {
				String line = world.get(i);
				world.set(i, line.substring(1));
			}
			widthOffset++;
		}
	
		while (!world.isEmpty() && world.get(0).length() != 0
				&& isColumnEmpty(world.get(0).length() - 1)) {
			for (int i = 0; i < height(); i++) {
				String line = world.get(i);
				world.set(i, line.substring(0, world.get(i).length() - 1));
			}
		}
	}

	private boolean isColumnEmpty(int column) {
		for (int i = 0; i < height(); i++) {
			if (world.get(i).charAt(column) == '#')
				return false;
		}
		return true;
	}

	boolean isAlive(int x, int y) {
		char c = world.get(y).charAt(x);
	
		if (c == '#')
			return true;
		else
			return false;
	}

	int countAliveNeighbors(int h, int w) {
		int n = 0;
	
		if (h != 0 && w != 0 && isAlive(w - 1, h - 1))
			n++;
		if (h != 0 && isAlive(w, h - 1))
			n++;
		if (h != 0 && w != world.get(0).length() - 1
				&& isAlive(w + 1, h - 1))
			n++;
	
		if (w != 0 && isAlive(w - 1, h))
			n++;
	
		if (w != world.get(0).length() - 1 && isAlive(w + 1, h))
			n++;
	
		if (h != height() - 1 && w != 0
				&& isAlive(w - 1, h + 1))
			n++;
		if (h != height() - 1 && isAlive(w, h + 1))
			n++;
		if (h != height() - 1
				&& w != world.get(0).length() - 1
				&& isAlive(w + 1, h + 1))
			n++;
		return n;
	}
}
