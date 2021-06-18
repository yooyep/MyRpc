package enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 序列化器的编码；在字节流中第二位
 * @author yooyep
 * @create 2021-06-18 15:52
 */
@AllArgsConstructor
@Getter
public enum SerializerCode {

    JSON(1);

    private int code;
}
