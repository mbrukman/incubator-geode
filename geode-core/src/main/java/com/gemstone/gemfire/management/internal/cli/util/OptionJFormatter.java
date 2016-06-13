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

public class OptionJFormatter {

  private static final String J_OPTION = "--J=";
  private static final String QUOTE = "\"";
  private static final String SPACE = " ";

  private String command;
  private int index;
  private StringBuilder formatted = new StringBuilder();

  private void everythingBeforeJ() {
    int indexOfJ = this.command.indexOf(J_OPTION);
    this.formatted.append(this.command.substring(0, indexOfJ));
  }

  private int nextJ(int start) {
    int startOfJ = this.command.indexOf(J_OPTION, start);
    int nextSpace = this.command.indexOf(SPACE, startOfJ);
    if (start+1 != startOfJ){
      this.formatted.append(command.substring(start, startOfJ));
    }
    this.formatted.append(J_OPTION);
    this.formatted.append(QUOTE);
    if (nextSpace == -1) {
      this.formatted.append(this.command.substring(startOfJ + 4));
    } else {
      this.formatted.append(this.command.substring(startOfJ + 4, nextSpace));
    }
    this.formatted.append(QUOTE);
    return nextSpace;
  }

  public String formatCommand(String command){
    if (!containsJopt(command)) {
      return command;
    }
    this.command = command;

    int start = 0;
   // everythingBeforeJ();

    while (start > -1) {
      start = nextJ(start);

      boolean noMoreJs = command.indexOf(J_OPTION, start) == -1;
      boolean hasMoreOptions = start > -1;

      if (noMoreJs){
        formatted.append(command.substring(start));
        break;
      } else if (hasMoreOptions) {
        this.formatted.append(SPACE);
      }
    }

    return formatted.toString();
  }

  boolean containsJopt(String cmd){
    if (cmd.contains("--J")){
      return true;
    }
    return false;
  }

}
