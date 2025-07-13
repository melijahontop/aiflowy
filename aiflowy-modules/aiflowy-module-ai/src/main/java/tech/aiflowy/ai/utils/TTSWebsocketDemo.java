package tech.aiflowy.ai.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okio.ByteString;

/**
 * Author：censhengde on 2024/11/25 15:51
 *
 * explain：<a href="https://www.volcengine.com/docs/6561/1329505">双向流式API-支持复刻</a>
 */
public class TTSWebsocketDemo {

    private static final int PROTOCOL_VERSION = 0b0001;
    private static final int DEFAULT_HEADER_SIZE = 0b0001;

    // Message Type:
    private static final int FULL_CLIENT_REQUEST = 0b0001;
    private static final int AUDIO_ONLY_RESPONSE = 0b1011;
    private static final int FULL_SERVER_RESPONSE = 0b1001;
    private static final int ERROR_INFORMATION = 0b1111;

    // Message Type Specific Flags
    private static final int MsgTypeFlagNoSeq = 0b0000; // Non-terminal packet with no sequence
    private static final int MsgTypeFlagPositiveSeq = 0b1;// Non-terminal packet with sequence > 0
    private static final int MsgTypeFlagLastNoSeq = 0b10;// last packet with no sequence
    private static final int MsgTypeFlagNegativeSeq = 0b11; // Payload contains event number (int32)
    private static final int MsgTypeFlagWithEvent = 0b100;
    // Message Serialization
    private static final int NO_SERIALIZATION = 0b0000;
    private static final int JSON = 0b0001;
    // Message Compression
    private static final int COMPRESSION_NO = 0b0000;
    private static final int COMPRESSION_GZIP = 0b0001;

    // event


    // 默认事件,对于使用事件的方案，可以通过非0值来校验事件的合法性
    public static final int EVENT_NONE = 0;

    public static final int EVENT_Start_Connection = 1;


    // 上行Connection事件
    public static final int EVENT_FinishConnection = 2;

    // 下行Connection事件
    public static final int EVENT_ConnectionStarted = 50; // 成功建连

    public static final int EVENT_ConnectionFailed = 51; // 建连失败（可能是无法通过权限认证）

    public static final int EVENT_ConnectionFinished = 52; // 连接结束

    // 上行Session事件
    public static final int EVENT_StartSession = 100;

    public static final int EVENT_FinishSession = 102;

    // 下行Session事件
    public static final int EVENT_SessionStarted = 150;

    public static final int EVENT_SessionFinished = 152;

    public static final int EVENT_SessionFailed = 153;

    // 上行通用事件
    public static final int EVENT_TaskRequest = 200;

    // 下行TTS事件
    public static final int EVENT_TTSSentenceStart = 350;

    public static final int EVENT_TTSSentenceEnd = 351;

    public static final int EVENT_TTSResponse = 352;


