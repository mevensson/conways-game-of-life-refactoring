package gol;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ArgumentParser {

	private final Map<String, Argument> argumentMap = new LinkedHashMap<>();

	public void registerArgument(final Argument argument) {
		argumentMap.put(argument.getFlag(), argument);
	}

	public void parse(final String[] args) throws Exception {
		final Queue<String> argList = new LinkedList<String>(Arrays.asList(args));
		while (argList.size() > 0) {
			parseArgument(argList);
		}
	}

	private void parseArgument(final Queue<String> argList) throws Exception {
		final String arg = argList.remove();
		final Argument argument = argumentMap.get(arg);
		if (argument != null) {
			argument.parse(argList);
		} else if (arg.equals("-?")) {
			throw new Exception("Help requested");
		} else {
			throw new Exception("Unknown argument " + arg);
		}
	}

	public void usage(final String message) {
		line(message);
		line("");
		line("Usage");
		line(" java gol.Main [ARGUMENTS...]");
		line("");
		line(" arguments:");
		line("   -?              Prints this usage help.");
		for (final Argument argument : argumentMap.values()) {
			line("   " + argument.getHelp());
		}
	}

	private static void line(final String s) {
		System.out.println(s);
	}
}
