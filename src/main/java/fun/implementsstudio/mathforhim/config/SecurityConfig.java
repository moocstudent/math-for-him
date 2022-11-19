//package fun.implementsstudio.mathforhim.config;
//
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest()
//                .authenticated();
//    }
//
//    @Override
//    protected void  configure(AuthenticationManagerBuilder auth) throws Exception {
//        //模拟数据从数据库库取出
//        auth.inMemoryAuthentication()
//                .withUser("zhangjunyang").password("666666")
//             ;
//
//    }
//}
