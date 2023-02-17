package cs.jou;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

@EnableCaching
@RestController
@SpringBootApplication
public class CacheApplication {
    @Resource
    StringRedisTemplate template;

    public static void main(String[] args) {
        ArrayList<Object> ls = new ArrayList<>();
        ls.add("1");
        ls.add("2");
        ls.add("3");
        ls.add("4");
        ls.add("5");

        System.out.println(ls.indexOf("3"));

        ls.remove("4");
        System.out.println(ls.indexOf("4"));

        ls.forEach(System.out::println);
    }

    /**
     * cacheable  调用前查询缓存，调用后跟新缓存
     * cachePut   【save】调用前不查询缓存，无论如何都会执行，执行后结果放入缓存
     * cacheEvict 【del】清理缓存
     * caching    应用多个注解
     * cacheConfig在类级别共享相同的缓存配置
     */

    @GetMapping
    @Cacheable({"hot"})
    public String hello(int id, String name) {
        System.out.println("in hello()");
        return "hello " + id;
    }
}
