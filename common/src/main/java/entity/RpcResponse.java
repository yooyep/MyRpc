package entity;

import enumeration.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.io.Serializable;

/**
 * @author yooyep
 * @create 2021-06-07 21:44
 */
@Data
@NoArgsConstructor
public class RpcResponse<T> implements Serializable {
    // 响应状态码
    private Integer statusCode;
    // 响应消息
    private String message;
    // 响应数据
    private T data;

    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    public static <T> RpcResponse<T> fail(ResponseCode code) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }
}
