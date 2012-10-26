package com.squareup.jobs;

public class JspParser {
  private static final int STATE_ACCEPTING_STRING = 0;
  private static final int STATE_MIGHT_BE_JSP_OPEN_TAG = 1;
  private static final int STATE_MIGHT_BE_JSP_PRINT_TAG = 2;
  private static final int STATE_ACCEPTING_JSP = 3;
  private static final int STATE_MIGHT_BE_JSP_CLOSE_TAG = 4;

  private static final int TYPE_JAVA = 0;
  private static final int TYPE_PRINT = 1;

  /**
   * JSP Tags:
   *
   * <xyz> where the whole tag is passed to System.out.println
   *
   * <% xyz %> where "xyz" is pure Java code
   *
   * <%= xyz %> where "xyz" is passed to System.out.println.
   */
  public static String parse(String jsp) {
    final StringBuilder java = new StringBuilder("System.out.print(\"");

    int state = STATE_ACCEPTING_STRING;
    int type = -1;

    switch (state) {
      case STATE_ACCEPTING_STRING:
        break;
      case STATE_MIGHT_BE_JSP_OPEN_TAG:
        break;
      case STATE_MIGHT_BE_JSP_PRINT_TAG:
        break;
      case STATE_ACCEPTING_JSP:
        break;
      case STATE_MIGHT_BE_JSP_CLOSE_TAG:
        break;
    }

    return java.append("\");").toString();
  }

  public static void main(String... args) {
    String jsp = ""
        + "<!DOCTYPE html>"
        + "<html>"
        + "  <head>"
        + "    <% int i = 0, j = 2; %>"
        + "    <title>JSP Parser!</title>"
        + "  </head>"
        + "  <body>"
        + "    <h1>Time: <%= System.currentTimeMillis() %></h1>"
        + "    <p><code>i = <%=i%></code> and <code>j = <%=j%></p>"
        + "  </body>"
        + "</html>";
    //jsp += "<!-- <%=\"I'm in ur strings! <% <%= % > %> \"%> -->";

    System.out.println(parse(jsp));
  }
}
