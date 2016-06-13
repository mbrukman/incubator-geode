package com.gemstone.gemfire.management.internal.cli.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

public class CommentSkipHelperTest {

  private CommentSkipHelper commentSkipHelper;

  @Before
  public void setUp() {
    this.commentSkipHelper = new CommentSkipHelper();
  }

  @Test
  public void nullShouldThrowNullPointerException() {
    assertThatThrownBy(() -> this.commentSkipHelper.skipComments(null)).isExactlyInstanceOf(NullPointerException.class);
  }

  @Test
  public void emptyStringShouldReturnEmptyString() {
    assertThat(this.commentSkipHelper.skipComments("")).isEqualTo("");
  }

  @Test
  public void stringWithDoubleSlashCommentShouldReturnString() {
    String command = "start locator --name=loc1 //";
    assertThat(this.commentSkipHelper.skipComments(command)).isEqualTo(command);
  }

  @Test
  public void stringWithSlashAsterCommentShouldRemoveComment() {
    String command = "start locator /* starting locator */ --name=loc1";
    assertThat(this.commentSkipHelper.skipComments(command)).isEqualTo("start locator  --name=loc1");
  }

  @Test
  public void stringWithCommentWithoutSpacesShouldRemoveComment() { // TODO: possible bug
    String command = "start locator/* starting locator */--name=loc1";
    assertThat(this.commentSkipHelper.skipComments(command)).isEqualTo("start locator--name=loc1");
  }

  @Test
  public void stringWithOpenCommentShouldReturnNull() { // TODO: possible bug
    String command = "start locator /* --name=loc1";
    assertThat(this.commentSkipHelper.skipComments(command)).isNull();
  }

  @Test
  public void stringWithCloseCommentShouldReturnString() { // TODO: possible bug
    String command = "start locator */ --name=loc1";
    assertThat(this.commentSkipHelper.skipComments(command)).isEqualTo(command);
  }

  @Test
  public void stringWithMultiLineCommentShouldRemoveComment() {
    String command = "start locator /*\n some \n comment \n */ --name=loc1";
    assertThat(this.commentSkipHelper.skipComments(command)).isEqualTo("start locator  --name=loc1");
  }

  @Test
  public void stringWithCommentAtEndShouldRemoveComment() {
    String command = "start locator --name=loc1 /* comment at end */";
    assertThat(this.commentSkipHelper.skipComments(command)).isEqualTo("start locator --name=loc1 ");
  }

  @Test
  public void stringWithCommentAtBeginningShouldRemoveComment() {
    String command = "/* comment at begin */ start locator --name=loc1";
    assertThat(this.commentSkipHelper.skipComments(command)).isEqualTo(" start locator --name=loc1");
  }

  @Test
  public void stringWithInsideOutCommentShouldMisbehave() { // TODO: possible bug
    String command = "*/ this is a comment /* start locator --name=loc1";
    assertThat(this.commentSkipHelper.skipComments(command)).isEqualTo("*/ this is a comment  this is a comment /* start locator --name=loc1");
  }

}
