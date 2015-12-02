package gol;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameOfLife {

	private static long computationTimeStart;

	public static void main(String[] args) {
		GameOfLife game = new GameOfLife();

		computationTimeStart = System.currentTimeMillis();

		ArgumentParser parser = new ArgumentParser(args);

		if (parser.parse(game)) {
			if (game.data.world == null) {
				game.data.world = new ArrayList<String>();

				game.height = game.height == -1 ? 15 : game.height;
				game.width = game.width == -1 ? 20 : game.width;

				Random rand = new Random();
				for (int h = 0; h < game.height; h++) {
					String line = "";
					for (int w = 0; w < game.width; w++) {

						line += rand.nextBoolean() ? '#' : '-';
					}
					game.data.world.add(line);
				}
			}

			if (game.steps == -1)
				game.steps = 100;

			game.runSimulation();
		}
	}

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

	private int height = -1;
	private int width = -1;
	private int steps = -1;
	private int stepDelay = -1;
	private boolean quietMode = false;
	private boolean isAtSigns = false;
	private boolean isOSigns = false;
	private int historyLength;

	private World data = new World(null, 0, 0);
	private List<History> history = new LinkedList<History>();
	private int stepCount = 0;

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public void setStepDelay(int stepDelay) {
		this.stepDelay = stepDelay;
	}

	public void setHistoryLength(int historyLength) {
		this.historyLength = historyLength;
	}

	public void setQuietMode(boolean quietMode) {
		this.quietMode = quietMode;
	}

	public void setAtSigns(boolean isAtSigns) {
		this.isAtSigns = isAtSigns;
	}

	public void setOSigns(boolean isOSigns) {
		this.isOSigns = isOSigns;
	}

	public void parseFile(String filePath) throws FileNotFoundException {
		data = new World(filePath);
		if (height == -1)
			height = data.height();
		if (width == -1)
			width = data.width();
	}

	public void runSimulation() {

		while (stepCount <= steps) {

			if (stepCount != 0) {

				addMarginsToWorld();

				List<String> newWorld = new ArrayList<>();
				int newHeightOffset = data.heightOffset;
				int newWidthtOffset = data.widthOffset;

				for (int h = 0; h < data.height(); h++) {
					String line = "";
					for (int w = 0; w < data.world.get(0).length(); w++) {
						int n = countAliveNeighbors(h, w);

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

				history.add(0, new History(data.world, data.heightOffset, data.widthOffset));
				if (history.size() == historyLength + 1)
					history.remove(historyLength);

				data.world = newWorld;
				data.heightOffset = newHeightOffset;
				data.widthOffset = newWidthtOffset;

				stripMarginsFromWorld();
			}
			
			String loopDetection = "";

			int index = history.indexOf(new History(data.world, data.heightOffset,
					data.widthOffset));
			if (index != -1) {
				loopDetection = " - loop of length " + (index + 1)
						+ " detected";
			}

			String linePrefix = "";

			for (int i = 0; i < data.widthOffset; i++) {
				linePrefix += '-';
			}

			String lineSuffix = "";

			int worldWidth = data.width();
			for (int i = 0; i < width - worldWidth - data.widthOffset; i++) {
				lineSuffix += '-';
			}

			int printHeight = 0;

			if (!quietMode || stepCount == steps || !loopDetection.isEmpty()) {
				for (int i = 0; i < Math.min(data.heightOffset, height); i++) {
					String line = "";
					while (line.length() < width) {
						line += '-';
					}
					printWorldLine(line);
					printHeight++;
				}

				for (int i = Math.max(0, -data.heightOffset); i < data.height(); i++) {

					if (printHeight == height)
						break;
					String line = data.world.get(i);

					line = linePrefix + line + lineSuffix;

					if (data.widthOffset < 0)
						line = line.substring(-data.widthOffset);

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

	private int countAliveNeighbors(int h, int w) {
		int n = 0;

		if (h != 0 && w != 0 && isAlive(w - 1, h - 1))
			n++;
		if (h != 0 && isAlive(w, h - 1))
			n++;
		if (h != 0 && w != data.world.get(0).length() - 1
				&& isAlive(w + 1, h - 1))
			n++;

		if (w != 0 && isAlive(w - 1, h))
			n++;

		if (w != data.world.get(0).length() - 1 && isAlive(w + 1, h))
			n++;

		if (h != data.height() - 1 && w != 0
				&& isAlive(w - 1, h + 1))
			n++;
		if (h != data.height() - 1 && isAlive(w, h + 1))
			n++;
		if (h != data.height() - 1
				&& w != data.world.get(0).length() - 1
				&& isAlive(w + 1, h + 1))
			n++;
		return n;
	}
	
	private void printWorldLine(String line) {
		if (isAtSigns)
			System.out.println(line.replace("#", "@ ").replace("-", ". "));
		else if (isOSigns)
			System.out.println(line.replace("#", "O"));
		else
			System.out.println(line);
	}

	private boolean isAlive(int x, int y) {
		char c = data.world.get(y).charAt(x);

		if (c == '#')
			return true;
		else
			return false;
	}

	private void addMarginsToWorld() {
		data.world.add(data.emptyLine());
		data.world.add(0, data.emptyLine());
		data.heightOffset--;

		for (int i = 0; i < data.height(); i++) {
			String line = data.world.get(i);
			data.world.set(i, '-' + line + '-');
		}
		data.widthOffset--;
	}

	private boolean isColumnEmpty(int column) {

		for (int i = 0; i < data.height(); i++) {
			if (data.world.get(i).charAt(column) == '#')
				return false;
		}
		return true;
	}

	private void stripMarginsFromWorld() {
		while (!data.world.isEmpty() && data.world.get(0).equals(data.emptyLine())) {
			data.world.remove(0);
			data.heightOffset++;
		}
		while (!data.world.isEmpty()
				&& data.world.get(data.height() - 1).equals(data.emptyLine())) {
			data.world.remove(data.height() - 1);
		}

		while (!data.world.isEmpty() && data.world.get(0).length() != 0
				&& isColumnEmpty(0)) {
			for (int i = 0; i < data.height(); i++) {
				String line = data.world.get(i);
				data.world.set(i, line.substring(1));
			}
			data.widthOffset++;
		}

		while (!data.world.isEmpty() && data.world.get(0).length() != 0
				&& isColumnEmpty(data.world.get(0).length() - 1)) {
			for (int i = 0; i < data.height(); i++) {
				String line = data.world.get(i);
				data.world.set(i, line.substring(0, data.world.get(i).length() - 1));
			}
		}
	}
}
