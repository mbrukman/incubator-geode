/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

  @Test
  public void multipleJOptionsShould_something() {
    String cmd = "start locator --name=loc1 --J=-Dfoo=bar --J=-Dbar=foo";
    OptionJFormatter ojf = new OptionJFormatter();
    String formattedCmd = ojf.formatCommand(cmd);

    String expected = "start locator --name=loc1 --J=" + quote + "-Dfoo=bar" + quote + " --J=" + quote + "-Dbar=foo" + quote;
    assertThat(formattedCmd).isEqualTo(expected);
  }

  @Test
  public void multipleJOptionsWithSomethingAfterShould_something() {
    String cmd = "start locator --name=loc1 --J=-Dfoo=bar --J=-Dbar=foo --group=locators";
    OptionJFormatter ojf = new OptionJFormatter();
    String formattedCmd = ojf.formatCommand(cmd);

    String expected = "start locator --name=loc1 --J=" + quote + "-Dfoo=bar" + quote + " --J=" + quote + "-Dbar=foo" + quote + " --group=locators";
    assertThat(formattedCmd).isEqualTo(expected);
  }

  @Test
  public void multipleJOptionsWithSomethingBetweenShould_something() {
    String cmd = "start locator --name=loc1 --J=-Dfoo=bar --group=locators --J=-Dbar=foo";
    OptionJFormatter ojf = new OptionJFormatter();
    String formattedCmd = ojf.formatCommand(cmd);

    String expected = "start locator --name=loc1 --J=" + quote + "-Dfoo=bar" + quote + " --group=locators --J=" + quote + "-Dbar=foo" + quote;
    assertThat(formattedCmd).isEqualTo(expected);
  }

}
