// 商品管理

// 展示商品后台列表
function showProductsofBlackground(page = 1, pageSize = 10) {
    fetch(`http://localhost:8080/api/products?page=${page}&pageSize=${pageSize}`)
        .then(res => res.json())
        .then(result => {
            const list = Array.isArray(result) ? result : result.data || [];
            const panel = document.getElementById('main-panel');
            panel.innerHTML = `
                <h2>商品列表</h2>
                <table>
                    <tr>
                        <th>序号</th><th>商品名称</th><th>操作</th>
                    </tr>
                    ${list.map((product, index) => `
                        <tr>
                            <td>${index + 1 + (page - 1) * pageSize}</td>
                            <td>${product.name}</td>
                            <td>
                                <button onclick="editProduct(${product.id})">编辑</button>
                                <button onclick="deleteProduct(${product.id})">删除</button>
                            </td>
                        </tr>`).join('')}
                </table>`;
        })
        .catch(() => alert('获取商品列表失败，请稍后重试'));
}

// 添加商品
document.getElementById('add-product-form').addEventListener('submit', async function (e) {
    e.preventDefault();
    const formData = new FormData(this);
    const product = {
        name: String(formData.get('name')),
        description: String(formData.get('description')),
        price: parseFloat(String(formData.get('price'))),
        stock: parseInt(String(formData.get('stock'))),
        imageUrl: String(formData.get('imageUrl'))
    };

    const res = await fetch('http://localhost:8080/api/products', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(product)
    });

    const result = await res.json();
    if (result.code === 1) {
        alert('商品添加成功！');
        showProducts();
    } else {
        alert('添加失败：' + (result.msg || '未知错误'));
    }
});

// 删除商品
async function deleteProduct(id) {
    if (!confirm(`确定要删除商品 ID 为 ${id} 吗？`)) return;
    const res = await fetch(`http://localhost:8080/api/products/${id}`, { method: 'DELETE' });
    const result = await res.json();
    if (result.code === 1) {
        alert('商品删除成功！');
        showProductsofBlackground();
    } else {
        alert('删除失败：' + (result.msg || '未知错误'));
    }
}

// 前台展示商品
function showProducts() {
    fetch('http://localhost:8080/api/products')
        .then(res => res.json())
        .then(products => {
            const panel = document.getElementById('main-panel');
            panel.innerHTML = `
                <h2>商品列表</h2>
                <table>
                    <tr><th>序号</th><th>商品名</th><th>价格</th><th>库存</th><th>图片</th></tr>
                    ${products.map((p, index) => `
                        <tr>
                            <td>${index + 1}</td>
                            <td>${p.name}</td>
                            <td>¥${p.price}</td>
                            <td>${p.stock}</td>
                            <td><img src="${p.imageUrl}" alt="${p.name}" style="height:50px"></td>
                        </tr>`).join('')}
                </table>`;
        });
}
