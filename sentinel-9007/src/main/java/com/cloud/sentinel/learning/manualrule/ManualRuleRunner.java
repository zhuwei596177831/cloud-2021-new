package com.cloud.sentinel.learning.manualrule;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-02-20 17:13:10
 * @description 编码方式定义资源限流、降级、熔断、保护等等
 */
@Component
public class ManualRuleRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //流量控制规则
//        loadFlowRule();
        //熔断降级规则 (DegradeRule)
        loadDegradeRule();
    }

    /**
     * @author: 朱伟伟
     * @date: 2021-02-20 17:15
     * @description: 流量控制规则
     **/
    private void loadFlowRule() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule flowRule = new FlowRule("testSentinel");
        //set limit qps to 10
        flowRule.setCount(10);
        //QPS 模式（1）或并发线程数模式（0） 默认:qps
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        flowRule.setGrade(RuleConstant.FLOW_GRADE_THREAD);
        //流控效果（直接拒绝/WarmUp/匀速+排队等待），不支持按调用关系限流 默认直接拒绝
//        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_WARM_UP);
        rules.add(flowRule);
        FlowRuleManager.loadRules(rules);
    }

    /**
     * @author: 朱伟伟
     * @date: 2021-02-20 17:38
     * @description: 熔断降级规则
     **/
    private void loadDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource("testDegradeRule");
        // set threshold RT, 10 ms
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        //慢调用比例模式下为慢调用临界 RT（超出该值计为慢调用）；异常比例/异常数模式下为对应的阈值
        rule.setCount(1);
        //熔断时长，单位为 s
        rule.setTimeWindow(100);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }

}
