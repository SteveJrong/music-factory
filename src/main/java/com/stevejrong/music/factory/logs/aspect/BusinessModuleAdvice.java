package com.stevejrong.music.factory.logs.aspect;

import com.stevejrong.music.factory.common.enums.BusinessModuleLogServiceEnums;
import com.stevejrong.music.factory.logs.service.IBusinessModuleLogService;
import org.aspectj.lang.JoinPoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BusinessModuleAdvice {

    /**
     * 后置通知
     * 在目标方法执行无异常后执行
     */
    public void afterReturn(JoinPoint joinpoint, Object returnObject) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-bean.xml");

        Class logServiceImplClassByBusinessModule = BusinessModuleLogServiceEnums.getLogServiceImplClassByBusinessModule(joinpoint.getTarget().getClass());
        IBusinessModuleLogService businessModuleLogService = (IBusinessModuleLogService) context.getBean(logServiceImplClassByBusinessModule);
        businessModuleLogService.afterReturnDoAction(returnObject);
    }

    /**
     * 后置异常通知
     * 在目标方法执行异常后执行
     */
    public void afterException(JoinPoint joinpoint, Throwable exception) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-bean.xml");

        Class logServiceImplClassByBusinessModule = BusinessModuleLogServiceEnums.getLogServiceImplClassByBusinessModule(joinpoint.getTarget().getClass());
        IBusinessModuleLogService businessModuleLogService = (IBusinessModuleLogService) context.getBean(logServiceImplClassByBusinessModule);
        businessModuleLogService.afterExceptionDoAction(exception);
    }
}
