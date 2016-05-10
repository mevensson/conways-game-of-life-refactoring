package gol;

import java.util.function.Supplier;

public class Cache<T> {

	private T cache = null;

	public T get(final Supplier<T> supplier) {
		if (cache == null) {
			cache = supplier.get();
		}

		return cache;
	}
}
