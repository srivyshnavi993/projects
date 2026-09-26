import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.SQLException;
import java.util.*;

public class MainServer {
    private static final int PORT = 8080;
    private static final Path WEB = Paths.get("src", "main", "webapp").toAbsolutePath().normalize();
    private static final EmployeeDAO dao = new EmployeeDAO();

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/api/employees", MainServer::handleEmployees);
        server.createContext("/", MainServer::serveStatic);
        server.setExecutor(null);
        server.start();
        System.out.println("Employee Management System running at http://localhost:" + PORT);
        System.out.println("Press Ctrl+C to stop the server.");
    }

    private static void handleEmployees(HttpExchange ex) throws IOException {
        addCors(ex);
        try {
            String method = ex.getRequestMethod();
            Map<String,String> q = params(ex.getRequestURI().getRawQuery());
            if ("GET".equalsIgnoreCase(method) && "delete".equals(q.get("action"))) {
                dao.delete(Integer.parseInt(q.get("id")));
                send(ex, "Employee deleted successfully", "text/plain", 200);
            } else if ("GET".equalsIgnoreCase(method)) {
                String action = q.getOrDefault("action", "list");
                if ("get".equals(action)) {
                    Employee e = dao.get(Integer.parseInt(q.get("id")));
                    send(ex, e == null ? "null" : json(e), "application/json", 200);
                } else if ("search".equals(action)) send(ex, json(dao.list(q.getOrDefault("keyword", ""))), "application/json", 200);
                else send(ex, json(dao.list("")), "application/json", 200);
            } else if ("POST".equalsIgnoreCase(method)) {
                Map<String,String> p = bodyParams(ex);
                String action = p.getOrDefault("action", "add");
                double salary = Double.parseDouble(p.get("salary"));
                if ("update".equals(action)) dao.update(Integer.parseInt(p.get("id")), p.get("name"), p.get("email"), p.get("department"), salary);
                else dao.add(p.get("name"), p.get("email"), p.get("department"), salary);
                send(ex, "update".equals(action) ? "Employee updated successfully" : "Employee added successfully", "text/plain", 200);
            } else if ("DELETE".equalsIgnoreCase(method)) {
                dao.delete(Integer.parseInt(q.get("id"))); send(ex, "Employee deleted successfully", "text/plain", 200);
            } else send(ex, "Method not allowed", "text/plain", 405);
        } catch (Exception e) {
            e.printStackTrace();
            send(ex, "Error: " + e.getMessage(), "text/plain", 500);
        } finally { ex.close(); }
    }

    private static void serveStatic(HttpExchange ex) throws IOException {
        try {
            String path = ex.getRequestURI().getPath();
            if (path.equals("/")) path = "/index.html";
            Path file = WEB.resolve(path.substring(1)).normalize();
            if (!file.startsWith(WEB) || !Files.exists(file) || Files.isDirectory(file)) { send(ex, "Not Found", "text/plain", 404); return; }
            String type = contentType(file.toString());
            byte[] data = Files.readAllBytes(file);
            ex.getResponseHeaders().set("Content-Type", type);
            ex.sendResponseHeaders(200, data.length);
            ex.getResponseBody().write(data);
        } finally { ex.close(); }
    }

    private static Map<String,String> bodyParams(HttpExchange ex) throws IOException {
        String body = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        return params(body);
    }
    private static Map<String,String> params(String raw) throws UnsupportedEncodingException {
        Map<String,String> m = new HashMap<>();
        if (raw == null || raw.isEmpty()) return m;
        for (String pair : raw.split("&")) { String[] a = pair.split("=",2); String k=URLDecoder.decode(a[0],"UTF-8"); String v=a.length>1?URLDecoder.decode(a[1],"UTF-8"):""; m.put(k,v); }
        return m;
    }
    private static String json(Employee e) { return "{\"id\":"+e.id+",\"name\":"+q(e.name)+",\"email\":"+q(e.email)+",\"department\":"+q(e.department)+",\"salary\":"+e.salary+"}"; }
    private static String json(List<Employee> es) { StringBuilder s=new StringBuilder("["); for(int i=0;i<es.size();i++){if(i>0)s.append(',');s.append(json(es.get(i)));} return s.append(']').toString(); }
    private static String q(String s) { return "\"" + s.replace("\\","\\\\").replace("\"","\\\"").replace("\n","\\n").replace("\r","\\r") + "\""; }
    private static void send(HttpExchange ex,String text,String type,int code)throws IOException{byte[] b=text.getBytes(StandardCharsets.UTF_8);ex.getResponseHeaders().set("Content-Type",type+"; charset=UTF-8");ex.sendResponseHeaders(code,b.length);ex.getResponseBody().write(b);}
    private static void addCors(HttpExchange ex){ex.getResponseHeaders().set("Access-Control-Allow-Origin","*");}
    private static String contentType(String f){String x=f.toLowerCase();if(x.endsWith(".html"))return"text/html";if(x.endsWith(".css"))return"text/css";if(x.endsWith(".js"))return"application/javascript";return"application/octet-stream";}
}