    public static void main(String[] args) throws IOException {
        final String appId = "7376564606";
        final String token = "jK1KuLGhfitieeOknj2Yam94R0bpN8ab";

        String url = "wss://openspeech.bytedance.com/api/v3/tts/bidirection";
        final String testText = "落霞与孤鹜齐飞，秋水共长天一色。东隅已逝，桑榆非晚，关山难越，谁悲失路之人？";
        final String speaker = "zh_female_shuangkuaisisi_moon_bigtts";

        final File outputFile = new File(
                "填写输出音频文件的路径");
        if (outputFile.exists()) {
            outputFile.delete();
        }
        outputFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(outputFile);
        final Request request = new Request.Builder()
                .url(url)
                .header("X-Api-App-Key", appId)
                .header("X-Api-Access-Key", token)
                .header("X-Api-Resource-Id", "volc.service_type.10029")
                .header("X-Api-Connect-Id", UUID.randomUUID().toString())
                .build();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder().pingInterval(50, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .build();

        okHttpClient.newWebSocket(request, new WebSocketListener() {

            final String sessionId = UUID.randomUUID().toString().replace("-", "");

            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                startConnection(webSocket);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                TTSResponse response = parserResponse(bytes.toByteArray());
                switch (response.optional.event) {
                    case EVENT_ConnectionFailed:
                    case EVENT_SessionFailed: {
                        System.exit(-1);
                    }
                    case EVENT_ConnectionStarted:
                        startTTSSession(webSocket, sessionId, speaker);
                        break;
                    case EVENT_SessionStarted:
                        // 多段文本
                        String[] testTexts = new String[]{"你好，", "帮我", "合成", "一个", "音频"};
                        for (String text : testTexts) {
                            sendMessage(webSocket, speaker, sessionId, text);
                        }
                        // 单段文本
//                        sendMessage(webSocket, speaker, sessionId, testText);
                        // 没有要发送的文本了，finish
                        finishSession(webSocket, sessionId);
                        break;
                    case EVENT_TTSSentenceStart:
                    case EVENT_TTSSentenceEnd:
                        break;
                    case EVENT_TTSResponse: {
                        if (response.payload == null) {
                            return;
                        }
                        // 输出结果
                        if (response.header.message_type == AUDIO_ONLY_RESPONSE) {
                            try {
                                System.out.println(Base64.getEncoder().encodeToString(response.payload));
                                fos.write(response.payload);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;
                    }
                    case EVENT_ConnectionFinished:
                        try {
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.exit(0);
                    case EVENT_SessionFinished:
                        finishConnection(webSocket);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
            }

            public void onClosing(WebSocket webSocket, int code, String reason) {
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                t.printStackTrace();
                System.exit(1000);
            }
        });

        try {
            synchronized (Thread.currentThread()) {
                Thread.currentThread().wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    static int bytesToInt(byte[] src) {
        if (src == null || (src.length != 4)) {
            throw new IllegalArgumentException("");
        }
        return ((src[0] & 0xFF) << 24)
                | ((src[1] & 0xff) << 16)
                | ((src[2] & 0xff) << 8)
                | ((src[3] & 0xff));
    }

    static byte[] intToBytes(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)

        };
    }

    public static class Header {

        public int protocol_version = PROTOCOL_VERSION;
        public int header_size = DEFAULT_HEADER_SIZE;
        public int message_type;
        public int message_type_specific_flags = MsgTypeFlagWithEvent;
        public int serialization_method = NO_SERIALIZATION;
        public int message_compression = COMPRESSION_NO;
        public int reserved = 0;

        public Header() {
        }

        public Header(int protocol_version, int header_size, int message_type, int message_type_specific_flags,
                int serialization_method, int message_compression, int reserved) {
            this.protocol_version = protocol_version;
            this.header_size = header_size;
            this.message_type = message_type;
            this.message_type_specific_flags = message_type_specific_flags;
            this.serialization_method = serialization_method;
            this.message_compression = message_compression;
            this.reserved = reserved;
        }

        /**
         * 转成 byte 数组
         *
         * @return
         */
        public byte[] getBytes() {
            return new byte[]{
                    // Protocol version | Header size (4x)
                    (byte) ((protocol_version << 4) | header_size),
                    // Message type | Message type specific flags
                    (byte) (message_type << 4 | message_type_specific_flags),
                    // Serialization method | Compression method
                    (byte) ((serialization_method << 4) | message_compression),
                    (byte) reserved
            };
        }
    }

    public static class Optional {

        public int event = EVENT_NONE;
        public String sessionId;

        public int errorCode;
        public String connectionId;

        public String response_meta_json;

        public Optional(int event, String sessionId) {
            this.event = event;
            this.sessionId = sessionId;
        }

        public Optional() {
        }

        public byte[] getBytes() {
            byte[] bytes = new byte[0];
            if (event != EVENT_NONE) {
                bytes = intToBytes(event);
            }
            if (sessionId != null) {
                byte[] sessionIdSize = intToBytes(sessionId.getBytes().length);
                final byte[] temp = bytes;
                int desPos = 0;
                bytes = new byte[temp.length + sessionIdSize.length + sessionId.getBytes().length];
                System.arraycopy(temp, 0, bytes, desPos, temp.length);
                desPos += temp.length;
                System.arraycopy(sessionIdSize, 0, bytes, desPos, sessionIdSize.length);
                desPos += sessionIdSize.length;
                System.arraycopy(sessionId.getBytes(), 0, bytes, desPos, sessionId.getBytes().length);

            }
            return bytes;
        }
    }

    static class Pair<F, S> {

        public Pair(F fst, S snd) {
            this.fst = fst;
            this.snd = snd;
        }

        public F fst;
        public S snd;
    }

    public static class TTSResponse {

        public Header header;
        public Optional optional;
        public int payloadSize;
        transient public byte[] payload;

        public String payloadJson;


        @Override
        public String toString() {
            return new Gson().toJson(this);
        }


    }


    /**
     * 解析响应包
     *
     * @param res
     * @return
     */
    static TTSResponse parserResponse(byte[] res) {
        if (res == null || res.length == 0) {
            return null;
        }
        final TTSResponse response = new TTSResponse();
        Header header = new Header();
        response.header = header;

        // 当符号位为1时进行 >> 运算后高位补1（预期是补0），导致结果错误，所以增加个数再与其& 运算，目的是确保高位是补0.
        final byte num = 0b00001111;
        // header 32 bit=4 byte
        header.protocol_version = (res[0] >> 4) & num;
        header.header_size = res[0] & 0x0f;
        header.message_type = (res[1] >> 4) & num;
        header.message_type_specific_flags = res[1] & 0x0f;
        header.serialization_method = res[2] >> num;
        header.message_compression = res[2] & 0x0f;
        header.reserved = res[3];

        int offset = 4;
        response.optional = new Optional();
        // 正常Response
        if (header.message_type == FULL_SERVER_RESPONSE || header.message_type == AUDIO_ONLY_RESPONSE) {
            // 如果有event
            offset = readEvent(res, header.message_type_specific_flags, response, offset);
            final int event = response.optional.event;
            // 根据 event 类型解析
            switch (event) {
                case EVENT_ConnectionStarted:
                    readConnectStarted(res, response, offset);
                    break;
                case EVENT_ConnectionFailed:
                    readConnectFailed(res, response, offset);
                    break;
                case EVENT_SessionStarted:
                    offset = readSessionId(res, response, offset);
                    break;
                case EVENT_TTSResponse:
                    offset = readSessionId(res, response, offset);
                    offset = readPayload(res, response, offset);
                    break;
                case EVENT_TTSSentenceStart:
                case EVENT_TTSSentenceEnd:
                    offset = readSessionId(res, response, offset);
                    offset = readPayloadJson(res, response, offset);
                    break;

                case EVENT_SessionFailed:
                case EVENT_SessionFinished:
                    offset = readSessionId(res, response, offset);
                    readMetaJson(res, response, offset);
                    break;
                default:
                    break;


            }
        }
        // 错误
        else if (header.message_type == ERROR_INFORMATION) {
            offset = readErrorCode(res, response, offset);
            readPayload(res, response, offset);
        }
        return response;
    }

    static void readConnectStarted(byte[] res, TTSResponse response, int start) {
        // 8--11: connection id size
        start = readConnectId(res, response, start);
    }

    static void readConnectFailed(byte[] res, TTSResponse response, int start) {
        // 8--11: connection id len
        start = readConnectId(res, response, start);

        readMetaJson(res, response, start);
    }

    static int readConnectId(byte[] res, TTSResponse response, int start) {
        Pair<Integer, String> r = readText(res, start);
        start = r.fst;
        response.optional.connectionId = r.snd;
        return start;
    }


    static int readMetaJson(byte[] res, TTSResponse response, int start) {
        Pair<Integer, String> r = readText(res, start);
        start = r.fst;
        response.optional.response_meta_json = r.snd;
        return start;
    }

    static int readPayloadJson(byte[] res, TTSResponse response, int start) {
        Pair<Integer, String> r = readText(res, start);
        start = r.fst;
        response.payloadJson = r.snd;
        return start;
    }

    static Pair<Integer, String> readText(byte[] res, int start) {
        byte[] b = new byte[4];
        System.arraycopy(res, start, b, 0, b.length);
        start += b.length;
        int size = bytesToInt(b);
        b = new byte[size];
        System.arraycopy(res, start, b, 0, b.length);
        String text = new String(b);
        start += b.length;
        return new Pair<>(start, text);
    }

    static int readPayload(byte[] res, TTSResponse response, int start) {
        byte[] b = new byte[4];
        System.arraycopy(res, start, b, 0, b.length);
        start += b.length;
        int size = bytesToInt(b);
        response.payloadSize += size;
        b = new byte[size];
        System.arraycopy(res, start, b, 0, b.length);
        response.payload = b;
        start += b.length;
        if (response.optional.event == FULL_SERVER_RESPONSE) {
        }
        return start;
    }

    static int readErrorCode(byte[] res, TTSResponse response, int start) {
        byte[] b = new byte[4];
        System.arraycopy(res, start, b, 0, b.length);
        start += b.length;
        response.optional.errorCode = bytesToInt(b);
        return start;
    }


    static int readEvent(byte[] res, int masTypeFlag, TTSResponse response, int start) {
        if (masTypeFlag == MsgTypeFlagWithEvent) {
            byte[] temp = new byte[4];
            System.arraycopy(res, start, temp, 0, temp.length);
            response.optional.event = bytesToInt(temp);
            start += temp.length;
        }
        return start;
    }


    static int readSessionId(byte[] res, TTSResponse response, int start) {
        Pair<Integer, String> r = readText(res, start);
        start = r.fst;
        response.optional.sessionId = r.snd;
        return start;
    }


    static boolean startConnection(WebSocket webSocket) {
        byte[] header = new Header(
                PROTOCOL_VERSION,
                FULL_CLIENT_REQUEST,
                DEFAULT_HEADER_SIZE,
                MsgTypeFlagWithEvent,
                JSON,
                COMPRESSION_NO,
                0).getBytes();
        byte[] optional = new Optional(EVENT_Start_Connection, null).getBytes();
        byte[] payload = "{}".getBytes();
        return sendEvent(webSocket, header, optional, payload);
    }

    static boolean finishConnection(WebSocket webSocket) {
        byte[] header = new Header(
                PROTOCOL_VERSION,
                FULL_CLIENT_REQUEST,
                DEFAULT_HEADER_SIZE,
                MsgTypeFlagWithEvent,
                JSON,
                COMPRESSION_NO,
                0).getBytes();
        byte[] optional = new Optional(EVENT_FinishConnection, null).getBytes();
        byte[] payload = "{}".getBytes();
        return sendEvent(webSocket, header, optional, payload);

    }

    static boolean finishSession(WebSocket webSocket, String sessionId) {
        byte[] header = new Header(
                PROTOCOL_VERSION,
                FULL_CLIENT_REQUEST,
                DEFAULT_HEADER_SIZE,
                MsgTypeFlagWithEvent,
                JSON,
                COMPRESSION_NO,
                0).getBytes();
        byte[] optional = new Optional(EVENT_FinishSession, sessionId).getBytes();
        byte[] payload = "{}".getBytes();
        return sendEvent(webSocket, header, optional, payload);
    }

    static boolean startTTSSession(WebSocket webSocket, String sessionId, String speaker) {
        byte[] header = new Header(
                PROTOCOL_VERSION,
                FULL_CLIENT_REQUEST,
                DEFAULT_HEADER_SIZE,
                MsgTypeFlagWithEvent,
                JSON,
                COMPRESSION_NO,
                0).getBytes();

        final int event = EVENT_StartSession;
        byte[] optional = new Optional(event, sessionId).getBytes();
        JsonObject payloadJObj = new JsonObject();
        JsonObject user = new JsonObject();
        user.addProperty("uid", "123456");

        payloadJObj.add("user", user);
        payloadJObj.addProperty("event", event);
        payloadJObj.addProperty("namespace", "BidirectionalTTS");

        JsonObject req_params = new JsonObject();
        req_params.addProperty("speaker", speaker);

        JsonObject audio_params = new JsonObject();
        audio_params.addProperty("format", "mp3");
        audio_params.addProperty("sample_rate", 24000);
        audio_params.addProperty("enable_timestamp", true);

        req_params.add("audio_params", audio_params);
        payloadJObj.add("req_params", req_params);
        byte[] payload = payloadJObj.toString().getBytes();
        return sendEvent(webSocket, header, optional, payload);
    }

    /**
     * 分段合成音频
     *
     * @param webSocket
     * @param speaker
     * @param sessionId
     * @param text
     * @return
     */
    static boolean sendMessage(WebSocket webSocket, String speaker, String sessionId, String text) {
        byte[] header = new Header(
                PROTOCOL_VERSION,
                FULL_CLIENT_REQUEST,
                DEFAULT_HEADER_SIZE,
                MsgTypeFlagWithEvent,
                JSON,
                COMPRESSION_NO,
                0).getBytes();

        byte[] optional = new Optional(EVENT_TaskRequest, sessionId).getBytes();

        JsonObject payloadJObj = new JsonObject();
        JsonObject user = new JsonObject();
        user.addProperty("uid", "123456");
        payloadJObj.add("user", user);

        payloadJObj.addProperty("event", EVENT_TaskRequest);
        payloadJObj.addProperty("namespace", "BidirectionalTTS");

        JsonObject req_params = new JsonObject();
        req_params.addProperty("text", text);
        req_params.addProperty("speaker", speaker);

        JsonObject audio_params = new JsonObject();
        audio_params.addProperty("format", "mp3");
        audio_params.addProperty("sample_rate", 24000);

        req_params.add("audio_params", audio_params);
        payloadJObj.add("req_params", req_params);
        byte[] payload = payloadJObj.toString().getBytes();
        return sendEvent(webSocket, header, optional, payload);
    }

    static boolean sendEvent(WebSocket webSocket, byte[] header, byte[] optional, byte[] payload) {
        assert webSocket != null;
        assert header != null;
        assert payload != null;
        final byte[] payloadSizeBytes = intToBytes(payload.length);
        byte[] requestBytes = new byte[
                header.length
                        + (optional == null ? 0 : optional.length)
                        + payloadSizeBytes.length + payload.length];
        int desPos = 0;
        System.arraycopy(header, 0, requestBytes, desPos, header.length);
        desPos += header.length;
        if (optional != null) {
            System.arraycopy(optional, 0, requestBytes, desPos, optional.length);
            desPos += optional.length;
        }
        System.arraycopy(payloadSizeBytes, 0, requestBytes, desPos, payloadSizeBytes.length);
        desPos += payloadSizeBytes.length;
        System.arraycopy(payload, 0, requestBytes, desPos, payload.length);
        return webSocket.send(ByteString.of(requestBytes));
    }

}
