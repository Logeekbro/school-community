package com.db.dbcommunity.visit;

import com.db.dbcommunity.visit.feign.UserPermissionFeignClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.util.pattern.PathPattern;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SpringBootTest
class SchoolCommunityVisitApplicationTests {

	@Resource
	private ApplicationContext applicationContext;

	@Resource
	private UserPermissionFeignClient userPermissionFeignClient;

	@Test
	void contextLoads() {
		AbstractHandlerMethodMapping<RequestMappingInfo> objHandlerMethodMapping = (AbstractHandlerMethodMapping<RequestMappingInfo>)applicationContext.getBean("requestMappingHandlerMapping");
		Map<RequestMappingInfo, HandlerMethod> mapRet = objHandlerMethodMapping.getHandlerMethods();
		for(RequestMappingInfo r : mapRet.keySet()) {
			if(r.getName() == null) continue;
			boolean isUserDefault;
			String name = r.getName();
			String[] split = name.split("_");
			if(split.length == 2) {
				isUserDefault = split[1].equals("true");
			} else {
				System.out.println("名称格式有误--->" + name);
				continue;
			}
			Set<RequestMethod> methods = r.getMethodsCondition().getMethods();
			for(RequestMethod method : methods) {
				PathPatternsRequestCondition patternsCondition = r.getPathPatternsCondition();
				if(patternsCondition == null) continue;
				for(PathPattern path : patternsCondition.getPatterns()) {
					String ps = path.getPatternString().replaceAll("\\{.*?}", "*");
					String urlPerm = method + ":" + ps;
					Map<String, Object> map = new HashMap<>();
					map.put("isUserDefault",isUserDefault);
					map.put("name",name.substring(0, name.indexOf('_')));
					map.put("urlPerm",urlPerm);
					System.out.println(map);
					userPermissionFeignClient.addPermission(map);
				}
			}
		}
	}

}
