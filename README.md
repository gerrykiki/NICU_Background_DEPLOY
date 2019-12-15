# Spring_Boot
Java backend

# Project Structure
├── nicu	==>專案名稱

	└──Java Resource ==>Java資源目錄

		└──src ==>原始碼檔案目錄

			└──main

				└──java ==>目錄下放置java檔案的原始碼

					└──wi.nicu.demo ==>初始化，主程式入口

					└──wi.nicu.controller ==>放置Controller原始java檔案

					└──wi.nicu.model ==>放置Model原始java檔案

			└──resources ==>資源目錄

				└──static ==>存放Javascript，CSS，image等靜態資源

				└──template ==>存放頁面檔案，ex:HTML，Spring Boot使用模版引擎建構頁面

				└──application.properties ==>存放Spring Boot的參數配置，ex:Tomcat的port，資料庫連線

			└──test ==>單元測試程式目錄

				└──java

					└──wi.nicu.demo ==>測試類

		└──Library ==>程式庫檔案目錄

			└──JRE System Library ==>JDK檔案目錄

			└──Maven Dependences ==>存放pom.xml定義依賴的jar檔

	└──pom.xml =>Dependency配置檔，新增依賴像在這，maven會自動載入
