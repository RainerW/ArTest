package de.bitnoise.artest.aspects;

import de.bitnoise.artest.control.ArTestControl;

public aspect AroundJUnitTestMethod {
	pointcut pc_TargetMethod()  : 
		execution( @org.junit.Test@de.bitnoise.artest.annotation.EnableArTest void * (..) ) 
		&& !within(de.bitnoise.artest..*);

	void around() : pc_TargetMethod() {
		try {
			ArTestControl.testMethodBegin(thisEnclosingJoinPointStaticPart);
			proceed();
		} finally {
			ArTestControl.testMethodEnd(thisEnclosingJoinPointStaticPart);
		}
	}
}
