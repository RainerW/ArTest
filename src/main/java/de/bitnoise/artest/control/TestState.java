package de.bitnoise.artest.control;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.reflect.SourceLocation;

import de.bitnoise.artest.model.State;
import de.bitnoise.artest.model.TestStep;

public class TestState {

	private TestStep model;

	public TestState(JoinPoint joinPoint) {
		model = new TestStep();
		StaticPart thisJoinPointStaticPart = joinPoint.getStaticPart();
		SourceLocation location = thisJoinPointStaticPart.getSourceLocation();
		Signature sign = thisJoinPointStaticPart.getSignature();
		int line = location.getLine();

		model.className = sign.getDeclaringTypeName();
		model.classMethodName = sign.getName();
		model.codeLine = line;
		model.state = State.NOT_EXECUTED;
		
		Object[] args = joinPoint.getArgs();
		if(args!=null) {
		  List<String> params = new ArrayList<String>();
		  for(Object arg:args) {
		    params.add(arg.toString());
		  }
		  model.paramValuesAsString=params;
		}
	}

	public void before() {
	}

	public void succeeded() {
		model.state = State.SUCCESS;
	}

	public void assertionError(AssertionError a) {
		model.state = State.ERROR;
		model.throwable=a;
	}

	public void throwable(Throwable t) {
		model.state = State.FAIL;
		model.throwable=t;
	}

	public TestStep getModel() {
		return model;
	}

}
