package com.imnu.cloudDisk.config;

import com.imnu.cloudDisk.entity.SysMenu;
import com.imnu.cloudDisk.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/7/28 <br>
 */
@Service
public class MetadataSourceService  implements
        FilterInvocationSecurityMetadataSource {


    private HashMap<String, Collection<ConfigAttribute>> map =null;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 自定义方法。最好在项目启动时，去数据库查询一次就好。
     * 数据库查询一次 权限表出现的所有要拦截的url
     */
    public void loadResourceDefine(){
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        //去数据库查询 使用dao层。 你使用自己的即可
        List<SysMenu> menus = sysMenuService.findAllMenu();
        for(SysMenu menu : menus) {
            array = new ArrayList<>();
            //下面你可以添加你想要比较的信息过去。 注意的是，需要在用户登录时存储的权限信息一致
            cfg = new SecurityConfig(menu.getMUrl());
            //此处添加了资源菜单的名字，例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。

            array.add(cfg);
            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
            map.put(menu.getMUrl(), array);
        }

    }

    //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String fullRequestUrl = filterInvocation.getFullRequestUrl();
        //若是静态资源 不做拦截  下面写了单独判断静态资源方法
        if (isMatcherAllowedRequest(filterInvocation)) {
            System.out.println("我没有被拦截"+fullRequestUrl);
            return null;
        }
        //map 为null 就去数据库查
        if(map ==null)  {
            loadResourceDefine();
        }
        //测试 先每次都查
        //object 中包含用户请求的request 信息


        HttpServletRequest request = filterInvocation.getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for(Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if(matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;
    }


    /**
     * 判断当前请求是否在允许请求的范围内
     * @param fi 当前请求
     * @return 是否在范围中
     */
    private boolean isMatcherAllowedRequest(FilterInvocation fi){
        return allowedRequest().stream().map(AntPathRequestMatcher::new)
                .filter(requestMatcher -> requestMatcher.matches(fi.getHttpRequest()))
                .toArray().length > 0;
    }

    /**
     * 参考：
     * https://blog.csdn.net/pujiaolin/article/details/73928491
     * @return 定义允许请求的列表
     */
    private List<String> allowedRequest(){
        return Arrays.asList("/login","/css/**","/fonts/**","/js/**","/scss/**","/img/**");
    }


    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}