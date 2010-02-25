package de.bitnoise.artest.aspects;

import de.bitnoise.artest.annotation.ArTestSubStep;
import de.bitnoise.artest.annotation.EnableArTest;
import de.bitnoise.artest.control.ArTestControl;
import de.bitnoise.artest.control.TestState;

public aspect CatchAllTestThrowables {

	pointcut pc_TargetMethod()  : 
	call( void * (..) ) 
//	&& cflow( execution( @de.bitnoise.artest.annotation.EnableArTest * *(..) ))
//	&& withincode( @de.bitnoise.artest.annotation.ArTestSubStep * *(..) )
	&& withincode( @de.bitnoise.artest.annotation.EnableArTest * *(..) )
	&& !within(de.bitnoise.artest..*)
//	&& @annotation(anno)
	;
	
 
	void around() : pc_TargetMethod() {
		TestState methodCall = ArTestControl.newMethodCall(thisJoinPoint);
		try {
			methodCall.before();
			proceed();
			methodCall.succeeded();
		} catch (AssertionError a) {
			methodCall.assertionError(a);
		} catch (Throwable t) {
			methodCall.throwable(t);
		}
	}
}
