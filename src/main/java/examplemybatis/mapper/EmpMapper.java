package examplemybatis.mapper;

import examplemybatis.pojo.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Mapper
public interface EmpMapper {

    List<Emp> list(@Param("name") String name,
                   @Param("gender") Short gender,
                   @Param("begin") LocalDate begin,
                   @Param("end") LocalDate end);


    void delete(@Param("list") List<Integer> id);

    void insert(Emp emp);

    Emp selectByid(Integer id);

    void update(Emp emp);

    Emp findByUsernameAndPassword(String username, String password);

    Emp selectByUsername(String username);
}
