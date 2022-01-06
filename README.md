# Introduction

* CQRS架構設計
* 分散式事務SAGA設計

## Members

Tony.su

## 專案Local執行環境

* 安裝Axon Server

  ```sh
  # UI see http://127.0.0.1:8024/
  docker run -d --name axonserver -p 8024:8024 -p 8124:8124 axoniq/axonserver:4.5.9
  ```

* 建立一個Mysql 資料庫

  ```sql
  CREATE DATABASE IF NOT EXISTS `bank`;
  USE `bank`;

  CREATE DATABASE IF NOT EXISTS `order`;
  USE `order`;

  CREATE DATABASE IF NOT EXISTS `payment`;
  USE `payment`;

  CREATE DATABASE IF NOT EXISTS `shipping`;
  USE `shipping`;
  ```

* 設定專案環境參數
  
  Eclipse可以使用STS套件

  ```text
  AXONSERVER_HOST=172.20.101.27
  AXONSERVER_PORT=8124
  ```

  或是設定 hosts

  ```text
  # Axon
  172.20.101.27 axonserver
  ```

## API測試

VsCode 請安裝 REST Client套件，執行 xx.http 檔案，例如request.http。

## Swagger UI

see <http://localhost:8080/swagger-ui.html>
