package entity;

import jdk.jfr.DataAmount;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yooyep
 * @create 2021-06-07 21:42
 */
@Data
@Builder
public class RpcRequest implements Serializable {
    // 待调用接口名称
    private String interfaceName;
    // 待调用方法名称
    private String methodName;
    // 调用方法的参数
    private Object[] parameters;
    // 调用方法的参数类型
    private Class<?>[] paramTypes;
}
