package gol.util;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class OutputVerifier {

	private static class OutputData {
		int stepsCount = 0;
		int height = 0;
		int width = 0;
	}

	private static final Pattern STEP_PATTERN = Pattern.compile("step \\d+.*");
	private static final Pattern START_PATTERN = Pattern.compile("start");

	public void verify(Expectation expects, List<String> outputLines,
			List<String> errorLines) {
		String errorText = toSingleString(errorLines);
		assertEquals(Expectation.NOTHING, errorText);

		if (expects.output != Expectation.NOTHING) {
			String outText = toSingleString(outputLines);
			assertEquals("Output missmatch", expects.output, outText);
		}

		OutputData actual = interpretOutput(outputLines);
		assertNonIgnored("Step count", expects.stepCount, actual.stepsCount);
		assertNonIgnored("Viewport width", expects.width, actual.width);
		assertNonIgnored("Viewport height", expects.height, actual.height);
	}

	public List<String> consumeStream(InputStream stream) {
		Scanner scanner = new Scanner(stream);
		List<String> result = new ArrayList<>();
		while (scanner.hasNextLine())
			result.add(scanner.nextLine());
		scanner.close();
		return result;
	}

	private String toSingleString(List<String> lines) {
		if (lines.isEmpty())
			return Expectation.NOTHING;

		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line);
			sb.append(System.lineSeparator());
		}

		return sb.toString();
	}

	private OutputData interpretOutput(List<String> lines) {

		OutputData data = new OutputData();

		for (String line : lines)
			data.stepsCount += isStepLine(line) ? 1 : 0;

		for (String line : lines) {
			boolean isStartLine = isStartLine(line);
			if (isStartLine)
				break;

			data.width = line.length();
			data.height++;
		}

		return data;
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
}
