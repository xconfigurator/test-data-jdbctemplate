package liuyang.testdatajdbctemplate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

/**
 * Test
 * 数据库
 *
 * @author xconf
 * @since 2022/11/26
 */
@SpringBootTest
@ActiveProfiles({"mysql-test"})
@Slf4j
public class TestJdbcTemplateMySQL {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void foo202402191714() {
        // since JDK 15
        String SQL = """
                    select * 
                    from foo
                """;
        final List<Map<String, Object>> maps = jdbcTemplate.queryForList(SQL);
        maps.stream().forEach(m -> log.info("{}", m));
    }

    @Test
    void foo() {
        final List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from foo");
        maps.stream().forEach(m -> log.info("{}", m));
    }


}
