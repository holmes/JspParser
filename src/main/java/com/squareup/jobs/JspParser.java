package com.squareup.jobs;

public class JspParser {

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
   */
  public static String parse(String jsp) {
    // TODO Implement me!
    return "";
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
