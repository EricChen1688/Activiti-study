package cn.eccto.activiti.test.api;

import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author ericchen.vip@foxmail.com 2019/04/21 14:17
 */
public class RepositoryServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryServiceTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();


    @Test
    @Deployment(resources = "second_approve.bpmn20.xml")
    public void testCandidateStrat() {
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
        LOGGER.info("EC 测试 processDefinitionName: {} ,id ={}", processDefinition.getName(), processDefinition.getId());
        repositoryService.addCandidateStarterUser(processDefinition.getId(), "EricChen");
        repositoryService.addCandidateStarterGroup(processDefinition.getId(), "EC_GROUP");


        //查询用户组和用户对象
        List<IdentityLink> list = repositoryService.getIdentityLinksForProcessDefinition(processDefinition.getId());
        for (IdentityLink link : list) {
            LOGGER.info("identityLink ->{}", link);
        }

    }

}
