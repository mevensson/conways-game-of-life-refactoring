package gol;

public class GameOfLife {
	private final History history;
	private final StepDelayer stepDelayer;
	private final LoopDetector loopDetector;
	private final WorldPrinter worldPrinter;
	private final int maxSteps;
	private final boolean quietMode;

	private World world;
	private int stepCount = 0;

	public GameOfLife(World world, History history, StepDelayer stepDelayer,
			LoopDetector loopDetector, WorldPrinter worldPrinter,
			int maxSteps, boolean quietMode) {
		this.world = world;
		this.history = history;
		this.stepDelayer = stepDelayer;
		this.loopDetector = loopDetector;
		this.worldPrinter = worldPrinter;
		this.maxSteps = maxSteps;
		this.quietMode = quietMode;
	}

	public void runSimulation() {
		while (stepCount <= maxSteps) {
			if (stepCount != 0) {
				stepWorld();
			}

			if (!quietMode || simulationDone()) {
				worldPrinter.printWorld(world);
				printStepCount();
				line("");
			}

			stepCount++;

			if (!quietMode) {
				stepDelayer.delay();
			}

			if (loopDetector.hasLoop(world)) {
				break;
			}
		}
	}

	private void stepWorld() {
		history.add(world);
		world = new WorldStepper().step(world);
	}

	private boolean simulationDone() {
		return stepCount >= maxSteps || loopDetector.hasLoop(world);
	}

	private void printStepCount() {
		if (stepCount == 0) {
			line("start");
		} else {
			line("step " + stepCount + loopDetector.getLoopString(world));
		}
	}

	private void line(String s) {
		System.out.println(s);
	}
}
