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
   *  <% xyz %>
   *
   * where "xyz" is pure Java code
   *
   *  <%= xyz %>
   *
   * where "xyz" is passed to System.out.println.
   *
   * System.out.print("<!DOCTYPE html><html>  <head>    ");
   * int i = 3 % 5, j = 2; ;
   * System.out.print("    <title>JSP Parser!</title>  </head>  <body>    <h1>Time: ");
   * System.out.print( System.currentTimeMillis() );
   * System.out.print("</h1>    <p><code>i = ");
   * System.out.print(i);
   * System.out.print("</code> and that's 33% more than <code>j = ");
   * System.out.print(j);
   * System.out.print("</p>  </body></html>");
   */
  public static String parse(String jsp) {
    final StringBuilder java = new StringBuilder("System.out.print(\"");

    int state = STATE_ACCEPTING_STRING;
    int type = -1;
    for (int i = 0; i < jsp.length(); i++) {
      final char current = jsp.charAt(i);

      switch (state) {

        case STATE_ACCEPTING_STRING:
          if ('<' == current) {
            state = STATE_MIGHT_BE_JSP_OPEN_TAG;
          } else {
            java.append(current);
          }
          break;

        case STATE_MIGHT_BE_JSP_OPEN_TAG:
          if ('%' == current) {
            state = STATE_MIGHT_BE_JSP_PRINT_TAG;
            java.append("\");"); // Close string output
          } else {
            state = STATE_ACCEPTING_STRING;
            java.append('<').append(current);
          }
          break;

        case STATE_MIGHT_BE_JSP_PRINT_TAG:
          if ('=' == current) {
            type = TYPE_PRINT;
            java.append("System.out.print("); // Start evaluation output
          } else {
            type = TYPE_JAVA;
            java.append(current);
          }
          state = STATE_ACCEPTING_JSP;
          break;

        case STATE_ACCEPTING_JSP:
          if ('%' == current) {
            state = STATE_MIGHT_BE_JSP_CLOSE_TAG;
          } else {
            java.append(current);
          }
          break;

        case STATE_MIGHT_BE_JSP_CLOSE_TAG:
          if ('>' == current) {
            switch (type) {
              case TYPE_JAVA:
                java.append(";");
                break;
              case TYPE_PRINT:
                java.append(");");
                break;
            }
            state = STATE_ACCEPTING_STRING;
            java.append("System.out.print(\"");
          } else {
            java.append('%').append(current);
            state = STATE_ACCEPTING_JSP;
          }
          break;
      }
    }

    return java.append("\");").toString();
  }

  public static void main(String... args) {
    String jsp = ""
        + "<!DOCTYPE html>"
        + "<html>"
        + "  <head>"
        + "    <% int i = 3 % 5, j = 2; %>"
        + "    <title>JSP Parser!</title>"
        + "  </head>"
        + "  <body>"
        + "    <h1>Time: <%= System.currentTimeMillis() %></h1>"
        + "    <p><code>i = <%=i%></code> and that's 33% more than <code>j = <%=j%></p>"
        + "  </body>"
        + "</html>";
    jsp += "<!-- <%=\"I'm in ur strings! <% <%= % > \"%> -->";

    System.out.println(parse(jsp));
  }

}
