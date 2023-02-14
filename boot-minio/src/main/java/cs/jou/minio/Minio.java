package cs.jou.minio;

import cs.jou.support.AbstractOss;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class Minio extends AbstractOss {

    private static final String BUCKET = "nfs";

    @NotNull
    @Override
    protected String namespace() {
        return BUCKET;
    }

    @Data
    @ConfigurationProperties(prefix = "minio")
    public static class Props {
        private String endpoint;
        private String accesskey;
        private String secretkey;
    }
}
