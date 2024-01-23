package io.github.zhdotm.ohzh.statemachine.starter.web;

import io.github.zhdotm.ohzh.statemachine.starter.web.annotation.EnableStateMachine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhihao.mao
 */

@EnableStateMachine
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
