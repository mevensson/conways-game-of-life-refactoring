package gol.argument_parser;

import java.util.Optional;
import java.util.Queue;

public final class StringArgument extends Argument {

	private Optional<String> argument = Optional.empty();

	public StringArgument(final String flag, final String parameter,
			final String help) {
		super(flag, Optional.of(parameter), help);
	}

	public Optional<String> getArgument() {
		return argument;
	}

	@Override
	public void parse(final Queue<String> argList) {
		argument = Optional.of(argList.remove());
	}

}
