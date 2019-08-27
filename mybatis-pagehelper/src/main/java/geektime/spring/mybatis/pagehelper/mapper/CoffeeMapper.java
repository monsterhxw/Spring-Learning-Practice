package geektime.spring.mybatis.pagehelper.mapper;


import geektime.spring.mybatis.pagehelper.model.Coffee;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface CoffeeMapper {

    @Select("select * from t_coffee order by id")
    List<Coffee> findAllWithRowBounds(RowBounds rowBounds);

    @Select("select * from t_coffee order by id")
    List<Coffee> findAllWithParam(
        @Param("pageNum") int pageNum,
        @Param("pageSize") int pageSize);
}
