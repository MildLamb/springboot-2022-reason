# Bean的加载方式
1. xml + <bean/>
2. xml:context + 扫描 + 注解 
3. 配置类 + 扫描 + 注解
4. @Import导入bean的类
5. AnnotationConfigApplicationContext调用register方法
6. @Import导入ImportSelector接口实现类
7. @Import导入ImportBeanDefinitionRegistrar接口实现类
8. @Import导入BeanDefinitionRegistryPostProcessor接口实现类

## xml方式
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- xml方式声明自己开发的bean -->
    <bean id="lamb" class="com.mildlamb.pojo.Lamb"></bean>

    <!-- xml方式声明第三方的bean -->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"></bean>
    <bean class="com.alibaba.druid.pool.DruidDataSource"></bean>
    <bean class="com.alibaba.druid.pool.DruidDataSource"></bean>

</beans>
```
## 注解方式
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 指定扫描包路径 -->
    <context:component-scan base-package="com.mildlamb.pojo,com.mildlamb.config"></context:component-scan>
</beans>
```
```java
// 注解的方式定义bean
@Component("kindred")
public class Kindred {
}
```
```java
@Configuration
public class DSConfig {
    @Bean
    public DruidDataSource dataSource(){
        return new DruidDataSource();
    }
}
```
## 完全注解方式
```java
@Configuration
@ComponentScan(basePackages = {"com.mildlamb.pojo","com.mildlamb.config"})
public class SpringConfig {
}
```
- 加载配置类并加载配置文件
```java
// 加载xml配置的bean
@Configuration
@ImportResource({"bean.xml","bean2.xml"})
@ComponentScan({"com.mildlamb.pojo"})
public class SpringConfig2 {
}
```
- @Configuration注解中,使用proxyBeanMethods=true可以保障当前配置类中用此方法得到的对象是从容器中获取的而不是重新创建的
```java
// proxyBeanMethods = false ，使用当前配置类每一次调用lamb()生成的Lamb对象都是新new出来的
@Configuration(proxyBeanMethods = false)
public class SpringConfig3 {
    @Bean
    public Lamb lamb(){
        return new Lamb();
    }
}
```
## 使用@Import注解导入要注入的bean对应的字节码,被导入的bean无需使用注解声明为bean,还可以加载配置类，配置类和其中的bean都会加载成为bean
```java
@Configuration
@Import({Lamb.class, Wolf.class,DSConfig.class})
public class SpringConfig4 {
}
```
## 使用AnnotationConfigApplicationContext上下文对象在容器中初始化完成后注册bean
```java
public class App5 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig4.class);

        // 上下文容器对象已经初始化完毕后，手工加载bean
        ac.registerBean("mildlamb", Kindred.class,0);
        ac.registerBean("mildlamb", Kindred.class,1);
        ac.registerBean("mildlamb", Kindred.class,2);
        ac.register(Lamb.class);

        String[] names = ac.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

        System.out.println("-----------------");
        System.out.println(ac.getBean(Kindred.class));
    }
}
```
## 导入实现了ImportSelector接口的类，实现对导入源的编程式处理，引用者使用@Import进行引用
```java
public class MyImportSelector implements ImportSelector {
    @Override
    /*
    * importingClassMetadata 相当于元数据
    * 哪个类上通过@Import导入了当前ImportSelector，谁就是importingClassMetadata
    * */
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 获取元数据全限定类名
        System.out.println(importingClassMetadata.getClassName());
        // 判断元数据是否包含注解
        System.out.println("导入我的类上面是否有@Configuration注解？" + importingClassMetadata.hasAnnotation("org.springframework.context.annotation.Configuration"));
        // 判断元数据中的某个注解中所包含的属性
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes("org.springframework.context.annotation.ComponentScan");
        System.out.println(annotationAttributes);



        // 可以进行各种条件的判定，判定完毕后，再决定是否加载指定的bean
/*        boolean flag = importingClassMetadata.hasAnnotation("org.springframework.context.annotation.Configuration");
        if(flag){
            return new String[]{"com.mildlamb.pojo.Lamb"};
        }

        return new String[]{"com.mildlamb.pojo.Wolf"};*/

        // 参数为要加载的bean的全路径类名
        return new String[]{"com.mildlamb.pojo.Lamb","com.mildlamb.pojo.Wolf"};
    }
}
```
## 导入实现ImportBeanDefinitionRegistrar接口的类，通过BeanDefinition的主城区注册实名bean，可以实现对容器中bean的裁定，实现不修改源码情况下更换实现的效果
```java
public class MyBeanRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 1. 使用元数据（importingClassMetadata）进行判定 是否需要加载该bean

        // 2. 创建一个beanDefinition
        BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Wolf.class).getBeanDefinition();
        // 设置bean为单例
        beanDefinition.setScope("singleton");

        // 3. 注册BeanDefinition
        registry.registerBeanDefinition("gnar",beanDefinition);
    }
}
```
## 导入实现了 BeanDefinitionRegistryPostProcessor 接口的类，通过BeanDefinition的驻长崎注册实名bean，实现对容器中bean的最终裁定
```java
public class MyPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        // 创建一个beanDefinition
        BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(RoleServiceImpl4.class).getBeanDefinition();
        beanDefinition.setScope("singleton");

        // 注册BeanDefinition
        beanDefinitionRegistry.registerBeanDefinition("roleService",beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
```

