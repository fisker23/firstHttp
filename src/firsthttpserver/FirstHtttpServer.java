package firsthttpserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author Lars Mortensen
 */
public class FirstHtttpServer {
  static int port = 8080;
  static String ip = "127.0.0.1";
  static String contentFolder = "public/";
  public static void main(String[] args) throws Exception {
    if(args.length == 2){
      port = Integer.parseInt(args[1]);
      ip = args[0];
    }
    HttpServer server = HttpServer.create(new InetSocketAddress(ip,port), 0);
    server.createContext("/welcome", new RequestHandler());
    server.createContext("/files", new SimpleFileHandler());
    server.createContext("/headers", new RequestHandlerHeader());
    server.createContext("/pages/", new FileHandlerO3());
    server.setExecutor(null); // Use the default executor
    server.start();
    System.out.println("Server started, listening on port: "+port);
  }

  static class RequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
      String response = "<h1> Drankie_DEFENDER VERDENS BEDSTE World of Tank player, ik kun i i EU </h1>";
            response += "<br>" +" URI:"+ he.getRequestURI();
            StringBuilder sb = new StringBuilder();
sb.append("<!DOCTYPE html>\n");
sb.append("<html>\n");
sb.append("<head>\n");
sb.append("<title>My fancy Web Site</title>\n");
sb.append("<meta charset='UTF-8'>\n");
sb.append("</head>\n");
sb.append("<body>\n");
sb.append("<h2>Welcome to my very first home made Web Server :-)</h2>\n");
sb.append("</body>\n");
sb.append("</html>\n");
response = sb.toString();
      Scanner scan = new Scanner(he.getRequestBody());
      while(scan.hasNext()){
          response += " <br> " +scan.nextLine();
      }
      Headers h = he.getResponseHeaders();
        h.add("Content-Type", "text/html");

      he.sendResponseHeaders(200, response.length());
      try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
        pw.print(response); //What happens if we use a println instead of print --> Explain
      }
    }
  }

    static class RequestHandlerHeader implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
      String response = "";
            StringBuilder sb = new StringBuilder();
            he.getRequestHeaders();
            

            sb.append("<!DOCTYPE html>\n");
sb.append("<html>\n");
sb.append("<head>\n");
sb.append("<meta charset='UTF-8'>\n");
sb.append("</head>");
sb.append("<body>");
sb.append("Opgave2\n");
sb.append("<table border=4px>\n");
sb.append("<tr><th> Header </th> <th>Value  </th></tr>\n");
        for (String s : he.getRequestHeaders().keySet()){
            {
                sb.append("<tr>\n");
                sb.append("<td>" + s + "</td>\n" );
                sb.append("<td>" + he.getRequestHeaders().getFirst(s) + "</td>");
                sb.append("</tr>");
            }
            
        }
sb.append("</table>");
sb.append("</body>");
sb.append("</html>");
response = sb.toString();
      Scanner scan = new Scanner(he.getRequestBody());
      while(scan.hasNext()){
          response += " <br> " +scan.nextLine();
      }
      Headers h = he.getResponseHeaders();
        h.add("Content-Type", "text/html");

      he.sendResponseHeaders(200, response.length());
      try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
        pw.print(response); //What happens if we use a println instead of print --> Explain
      }
    }
  }

    
    static class SimpleFileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
      File file = new File(contentFolder + "index.html");
      byte[] bytesToSend = new byte[(int) file.length()];
      try {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        bis.read(bytesToSend, 0, bytesToSend.length);
      } catch (IOException ie) {
        System.out.println(ie);
      }
      he.sendResponseHeaders(200, bytesToSend.length);
      try (OutputStream os = he.getResponseBody()) {
        os.write(bytesToSend, 0, bytesToSend.length);
      }
    }
  }
     static class FileHandlerO3 implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
      File file = new File(contentFolder + "index.html");
      byte[] bytesToSend = new byte[(int) file.length()];
      try {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        bis.read(bytesToSend, 0, bytesToSend.length);
      } catch (IOException ie) {
        System.out.println(ie);
      }
      he.sendResponseHeaders(200, bytesToSend.length);
      try (OutputStream os = he.getResponseBody()) {
        os.write(bytesToSend, 0, bytesToSend.length);
      }
    }
  }
}

