package gol;

import gol.util.DebugExecutor;
import gol.util.Executor;
import gol.util.Expectation;
import gol.util.SeparateProcessExecutor;
import gol.util.Text;
import gol.util.TimedExecutor;

import java.util.function.Consumer;

import org.junit.After;
import org.junit.Before;

public abstract class AbstractTest {

	private static final boolean DEBUG_MODE = false;

	private String args = "";
	private Expectation expectation;
	private Text output;

	protected void output(final String l) {
		output.ln(l);
	}

	protected void output() {
		output.ln();
	}

	protected void output(Consumer<Text> c) {
		output.add(c);
	}

	protected void executionTimeMustBeInRange(final int min, final int max) {
		expectation.executionTimeMin = min;
		expectation.executionTimeMax = max;
	}

	protected void givenProgramArgs(final String args) {
		this.args = args;
	}

	protected void outputHasStepCount(final int stepCount) {
		expectation.stepCount = stepCount;
	}

	protected void viewportHasHeight(int height) {
		expectation.height = height;
	}

	protected void viewportHasWidth(int width) {
		expectation.width = width;
	}

	@Before
	public void before() {
		expectation = new Expectation();
		output = new Text();
		args = "";
	}

	@After
	public void after() {
		expectation.output = output.text;
		Executor executor = getExecutor();
		executor.executeProgram(args);
		executor.assertExpectations(expectation);
	}

	protected Executor getExecutor() {
		if (DEBUG_MODE) {
			System.err.println("Warning! Running test in debug mode.");
			System.err.println("Alter with variable gol.AbstractTest.DEBUG_MODE.");
			System.err.println("");
			
			return new DebugExecutor();
		} else
			return new TimedExecutor(new SeparateProcessExecutor());
	}

}