package gol;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class AliveNeighborCounter {
	public Map<Point, Integer> count(final World world) {
		return alivePoints(world)
				.flatMap(p -> neighbors(p))
				.collect(countUnique());
	}

	private <T> Collector<T, ?, Map<T, Integer>> countUnique() {
		return toMap(identity(), (k) -> 1, (v1, v2) -> v1 + v2);
	}

	private Stream<Point> alivePoints(final World world) {
		return StreamSupport.stream(world.spliterator(), false);
	}

	private Stream<Point> neighbors(final Point point) {
		return Stream.of(
				new Point(point.getX() - 1, point.getY() - 1),
				new Point(point.getX()    , point.getY() - 1),
				new Point(point.getX() + 1, point.getY() - 1),
				new Point(point.getX() - 1, point.getY()),
				new Point(point.getX() + 1, point.getY()),
				new Point(point.getX() - 1, point.getY() + 1),
				new Point(point.getX()    , point.getY() + 1),
				new Point(point.getX() + 1, point.getY() + 1));
	}
}