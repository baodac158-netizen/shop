package examplemybatis.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import examplemybatis.mapper.EmpMapper;
import examplemybatis.pojo.Emp;
import examplemybatis.pojo.PageBean;
import examplemybatis.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class EmpServiceimpl implements EmpService {

    private final EmpMapper empMapper;

    @Autowired
    public EmpServiceimpl(EmpMapper empMapper){
        this.empMapper = empMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public PageBean page(Integer page, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end) {
        PageHelper.startPage(page, pageSize);
        List<Emp> empList = empMapper.list(name,gender,begin,end);
        PageInfo<Emp> pageInfo = PageInfo.of(empList);
        System.out.println("Total records: " + pageInfo.getTotal());
        System.out.println("Current page size: " + pageInfo.getList().size());

        return new PageBean(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void deleteByids(List<Integer> ids) {
        empMapper.delete(ids);
    }

    @Override
    public void add(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        emp.setCreateTime(LocalDateTime.now());

        System.out.println("Inserted emp ID: " + emp.getId());

        empMapper.insert(emp);
    }

    @Override
    public Emp selectByid(Integer id) {
        Emp emp = empMapper.selectByid(id);
        System.out.println(emp);
        return emp;
    }

    @Override
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.update(emp);
    }

    @Override
    public boolean verifyLogin(String username, String password) {
        Emp emp = empMapper.findByUsernameAndPassword(username, password);
        return emp != null;
    }

    @Override
    public Emp selectByUsername(String username) {
        return empMapper.selectByUsername(username);
    }

}
