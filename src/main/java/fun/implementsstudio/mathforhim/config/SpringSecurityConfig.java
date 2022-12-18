package fun.implementsstudio.mathforhim.config;


//import fun.implementsstudio.mathforhim.filter.MyAuthenticationTokenGenericFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/***
 * SpringSecurity 配置类
 * @author lucas
 * @date 2019-12-23 17:17:29
 * 会好的，悲观者往往正确，乐观者往往成功！
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService dbUserLoginService;
    @Autowired
    private AuthenticationProvider customAuthenticationProvider;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用csrf防御机制(跨域请求伪造)，这么做在测试和开发会比较方便。
        http.csrf().disable();
        // 不适用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/dbUser").hasAuthority("dbUser")
                .antMatchers("math.html").authenticated()
                // swagger start
                .antMatchers("/swagger-ui.html").permitAll().antMatchers("/swagger-resources/**").permitAll().antMatchers("/webjars/**").permitAll().antMatchers("/v2/api-docs").permitAll()
                // swagger end
                .antMatchers("/auth/**").permitAll().antMatchers("/fun/**").permitAll().antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll().antMatchers("/").permitAll().and().logout().logoutUrl("/auth/doLogout").logoutSuccessUrl("/").permitAll()
                .and()
                .sessionManagement()
                // 失效后跳转到登陆页面
                .invalidSessionUrl("/");

        // 禁用缓存
//        http.headers().cacheControl();
        http.rememberMe().key("random1").tokenValiditySeconds(86400);
        http.authenticationProvider(customAuthenticationProvider);
        // 加入jwt token验证过滤器
//        http.addFilterBefore(myAuthenticationTokenGenericFilter(), UsernamePasswordAuthenticationFilter.class);
    }

//    @Bean
//    public MyAuthenticationTokenGenericFilter myAuthenticationTokenGenericFilter() {
//        return new MyAuthenticationTokenGenericFilter();
//    }

    /**
     * 认证管理器：使用spring自带的验证密码的流程
     * <p>
     * 负责验证、认证成功后，AuthenticationManager 返回一个填充了用户认证信息（包括权限信息、身份信息、详细信息等，但密码通常会被移除）的 Authentication 实例。
     * 然后再将 Authentication 设置到 SecurityContextHolder 容器中。
     * AuthenticationManager 接口是认证相关的核心接口，也是发起认证的入口。
     * 但它一般不直接认证，其常用实现类 ProviderManager 内部会维护一个 List<AuthenticationProvider> 列表，
     * 存放里多种认证方式，默认情况下，只需要通过一个 AuthenticationProvider 的认证，就可被认为是登录成功
     *
     * @return 认证信息
     * @throws Exception Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * yuanlaizaizheli
     * 问题的求解要有耐心patience patient
     * <p>
     * //     * @param auth
     *
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(dbUserLoginService)
                .passwordEncoder(passwordEncoder());
    }


    @Override
    public void configure(WebSecurity web)  {
        // 配置静态文件不需要认证
        web.ignoring().antMatchers("/static/**");
    }
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        return username -> {
//            MemberDo memberDo = memberService.getMemberByPhone(username);
//            if(memberDo != null){
//                return new MemberDetail(memberDo);
//            }
//            throw new UsernameNotFoundException("用户名或密码错误");
//        };
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
