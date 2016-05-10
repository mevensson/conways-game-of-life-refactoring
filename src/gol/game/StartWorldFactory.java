package gol.game;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;

import gol.Arguments;
import gol.game.input.FileWorldReader;
import gol.game.input.RandomWorldGenerator;
import gol.game.world.World;

public class StartWorldFactory implements Supplier<StartWorld> {

	private final FileWorldReader fileWorldReader;
	private final RandomWorldGenerator randomWorldGenerator;
	private final OptionalInt width;
	private final OptionalInt height;
	private final Optional<String> fileName;

	public StartWorldFactory(
			final FileWorldReader fileWorldReader,
			final RandomWorldGenerator randomWorldGenerator,
			final OptionalInt width,
			final OptionalInt height,
			final Optional<String> fileName) {
		this.fileWorldReader = fileWorldReader;
		this.randomWorldGenerator = randomWorldGenerator;
		this.width = width;
		this.height = height;
		this.fileName = fileName;
	}

	@Override
	public StartWorld get() {
		if (fileName.isPresent()) {
			return createFileWorld();
		}
		return createRandomWorld();
	}

	private StartWorld createFileWorld() {
		try {
			final World world = fileWorldReader.read(fileName.get());
			final int fileWidth = width.orElse(fileWorldReader.getWidth());
			final int fileHeight = height.orElse(fileWorldReader.getHeight());
			return new StartWorld(world, fileWidth, fileHeight);
		} catch (final FileNotFoundException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private StartWorld createRandomWorld() {
		final int actualWidth = width.orElse(Arguments.DEFAULT_WIDTH);
		final int actualHeight = height.orElse(Arguments.DEFAULT_HEIGHT);
		final World world = randomWorldGenerator.generate(
				actualWidth, actualHeight);
		return new StartWorld(world, actualWidth, actualHeight);
	}
}
