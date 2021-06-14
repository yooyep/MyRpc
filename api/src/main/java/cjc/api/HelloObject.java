package cjc.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yooyep
 * @create 2021-06-07 21:33
 */
@Data
@AllArgsConstructor
public class HelloObject implements Serializable {
    private int id;
    private String message;
}
