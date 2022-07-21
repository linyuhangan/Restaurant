package com.lyhyl.restaurant.common;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
@Slf4j
public class MyMetaObjectHandler  implements MetaObjectHandler {
    /**
     * 插入操作，自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());

        metaObject.setValue("createUser",BaseContext.getCurrentId() );

        metaObject.setValue("updateTime", LocalDateTime.now());

        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }

    /**
     * 更新操作，自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        metaObject.setValue("updateTime", LocalDateTime.now());

        long l = Thread.currentThread().getId();
        log.info("线程ID为：{}",l);

        metaObject.setValue("updateUser", BaseContext.getCurrentId());

    }
}
