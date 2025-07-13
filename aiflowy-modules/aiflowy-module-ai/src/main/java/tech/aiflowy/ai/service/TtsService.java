package tech.aiflowy.ai.service;

import okhttp3.WebSocket;
import tech.aiflowy.ai.service.impl.VolcTtsServiceImpl;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface TtsService {

    WebSocket init(String connectId, String sessionId,Consumer<String> responseHandler,Consumer<String> sessionFinishHandler);

    void sendTTSMessage(WebSocket webSocket, String sessionId,String text);

}
