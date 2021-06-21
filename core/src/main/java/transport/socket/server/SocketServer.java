package transport.socket.server;

import serializer.CommonSerializer;
import transport.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import provider.ServiceProvider;
import transport.RpcServer;

import javax.imageio.spi.ServiceRegistry;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author yooyep
 * @create 2021-06-14 11:29
 */
public class SocketServer implements RpcServer {
    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private final String host;
    private final int port;
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;
    private final ExecutorService threadPool;
    private final ServiceProvider serviceRegistry;
    private RequestHandler requestHandler = new RequestHandler();
    private CommonSerializer serializer;

    public SocketServer(String host, int port, ServiceProvider serviceRegistry) {
        this.host = host;
        this.port = port;
        this.serviceRegistry = serviceRegistry;
        // 创建线程池
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE_TIME,
                                TimeUnit.SECONDS,workingQueue,threadFactory);
    }

    @Override
    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            logger.info("服务启动");
            Socket socket;
            while ((socket = serverSocket.accept()) != null){
                logger.info("消费者连接：{}:{}" + socket.getInetAddress(), socket.getPort());
//                threadPool.submit(new WorkerThread(transport.socket, service));
                threadPool.submit(new RequestHandlerThread(socket, requestHandler, serviceRegistry));
            }
        } catch (IOException e) {
            logger.error("服务器启动时有错误发生:", e);
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public <T> void publishService(Object service, Class<T> serviceClass) {

    }
}
