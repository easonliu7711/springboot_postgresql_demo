### 零、基本關鍵技術和函式庫的簡介
當使用Spring Boot建立應用程式時，通常會涉及Spring Web、Lombok、JPA和PostgreSQL。 以下是這些關鍵技術和函式庫的簡要介紹：

1. **Spring Web**：
    - Spring Web是Spring框架的一部分，它提供了建立Web應用程式的核心功能。 Spring Web包含Spring MVC（Model-View-Controller）框架，它用於處理HTTP請求和回應，實現Web應用程式的控制器和視圖層。 Spring Web使開發人員能夠創建RESTful API和Web應用程序，並提供了處理請求映射、請求參數綁定、視圖渲染等功能。

2. **Lombok**：
    - Lombok是一個Java庫，旨在簡化Java程式碼的編寫。 它透過註解來自動產生常見的Java類別成員，如getter、setter、equals、hashCode和toString方法，從而減少了冗長的程式碼。 Lombok可以提高程式碼的可讀性和可維護性，同時減少了樣板程式碼的編寫工作。

3. **JPA（Java Persistence API）**：
    - JPA是Java EE標準中的一部分，用於將Java物件對應到關聯式資料庫中的資料。 它提供了一種方便的方式來執行資料庫操作，而不需要編寫原生SQL查詢。 JPA定義了一組註解，用於描述實體類別和它們之間的關係，以及如何將它們對應到資料庫表。 Spring Boot通常與Spring Data JPA一起使用，這是Spring專案的一部分，提供了簡化JPA資料存取的功能。

4. **PostgreSQL**：
    - PostgreSQL是一個強大的開源關係型資料庫管理系統（RDBMS），它具有高度的可擴展性和可自訂性。 PostgreSQL支援SQL標準，並提供了許多進階功能，如複雜的資料類型、觸發器、預存程序和擴充性。 在Spring Boot應用程式中，你可以使用PostgreSQL作為後端資料庫來儲存和管理資料。 Spring Boot提供了對PostgreSQL的良好整合支持，讓你可以輕鬆地連接、查詢和操作資料庫。

5. **TimescaleDB**：
    - TimescaleDB是一個開源的時間序列資料庫擴展，它建立在PostgreSQL之上。 它專注於處理大量時間序列數據，例如IoT感測器數據、監控數據和日誌。 TimescaleDB透過表格分區和最佳化查詢來提供高效能的時間序列資料儲存和檢索。 它允許你使用標準SQL查詢語言來處理時間序列數據，同時仍然享受PostgreSQL的強大功能。

綜合使用Spring Web、Lombok、JPA、PostgreSQL和TimescaleDB，你可以建立具有高度可維護性、高效能和可擴充性的Web應用程式和服務，尤其適用於需要儲存和查詢大量時間序列資料的應用場景。 這些技術的組合提供了快速開發和資料管理的強大工具，幫助開發人員更輕鬆地建立現代應用程式。

### 一、創建Spring Boot專案並整合Spring Web、Lombok、JPA、PostgreSQL和Flyway
創建一個Spring Boot專案並整合Spring Web、Lombok、JPA、PostgreSQL和Flyway，你可以按照以下步驟操作：

1. 開啟IntelliJ IDEA並點選"File"（檔案） > "New"（新建） > "Project"（專案）。

2. 在彈出的視窗中，選擇"Spring Initializr"作為項目類型。 然後點選"Next"（下一步）。

3. 在下一步中，配置項目的基本資訊：
    - 專案名稱：為你的專案取一個名字。
    - 項目位置：選擇你想要儲存項目的目錄。
    - 使用的語言：選擇Java。
    - 選擇Spring Initializr的版本（建議選擇最新版本）。

4. 點選"Next"（下一步）。

5. 在下一步中，配置專案的依賴：
    - 勾選"Spring Boot DevTools"（用於開發時熱部署）。
    - 勾選"Spring Web"（用於建立Web應用程式）。
    - 勾選"Lombok"（用於簡化Java程式碼）。
    - 勾選"Spring Data JPA"（用於存取資料庫）。
    - 勾選"PostgreSQL Driver"（用於連接PostgreSQL資料庫）。
    - 勾選"Flyway"（用於資料庫遷移）。

6. 點選"Next"（下一步）。

7. 在下一個步驟中，設定資料庫連線：
    - 資料來源URL：輸入你的PostgreSQL資料庫的連接URL。
    - 資料來源使用者名稱：輸入連接資料庫所需的使用者名稱。
    - 資料來源密碼：輸入連接資料庫所需的密碼。

8. 點選"Next"（下一步）。

9. 設定專案的套件和類別名，然後點擊"Finish"（完成）。

10. IntelliJ會自動產生一個Spring Boot項目，包括所需的依賴和設定檔。 你可以在專案結構中找到"src/main/resources/application.properties"文件，然後在其中設定資料庫連接資訊和Flyway遷移的相關配置。

11. 建立你的實體類別、Repository介面和控制器類，以建立你的應用程式邏輯。

12. 最後，運行你的Spring Boot應用程序，它將自動啟動內嵌的Tomcat伺服器，並在你配置的連接埠上提供Web服務。

這樣，你就成功創建了一個Spring Boot項目，整合了Spring Web、Lombok、JPA、PostgreSQL和Flyway。 你可以根據你的需求添加更多的業務邏輯和功能。

