package geektime.spring.jdbc.repository;

import geektime.spring.jdbc.entity.Foo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class FooDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    public void insertData() {
        Arrays.asList("b", "c").forEach(bar -> {
            jdbcTemplate.update("INSERT INTO FOO (BAR) VALUES (?)", bar);
        });
        HashMap<String, String> row = new HashMap<>(1);
        row.put("BAR", "d");
        Number id = simpleJdbcInsert.executeAndReturnKey(row);
        log.info("\n");
        log.info("ID of d: {}", id.longValue());
        log.info("\n");
    }

    public void listData() {
        log.info("\n");
        log.info("Count: {}", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO", Long.class));

        List<String> list = jdbcTemplate.queryForList("SELECT BAR FROM FOO", String.class);
        log.info("\n");
        list.forEach(s -> log.info("Bar: {}", s));
        log.info("\n");

        List<Foo> fooList = jdbcTemplate.query("SELECT * FROM FOO", new RowMapper<Foo>() {
            @Override
            public Foo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return Foo.builder()
                    .id(resultSet.getLong(1))
                    .bar(resultSet.getString(2))
                    .build();
            }
        });
        log.info("\n");
        fooList.forEach(foo -> log.info("Foo: {}", foo));
        log.info("\n");
    }


}
