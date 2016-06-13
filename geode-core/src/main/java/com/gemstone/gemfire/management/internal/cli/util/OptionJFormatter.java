package com.gemstone.gemfire.management.internal.cli.util;

public class OptionJFormatter {

  public String formatCommand(String cmd){
    if (!containsJopt(cmd)) {
      return cmd;
    }
    int indexOfJ = cmd.indexOf("--J=");
    StringBuilder sb =  new StringBuilder();
    sb.append(cmd.substring(0, indexOfJ+4));
    sb.append("\"");
    sb.append(cmd.substring(indexOfJ+4));
    sb.append("\"");


//    for () {
//
//    }
//    while () {
//
//    }
//    do {
//
//    } while();
    return sb.toString();
  }

  boolean containsJopt(String cmd){
    if (cmd.contains("--J")){
      return true;
    }
    return false;
  }

}
