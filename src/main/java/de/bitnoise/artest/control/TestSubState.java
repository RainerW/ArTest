package de.bitnoise.artest.control;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;

public class TestSubState extends TestState {

	public TestSubState(JoinPoint joinPoint) {
		super(joinPoint);
	}
	
}
