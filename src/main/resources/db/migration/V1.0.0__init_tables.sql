--
-- PostgreSQL database
--

-- 設置語句超時為0，即無超時限制
SET statement_timeout = 0;

-- 設置鎖超時為0，即無超時限制
SET lock_timeout = 0;

-- 設置事務中閒置會話超時為0，即無超時限制
SET idle_in_transaction_session_timeout = 0;

-- 設置客戶端編碼為UTF8
SET client_encoding = 'UTF8';

-- 啟用標準符合字串
SET standard_conforming_strings = on;

-- 設置搜索路徑為空字符串並禁用遞歸查找
SELECT pg_catalog.set_config('search_path', '', false);

-- 禁用函數體檢查
SET check_function_bodies = false;

-- 設置XML選項為內容(content)
SET xmloption = content;

-- 設置客戶端最小消息級別為警告
SET client_min_messages = warning;

-- 關閉行級安全性
SET row_security = off;

--
-- 類型：架構; 架構：demo;
--

-- 如果不存在，則創建demo架構
CREATE SCHEMA IF NOT EXISTS demo;

-- 設置默認表空間為空字符串
SET default_tablespace = '';

-- 設置默認表訪問方法為heap
SET default_table_access_method = heap;

--
-- Name: device_info; Type: TABLE; Schema: demo;
--
DROP TABLE IF EXISTS demo.device_info;
CREATE TABLE demo.device_info
(
    device_id VARCHAR(50) PRIMARY KEY,
    device_name VARCHAR(50) NOT NULL,
    device_type VARCHAR(50) NOT NULL,
    device_status VARCHAR(50) NOT NULL,
    create_time timestamp without time zone NOT NULL,
    update_time timestamp without time zone NOT NULL
);