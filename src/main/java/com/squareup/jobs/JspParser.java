package com.squareup.jobs;

public class JspParser {
  private static final int STATE_ACCEPTING_STRING = 0;
  private static final int STATE_MIGHT_BE_JSP_OPEN_TAG = 1;
  private static final int STATE_MIGHT_BE_JSP_PRINT_TAG = 2;
  private static final int STATE_ACCEPTING_JSP = 3;
  private static final int STATE_MIGHT_BE_JSP_CLOSE_TAG = 4;

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
      int state = STATE_ACCEPTING_STRING;
      boolean expression = false;

      StringBuilder builder = new StringBuilder("System.out.print(\"");
      boolean needToPrintOpener = false;
      for (char ch : jsp.toCharArray()) {
          switch (state) {

              case STATE_ACCEPTING_STRING:
                  if (ch == '<') {
                      state = STATE_MIGHT_BE_JSP_OPEN_TAG;
                  } else {
                      if (needToPrintOpener) {
                          builder.append("\nSystem.out.print(\"");
                          needToPrintOpener = false;
                      }

                      builder.append(ch);
                  }
                  break;
              case STATE_MIGHT_BE_JSP_OPEN_TAG:
                  if (ch == '%') {
                      state = STATE_MIGHT_BE_JSP_PRINT_TAG;
                      if (!needToPrintOpener) {
                        builder.append("\");");
                      }
                      builder.append("\n");
                  } else {
                      state = STATE_ACCEPTING_STRING;
                      if (needToPrintOpener) {
                          builder.append("\nSystem.out.print(\"");
                          needToPrintOpener = false;
                      }

                      builder.append('<');
                      builder.append(ch);
                  }
                  break;
              case STATE_MIGHT_BE_JSP_PRINT_TAG:
                  if (ch == '=') {
                      expression = true;
                      builder.append("System.out.print(");
                  } else {
                      expression = false;
                      builder.append(ch);
                  }

                  state = STATE_ACCEPTING_JSP;
                  break;
              case STATE_ACCEPTING_JSP:
                  if (ch == '%') {
                      state = STATE_MIGHT_BE_JSP_CLOSE_TAG;
                  } else {
                      builder.append(ch);
                  }

                  break;
              case STATE_MIGHT_BE_JSP_CLOSE_TAG:
                  if (ch == '>') {
                      state = STATE_ACCEPTING_STRING;
                      if (expression) {
                          builder.append(");");
                      }

                      needToPrintOpener = true;
                  } else {
                      state = STATE_ACCEPTING_JSP;
                      builder.append('%');
                      builder.append(ch);
                  }
                  break;
          }
      }

      if (state != STATE_ACCEPTING_JSP) {
          builder.append("\");");
      } else if (expression) {
          builder.append(");");
      }

      return builder.toString();
  }

  public static void main(String... args) {
    String jsp = ""
        + "<!DOCTYPE html>"
        + "<html>"
        + "  <head>"
        + "    <% int i = 3 % 5, j = 2; %><% j = 3; %>"
        + "    <title>JSP Parser!</title>"
        + "  </head>"
        + "  <body>"
        + "    <h1>Time: <%= System.currentTimeMillis() %></h1>"
        + "    <p><code>i = <%=i%></code> and that's 33% more than <code>j = <%=j%></p>"
        + "  </body>"
        + "</html>";
    //jsp += "<!-- <%=\"I'm in ur strings! <% <%= % > %> \"%> -->";

    System.out.println(parse(jsp));
  }

}
