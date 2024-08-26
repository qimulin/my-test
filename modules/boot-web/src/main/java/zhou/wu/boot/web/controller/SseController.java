package zhou.wu.boot.web.controller;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
public class SseController {

    @GetMapping("/sse")
    public SseEmitter streamEvents() {
        log.info("into sse method");
        SseEmitter emitter = new SseEmitter(30000L);

        emitter.onCompletion(()-> log.info("onCompletion"));
        emitter.onError((callback)-> log.error("onError:"+callback));
        emitter.onTimeout(()-> log.warn("onTimeout"));

        try {
            emitter.send(SseEmitter.event().data("SSE Connected - " + LocalTime.now().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                emitter.send(SseEmitter.event().data("SSE Heart - " + LocalTime.now().toString()));
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        }, 0, 10, TimeUnit.SECONDS);
        return emitter;
    }
}
