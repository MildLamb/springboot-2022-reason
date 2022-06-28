# Bean的加载方式
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