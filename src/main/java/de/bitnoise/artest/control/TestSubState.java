package de.bitnoise.artest.control;

import org.aspectj.lang.JoinPoint.StaticPart;

public class TestSubState extends TestState {

	public TestSubState(StaticPart thisJoinPointStaticPart) {
		super(thisJoinPointStaticPart);
	}

}
