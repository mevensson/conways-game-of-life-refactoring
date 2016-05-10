package gol;

import static gol.ApplicationInjector.injectGameOfLifeApplication;

public class Main {
	public static void main(final String[] args) {
		final ApplicationScope scope = new ApplicationScope(args);
		final GameOfLifeApplication application = injectGameOfLifeApplication(scope);
		application.run();
	}
}
