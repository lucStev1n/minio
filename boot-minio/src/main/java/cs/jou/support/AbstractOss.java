package cs.jou.support;

import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public abstract class AbstractOss implements FileClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractOss.class.getName());

    @Autowired
    private MinioClient client; // 七牛，华为，阿里，腾讯。。 aws gms

    @NotNull
    abstract protected String namespace();

    @SneakyThrows // upload file or create dir
    public void create(String dir, InputStream stream) {
        LOGGER.info("mkdir or upload");
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(namespace())
                .object("/hello/" + dir + "/")
                .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                .build();
        ObjectWriteResponse or = Client().putObject(args);
        LOGGER.info("after mk: {}", or);
    }

    public void delete() {

    }

    public void find(String root, String regex) {

    }

    public void preview() {

    }

    public void rename() {

    }

    protected MinioClient Client() {
        return client;
    }
}
