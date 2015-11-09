package gol.execution;

import java.util.ArrayList;
import java.util.Collection;

public interface Result {
	
	void addOutputLine(String line);
	Iterable<String> getOutputLines();
	
	void addErrorLine(String line);
	Iterable<String> getErrorLines();
	
	void setExitValue(int exitValue);
	int getExitValue();

	void setExecutionTimeMillis(long milliseconds);
	long getExecutionTimeMillis();
	
	
	static class Empty implements Result {
		
		private Collection<String> outputLines = new ArrayList<String>();
		private Collection<String> errorLines  = new ArrayList<String>();
		private int exitValue = 0;
		private long milliseconds;

		@Override
		public void addOutputLine(String line) {
			outputLines.add(line);
		}

		@Override
		public Iterable<String> getOutputLines() {
			return outputLines;
		}

		@Override
		public void addErrorLine(String line) {
			errorLines.add(line);
		}

		@Override
		public Iterable<String> getErrorLines() {
			return errorLines;
		}

		@Override
		public void setExitValue(int exitValue) {
			this.exitValue  = exitValue;
		}

		@Override
		public int getExitValue() {
			return exitValue;
		}

		@Override
		public void setExecutionTimeMillis(long milliseconds) {
			this.milliseconds = milliseconds;
		}

		@Override
		public long getExecutionTimeMillis() {
			return milliseconds;
		}
	}
	
	static abstract class AbstractDecoratorResult implements Result {
		
		private Result inner;

		AbstractDecoratorResult(Result inner) {
			this.inner = inner;
		}
		
		@Override
		public void addOutputLine(String line) {
			inner.addOutputLine(line);
		}

		@Override
		public Iterable<String> getOutputLines() {
			return inner.getOutputLines();
		}

		@Override
		public void addErrorLine(String line) {
			inner.addErrorLine(line);
		}

		@Override
		public Iterable<String> getErrorLines() {
			return inner.getErrorLines();
		}

		@Override
		public void setExitValue(int exitValue) {
			inner.setExitValue(exitValue);
		}

		@Override
		public int getExitValue() {
			return inner.getExitValue();
		}

		@Override
		public void setExecutionTimeMillis(long milliseconds) {
			inner.setExecutionTimeMillis(milliseconds);
		}

		@Override
		public long getExecutionTimeMillis() {
			return inner.getExecutionTimeMillis();
		}
	}
	
	static class IgnoreOutAndErrorLinesResult extends AbstractDecoratorResult {

		IgnoreOutAndErrorLinesResult(Result inner) {
			super(inner);
		}

		@Override
		public void addOutputLine(String line) {
			// do noting
		}
		
		@Override
		public void addErrorLine(String line) {
			// do noting
		}
	}
}
