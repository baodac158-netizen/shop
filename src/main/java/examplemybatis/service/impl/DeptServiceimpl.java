package examplemybatis.service.impl;

import examplemybatis.mapper.DeptMapper;
import examplemybatis.pojo.Dept;
import examplemybatis.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceimpl implements DeptService {

    private final DeptMapper deptMapper;

    @Autowired
    public DeptServiceimpl(DeptMapper deptMapper) {
        this.deptMapper = deptMapper;
    }


    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    @Override
    public void delete(Integer id) {
        deptMapper.deleteByID(id);
    }

    @Override
    public void add(Dept dept) {

        dept.setUpdateTime(LocalDateTime.now());
        dept.setCreateTime(LocalDateTime.now());

        System.out.println("Inserted dept ID: " + dept.getId());

        deptMapper.insert(dept);

    }

    @Override
    public void put(Dept dept) {
        if (dept.getId() == null || dept.getId() <= 0) {
            throw new IllegalArgumentException("更新操作必须提供有效的 ID！");
        }
        deptMapper.update(dept.getId(),dept.getName());
    }

    @Override
    public Dept selectById(Integer id) {
        return deptMapper.selectById(id);
    }

}
