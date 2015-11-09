package gol.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gol.execution.Result;

import java.util.regex.Pattern;

public class OutputVerifier {

	private static class OutputMetrics {
		int stepsCount = 0;
		int height = 0;
		int width = 0;
	}

	private static final Pattern STEP_PATTERN = Pattern.compile("step \\d+.*");
	private static final Pattern START_PATTERN = Pattern.compile("start");

	public void verify(Expectation expectation, Result result) {

		assertEquals(0, result.getExitValue());

		if (expectation.executionTimeMin != Expectation.IGNORE) {
			verifyExecutionTimeIsInRange(expectation, result);
		}

		verify(expectation, result.getOutputLines(), result.getErrorLines());

	}

	private void verify(Expectation expects, Iterable<String> outputLines,
			Iterable<String> errorLines) {

		String errorText = toSingleString(errorLines);
		assertEquals(Expectation.NOTHING, errorText);

		if (expects.output != Expectation.NOTHING) {
			String outText = toSingleString(outputLines);
			assertEquals("Output missmatch", expects.output, outText);
		}

		OutputMetrics actual = interpretOutput(outputLines);
		assertNonIgnored("Step count", expects.stepCount, actual.stepsCount);
		assertNonIgnored("Viewport width", expects.width, actual.width);
		assertNonIgnored("Viewport height", expects.height, actual.height);
	}

	private String toSingleString(Iterable<String> lines) {
		if (!lines.iterator().hasNext())
			return Expectation.NOTHING;

		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line);
			sb.append(System.lineSeparator());
		}

		return sb.toString();
	}

	private OutputMetrics interpretOutput(Iterable<String> lines) {

		OutputMetrics metrics = new OutputMetrics();

		for (String line : lines)
			metrics.stepsCount += isStepLine(line) ? 1 : 0;

		for (String line : lines) {
			boolean isStartLine = isStartLine(line);
			if (isStartLine)
				break;

			metrics.width = line.length();
			metrics.height++;
		}

		return metrics;
	}

	private boolean isStartLine(String line) {
		return START_PATTERN.matcher(line).matches();
	}

	private boolean isStepLine(String line) {
		return STEP_PATTERN.matcher(line).matches();
	}

	private void assertNonIgnored(String message, int expected, int actual) {
		if (expected != Expectation.IGNORE)
			assertEquals(message, expected, actual);
	}



	private void verifyExecutionTimeIsInRange(Expectation expectation,
			Result result) {
		long actual = result.getExecutionTimeMillis();
		long expectedMin = expectation.executionTimeMin;
		long expectedMax = expectation.executionTimeMax;

		assertTrue("Expected execution time >= " + expectedMin
				+ " ms, but was " + actual, actual >= expectedMin);

		assertTrue("Expected execution time < " + expectedMax + " ms, but was "
				+ actual, actual < expectedMax);
	}
}
