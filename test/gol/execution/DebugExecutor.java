package gol.execution;

import gol.GameOfLife;
import gol.util.Expectation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * <p>
 * This debug executor just calls the method {@link GameOfLife#main(String[])}
 * with given arguments, to enable easy debugging and code coverage analysis.
 * </p>
 * 
 * <p>
 * No output is collected. The method {@link #assertExpectations(Expectation)}
 * does not do any assertions.
 * </p>
 * 
 * <p>
 * Calling {@link #executeProgram(String)} multiple times in the same runtime
 * can be a pitfall, because {@link GameOfLife} may have static variables that
 * are affected from previous calls.
 * </p>
 * 
 */
public class DebugExecutor implements Executor {

	@Override
	public void executeProgram(String args, Result result) {
		GameOfLife.main(tokenize(args));
	}

	private String[] tokenize(String rawText) {
		Scanner scanner = new Scanner(rawText);
		List<String> list = new ArrayList<String>();
		while (scanner.hasNext())
			list.add(scanner.next());
		scanner.close();
		return collectionAsArray(list);
	}

	private String[] collectionAsArray(Collection<String> list) {
		return list.toArray(new String[0]);
	}

}
