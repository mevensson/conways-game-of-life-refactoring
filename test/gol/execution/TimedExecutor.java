package gol.execution;

public class TimedExecutor implements Executor {

	private Executor inner;

	public TimedExecutor(Executor inner) {
		this.inner = inner;
	}

	@Override
	public void executeProgram(String args, Result result) {
		long startTime = System.currentTimeMillis();
		
		inner.executeProgram(args, result);
		
		long executionTime = System.currentTimeMillis() - startTime;
		result.setExecutionTimeMillis(executionTime);
	}
}