<hr>

# 自动配置原理

```text
1. springboot启动时加载所有技术实现对应的自动配置类
2. 检测每个配置类的加载条件是否满足并进行对应的初始化
3. 切记是先加载所有的外部资源，然后根据外部资源进行条件对比
```


- @SpringBootApplication
  - @SpringBootConfiguration
    - @Configuration
      - @Component
    - @Indexed
  - @EnableAutoConfiguration
    - @AutoConfigurationPackage
      - @Import({Registrar.class})
    - @Import({AutoConfigurationImportSelector.class})
  - @ComponentScan

# 自动配置核心
- @Import({Registrar.class})   // 设置当前启动类所在的包作为扫描包，后续要针对当前的包进行扫描
```java
    static class Registrar implements ImportBeanDefinitionRegistrar, DeterminableImports {
         ... ...

        public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
            AutoConfigurationPackages.register(registry, (String[])(new AutoConfigurationPackages.PackageImports(metadata)).getPackageNames().toArray(new String[0]));
        }

        ...  ...
    }
```
- @Import({AutoConfigurationImportSelector.class})
```java
public class AutoConfigurationImportSelector implements DeferredImportSelector, BeanClassLoaderAware, ResourceLoaderAware, BeanFactoryAware, EnvironmentAware, Ordered {}
```
```java
public void process(AnnotationMetadata annotationMetadata, DeferredImportSelector deferredImportSelector) {
            ... ...
            AutoConfigurationImportSelector.AutoConfigurationEntry autoConfigurationEntry = ((AutoConfigurationImportSelector)deferredImportSelector).getAutoConfigurationEntry(annotationMetadata);
            this.autoConfigurationEntries.add(autoConfigurationEntry);
            Iterator var4 = autoConfigurationEntry.getConfigurations().iterator();

            while(var4.hasNext()) {
                String importClassName = (String)var4.next();
                this.entries.putIfAbsent(importClassName, annotationMetadata);
            }

        }
```

```java
protected AutoConfigurationImportSelector.AutoConfigurationEntry getAutoConfigurationEntry(AnnotationMetadata annotationMetadata) {
        if (!this.isEnabled(annotationMetadata)) {
            return EMPTY_ENTRY;
        } else {
            AnnotationAttributes attributes = this.getAttributes(annotationMetadata);   // 获取不需要的配置  
            List<String> configurations = this.getCandidateConfigurations(annotationMetadata, attributes);   // 获取候选配置
            configurations = this.removeDuplicates(configurations);
            Set<String> exclusions = this.getExclusions(annotationMetadata, attributes);
            this.checkExcludedClasses(configurations, exclusions);
            configurations.removeAll(exclusions);
            configurations = this.getConfigurationClassFilter().filter(configurations);
            this.fireAutoConfigurationImportEvents(configurations, exclusions);
            return new AutoConfigurationImportSelector.AutoConfigurationEntry(configurations, exclusions);
        }
    }
```

