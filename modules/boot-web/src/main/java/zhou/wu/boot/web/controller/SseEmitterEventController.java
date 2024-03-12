package zhou.wu.boot.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * SseEmitter服务端简单使用示例
 * "SseEmitter" 中的 "SSE" 意指 "Server-Sent Events"，表示服务器发送事件。而 "Emitter" 是发射器或发射者的意思，通常指代可以发送事件的对象。
 * @author zhou.wu
 * @date 2024/3/11
 **/
@RestController
@RequestMapping("/sse-emitter")
public class SseEmitterEventController {

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @GetMapping("/events")
    public SseEmitter handleEvents() {
        SseEmitter emitter = new SseEmitter();

        // 使用定时任务每秒向客户端发送一个实时事件
        executorService.scheduleAtFixedRate(() -> {
            try {
                emitter.send(LocalTime.now().toString());
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        }, 0, 10, TimeUnit.SECONDS);

        // 当客户端连接关闭时，取消定时任务并关闭 SseEmitter
        emitter.onCompletion(() -> executorService.shutdown());
        emitter.onTimeout(() -> emitter.complete());

        return emitter;
    }
}
