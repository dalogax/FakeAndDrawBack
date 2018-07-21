package com.fakeanddraw.domain.usecase;

public interface UseCase<REQUEST> {

	void execute(REQUEST request);
}
