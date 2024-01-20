## 透過Docker起PostgreSQL服務
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

## 設定資料庫連接
要在Spring Boot專案中設定資料庫連接，你需要編輯`application.properties`文件。 以下是配置資料庫連線的範例：

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


一旦你完成了這些配置，Spring Boot應用程式將使用這些配置連接到PostgreSQL資料庫，並且Flyway將執行資料庫遷移（如果你已啟用Flyway）。 確保資料庫伺服器在指定的URL上運行，並且你有正確的資料庫憑證。

其中JPA相關屬性的說明：

1. `spring.jpa.database=POSTGRESQL`：
    - 這個屬性指定了要使用的資料庫類型。 在這裡，你明確告訴Spring Boot你正在使用PostgreSQL資料庫。 Spring Boot可以透過這個屬性來選擇適當的資料庫方言，以便在產生SQL查詢時可以使用正確的語法。

2. `spring.jpa.show-sql=true`：
    - 這個屬性控制是否在控制台上顯示產生的SQL語句。 當它設定為`true`時，Spring Boot會在控制台上列印產生的SQL查詢語句，這對於偵錯和監視資料庫操作非常有用。

3. `spring.jpa.hibernate.ddl-auto=update`：
    - 這個屬性定義了Hibernate的DDL（Data Definition Language）自動化行為。 具體來說，`update`選項告訴Hibernate在啟動時自動更新資料庫模式。 這表示如果實體類別發生更改，Hibernate會嘗試自動更新資料庫表結構，以反映這些更改，而不會刪除現有資料。 這對於開發和調試階段很有用，但在生產環境中，通常建議停用此選項，以確保資料庫結構的穩定性和一致性。

要注意的是，`spring.jpa.hibernate.ddl-auto`屬性有幾個可選的值，每個值都控制著Hibernate的DDL自動化行為。 除了`update`之外，還有其他選項，例如`create`（每次啟動時都會建立表格），`create-drop`（建立後在關閉時刪除表），以及`validate`（只驗證資料庫模式 與實體類別是否匹配，不做任何自動更新）。 你可以根據專案的需求選擇適合的選項。

## 討論筆記
請看 https://hackmd.io/R_QlyLOBQCa2ka0a0jqhMQ?both