package liuyang.testdatajdbctemplate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ActiveProfiles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
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
        String SQL = "select * from actor";

        // 写法1： .query + RowMapper
        System.out.println("==========================================");
        jdbcTemplate.query(SQL, new RowMapper<Actor>() {
            @Override
            public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
                Actor actor = new Actor();
                actor.setActorId(rs.getShort("actor_id"));
                actor.setFirstName(rs.getString("first_name"));
                actor.setLastName(rs.getString("last_name"));
                // Timestamp -> LocalDateTime
                actor.setLastUpdate(rs.getTimestamp("last_update").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                return actor;
            }
        }).stream().forEach(System.out::println);


        // 写法2： .queryForList + Entity
        System.out.println("==========================================");
        // TODO
        // 参考视频：https://www.bilibili.com/video/BV1Km4y1k7bn?spm_id_from=333.788.player.switch&vd_source=8bd7b24b38e3e12c558d839b352b32f4&p=70
        // 这个视频中有对BeanPropertyRowMapper的讲解。
        //jdbcTemplate.queryForList(SQL, Actor.class).stream().forEach(System.out::println);// 有异常！
        //jdbcTemplate.queryForList(SQL, new BeanPropertyRowMapper<Actor>()).stream().forEach(System.out::println);// 有异常！
        //jdbcTemplate.query(SQL, new BeanPropertyRowMapper<Actor>()).stream().forEach(System.out::println);// 有异常！
    }


}
