package gol;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentParser {

	List<String> argList;

	public ArgumentParser(String[] args) {
		argList = new LinkedList<String>(Arrays.asList(args));
	}

	static void line(String s) {
		System.out.println(s);
	}

	private String getArg() {
		String arg = argList.get(0);
		argList.remove(0);
		return arg;
	}

	private int getIntArg() {
		String arg = getArg();
		int n = Integer.parseInt(arg);
		if (n < 0)
			throw new RuntimeException("Invalid argument value " + n);
		return n;
	}

	public boolean parse(GameOfLife game) {
		try {
			while (argList.size() > 0) {
				String arg = getArg();
				switch (arg) {
				case "-s":
					game.steps = getIntArg();
					break;
				case "-f":
					String filePath = getArg();
					File file = new File(filePath);
					@SuppressWarnings("resource")
					Scanner scanner = new Scanner(file);
					ArrayList<String> world = new ArrayList<String>();
					game.world = world;
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
						game.world = world;
						lineNumber++;
					}
					for (int i = 0; i < world.size(); ++i) {
						String line = world.get(i);

						while (line.length() < maxWidth)
							line += '-';

						world.set(i, line);
					}
					if (game.height == -1)
						game.height = world.size();
					if (game.width == -1)
						game.width = world.isEmpty() ? 0 : world.get(0)
								.length();
					break;
				case "-?":
					throw new Exception("Help requested");
				case "-@":
					game.isAtSigns = true;
					break;
				case "-O":
					game.isOSigns = true;
					break;
				case "-w":
					game.width = getIntArg();
					break;
				case "-h":
					game.height = getIntArg();
					break;
				case "-l":
					game.historyLength = getIntArg();
					break;
				case "-t":
					game.stepDelay = getIntArg();
					break;
				case "-q":
					game.quietMode = true;
					break;
				default:
					throw new Exception("Unknown argument " + arg);
				}
			}
		} catch (Exception e) {
			usage(e.getMessage());
			return false;
		}
		return true;
	}

	private void usage(String message) {
		line(message);
		line("");
		line("Usage");
		line(" java gol.GameOfLife [ARGUMENTS...]");
		line("");
		line(" arguments:");
		line("   -?              Prints this usage help.");
		line("   -w <WIDTH>      Width of simulation view port. Default is 20.");
		line("   -h <HEIGHT>     Height of simulation view port. Default is 15.");
		line("   -f <FILE_PATH>  File with start state. Default is a random start state.");
		line("   -@              Use spaced '@' and '.' instead of default '#' and '-'.");
		line("   -O              Use 'O' instead of default '#'.");
		line("   -s <STEPS>      Number of maximum generation steps. Default is 100.");
		line("   -l <X>          Detect loops of maximum length x. Default is 0 - no loop detection.");
		line("   -t <MS>         Time delay (ms) to wait between each step. Default is 0 ms.");
		line("   -q              Quiet mode. Only outputs the last step in a simulation. Ignores time delay.");
	}
}
