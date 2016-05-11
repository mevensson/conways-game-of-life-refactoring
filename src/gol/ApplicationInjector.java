package gol;

import common.argument_parser.ArgumentParser;
import gol.game.GameOfLife;

public class ApplicationInjector {

	public static GameOfLifeApplication injectGameOfLifeApplication(
			final ApplicationScope scope) {
		return new GameOfLifeApplication(
				injectGameOfLifeScopeEntrance(),
				injectArguments(scope),
				injectArgumentParser(scope),
				scope.getArgs());
	}

	private static ScopeEntrance<GameOfLife, GameOfLifeScope>
			injectGameOfLifeScopeEntrance() {
		return (scope) -> GameOfLifeInjector.injectGameOfLife(scope);
	}

	private static Arguments injectArguments(final ApplicationScope scope) {
		return new Arguments(injectArgumentParser(scope));
	}

	private static ArgumentParser injectArgumentParser(
			final ApplicationScope scope) {
		return scope.getArgumentsParser(ArgumentParser::new);
	}
}
