package gol;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringArrayWorld implements World {
	private List<String> world;
	private int heightOffset;
	private int widthOffset;

	public StringArrayWorld(String filePath) throws FileNotFoundException {
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

	public StringArrayWorld(int width, int height) {
		world = new ArrayList<String>();

		Random rand = new Random();
		for (int h = 0; h < height; h++) {
			String line = "";
			for (int w = 0; w < width; w++) {

				line += rand.nextBoolean() ? '#' : '-';
			}
			world.add(line);
		}
	}

	public StringArrayWorld(List<String> world, int heightOffset, int widthOffset) {
		this.world = world;
		this.heightOffset = heightOffset;
		this.widthOffset = widthOffset;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StringArrayWorld other = (StringArrayWorld) obj;
		if (heightOffset != other.heightOffset)
			return false;
		if (widthOffset != other.widthOffset)
			return false;
		if (world == null) {
			if (other.world != null)
				return false;
		} else if (!world.equals(other.world))
			return false;
		return true;
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

	public StringArrayWorld step() {
		StringArrayWorld newWorld = new StringArrayWorld(new ArrayList<>(),
				heightOffset, widthOffset);

		for (int h = 0; h < height(); h++) {
			String line = "";
			for (int w = 0; w < world.get(0).length(); w++) {
				int n = countAliveNeighbors(h, w);

				char cell = '-';

				if (isAliveLocal(w, h)) {
					if (n == 2 || n == 3)
						cell = '#';
				} else {
					if (n == 3)
						cell = '#';
				}

				line += cell;
			}
			newWorld.world.add(line);
		}
		return newWorld;
	}

	private int countAliveNeighbors(int h, int w) {
		int n = 0;

		if (h != 0 && w != 0 && isAliveLocal(w - 1, h - 1))
			n++;
		if (h != 0 && isAliveLocal(w, h - 1))
			n++;
		if (h != 0 && w != world.get(0).length() - 1
				&& isAliveLocal(w + 1, h - 1))
			n++;

		if (w != 0 && isAliveLocal(w - 1, h))
			n++;

		if (w != world.get(0).length() - 1 && isAliveLocal(w + 1, h))
			n++;

		if (h != height() - 1 && w != 0
				&& isAliveLocal(w - 1, h + 1))
			n++;
		if (h != height() - 1 && isAliveLocal(w, h + 1))
			n++;
		if (h != height() - 1
				&& w != world.get(0).length() - 1
				&& isAliveLocal(w + 1, h + 1))
			n++;
		return n;
	}

	private boolean isAliveLocal(int x, int y) {
		char c = world.get(y).charAt(x);

		return c == '#';
	}

	@Override
	public boolean isAlive(int x, int y) {
		if (y < heightOffset || y >= heightOffset + height()) {
			return false;
		}

		if (x < widthOffset || x >= widthOffset + width()) {
			return false;
		}

		return isAliveLocal(x - widthOffset, y - heightOffset);
	}
}
