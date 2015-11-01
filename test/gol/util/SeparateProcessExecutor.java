package gol.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

public class SeparateProcessExecutor implements Executor {

	private OutputVerifier verifier = new OutputVerifier();
	private List<String> outputLines = null;
	private Process p = null;

	@Override
	public void executeProgram(String args) {
		try {
			String command = "java -cp bin/ gol.GameOfLife " + args;
			p = Runtime.getRuntime().exec(command);

			// Need to consume large outputs or else it will 
			// block the process.
			outputLines = verifier.consumeStream(p.getInputStream());
			
			p.waitFor();

		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void assertExpectations(Expectation expects) {
		List<String> errorLines = verifier.consumeStream(p.getErrorStream());
		verifier.verify(expects, outputLines, errorLines);
		assertEquals(0, p.exitValue());
	}
}
