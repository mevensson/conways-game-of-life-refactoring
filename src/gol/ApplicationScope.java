package gol;

import java.util.function.Supplier;

import common.argument_parser.ArgumentParser;

public class ApplicationScope {

	private final String[] args;

	private final Cache<ArgumentParser> argumentParserCache = new Cache<>();

	public ApplicationScope(final String[] args) {
		this.args = args;
	}

	public String[] getArgs() {
		return args;
	}

	public ArgumentParser getArgumentsParser(final Supplier<ArgumentParser> supplier) {
		return argumentParserCache.get(supplier);
	}
}
