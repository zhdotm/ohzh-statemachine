package io.github.zhdotm.ohzh.statemachine.starter.web.annotation;

import java.lang.annotation.*;

/**
 * 状态机条件组件
 *
 * @author zhihao.mao
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StateMachineCondition {

    String conditionCode() default "";
}
