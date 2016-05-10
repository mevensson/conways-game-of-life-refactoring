package gol;

public interface ScopeEntrance<Result, Scope> {

	Result enter(Scope scope);
}
