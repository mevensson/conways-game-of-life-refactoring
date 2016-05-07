package gol.argument_parser;

import java.util.Optional;
import java.util.Queue;

public abstract class Argument {

	private final String flag;
	private final Optional<String> parameter;
	private final String help;

	public Argument(final String flag, final Optional<String> parameter,
			final String help) {
		this.flag = flag;
		this.parameter = parameter;
		this.help = help;
	}

	public final String getFlag() {
		return flag;
	}

	public final Optional<String> getParameterName() {
		return parameter;
	}

	public final String getHelp() {
		return help;
	}

	public abstract void parse(Queue<String> argList);
}