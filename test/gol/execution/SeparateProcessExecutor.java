package gol.execution;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.function.Consumer;

public class SeparateProcessExecutor implements Executor {

	private Process p = null;

	@Override
	public void executeProgram(String args, Result result) {
		try {
			String command = "java -cp bin/ gol.GameOfLife " + args;
			p = Runtime.getRuntime().exec(command);

			// Need to consume large outputs or else it will 
			// block the process.			
			consumeStream(p.getInputStream(), s -> result.addOutputLine(s));
			
			p.waitFor();

			consumeStream(p.getErrorStream(), s -> result.addErrorLine(s));
		
			result.setExitValue(p.exitValue());	
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void consumeStream(InputStream stream, Consumer<String> consumer) {
		Scanner scanner = new Scanner(stream);
		while (scanner.hasNextLine())
			consumer.accept(scanner.nextLine());
		scanner.close();
	}
}