```java
 protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
        List<String> configurations = new ArrayList(SpringFactoriesLoader.loadFactoryNames(this.getSpringFactoriesLoaderFactoryClass(), this.getBeanClassLoader()));
        ImportCandidates.load(AutoConfiguration.class, this.getBeanClassLoader()).forEach(configurations::add);
        Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories nor in META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports. If you are using a custom packaging, make sure that file is correct.");
        return configurations;
    }
```

```java
public static List<String> loadFactoryNames(Class<?> factoryType, @Nullable ClassLoader classLoader) {
        ClassLoader classLoaderToUse = classLoader;
        if (classLoader == null) {
            classLoaderToUse = SpringFactoriesLoader.class.getClassLoader();
        }

        String factoryTypeName = factoryType.getName();
        return (List)loadSpringFactories(classLoaderToUse).getOrDefault(factoryTypeName, Collections.emptyList());
    }
```

```java
private static Map<String, List<String>> loadSpringFactories(ClassLoader classLoader) {
                ... ...
            try{
                Enumeration urls=classLoader.getResources("META-INF/spring.factories");
                ... ...
        }
```

## Spring Boot 2.7 新特性
- 自动配置变更（重要）
自动配置注册文件

- 自动配置注册有了一个比较大的调整，之前都是写在下面 文件中的：
```text
META-INF/spring.factories
```

现在改名了：
```text
META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

## 自定义自动配置
- 添加自定义的自动配置类，resources目录中新建META-INF/spring.factories文件,将自动配置类的全限定类名填写进去
```properties
# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.mildlamb.pojo.Spirit
```

- 排除某些自动装配的bean
1. 配置文件配置
```yaml
# 排除某些自动装配的bean
spring:
  autoconfigure:
    exclude:
      - org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration
#      - xxx
```
2. 启动类注解声明属性
```java
// 排除某些自动装配的bean
@SpringBootApplication(excludeName = {"org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration"})
//@Import(Spirit.class)
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(App.class, args);
        Spirit bean = run.getBean(Spirit.class);
        bean.play();
    }
}
```

<hr>

# 自定义starter
## 案例：记录系统访客独立IP访问次数
- 需求分析
  1. 数据记录方式：Map/Redis
  2. 功能触发位置：每次web请求(拦截器)
     1. 步骤一：降低难度，主动调用，仅统计单一操作访问次数(例如查询)
     2. 步骤二：开发拦截器
  3. 业务参数(配置项)
     1. 输出频度，默认10s
     2. 数据特征：累计数据/阶段数据，默认为累计数据
     3. 输出格式：详细模式/简易模式

## 开发
- 导入依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
- 编写业务类 IpCountService
```java
public class IpCountService {

    private Map<String,Integer> ipCountMap = new HashMap<>();

    @Autowired
    // 当前的request对象的注入工作，由使用当前starter的项目提供自动装配
    private HttpServletRequest httpServletRequest;

    public void count(){
        System.out.println("-------------------- IP COUNT ----------------------");
        // 每次调用当前操作，就记录当前访问的IP，然后累加访问次数
        // 1. 获取当前操作的IP地址
        String ip = httpServletRequest.getRemoteAddr();
        // 2. 根据IP地址从Map中取值，并递增,再重新放回集合中
        Integer ipCount = ipCountMap.get(ip);
        if (ipCount == null){
            ipCountMap.put(ip,1);
        }else{
            ipCountMap.put(ip,(ipCount + 1));
        }
    }
}
```
- 编写自动配置类
```java
// 该类已在spring.factories中声明为bean了，所以可以直接@Bean
public class IpAutoConfiguration {
    @Bean
    public IpCountService ipCountService(){
        return new IpCountService();
    }
}
```
- 编写自动配置文件 META-INF/spring.factories
```properties
# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
cn.engulf.autoconfig.IpAutoConfiguration
```
- 在使用自定义starter时，先安装starter到本地仓库中
```text
maven -> 选择clean和install
```
- 设置定时任务，定时展示 (IpCountService)
```java
    /**
     * 展示统计数据
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void showIpCounts(){
        System.out.println("<----------         IP 访问监控       --------------->");
        System.out.println("+-------- ip_address ---------+----- ip_counts -----+");
        for (Map.Entry<String, Integer> entry : ipCountMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(String.format("|%26s   |         %-12d|",key,value));
        }
        System.out.println("+-----------------------------+---------------------+");
    }
```