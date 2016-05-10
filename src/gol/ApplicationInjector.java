package gol;

import gol.argument_parser.ArgumentParser;

public class ApplicationInjector {

	public static GameOfLifeApplication injectGameOfLifeApplication(
			final ApplicationScope scope) {
		return new GameOfLifeApplication(
				injectArguments(scope),
				injectArgumentParser(scope),
				scope.getArgs());
	}

	private static Arguments injectArguments(final ApplicationScope scope) {
		return new Arguments(injectArgumentParser(scope));
	}

	private static ArgumentParser injectArgumentParser(
			final ApplicationScope scope) {
		return scope.getArgumentsParser(ArgumentParser::new);
	}
}
