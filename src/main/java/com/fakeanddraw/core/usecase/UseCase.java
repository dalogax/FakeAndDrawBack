package com.fakeanddraw.core.usecase;

public interface UseCase<R> {

	void execute(R request);
}
