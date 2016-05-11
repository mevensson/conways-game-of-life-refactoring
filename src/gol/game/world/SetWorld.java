package gol.game.world;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class SetWorld implements World {
	private final Set<Point> world;

	public SetWorld() {
		world = new HashSet<>();
	}

	@Override
	public boolean isAlive(final int x, final int y) {
		return world.contains(new Point(x, y));
	}

	@Override
	public void setAlive(final int x, final int y) {
		world.add(new Point(x, y));
	}

	@Override
	public boolean equals(final Object obj) {
		final SetWorld other = (SetWorld) obj;
		if (world == null) {
			if (other.world != null)
				return false;
		} else if (!world.equals(other.world))
			return false;
		return true;
	}

	@Override
	public Stream<Point> stream() {
		return world.stream();
	}
}