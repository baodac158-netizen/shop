// 员工管理

// 展示员工列表
async function showEmp(page = 1, pageSize = 10) {
    try {
        const base = 'http://localhost:8080';
        const res = await fetch(`${base}/emps?page=${page}&pageSize=${pageSize}`);
        const result = await res.json();

        const list = result.data.rows || [];
        const pageBean = result.data || {};

        // 收集所有非空的 deptId
        const deptIds = Array.from(new Set(list.map(e => e.deptId).filter(id => id !== null && id !== undefined)));

        // 并行请求所有需要的部门信息
        const deptMap = {}; // { deptId: deptName }
        if (deptIds.length > 0) {
            // 用 Promise.all 并行请求
            const deptPromises = deptIds.map(id =>
                fetch(`${base}/depts/${id}`)
                    .then(r => {
                        if (!r.ok) return null;
                        return r.json().catch(() => null);
                    })
                    .then(data => {
                        if (!data) return null;
                        const payload = data.data ?? data;
                        if (payload && payload.name) {
                            deptMap[id] = payload.name;
                        } else if (payload && payload.deptName) {
                            deptMap[id] = payload.deptName;
                        } else {
                            deptMap[id] = payload?.name ?? payload?.deptName ?? '未分配';
                        }
                        return null;
                    })
                    .catch(() => {
                        deptMap[id] = '未分配';
                    })
            );

            await Promise.all(deptPromises);
        }

        // 将部门名称注入到每个 emp
        for (const emp of list) {
            if (emp.deptId != null) {
                emp.deptName = deptMap[emp.deptId] ?? '未分配';
            } else {
                emp.deptName = '未分配';
            }
        }

        // 渲染表格
        const panel = document.getElementById('main-panel');
        panel.innerHTML = `
        <h2>员工列表</h2>
        <table>
          <tr>
            <th>序号</th><th>部门</th><th>用户名</th><th>头像</th><th>姓名</th><th>性别</th><th>操作</th>
          </tr>
          ${list.map((emp, index) => {
            const imgUrl = emp.image?.startsWith('http') ? emp.image : `https://my-ownbucket-test.oss-cn-guangzhou.aliyuncs.com/${emp.image}`;
            return `
            <tr>
              <td>${index + 1 + (page - 1) * pageSize}</td>
              <td>${emp.deptName ?? '未分配'}</td>
              <td>${emp.username ?? ''}</td>
              <td><img src="${imgUrl}" alt="员工头像" style="width:48px;height:48px;border-radius:50%;"></td>
              <td>${emp.name ?? ''}</td>
              <td>${emp.gender == 1 ? '男' : '女'}</td>
              <td>
                <button onclick="editEmp(${emp.id})">编辑</button>
                <button onclick="deleteEmp(${emp.id})">删除</button>
              </td>
            </tr>`;
        }).join('')}
        </table>
        <div style="margin-top: 20px; text-align: center;">
            ${buildPagination(page, pageBean.total, pageSize)}
        </div>
      `;

    } catch (err) {
        console.error('加载员工列表出错：', err);
        alert('加载员工列表失败，请检查后端服务或网络。');
    }
}


// 删除员工
async function deleteEmp(id) {
    if (!confirm(`确定要删除员工 ID 为 ${id} 吗？`)) return;
    const res = await fetch(`http://localhost:8080/emps/${id}`, { method: 'DELETE' });
    const result = await res.json();
    if (result.success || result.code === 1) {
        alert('删除成功');
        await showEmp();
    } else {
        alert('删除失败');
    }
}


// 查询员工
async function getEmp(id) {
    const controller = new AbortController();
    const timeoutId = setTimeout(() => controller.abort(), 10000);

    try {
        const res = await fetch(`http://localhost:8080/emps/${id}`, {
            method: 'GET',
            signal: controller.signal
        });

        clearTimeout(timeoutId);

        if (!res.ok) {
            throw new Error(`查询失败: ${res.status}`);
        }

        const result = await res.json();
        return result.data; // 你的后端 Result.success(emp) 包装了一层
    } catch (err) {
        console.error("请求出错:", err);
        alert("请求超时或发生错误");
        return null;
    }
}

//编辑员工
async function editEmp(id) {
    const emp = await getEmp(id);
    if (!emp) {
        alert("找不到员工");
        return;
    }

    document.getElementById('title-name').innerHTML = "编辑员工";
    showModalForm('add-emp-form');

    // 回填表单
    document.getElementById('emp-id').value = emp.id; // 隐藏域存id
    document.getElementById('emp-username').value = emp.username;
    document.getElementById('emp-name').value = emp.name;
    document.getElementById('emp-gender').value = emp.gender;
    document.getElementById('image').value = emp.image;
}

//新增员工
function addEmp() {
    document.getElementById('title-name').innerHTML = "新增员工";
    document.getElementById('emp-id').value = '';
    document.getElementById('emp-username').value = '';
    document.getElementById('emp-name').value = '';
    document.getElementById('emp-gender').value = '';
    document.getElementById('image').value = '';
    showModalForm('add-emp-form');
}

async function submitEmp() {
    const id = document.getElementById('emp-id').value;
    const username = document.getElementById('emp-username').value.trim();
    const name = document.getElementById('emp-name').value.trim();
    const gender = document.getElementById('emp-gender').value;

    if (!username || !name) {
        alert('请填写完整信息');
        return;
    }

    const emp = { username, name, gender };

    let url = 'http://localhost:8080/emps';
    let method = 'POST';

    if (id) { // 编辑模式
        url = `http://localhost:8080/emps/${id}`;
        method = 'PUT';
    }

    const res = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(emp)
    });

    const result = await res.json();
    if (result.code === 1) {
        alert(id ? '员工修改成功！' : '员工添加成功！');
        closeModal('add-emp-form');
        showEmp();
    } else {
        alert('操作失败：' + (result.msg || '未知错误'));
    }
}
