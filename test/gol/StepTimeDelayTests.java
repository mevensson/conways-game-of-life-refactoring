package gol;

import org.junit.Test;

import static gol.OutputPrimitives.*;

public class StepTimeDelayTests extends AbstractTest {
	
	@Test
	public void usingTimeSteps() {
		givenProgramArgs("-f examples/blinker -s 4 -t 200");

		executionTimeMustBeInRange(1000, 1100);

		output(blinkerHorizontal);
		output(start);

		output(blinkerVertical);
		output(step(1));

		output(blinkerHorizontal);
		output(step(2));

		output(blinkerVertical);
		output(step(3));

		output(blinkerHorizontal);
		output(step(4));
	}

	@Test
	public void largeSimulationsHaveCalculationTimeCompensationInFirstTimeStep() {
		givenProgramArgs("-f examples/largeGlidersAndBlinkers -s 0 -t 300");

		executionTimeMustBeInRange(300, 500);
		outputHasStepCount(0);
	}

	@Test
	public void largeSimulationsHaveCalculationTimeCompensationForAllTimeSteps() {
		givenProgramArgs("-f examples/largeGlidersAndBlinkers -s 3 -t 300");

		executionTimeMustBeInRange(1200, 1400);
		outputHasStepCount(3);
	}
	
	@Test
	public void timeDriftIsVeryLittleForManyTimeSteps() {
		givenProgramArgs("-s 200 -t 5 -f examples/blinker");
		
		executionTimeMustBeInRange(1000, 1200);
		outputHasStepCount(200);
	}
}
