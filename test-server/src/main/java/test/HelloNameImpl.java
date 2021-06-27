package test;

import annotation.Service;
import cjc.api.HelloName;

/**
 * @author yooyep
 * @create 2021-06-27 15:59
 */
@Service
public class HelloNameImpl implements HelloName {
    @Override
    public String hello(String name) {
        return "hello," + name;
    }
}
