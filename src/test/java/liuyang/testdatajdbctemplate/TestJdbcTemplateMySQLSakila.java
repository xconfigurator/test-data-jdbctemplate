package liuyang.testdatajdbctemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ActiveProfiles;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 视频：
 * https://www.bilibili.com/video/BV1Km4y1k7bn?p=69&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 * <p>
 * Lambda since JDK8
 * Block since JDK15
 * Record since JDK16
 *
 * 传参多的情况系可以考虑NamedParameterJdbcTemplate。
 * https://www.bilibili.com/video/BV1Km4y1k7bn/?p=73&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 *
 * 任意结果集（多表连接查询结果）可以通过RowMapper映射即可。
 * https://www.bilibili.com/video/BV1Km4y1k7bn?p=74&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 * 或者直接
 *  1. queryForList返回List<Map<String, Object>>
 *  2. queryForMap返回Map<String, Object>
 *
 * @author xconf
 * @since 2024/2/4
 * @update 2024/2/19 增加笔记
 *
 */
@SpringBootTest
@ActiveProfiles({"mysql-sakila"})
@Slf4j
public class TestJdbcTemplateMySQLSakila {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @DisplayName("Map<String, Object>")
    @Test
    void testQueryForMap202402191843() {
        String SQL = """
                select *
                from actor t
                where t.actor_id = ?
                """;
        final Map<String, Object> result = jdbcTemplate.queryForMap(SQL, 1);
        log.info("result = {}", result);
    }

    @DisplayName("List<Map<String, Object>")
    @Test
    void testQueryForList202402040000() {
        String SQL = """
                select * from actor
                """;
        final List<Map<String, Object>> maps = jdbcTemplate.queryForList(SQL);
        maps.stream().forEach(m -> log.info("{}", m));
    }

    @DisplayName("List<Integer>")
    @Test
    void testQueryForList202402192006() {
        String SQL = """
                select actor_id
                from actor
                """;
        final List<Long> actorIds = jdbcTemplate.queryForList(SQL, Long.class);// SingleColumRowMapper!!
        actorIds.stream().forEach(System.out::println);
    }

    @DisplayName("List<Actor>  取部分字段的方法与此类似。没有单行的那种方便的BeanPropertyRowMapper。")
    @Test
    void testQueryForList202402191951() {
        String SQL = """
                select * from actor
                """;
        //final List<Actor> actors = jdbcTemplate.queryForList(SQL, Actor.class);// 错！ SingleColumRowMapper!!
        final List<Actor> actors = jdbcTemplate.query(SQL, new RowMapper<Actor>() {
            @Override
            public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
                Actor actor = new Actor();
                actor.setActorId(rs.getShort("actor_id"));
                actor.setFirstName(rs.getString("first_name"));
                actor.setLastName(rs.getString("last_name"));
                actor.setLastUpdate(rs.getTimestamp("last_update").toLocalDateTime());// java.sql.Date -> LocalDateTime
                return actor;
            }
        });
        actors.stream().forEach(System.out::println);
    }

    @DisplayName("count(1)")
    @Test
    void testQueryForObjectCount202402042011() {
        String SQL = """
                select count(1)
                from actor
                """;
        Long count = jdbcTemplate.queryForObject(SQL, Long.class);
        log.info("sakila.actor count = {}", count);
    }

    @DisplayName("Actor BeanPropertyRowMapper 包含全部字段的实体类")
    @Test
    void testQueryForObjectRowMapper202402191833() {
        String SQL = """
                select *
                from actor t
                where t.actor_id = ?
                """;
        Actor actor = jdbcTemplate.queryForObject(SQL, new BeanPropertyRowMapper<>(Actor.class), 1);
        log.info("actor = {}", actor);
    }

    @DisplayName("Actor 的部分属性对象 BeanPropertyRowMapper 包含部分字段的数据对象 测试：Class")
    @Test
    void testQueryForObjectRowMapper202402191908() {
        String SQL = """
                select *
                from actor t
                where t.actor_id = ?
                """;
        ActorPartial actorPartial = jdbcTemplate.queryForObject(SQL, new BeanPropertyRowMapper<>(ActorPartial.class), 1);
        log.info("actorPartial = {}", actorPartial);
    }

    // 202402191918 不能使用Record作为BeanPropertyRowMapper的类型，因为Record不遵循JavaBean规范 apr.actorId() 而不是a.getActorId()
    /*
    @DisplayName("Actor 的部分属性对象 BeanPropertyRowMapper 包含部分字段的数据对象 测试：Record")
    @Test
    void testQueryForObjectRowMapper202402191905() {
        String SQL = """
                select *
                from actor t
                where t.actor_id = ?
                """;
        ActorPartialRecord actorPartialRecord = jdbcTemplate.queryForObject(SQL, new BeanPropertyRowMapper<>(ActorPartialRecord.class), 1);
        log.info("actorPartialRecord = {}", actorPartialRecord);
    }
     */

    @DisplayName("ActorPartialRecord 结构示例")
    @Test
    void testQueryForObjectRowMapper202402191856() {
        String SQL = """
                select *
                from actor t
                where t.actor_id = ?
                """;
        final ActorPartialRecord actorPartialRecord = jdbcTemplate.queryForObject(SQL, new RowMapper<ActorPartialRecord>() {
            @Override
            public ActorPartialRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ActorPartialRecord(rs.getShort("actor_id"), rs.getString("first_name"), rs.getString("last_name"));
            }
        }, 1);
        log.info("actorPartialRecord = {}", actorPartialRecord);
    }

    /**
     * Lambda since JDK8
     * Block since JDK15
     * Record since JDK16
     */
    @DisplayName("ActorPartialRecord Lambda")
    @Test
    void testQueryForObjectRowMapper202401191900() {
        String SQL = """
                select *
                from actor t
                where t.actor_id = ?
                """;
        final ActorPartialRecord actorPartialRecord = jdbcTemplate.queryForObject(SQL
                , (rs, rowNum) -> {
                    return new ActorPartialRecord(rs.getShort("actor_id"), rs.getString("first_name"), rs.getString("last_name"));
                }
                , 1);
        log.info("actorPartialRecord = {}", actorPartialRecord);
    }

}


// entity 可以由工具生成
class Actor implements Serializable {

    private static final long serialVersionUID = 1L;

    //@TableId(value = "actor_id", type = IdType.AUTO)
    private Short actorId;

    private String firstName;

    private String lastName;

    private LocalDateTime lastUpdate;

    public Short getActorId() {
        return actorId;
    }

    public void setActorId(Short actorId) {
        this.actorId = actorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "actorId = " + actorId +
                ", firstName = " + firstName +
                ", lastName = " + lastName +
                ", lastUpdate = " + lastUpdate +
                "}";
    }
}

@ToString
@Data // 必须有getter和setter
class ActorPartial implements Serializable {
    private static final long serialVersionUID = 1L;

    //@TableId(value = "actor_id", type = IdType.AUTO)
    private Short actorId;

    private String firstName;

    private String lastName;
}

record ActorPartialRecord(
        Short actorId,
        String firstName,
        String lastName
) {
    public ActorPartialRecord {
    } // 做RowMapper泛型时不需要，但是作queryForObject的requiredType时需要。否则报错
}
