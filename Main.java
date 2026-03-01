package app;

import com.sun.net.httpserver.HttpServer;
import config.AppConfig;
import db.Database;

import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws Exception {
        AppConfig cfg = AppConfig.load("config.properties");
        Files.createDirectories(Path.of(cfg.uploadsDir));

        Database db = new Database(cfg);

        HttpServer server = HttpServer.create(new InetSocketAddress(cfg.port), 0);
        Router router = new Router(cfg, db);

        server.createContext("/", router::handle);
        server.setExecutor(null);
        server.start();

        System.out.println("API corriendo en http://localhost:" + cfg.port);

    }



}