### 二、透過Docker起PostgreSQL服務
本機開發環境需搭配使用相關第三方服務，採用Docker方式安裝相關第三方服務，先在根目錄新增資料夾demo-docker，並在之中建立docker-compose.yml檔案，內容如下：
```yaml
version: '3'
services:
  timescaledb:
    image: timescale/timescaledb-ha:pg14-latest
    container_name: timescale-demo
    restart: on-failure
    ports:
      - 25432:5432/tcp
    volumes:
      - timescaledb-data-demo:/var/lib/postgresql/data
    environment:
      POSTGRES_PASSWORD: password
      POSTGRES_USER: postgres
      POSTGRES_DB: tsdb
      POSTGRES_HOST_AUTH_METHOD: trust
    networks:
      - demo-net
volumes:
  timescaledb-data-demo:
    external: true
networks:
  demo-net:
    external: true
  ```

依下列程序安裝：

* 創建容器溝通網域(network)
  ```bash
  docker network create -d bridge demo-net
  ```
* 創建容器儲存資料目錄(volume)

    * Timescaledb
      ```bash
      docker volume create timescaledb-data-demo
      ```
* 執行docker-compose啟動相關第三方服務：
    * 執行移至專案內bms-docker目錄
      ```bash
      cd demo-docker
      ```
    * 執行docker-compose啟動相關第三方服務：
      ```bash
      docker-compose up -d
      ```

### 三、設定資料庫連接
要在Spring Boot專案中設定資料庫連接，你需要編輯`application.properties`或`application.yml`文件，這取決於你的設定檔格式。 以下是配置資料庫連線的範例：

**使用application.properties:**

在`src/main/resources`目錄下找到或建立`application.properties`文件，然後新增下列屬性配置：

```properties
# 資料來源配置
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA配置
spring.jpa.database=POSTGRESQL
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

# Flyway配置
spring.flyway.enabled=true
spring.flyway.url=jdbc:postgresql://localhost:5432/your_database_name
spring.flyway.user=your_database_username
spring.flyway.password=your_database_password
```

請替換上述範例中的以下內容：
- `your_database_name`：你的PostgreSQL資料庫的名稱。
- `your_database_username`：連接資料庫所需的使用者名稱。
- `your_database_password`：連接資料庫所需的密碼。

**使用application.yml:**

在`src/main/resources`目錄下找到或建立`application.yml`文件，然後新增下列屬性配置：

```yaml
# 資料來源配置
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/your_database_name
    username: your_database_username
    password: your_database_password
    driver-class-name: org.postgresql.Driver

  # JPA配置
  jpa:
    database: POSTGRESQL
    show-sql: true
    hibernate:
      ddl-auto: update

  # Flyway配置
  flyway:
    enabled: true
    url: jdbc:postgresql://localhost:5432/your_database_name
    user: your_database_username
    password: your_database_password
```

同樣，請替換上述範例中的以下內容：
- `your_database_name`：你的PostgreSQL資料庫的名稱。
- `your_database_username`：連接資料庫所需的使用者名稱。
- `your_database_password`：連接資料庫所需的密碼。

一旦你完成了這些配置，Spring Boot應用程式將使用這些配置連接到PostgreSQL資料庫，並且Flyway將執行資料庫遷移（如果你已啟用Flyway）。 確保資料庫伺服器在指定的URL上運行，並且你有正確的資料庫憑證。

其中JPA相關屬性的說明：

1. `spring.jpa.database=POSTGRESQL`：
    - 這個屬性指定了要使用的資料庫類型。 在這裡，你明確告訴Spring Boot你正在使用PostgreSQL資料庫。 Spring Boot可以透過這個屬性來選擇適當的資料庫方言，以便在產生SQL查詢時可以使用正確的語法。

2. `spring.jpa.show-sql=true`：
    - 這個屬性控制是否在控制台上顯示產生的SQL語句。 當它設定為`true`時，Spring Boot會在控制台上列印產生的SQL查詢語句，這對於偵錯和監視資料庫操作非常有用。

3. `spring.jpa.hibernate.ddl-auto=update`：
    - 這個屬性定義了Hibernate的DDL（Data Definition Language）自動化行為。 具體來說，`update`選項告訴Hibernate在啟動時自動更新資料庫模式。 這表示如果實體類別發生更改，Hibernate會嘗試自動更新資料庫表結構，以反映這些更改，而不會刪除現有資料。 這對於開發和調試階段很有用，但在生產環境中，通常建議停用此選項，以確保資料庫結構的穩定性和一致性。

要注意的是，`spring.jpa.hibernate.ddl-auto`屬性有幾個可選的值，每個值都控制著Hibernate的DDL自動化行為。 除了`update`之外，還有其他選項，例如`create`（每次啟動時都會建立表格），`create-drop`（建立後在關閉時刪除表），以及`validate`（只驗證資料庫模式 與實體類別是否匹配，不做任何自動更新）。 你可以根據專案的需求選擇適合的選項。

### 四、資料庫初始設定
新建一個檔案V1.0.0__init_tables.sql放置到migration的資料夾中，這段SQL程式碼是用來初始化和設定PostgreSQL資料庫的一部分。 它包括以下幾個方面的設定和操作：

```sql
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

```

### 五、開發API
接下來，我們可以開始開發DeviceController，以包含GET和POST操作device_info資料的API。首先，我們將創建一個名為`DeviceController`的類別。以下是一個簡單的示例：
