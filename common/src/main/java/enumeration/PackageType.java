package enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yooyep
 * @create 2021-06-18 16:02
 */
@AllArgsConstructor
@Getter
public enum PackageType {

    REQUEST(0),
    RESPONSE(1);
    private final int code;

}
