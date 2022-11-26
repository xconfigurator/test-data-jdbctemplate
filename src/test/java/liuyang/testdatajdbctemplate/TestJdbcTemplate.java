package liuyang.testdatajdbctemplate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author xconf
 * @since 2022/11/26
 */
@SpringBootTest
@Slf4j
public class TestJdbcTemplate {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void foo() {
        final List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from foo");
        maps.stream().forEach(m -> log.info("{}", m));
    }
}
