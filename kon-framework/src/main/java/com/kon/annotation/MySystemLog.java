package com.kon.annotation;



import java.lang.annotation.*;

/**
 * 自定义注解，用于标识某个方法需要进行功能字段自动填充处理
 */
@Documented
@Target(ElementType.METHOD) //表示AutoFill注解只能用于方法上
@Retention(RetentionPolicy.RUNTIME)//表示AutoFill注解会保持到runtime阶段
public @interface MySystemLog {
    //为controller提供接口的描述信息,用于"日志记录"功能
    String businessName();
}