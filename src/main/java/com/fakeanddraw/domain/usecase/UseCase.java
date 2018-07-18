package com.fakeanddraw.domain.usecase;

public interface UseCase<REQUEST, RESPONSE> {

	RESPONSE execute(REQUEST request);
}
