package transport;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.RpcRequest;
import serializer.CommonSerializer;

/**
 * @author yooyep
 * @create 2021-06-18 17:42
 */
public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
    void setSerializer(CommonSerializer serializer);
}
