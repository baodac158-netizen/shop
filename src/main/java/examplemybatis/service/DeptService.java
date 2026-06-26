package examplemybatis.service;

import examplemybatis.pojo.Dept;

import java.util.List;

public interface DeptService {

    List list();

    void delete(Integer id);

    void add(Dept dept);

    void put(Dept dept);

    Dept selectById(Integer id);
}
