package com.cloud.sentinel.learning.manualrule;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
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
//        apiLoadRule();
    }

    /**
     * @author: 朱伟伟
     * @date: 2021-02-22 16:52
     * @description: api编码方式添加sentinel配置
     **/
    private void apiLoadRule() {
        //流量控制规则
        loadFlowRule();

        //熔断降级规则 (DegradeRule)
        loadDegradeRule();

        //访问控制规则 (AuthorityRule)
        loadAuthorityRule();

        //热点参数规则
        loadParamFlowRule();
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
        // 最近的10秒内，如果请求数大于5并且有百分之60的请求都是慢调用 进行熔断 10s
        // set threshold RT, 10 ms 熔断策略，支持慢调用比例/异常比例/异常数策略 默认:慢调用比例
//        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        //异常比例 [0.0, 1.0]
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO);
        //异常数策略
//        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        //慢调用比例模式下为慢调用临界 RT（即最大的响应时间）（超出该值计为慢调用）；异常比例/异常数模式下为对应的阈值
//        rule.setCount(1);
        rule.setCount(0.6);
//        rule.setCount(6);
        //熔断时长，单位为 s
        rule.setTimeWindow(10);
        //熔断触发的最小请求数，请求数小于该值时即使异常比率超出阈值也不会熔断（1.7.0 引入） 默认 5
        rule.setMinRequestAmount(5);
        //统计时长（单位为 ms），如 60*1000 代表分钟级（1.8.0 引入） 默认1000 ms
        rule.setStatIntervalMs(10000);
        //慢调用比例阈值，仅慢调用比例模式有效（1.8.0 引入）
        rule.setSlowRatioThreshold(60);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }

    /**
     * @author: 朱伟伟
     * @date: 2021-02-22 13:51
     * @description: 访问控制规则
     **/
    private void loadAuthorityRule() {
        List<AuthorityRule> rules = new ArrayList<>();
        AuthorityRule authorityRule = new AuthorityRule();
        authorityRule.setResource("testAuthorityRule");
        //限制模式，AUTHORITY_WHITE 为白名单模式，AUTHORITY_BLACK 为黑名单模式，默认为白名单模式
        authorityRule.setStrategy(RuleConstant.AUTHORITY_WHITE);
        //对应的黑名单/白名单，不同 origin 用 , 分隔，如 appA,appB
        authorityRule.setLimitApp("127.0.0.1,192.168.1.117");
        rules.add(authorityRule);
        AuthorityRuleManager.loadRules(rules);
    }

    /**
     * @author: 朱伟伟
     * @date: 2021-02-22 15:29
     * @description: 热点参数规则
     * 商品id 111 qps 10
     * 其他 qps 20
     **/
    private void loadParamFlowRule() {
        List<ParamFlowRule> rules = new ArrayList<>();
        ParamFlowRule goodsIDFlowRule = new ParamFlowRule();
        goodsIDFlowRule.setResource("testParamFlowRule");
        goodsIDFlowRule.setCount(20);
        goodsIDFlowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //统计窗口时间长度（单位为秒），1.6.0 版本开始支持	default：1s
        goodsIDFlowRule.setDurationInSec(1);
        //流控效果（支持快速失败和匀速排队模式），1.6.0 版本开始支持   default：快速失败
        goodsIDFlowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        //热点参数的索引，必填，对应 SphU.entry(xxx, args) 中的参数索引位置
        goodsIDFlowRule.setParamIdx(0);
        // 针对 string 类型的参数 goodsID，单独设置限流 QPS 阈值为 10，而不是全局的阈值 200.
        ParamFlowItem item = new ParamFlowItem()
                .setObject("111")
                .setClassType(String.class.getName())
                .setCount(10);
        //参数例外项，可以针对指定的参数值单独设置限流阈值，不受前面 count 阈值的限制。仅支持基本类型和字符串类型
        goodsIDFlowRule.setParamFlowItemList(Collections.singletonList(item));
        rules.add(goodsIDFlowRule);
        ParamFlowRuleManager.loadRules(rules);
    }

}
