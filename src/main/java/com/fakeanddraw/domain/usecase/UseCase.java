package com.fakeanddraw.domain.usecase;

public interface UseCase<R> {

	void execute(R request);
}
