package gol.game;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import gol.game.world.World;

public class AliveNeighborCounter {

	public static class AliveNeighbors {
		public final Point point;
		public final int count;

		public AliveNeighbors(final Point point, final int count) {
			this.point = point;
			this.count = count;
		}
	}

	public Stream<AliveNeighbors> count(final World world) {
		return alivePoints(world)
				.flatMap(p -> neighbors(p))
				.collect(countUnique())
				.entrySet()
				.stream()
				.map((e) -> new AliveNeighbors(e.getKey(), e.getValue()));
	}

	private <T> Collector<T, ?, Map<T, Integer>> countUnique() {
		return toMap(identity(), (k) -> 1, (v1, v2) -> v1 + v2);
	}

	private Stream<Point> alivePoints(final World world) {
		return StreamSupport.stream(world.spliterator(), false);
	}

	private Stream<Point> neighbors(final Point point) {
		return Stream.of(
				new Point(point.x - 1, point.y - 1),
				new Point(point.x    , point.y - 1),
				new Point(point.x + 1, point.y - 1),
				new Point(point.x - 1, point.y),
				new Point(point.x + 1, point.y),
				new Point(point.x - 1, point.y + 1),
				new Point(point.x    , point.y + 1),
				new Point(point.x + 1, point.y + 1));
	}
}