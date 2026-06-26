// 部门管理

// 展示部门
function showDept(page = 1, pageSize = 10) {
    fetch(`http://localhost:8080/depts?page=${page}&pageSize=${pageSize}`)
        .then(res => res.json())
        .then(result => {
            const list = result.data;
            const panel = document.getElementById('main-panel');
            panel.innerHTML = `
        <h2>部门列表</h2>
        <table>
          <tr>
            <th>序号</th><th>部门名称</th><th>操作</th>
          </tr>
          ${list.map((dept, index) => `
            <tr>
              <td>${index + 1 + (page - 1) * pageSize}</td>
              <td>${dept.name}</td>
              <td>
                <button onclick="editDept(${dept.id})">编辑</button>
                <button onclick="deleteDept(${dept.id})">删除</button>
              </td>
            </tr>
          `).join('')}
        </table>`;
        });
}

// 添加部门
async function submitDept() {
    const name = document.getElementById('dept-name').value.trim();
    if (!name) {
        alert('请输入部门名称');
        return;
    }

    const dept = { name };
    try {
        const res = await fetch('http://localhost:8080/depts', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dept)
        });
        const result = await res.json();

        if (result.code === 1) {
            alert('部门添加成功！');
            closeModal('product-modal');
            showDept();
        } else {
            alert('添加失败：' + (result.msg || '未知错误'));
        }
    } catch (error) {
        alert('请求失败，服务器无响应');
    }
}

// 删除部门
async function deleteDept(id) {
    if (!confirm(`确定要删除部门 ID 为 ${id} 吗？`)) return;
    const res = await fetch(`http://localhost:8080/depts/${id}`, { method: 'DELETE' });
    const result = await res.json();
    if (result.code === 1) {
        alert('删除成功');
        await showDept();
    } else {
        alert('删除失败');
    }
}

// 编辑部门
async function editDept(id) {
    const newName = prompt("请输入你想要更改的部门名称：");
    if (!newName || newName.trim() === '') {
        return;
    }

    const trimmedName = newName.trim();
    if (trimmedName.length < 2 || trimmedName.length > 50) {
        alert('部门名称长度应在2-50个字符之间');
        return;
    }

    const updated = { id, name: newName };
    const res = await fetch(`http://localhost:8080/depts`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updated)
    });

    const result = await res.json();
    if (result.success || result.code === 1) {
        alert('修改成功');
        await showDept();
    } else {
        alert('修改失败');
    }
}
