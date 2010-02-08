package de.bitnoise.sample;

import static org.junit.Assert.*;

import org.junit.Test;

import de.bitnoise.artest.annotation.ArTestSubStep;
import de.bitnoise.artest.annotation.EnableArTest;

  public class AnSampleTest extends ASampleBase {
  
    @Test
    @EnableArTest
    public void testFails() throws Exception {
  
      Login.user("Reinar"); // Statische Methode
  
      isLoggedIn(); // Methode in der Basiklasse
  
      fastExit();
      noExit();
  
      assertEquals("fail", "here");
    }
  
    private void fastExit() {
      assertEquals("erwarte", "fehlschlag");
      assertEquals("same", "same");
    }
  
    @EnableArTest
    private void noExit() {
      assertEquals("erwarte", "fehlschlag");
      assertEquals("same", "same");
    }
  }
