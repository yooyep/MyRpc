package enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yooyep
 * @create 2021-06-15 21:12
 */
@Getter
@AllArgsConstructor
public enum RpcError {
    SERVICE_INVOCATION_FAILURE("服务调用出现失败"),
    SERVICE_NOT_FOUND("找不到对应的服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务未实现接口");

    private final String message;
}
