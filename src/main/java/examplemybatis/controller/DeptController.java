package examplemybatis.controller;

import examplemybatis.pojo.Dept;
import examplemybatis.pojo.Result;
import examplemybatis.service.DeptService;
import examplemybatis.service.impl.DeptServiceimpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/depts")

public class DeptController {

    private final DeptService deptService;

    @Autowired
    public DeptController(DeptServiceimpl deptService) {
        this.deptService = deptService;
    }

    @GetMapping
    public Result list() {
        log.info("查询全部部门数据");
        List list = deptService.list();
        return Result.success(list);
    }

    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id){
        log.info("根据ID查询部门");
        Dept dept = deptService.selectById(id);
        return Result.success(dept);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("删除部门" + id);
        deptService.delete(id);
        return Result.success();
    }

    @PostMapping
    public Result add(@RequestBody Dept dept) {
        log.info("新增部门: {}", dept);
        deptService.add(dept);
        return Result.success();
    }

    @PutMapping()
    public Result put(@RequestBody Dept dept){
        if (dept.getId() == null) {
            return Result.error("ID 不能为空！请提供有效的部门 ID。");
        }
        log.info("收到的 PUT 请求: id={}, name={}", dept.getId(), dept.getName());
        log.info("更改部门: {}", dept);
        deptService.put(dept);
        return Result.success();
    }
}
