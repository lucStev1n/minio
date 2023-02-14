package cs.jou.minio;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MinioConfig {

    @Bean
    public MinioClient minioClient(Minio.Props props) {
        return MinioClient.builder()
                .endpoint(props.getEndpoint())
                .credentials(props.getAccesskey(), props.getAccesskey())
                .build();
    }
}
