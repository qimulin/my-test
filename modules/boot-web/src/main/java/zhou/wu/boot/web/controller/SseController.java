package zhou.wu.boot.web.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhou.wu
 * @date 2024/7/1
 **/
@RestController
public class SseController {

    @GetMapping("/sse")
    public SseEmitter streamEvents() {
        SseEmitter emitter = new SseEmitter();

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                emitter.send(SseEmitter.event().data("SSE - " + LocalTime.now().toString()));
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        }, 0, 3, TimeUnit.SECONDS);

        return emitter;
    }
}
