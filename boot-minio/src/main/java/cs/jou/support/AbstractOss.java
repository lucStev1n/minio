package cs.jou.support;

import io.minio.*;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public abstract class AbstractOss implements FileClient {

    public static final String[] UNIT_NAMES = new String[]{"B", "KB", "MB", "GB", "TB", "PB", "EB"};
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractOss.class.getName());

    @Autowired
    private MinioClient client; // 七牛，华为，阿里，腾讯。。 aws gms

    public static String format(long size) {
        if (size <= 0L) {
            return "0";
        } else {
            int digitGroups = Math.min(UNIT_NAMES.length - 1, (int) (Math.log10((double) size) / Math.log10(1024.0)));
            return (new DecimalFormat("#,##0.##")).format((double) size / Math.pow(1024.0, (double) digitGroups)) + " " + UNIT_NAMES[digitGroups];
        }
    }

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

    public List find(String root, String regex) {

        return null;
    }

    public void preview() {

    }

    public void rename() {

    }

    protected MinioClient Client() {
        return client;
    }

    @SneakyThrows
    public List<?> ls(String root, boolean r) {
        if (root == null) {
            root = "/";
        }

        ArrayList<Item> result = new ArrayList<>();

        for (Result<Item> item
                : Client().listObjects(ListObjectsArgs.builder()
                .bucket(namespace())
                .prefix(root)
                .recursive(false)
                .build())) {
            result.add(item.get());
        }

        return result.stream().map(item -> {
            HashMap<String, Object> data = new HashMap<>();

            data.put("name", item.objectName());
            data.put("size", format(item.size()));
            data.put("type", item.isDir() ? "folder" : "file");

            return data;
        }).collect(Collectors.toList());
    }

    public void mv(Object source, Object target) {

    }

    public Object rm(Object source) {
        return null;
    }
}
