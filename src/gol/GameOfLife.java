package gol;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameOfLife {

	static void line(String s) {
		System.out.println(s);
	}

	static class History {
		List<String> world;
		int heightOffset;
		int widthOffset;

		History(List<String> world, int heightOffset, int widthOffset) {
			this.world = world;
			this.heightOffset = heightOffset;
			this.widthOffset = widthOffset;
		}

//		@Override
//		public int hashCode() {
//			final int prime = 31;
//			int result = 1;
//			result = prime * result + heightOffset;
//			result = prime * result + widthOffset;
//			result = prime * result + ((world == null) ? 0 : world.hashCode());
//			return result;
//		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			History other = (History) obj;
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
	}

	int steps = -1;
	List<String> world = null;
	int height = -1;
	int width = -1;
	int heightOffset = 0;
	private List<History> history = new LinkedList<History>();
	private int widthOffset = 0;
	private static long computationTimeStart;
	static int stepCount = 0;
	int historyLength;
	int stepDelay = -1;
	boolean quietMode = false;
	boolean isAtSigns = false;
	boolean isOSigns = false;

	public static void main(String[] args) {
		GameOfLife game = new GameOfLife();

		computationTimeStart = System.currentTimeMillis();

		ArgumentParser parser = new ArgumentParser(args);

		if (parser.parse(game)) {
			if (game.world == null) {
				game.world = new ArrayList<String>();

				game.height = game.height == -1 ? 15 : game.height;
				game.width = game.width == -1 ? 20 : game.width;

				Random rand = new Random();
				for (int h = 0; h < game.height; h++) {
					String line = "";
					for (int w = 0; w < game.width; w++) {

						line += rand.nextBoolean() ? '#' : '-';
					}
					game.world.add(line);
				}
			}

			if (game.steps == -1)
				game.steps = 100;

			game.runSimulation();
		}
	}

	void printWorldLine(String line) {
		if (isAtSigns)
			System.out.println(line.replace("#", "@ ").replace("-", ". "));
		else if (isOSigns)
			System.out.println(line.replace("#", "O"));
		else
			System.out.println(line);
	}

	public boolean isAlive(int x, int y) {
		char c = world.get(y).charAt(x);

		if (c == '#')
			return true;
		else
			return false;
	}

	String emptyLine() {
		if (world.isEmpty())
			return "";
		String result = "";
		while (result.length() < world.get(0).length())
			result += '-';
		return result;
	}

	void addMarginsToWorld() {
		world.add(emptyLine());
		world.add(0, emptyLine());
		heightOffset--;

		for (int i = 0; i < world.size(); i++) {
			String line = world.get(i);
			world.set(i, '-' + line + '-');
		}
		widthOffset--;
	}

	boolean isColumnEmpty(int column) {

		for (int i = 0; i < world.size(); i++) {
			if (world.get(i).charAt(column) == '#')
				return false;
		}
		return true;
	}

	void stripMarginsFromWorld() {
		while (!world.isEmpty() && world.get(0).equals(emptyLine())) {
			world.remove(0);
			heightOffset++;
		}
		while (!world.isEmpty()
				&& world.get(world.size() - 1).equals(emptyLine())) {
			world.remove(world.size() - 1);
		}

		while (!world.isEmpty() && world.get(0).length() != 0
				&& isColumnEmpty(0)) {
			for (int i = 0; i < world.size(); i++) {
				String line = world.get(i);
				world.set(i, line.substring(1));
			}
			widthOffset++;
		}

		while (!world.isEmpty() && world.get(0).length() != 0
				&& isColumnEmpty(world.get(0).length() - 1)) {
			for (int i = 0; i < world.size(); i++) {
				String line = world.get(i);
				world.set(i, line.substring(0, world.get(i).length() - 1));
			}
		}
	}

	private void runSimulation() {

		while (stepCount <= steps) {

			if (stepCount != 0) {

				addMarginsToWorld();

				List<String> newWorld = new ArrayList<>();
				int newHeightOffset = heightOffset;
				int newWidthtOffset = widthOffset;

				for (int h = 0; h < world.size(); h++) {
					String line = "";
					for (int w = 0; w < world.get(0).length(); w++) {
						/* count alive neighbors */
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

						if (h != world.size() - 1 && w != 0
								&& isAlive(w - 1, h + 1))
							n++;
						if (h != world.size() - 1 && isAlive(w, h + 1))
							n++;
						if (h != world.size() - 1
								&& w != world.get(0).length() - 1
								&& isAlive(w + 1, h + 1))
							n++;

						char cell = '-';

						if (isAlive(w, h)) {
							if (n == 2 || n == 3)
								cell = '#';
						} else {
							if (n == 3)
								cell = '#';
						}

						line += cell;
					}
					newWorld.add(line);
				}

				stripMarginsFromWorld();

				history.add(0, new History(world, heightOffset, widthOffset));
				if (history.size() == historyLength + 1)
					history.remove(historyLength);

				world = newWorld;
				heightOffset = newHeightOffset;
				widthOffset = newWidthtOffset;

				stripMarginsFromWorld();

			}
			
			String loopDetection = "";

			int index = history.indexOf(new History(world, heightOffset,
					widthOffset));
			if (index != -1) {
				loopDetection = " - loop of length " + (index + 1)
						+ " detected";
			}

			String linePrefix = "";

			for (int i = 0; i < widthOffset; i++) {
				linePrefix += '-';
			}

			String lineSuffix = "";

			int worldWidth = world.isEmpty() ? 0 : world.get(0).length();
			for (int i = 0; i < width - worldWidth - widthOffset; i++) {
				lineSuffix += '-';
			}

			int printHeight = 0;

			if (!quietMode || stepCount == steps || !loopDetection.isEmpty()) {
				for (int i = 0; i < Math.min(heightOffset, height); i++) {
					String line = "";
					while (line.length() < width) {
						line += '-';
					}
					printWorldLine(line);
					printHeight++;
				}

				for (int i = Math.max(0, -heightOffset); i < world.size(); i++) {

					if (printHeight == height)
						break;
					String line = world.get(i);

					line = linePrefix + line + lineSuffix;

					if (widthOffset < 0)
						line = line.substring(-widthOffset);

					if (line.length() > width)
						line = line.substring(0, width);
					printWorldLine(line);
					printHeight++;
				}

				for (; printHeight < height; printHeight++) {
					String line = "";
					while (line.length() < width) {
						line += '-';
					}
					printWorldLine(line);
				}

			

				if (stepCount == 0) {
					System.out.println("start");
				} else
					line("step " + stepCount + loopDetection);
				System.out.println();
			}

			stepCount++;

			long computationTime = System.currentTimeMillis()
					- computationTimeStart;

			// long computationTime = 0;
			if (!quietMode && stepDelay - computationTime >= 0) {
				try {

					Thread.sleep(stepDelay - computationTime);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}

			computationTimeStart = System.currentTimeMillis();

			if (!loopDetection.isEmpty()) {
				break;
			}
		}
	}

	void parseFile(String filePath) throws FileNotFoundException {
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
		if (height == -1)
			height = world.size();
		if (width == -1)
			width = world.isEmpty() ? 0 : world.get(0).length();
	}
}
