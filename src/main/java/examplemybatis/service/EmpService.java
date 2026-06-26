package examplemybatis.service;

import examplemybatis.pojo.Emp;
import examplemybatis.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {

    PageBean page(Integer page,Integer pageSize,
                  String name, Short gender,
                  LocalDate begin,LocalDate end);

    void deleteByids(List<Integer> ids);

    void add(Emp emp);

    Emp selectByid(Integer id);

    void update(Emp emp);

    boolean verifyLogin(String username, String password);

    Emp selectByUsername(String username);
}
