package gol;

import static gol.OutputPrimitives.*;

import org.junit.Test;

public class QuietModeTest extends AbstractTest {

	@Test
	public void quietModeOnlyDisplaysLastStep() {
		givenProgramArgs(" -q -s 55 -f examples/block");

		output(block);
		output(step(55));

		outputHasStepCount(1);
	}

	@Test
	public void quietModeOnlyDisplaysLastStepAlsoWhenLoopDetected() {
		givenProgramArgs(" -s 100 -l 100 -f examples/pentadecathlon -q ");

		output("----------------");
		output("----------------");
		output("----------------");
		output("-----#----#-----");
		output("---##-####-##---");
		output("-----#----#-----");
		output("----------------");
		output("----------------");
		output("----------------");
		output("step 15 - loop of length 15 detected");
		output();

		outputHasStepCount(1);
	}

	@Test
	public void quietModeCompletelyIgnoresTimeStep() {
		givenProgramArgs(" -s 8  -f examples/block -q -t 500 ");

		output(block);
		output(step(8));
		
		executionTimeMustBeInRange(0, 499);
	}
}
