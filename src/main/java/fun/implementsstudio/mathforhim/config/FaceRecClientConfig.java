package fun.implementsstudio.mathforhim.config;

import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FaceRecClientConfig {


    @Value("${aliyun.facerec.regionId}")
    private String regionId;
    @Value("${aliyun.facerec.accessKeyId}")
    private String accessKeyId = "LTAI5tLtZSiXoBHpg1osBEX6";
    @Value("${aliyun.facerec.accessKeySecret}")
    private String accessKeySecret = "JPOaJJypeV2k3yYTSMJdqCw9ME4bu1";

    @Bean
    public com.aliyun.facebody20191230.Client faceRecClient()throws Exception{
        Config config = new Config();
        // Franzzi的AccessKey ID
        config.accessKeyId = accessKeyId;
        // Franzzi的AccessKey Secret
        config.accessKeySecret = accessKeySecret;
        // Franzzi的可用区ID
        config.regionId = regionId;
        return new com.aliyun.facebody20191230.Client(config);
    }
}
