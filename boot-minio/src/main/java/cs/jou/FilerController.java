package cs.jou;

import cs.jou.support.AbstractOss;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController("oss")
@AllArgsConstructor
public class FilerController {

    private final AbstractOss service;

    // ls
    @GetMapping
    public Object ls() {
        return null;
    }

    // 搜索 find
    @GetMapping
    public Object find() {
        return null;
    }

    // 重命名 移动
    @PatchMapping
    public Object mv() {
        return null;
    }

    // 删除 rm
    @DeleteMapping
    public Object rm() {
        return null;
    }

    // 创建 文件夹
    @PostMapping
    public Object mkdir() {
        return null;
    }

    //    创建文件
    @PostMapping
    public Object touch() {
        return null;
    }

    // 查看
    @GetMapping
    public Object vim() {
        return null;
    }

    // 上传下载
    @PostMapping
    public Object scp() {
        return null;
    }

    // 取消上传下载
    @DeleteMapping
    public Object cancel() {
        return null;
    }

    // 预览
    @GetMapping
    public Object cat() {
        return null;
    }
}
