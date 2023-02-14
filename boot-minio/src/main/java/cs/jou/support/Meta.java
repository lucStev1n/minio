package cs.jou.support;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class Meta {
    private String name;
    private String path;
    private String size;
    private String modify;
    private boolean isDir;
    private String type;

    private List<Meta> children = Collections.emptyList();
}
