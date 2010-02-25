package de.bitnoise.artest.model;

import java.util.List;

public class TestStep
{
  public String className;

  public String classMethodName;

  public long codeLine;

  public State state;

  public Throwable throwable;

  public List<String> paramValuesAsString;
}
