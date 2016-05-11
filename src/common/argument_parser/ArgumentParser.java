package common.argument_parser;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

public final class ArgumentParser {

	private final Map<String, Argument> argumentMap = new LinkedHashMap<>();
	private int maxParameterNameHelpWidth =
			calculateParameterNameHelpWidth(Optional.empty());

	public void registerArgument(final Argument arg) {
		argumentMap.put(arg.getFlag(), arg);
		maxParameterNameHelpWidth = Math.max(
				maxParameterNameHelpWidth,
				calculateParameterNameHelpWidth(arg.getParameterName()));
	}

	private static int calculateParameterNameHelpWidth(
			final Optional<String> parameterName) {
		final int parameterNameLength = parameterName.
				map(ArgumentParser::getParameterNameHelp).
				map(s -> s.length()).
				orElse(0);
		return parameterNameLength + 2;
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
			line("   " + getHelpString(argument));
		}
	}

	private String getHelpString(final Argument arg) {
		final String parameterNameHelp = arg.getParameterName().
				map(ArgumentParser::getParameterNameHelp).
				orElse("");
		final String spaces = getSpaces(
				maxParameterNameHelpWidth - parameterNameHelp.length());
		return arg.getFlag() + parameterNameHelp + spaces + arg.getHelp();
	}

	private static String getParameterNameHelp(final String parameterName) {
		return " <" + parameterName + ">";
	}

	private static String getSpaces(final int num) {
		String result = "";
		for (int i = 0; i < num; ++i) {
			result += " ";
		}
		return result;
	}

	private static void line(final String s) {
		System.out.println(s);
	}
}
