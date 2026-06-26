// 公共方法

// 分页
function buildPagination(currentPage, totalItems, pageSize) {
    const totalPages = Math.ceil(totalItems / pageSize);
    let html = '';
    if (currentPage > 1) html += `<button onclick="showEmp(${currentPage - 1})">上一页</button>`;
    for (let i = 1; i <= totalPages; i++) {
        if (i === currentPage) {
            html += `<button style="font-weight: bold;" disabled>${i}</button>`;
        } else {
            html += `<button onclick="showEmp(${i})">${i}</button>`;
        }
    }
    if (currentPage < totalPages) html += `<button onclick="showEmp(${currentPage + 1})">下一页</button>`;
    return html;
}

// 模态框控制
function showModalForm(idToShow) {
    const modal = document.getElementById('product-modal');
    modal.style.display = 'block';
    // 隐藏所有直接子容器（form 或 div）
    modal.querySelectorAll('.modal-content > form, .modal-content > div').forEach(el => el.style.display = 'none');
    // 显示目标
    const target = document.getElementById(idToShow);
    if (target) target.style.display = 'block';
}

// 点击按钮调用
function showAddEmpForm() { addEmp(); }
function showAddDeptForm() { showModalForm('add-dept-form'); }
function showAddProductForm() { showModalForm('add-product-form'); }

// 关闭模态框
function closeModal(id) {
    const modal = document.getElementById(id);
    modal.style.display = 'none';
    // 隐藏模态框内所有 form 和 div
    modal.querySelectorAll('.modal-content > form, .modal-content > div').forEach(el => el.style.display = 'none');
}


// 图片上传
document.getElementById('upload-image').addEventListener('change', async function () {
    const file = this.files[0];
    if (!file) return;
    const formData = new FormData();
    formData.append('image', file);
    try {
        const res = await fetch('http://localhost:8080/upload', { method: 'POST', body: formData });
        const result = await res.json();
        if (result.code === 1) {
            alert('上传成功！');
            document.getElementById('uploaded-url').value = result.data;
        } else {
            alert('上传失败：' + result.msg);
        }
    } catch (err) {
        alert('上传出错');
    }
});
