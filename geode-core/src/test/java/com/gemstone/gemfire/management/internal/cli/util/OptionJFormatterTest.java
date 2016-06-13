package com.gemstone.gemfire.management.internal.cli.util;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.gemstone.gemfire.test.junit.categories.UnitTest;

@Category(UnitTest.class)
public class OptionJFormatterTest {

  private static final String quote = "\"";

  @Test
  public void containsJoptShouldReturnTrueIfCmdHasJ() {
    String cmd = "start locator --name=loc1 --J=-Dfoo=bar";
    OptionJFormatter ojf = new OptionJFormatter();
    assertTrue(ojf.containsJopt(cmd));
  }

  @Test
  public void containsJoptShouldReturnFalseIfCmdDoesntHaveJ() {
    String cmd = "start locator --name=loc1 ";
    OptionJFormatter ojf = new OptionJFormatter();
    assertFalse(ojf.containsJopt(cmd));
  }

  @Test
  public void containsJoptShouldReturnTrueIfCmdHasMultipleJ() {
    String cmd = "start locator --name=loc1 --J=-Dfoo=bar --J=-Dbar=foo";
    OptionJFormatter ojf = new OptionJFormatter();
    assertTrue(ojf.containsJopt(cmd));
  }

  @Test
  public void valueWithoutQuotesReturnsWithQuotes() {
    String cmd = "start locator --name=loc1 --J=-Dfoo=bar";
    OptionJFormatter ojf = new OptionJFormatter();
    String formattedCmd = ojf.formatCommand(cmd);

    String expected = "start locator --name=loc1 --J=" + quote + "-Dfoo=bar" + quote;
    assertThat(formattedCmd).isEqualTo(expected);
  }

  @Test
  public void valueWithoutQuotesReturnsWithQuotes_2() {
    String cmd = "start locator --J=-Dfoo=bar --name=loc1";
    OptionJFormatter ojf = new OptionJFormatter();
    String formattedCmd = ojf.formatCommand(cmd);

    String expected = "start locator --J=" + quote + "-Dfoo=bar" + quote + " --name=loc1";
    assertThat(formattedCmd).isEqualTo(expected);
  }

  @Test
  public void nullShouldThrowNullPointerException() {
    assertThatThrownBy(() -> new OptionJFormatter().formatCommand(null)).isExactlyInstanceOf(NullPointerException.class);
  }

  @Test
  public void emptyShouldThrowNullPointerException() {
    assertThat(new OptionJFormatter().formatCommand("")).isEqualTo("");
  }

  @Ignore
  @Test
  public void multipleJOptionsShould_something() {
    String cmd = "start locator --name=loc1 --J=-Dfoo=bar --J=-Dbar=foo";
    OptionJFormatter ojf = new OptionJFormatter();
    String formattedCmd = ojf.formatCommand(cmd);

    String expected = "start locator --name=loc1 --J=" + quote + "-Dfoo=bar" + quote + " --J=" + quote + "-Dbar=foo" + quote;
    assertThat(formattedCmd).isEqualTo(expected);
  }

}
