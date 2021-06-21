import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.RpcRequest;

/**
 * @author yooyep
 * @create 2021-06-18 18:59
 */
public class test {
    public static void main(String[] args) {
        String tmp = "{\"interfaceName\":\"cjc.api.HelloService\",\"methodName\":\"hello\",\"parameters\":[{\"id\":888,\"message\":\"this is a message\"}],\"paramTypes\":[\"cjc.api.HelloObject\"]}";
        ObjectMapper mapper = new ObjectMapper();
        try {
            RpcRequest rpcRequest = mapper.readValue(tmp, RpcRequest.class);
            System.out.println(rpcRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
