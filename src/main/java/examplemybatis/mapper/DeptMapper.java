package examplemybatis.mapper;

import examplemybatis.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {

    @Select("select * from dept")
    List<Dept> list();

    @Delete("delete from dept where id = #{id}")
    void deleteByID(Integer id);

    @Insert("insert into dept(name, create_time, update_time) values (#{name}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Dept dept);

    @Update("UPDATE dept SET name = #{name} WHERE id = #{id}")
    void update(Integer id,String name);

    @Select("select * from dept where id = #{id}")
    Dept selectById(Integer id);
}