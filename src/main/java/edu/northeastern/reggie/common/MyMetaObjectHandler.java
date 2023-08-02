package edu.northeastern.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * self defined - common data Object Handler
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * For new object, insert / create new Object
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("Common Text message auto [insert] ... !!!");

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }

    /**
     * For update object, update / edit current object
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("Common Text message auto [Update] ... !!!");

        Long id = Thread.currentThread().getId();
        log.info("Thread ID : {}", id);

        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());

    }
}
