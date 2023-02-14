package cs.jou;

import cs.jou.support.AbstractOss;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("oss")
public class FilerController {

    private final AbstractOss service;

    public FilerController(AbstractOss service) {
        this.service = service;
    }

    //todo ls
    @GetMapping({"ls/{root}", "ls/", "ls"})
    public Object ls(@PathVariable(required = false) String root) {
        List result = service.ls(root, false);
        return result;
    }

    //todo 搜索 find
    @GetMapping("source/{regex}")
    public Object find(@PathVariable String regex) {
        return service.find("", regex);
    }

    //todo 重命名 移动
    @PatchMapping
    public Object mv() {
        service.mv("source", "target");
        return null;
    }

    //todo 删除 rm
    @DeleteMapping
    public Object rm() {
        service.rm("");
        return null;
    }

    //todo 创建文件夹, 文件
    @PostMapping("source")
    public Object mkdir() {
        service.create("", null);
        return null;
    }

    //todo 上传下载
    @PostMapping("stream")
    public Object scp() {
        return null;
    }

    //todo 查看
    @GetMapping("cat")
    public Object cat() {
        return null;
    }

    //todo 取消上传下载
    @DeleteMapping("cancel")
    public Object cancel() {
        return null;
    }

    //todo 预览
    @GetMapping("preview")
    public Object preview() {
        return null;
    }
}
