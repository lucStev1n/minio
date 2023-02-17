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
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public abstract class AbstractOss implements FileClient {

    public static final String[] UNIT_NAMES = new String[]{"B", "KB", "MB", "GB", "TB", "PB", "EB"};
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractOss.class.getName());

    @Autowired
    private MinioClient client; // 七牛，华为，阿里，腾讯。。 aws gms

    public static String format(long size) {
        if (size <= 0L) {
            return "-";
        } else {
            int digitGroups = Math.min(UNIT_NAMES.length - 1, (int) (Math.log10((double) size) / Math.log10(1024.0)));
            return (new DecimalFormat("#,##0.##")).format((double) size / Math.pow(1024.0, (double) digitGroups)) + " " + UNIT_NAMES[digitGroups];
        }
    }

    private static String parseName(Item item, final String root) {
        if (root.length() > 1) {

        }
        return item.objectName();
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

    public List<Meta> find(String root, String regex) {
        return doFind(ls(root, true), regex);
    }

    private ArrayList<Meta> doFind(List<Meta> ls, String regex) {
        ArrayList<Meta> tmp = new ArrayList<>();
        for (Meta item : ls) {
            if (Pattern.matches(regex, item.getName())) tmp.add(item);
            if (item.isDir()) {
                tmp.addAll(doFind(item.getChildren(), regex));
            }
        }

        tmp.forEach(item -> item.setChildren(Collections.emptyList()));

        return tmp;
    }

    public void preview() {

    }

    public void rename() {

    }

    protected MinioClient Client() {
        return client;
    }

    @SneakyThrows
    public List<Meta> ls(String root, boolean r) {
        if (root == null) {
            root = "";
        }

        if (!root.endsWith("/")) {
            root += "/";
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

        final String finalRoot = root;
        return result.stream().map(item -> {
            Meta meta = new Meta();

            meta.setName(parseName(item, finalRoot));
            meta.setSize(format(item.size()));
            meta.setDir(item.isDir());

            if (meta.isDir()) {
                meta.setModify("-");
                meta.setType("folder");
            } else {
                meta.setModify(item.lastModified().toString());
                meta.setType("file");
            }

            if (r && meta.isDir()) {
                meta.setChildren(ls(parseName(item, finalRoot), true));
            }

            return meta;
        }).collect(Collectors.toList());
    }

    public void mv(Object source, Object target) {

    }

    public Object rm(Object source) {
        return null;
    }
}
