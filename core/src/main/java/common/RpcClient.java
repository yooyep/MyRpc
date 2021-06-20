package common;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.RpcRequest;

/**
 * @author yooyep
 * @create 2021-06-18 17:42
 */
public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
}
