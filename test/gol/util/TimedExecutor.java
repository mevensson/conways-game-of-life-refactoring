package gol.util;

import static org.junit.Assert.assertTrue;

public class TimedExecutor implements Executor {

	private Executor inner;
	private long executionTime;

	public TimedExecutor(Executor inner) {
		this.inner = inner;
	}

	@Override
	public void executeProgram(String args) {
		Long startTime = System.currentTimeMillis();
		inner.executeProgram(args);
		executionTime = System.currentTimeMillis() - startTime;
	}

	@Override
	public void assertExpectations(Expectation expects) {
		inner.assertExpectations(expects);
		
		if (expects.executionTimeMin != Expectation.IGNORE) {

			assertTrue("Expected execution time >= " + expects.executionTimeMin
					+ " ms, but was " + executionTime,
					executionTime >= expects.executionTimeMin);

			assertTrue("Expected execution time < " + expects.executionTimeMax
					+ " ms, but was " + executionTime,
					executionTime < expects.executionTimeMax);
		}
	}
}
