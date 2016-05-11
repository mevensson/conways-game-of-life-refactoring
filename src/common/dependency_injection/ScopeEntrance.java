package common.dependency_injection;

public interface ScopeEntrance<Result, Scope> {

	Result enter(Scope scope);
}
