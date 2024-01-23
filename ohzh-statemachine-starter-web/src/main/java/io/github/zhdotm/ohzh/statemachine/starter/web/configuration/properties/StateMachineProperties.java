package io.github.zhdotm.ohzh.statemachine.starter.web.configuration.properties;

import io.github.zhdotm.ohzh.statemachine.starter.web.enums.StateMachineScopeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author zhihao.mao
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "ohzh.statemachine")
public class StateMachineProperties {

    private String scope = StateMachineScopeEnum.LOCAL.getCode();

    private String remoteType;

    private List<String> remoteStatemachines;

    private List<String> localStatemachines;

}
