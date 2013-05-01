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
    // TODO Implement me!
    return "";
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
    //jsp += "<!-- <%=\"I'm in ur strings! <% <%= % > %> \"%> -->";

    System.out.println(parse(jsp));
  }

}
