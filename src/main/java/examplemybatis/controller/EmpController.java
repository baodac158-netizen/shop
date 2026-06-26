package examplemybatis.controller;

import examplemybatis.pojo.Emp;
import examplemybatis.pojo.PageBean;
import examplemybatis.pojo.Result;
import examplemybatis.service.EmpService;
import examplemybatis.service.impl.EmpServiceimpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping()
public class EmpController {

    private final EmpService empService;

    @Autowired
    public EmpController(EmpServiceimpl empService){
        this.empService = empService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Emp emp) {
        Map<String, Object> response = new HashMap<>();

        // 可选：查重用户名
        if (empService.selectByUsername(emp.getUsername()) != null) {
            response.put("success", false);
            response.put("message", "用户名已存在");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        empService.add(emp);

        response.put("success", true);
        response.put("message", "注册成功");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/emps")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, Short gender,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

        log.info("分页查询，参数:{},{},{},{},{},{}",page,pageSize,name,gender,begin,end);
        PageBean pageBean = empService.page(page,pageSize,name,gender,begin,end);
        log.info(pageBean.toString());
        return Result.success(pageBean);
    }

    @DeleteMapping("/emps/{ids}")
    public Result deleteBy(@PathVariable String ids) {
        // @DeleteMapping("/emps")
        //public Result deleteBy(@RequestParam List<Integer> ids) {
        //    empService.deleteByids(ids);
        //    return Result.success();
        //}
        List<Integer> list = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        log.info("删除员工:" + ids);
        empService.deleteByids(list);
        return Result.success();
    }

    @PostMapping ("/emps")
    public Result add(@RequestBody Emp emp){
        log.info("新增员工");
        empService.add(emp);
        return Result.success();
    }

    @GetMapping("emps/{id}")
    public Result selectByid(@PathVariable Integer id){
        log.info("查询id:{}",id);
        Emp emp = empService.selectByid(id);
        return Result.success(emp);
    }

    @PutMapping("emps")
    public Result update(@RequestBody Emp emp){
        log.info("更改员工信息");
        empService.update(emp);
        return Result.success();
    }

}
