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
package com.gemstone.gemfire.management.internal.cli;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.shell.event.ParseResult;

import com.gemstone.gemfire.test.junit.categories.IntegrationTest;

@Category(IntegrationTest.class)
public class GfshParserIntegrationTest {

  private CommandManager commandManager;
  private GfshParser parser;

  @Before
  public void setUp() throws Exception {
    CommandManager.clearInstance();
    this.commandManager = CommandManager.getInstance(true);

    this.parser = new GfshParser(commandManager);

    //CliUtil.isGfshVM = false;
  }

  @After
  public void tearDown() {
    CommandManager.clearInstance();
  }

  @Test
  public void testCommandWithoutOptions() throws Exception {
    String input = "start locator --name=loc1 --J=\"-Dgemfire.http-service-port=8080\"";
    //String input = "start locator --name=loc1";
    ParseResult parseResult = parser.parse(input);
    assertThat(parseResult).isNotNull().isExactlyInstanceOf(GfshParseResult.class);

    GfshParseResult gfshParseResult = (GfshParseResult) parseResult;

    assertThat(gfshParseResult.getMethod().getName()).isEqualTo("startLocator");
    assertThat(gfshParseResult.getUserInput()).isEqualTo(input);

    assertThat(gfshParseResult.getArguments()).isNotNull();
    boolean foundName = false;
    for (Object arg : gfshParseResult.getArguments()) {
      if ("loc1".equals(arg)) {
        foundName = true;
      }
    }
    assertThat(foundName).isTrue();

    assertThat(gfshParseResult.getCommandName()).isEqualTo("start locator");

    Map<String, String> params = gfshParseResult.getParamValueStrings();
    assertThat(params).isNotNull().isNotEmpty();

    for (String param : params.keySet()) {
      System.out.println(param + "=" + params.get(param));
    }
  }
}
