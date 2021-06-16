package server;

import entity.RpcRequest;
import entity.RpcResponse;
import enumeration.ResponseCode;
import enumeration.RpcError;
import exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * jdk动态代理的处理逻辑
 * @author yooyep
 * @create 2021-06-16 16:46
 */
public class RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    /**
     * 动态代理 调用服务方法
     * @param rpcRequest
     * @param service
     * @return
     */
    public Object handle(RpcRequest rpcRequest, Object service) {
        Object result = null;
        try {
            result = invokeTargetMethod(rpcRequest,service);
            logger.info("服务:{} 成功调用方法:{}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());

        } catch (InvocationTargetException | IllegalAccessException e) {
            logger.error("调用或发送时有错误发生：", e);
        }
        return result;
    }

    public Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws InvocationTargetException, IllegalAccessException {
        Method method;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            // 找不到对应方法
            return RpcResponse.fail(ResponseCode.NOT_FOUND_METHOD);
        }
        return method.invoke(service, rpcRequest.getParameters());
    }

}
