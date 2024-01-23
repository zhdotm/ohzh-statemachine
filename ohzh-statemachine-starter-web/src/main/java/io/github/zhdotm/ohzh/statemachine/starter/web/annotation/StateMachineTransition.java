package io.github.zhdotm.ohzh.statemachine.starter.web.annotation;

import io.github.zhdotm.ohzh.statemachine.core.enums.TransitionTypeEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 状态机组件
 *
 * @author zhihao.mao
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface StateMachineTransition {

    String stateMachineCode();

    TransitionTypeEnum type();

    String[] from();

    String on();

    String to() default "";

    int sort() default Integer.MAX_VALUE;
}
