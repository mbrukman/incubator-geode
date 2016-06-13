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
