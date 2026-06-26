-- ====================================================================
-- 慧购智能电商平台  数据库初始化脚本  System-DB.sql
-- 适用数据库：MySQL 8.x
-- 说明：与 application.yml 中 jdbc:mysql://localhost:3306/tlias 对应
-- 第12组  陈保达 202313010102
-- ====================================================================

CREATE DATABASE IF NOT EXISTS tlias DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE tlias;

-- --------------------------------------------------------------------
-- 1. 分类（部门）表 dept
-- --------------------------------------------------------------------
DROP TABLE IF EXISTS dept;
CREATE TABLE dept (
    id          INT          NOT NULL AUTO_INCREMENT COMMENT '分类编号',
    name        VARCHAR(50)  NOT NULL                COMMENT '分类名称',
    create_time DATETIME     DEFAULT NULL            COMMENT '创建时间',
    update_time DATETIME     DEFAULT NULL            COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

INSERT INTO dept (name, create_time, update_time) VALUES
('数码电器', NOW(), NOW()),
('服装鞋包', NOW(), NOW()),
('食品生鲜', NOW(), NOW()),
('家居百货', NOW(), NOW());

-- --------------------------------------------------------------------
-- 2. 用户表 emp
-- --------------------------------------------------------------------
DROP TABLE IF EXISTS emp;
CREATE TABLE emp (
    id          INT          NOT NULL AUTO_INCREMENT COMMENT '用户编号',
    username    VARCHAR(50)  NOT NULL                COMMENT '登录用户名',
    password    VARCHAR(100) NOT NULL                COMMENT '登录密码',
    name        VARCHAR(50)  DEFAULT NULL            COMMENT '姓名',
    gender      TINYINT      DEFAULT NULL            COMMENT '性别：1男 2女',
    image       VARCHAR(255) DEFAULT NULL            COMMENT '头像地址',
    job         TINYINT      DEFAULT NULL            COMMENT '职位/角色',
    entrydate   DATE         DEFAULT NULL            COMMENT '入职/注册日期',
    dept_id     INT          DEFAULT NULL            COMMENT '所属分类/部门',
    create_time DATETIME     DEFAULT NULL            COMMENT '创建时间',
    update_time DATETIME     DEFAULT NULL            COMMENT '修改时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

INSERT INTO emp (username, password, name, gender, job, entrydate, dept_id, create_time, update_time) VALUES
('admin',  '123456', '管理员',   1, 1, '2026-01-01', 1, NOW(), NOW()),
('test01', '123456', '测试用户', 2, 5, '2026-02-15', 2, NOW(), NOW());

-- --------------------------------------------------------------------
-- 3. 商品表 product
-- --------------------------------------------------------------------
DROP TABLE IF EXISTS product;
CREATE TABLE product (
    id           INT            NOT NULL AUTO_INCREMENT COMMENT '商品编号',
    name         VARCHAR(100)   NOT NULL                COMMENT '商品名称',
    description  VARCHAR(500)   DEFAULT NULL            COMMENT '商品描述',
    price        DECIMAL(10,2)  NOT NULL                COMMENT '商品价格',
    image_url    VARCHAR(255)   DEFAULT NULL            COMMENT '商品图片地址',
    stock        INT            DEFAULT 0               COMMENT '库存数量',
    brand        VARCHAR(50)    DEFAULT NULL            COMMENT '品牌',
    created_time DATETIME       DEFAULT NULL            COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

INSERT INTO product (name, description, price, image_url, stock, brand, created_time) VALUES
('智能蓝牙耳机',   '主动降噪，超长续航，舒适佩戴',       299.00, '', 120, '声悦',   NOW()),
('轻薄笔记本电脑', '14英寸全面屏，轻薄便携，办公学习首选', 4599.00, '', 35,  '智联',   NOW()),
('纯棉休闲T恤',    '透气亲肤，多色可选，男女同款',       89.00,  '', 200, '简衣',   NOW()),
('有机五常大米',   '东北黑土地，当季新米，5kg装',        69.90,  '', 80,  '田园',   NOW()),
('多功能收纳箱',   '大容量可折叠，居家车载两用',         49.90,  '', 150, '宜家用', NOW());

-- --------------------------------------------------------------------
-- 4. 购物车项表 cartitem
-- --------------------------------------------------------------------
DROP TABLE IF EXISTS cartitem;
CREATE TABLE cartitem (
    id         BIGINT        NOT NULL AUTO_INCREMENT COMMENT '购物车项编号',
    user_id    BIGINT        NOT NULL                COMMENT '所属用户ID',
    product_id BIGINT        NOT NULL                COMMENT '关联商品ID',
    quantity   INT           NOT NULL DEFAULT 1      COMMENT '购买数量',
    price      DECIMAL(10,2) DEFAULT NULL            COMMENT '加入时单价',
    PRIMARY KEY (id),
    KEY idx_user (user_id),
    KEY idx_product (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车项表';

INSERT INTO cartitem (user_id, product_id, quantity, price) VALUES
(2, 1, 1, 299.00),
(2, 3, 2, 89.00);

-- ====================================================================
-- 初始化完成
-- ====================================================================
