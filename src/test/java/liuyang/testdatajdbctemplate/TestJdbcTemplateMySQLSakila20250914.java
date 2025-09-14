package liuyang.testdatajdbctemplate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ActiveProfiles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import liuyang.testdatajdbctemplate.mysql.entity.Actor;// 明确引用。否则引用的就是TestJdbcTemplateMySQLSakila文件中声明的那个Actor

/**
 * 一套比较简单的
 * https://www.bilibili.com/video/BV1FK4y1b7PF/?spm_id_from=333.1391.0.0&p=4&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 *
 * .update      insert, update, delete
 * .query       select
 * .execute     DCL, DDL, DML
 *
 *
 *
 * @author xconf
 * @since 2025/9/14
 */
@SpringBootTest
@ActiveProfiles({"mysql-sakila"})
@Slf4j
public class TestJdbcTemplateMySQLSakila20250914 {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testInsert2025091420443() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String nowStr = sdf.format(new Date());
        String sql = "insert into actor (first_name, last_name, last_update) values (?, ?, ?)";
        jdbcTemplate.update(sql, "洋", "刘", new Date());// 实测这俩都可以 202509142055
        jdbcTemplate.update(sql, "洋", "刘", nowStr);// 实测这俩都可以 202509142055
    }

    @Test
    public void testQuery202509142045() {
        final List<Actor> list = jdbcTemplate.query("select * from actor", new RowMapper<Actor>() {
            @Override
            public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
                return null;
            }
        });
    }
}
