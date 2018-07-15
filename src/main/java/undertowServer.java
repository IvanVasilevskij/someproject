import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.PathHandler;
import io.undertow.util.Headers;

public class undertowServer {
    public static void main(String[] args) {
        Undertow server = Undertow.builder()
                .addHttpListener(8080,"localhost")
                .setHandler(new PathHandler()
                        .addExactPath("helloworld", new HttpHandler() {
                            @Override
                            public void handleRequest(final HttpServerExchange exchange) throws Exception {
                                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                                exchange.getResponseSender().send("Hello World");
                            }
                        })
                        .addPrefixPath("/", new HttpHandler() {
                            @Override
                            public void handleRequest(final HttpServerExchange exchange) throws Exception {
                                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                                exchange.getResponseSender().send("Another page");
                            }
                        })
                ).build();
        server.start();
    }
}
