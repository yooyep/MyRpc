package test;

import annotation.Service;
import cjc.api.HelloObject;
import cjc.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yooyep
 * @create 2021-06-14 15:56
 */
@Service
public class HelloServiceImpl implements HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到：{}",object.getMessage());
        return "这是函数调用的返回值，hello, id=" + object.getId();
    }
}
