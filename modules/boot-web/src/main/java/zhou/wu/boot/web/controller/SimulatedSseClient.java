package zhou.wu.boot.web.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * 模拟SseEmitter客户端简单使用示例
 * @author zhou.wu
 * @date 2024/3/11
 **/
public class SimulatedSseClient {

    public static void main(String[] args) {
        HttpClient client = HttpClients.createDefault();
        // 服务器端的事件源
        HttpGet request = new HttpGet("http://127.0.0.1:8080/sse-emitter/events");
        // 设置请求头
        request.setHeader("Accept", "text/event-stream");

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                HttpResponse response = client.execute(request);
                handleServerSentEvents(response.getEntity().getContent());
            } catch (IOException e) {
                System.err.println("Error occurred: " + e);
            }
        });

        // 在主线程中等待 CompletableFuture 执行完毕
        future.join();
    }

    private static void handleServerSentEvents(java.io.InputStream inputStream) throws IOException {
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("Received event: " + line);
        }
    }

}
