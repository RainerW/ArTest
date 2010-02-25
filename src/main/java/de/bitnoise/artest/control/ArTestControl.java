package de.bitnoise.artest.control;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.reflect.MethodSignature;

import de.bitnoise.artest.annotation.EnableArTest;
import de.bitnoise.artest.model.State;
import de.bitnoise.artest.model.TestStep;

public class ArTestControl
{

  private static List<TestState> states;

  private static String targetClassName;

  public static void testMethodBegin(StaticPart thisJoinPointStaticPart)
  {
    states = new ArrayList<TestState>();
    targetClassName = thisJoinPointStaticPart.getSignature()
        .getDeclaringTypeName();
  }

  public static void testMethodEnd(StaticPart thisJoinPointStaticPart)
  {
    int fail = 0;
    for (TestState state : states)
    {
      if (state instanceof TestSubState)
      {
        printSubCall(state.getModel());
      }
      else
      {
        printCall(state.getModel());
      }
      if (state.getModel().state != State.SUCCESS)
      {
        fail++;
      }
    }
    if (fail > 0)
    {
      throw new AssertionError("Test had " + fail + " failing Test Steps");
    }
  }

  private static void printCall(TestStep testStep)
  {
    System.out.print(testStep.state);
    System.out.print("\t: ");

    System.out.print(prettyName(testStep));

    if (testStep.paramValuesAsString != null)
    {
      System.out.print(" (");
      for (String param : testStep.paramValuesAsString)
      {
        System.out.print(" '");
        System.out.print(param);
        System.out.print("'");
      }
      System.out.print(" ) ");
    }

    if (testStep.state != State.SUCCESS)
    {
      System.out.print("\t\t");
      if (testStep.state == State.ERROR)
      {
        System.out.print("Error was : ");
      }
      if (testStep.state == State.FAIL)
      {
        System.out.print("Failed with : ");
      }
      System.out.print(testStep.throwable.getMessage());
    }

    System.out.println();
  }

  private static void printSubCall(TestStep testStep)
  {
    // System.out.print(state.getModel().state);
    // System.out.print("\t: Sub Sequence:");
    //
    // System.out.print( prettyName(state) );
    //
    // System.out.println();
  }

  private static String prettyName(TestStep testStep)
  {
    StringBuilder sb = new StringBuilder();
    String clName = testStep.className;
    if (!targetClassName.equals(clName) && !clName.startsWith("org.junit"))
    {
      sb.append(onlyClassName(clName));
    }
    String mName = testStep.classMethodName;
    sb.append(Character.toUpperCase(mName.charAt(0)));
    sb.append(mName.substring(1));

    RevertCamelCase(sb);

    return sb.toString();
  }

  private static void RevertCamelCase(StringBuilder sb)
  {
    for (int i = sb.length() - 1; i >= 0; i--)
    {
      char c = 0;
      if (Character.isUpperCase(c = sb.charAt(i)) && i > 0)
      {
        sb.setCharAt(i, Character.toLowerCase(c));
        sb.insert(i, ' ');
      }
    }
    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
  }

  private static String onlyClassName(String classMethodName)
  {
    int idx = classMethodName.lastIndexOf(".");
    return classMethodName.substring(idx + 1);
  }

  public static TestState newMethodCall(JoinPoint joinPoint)
  {
    TestState state = null;
    StaticPart thisJoinPointStaticPart = joinPoint.getStaticPart();
    if (thisJoinPointStaticPart.getSignature() instanceof MethodSignature)
    {
      MethodSignature ms = (MethodSignature) thisJoinPointStaticPart
          .getSignature();
      Method m = ms.getMethod();
      if (m != null)
      {
        EnableArTest anno = m.getAnnotation(EnableArTest.class);
        if (anno != null)
        {
          state = new TestSubState(joinPoint);
        }
      }
    }

    if (state == null)
    {
      state = new TestState(joinPoint);
    }
    states.add(state);
    return state;
  }

}
