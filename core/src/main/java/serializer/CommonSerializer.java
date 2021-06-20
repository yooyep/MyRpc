package serializer;

import com.fasterxml.jackson.databind.util.ObjectBuffer;

/**
 * 序列化接口
 * @author yooyep
 * @create 2021-06-18 15:20
 */
public interface CommonSerializer {
    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

    // 根据编号，获取不同的序列化器
    static CommonSerializer getByCode(int code){
        switch (code) {
            case 0:
                return new KryoSerializer();
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}
