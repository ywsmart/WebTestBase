# WebTestBase

Web UI automation testing framework based on Selenium and TestNG.

* 本框架参考了Dagger: https://github.com/NetEase/Dagger
* 本框架参考了Knife: https://github.com/defnngj/Knife

## 概述

  WebTestBase是基于Selenium（WebDriver）和TestNG的轻量WebUI自动化测试框架，是对Selenium进行了简单的二次封装（俗称造轮子），比Selenium所提供的原生方法操作更简单。参考了Dagger和Knife。
  * com.yvan.pageobject：Web对象目录，存放页面对象及二次封装的 API，方便维护
  * com.yvan.testpage：测试目录，存放测试用例及测试demo

## 设计理念

  Selenium 所提供的原生方法并不太好用，若需要长时间使用 Selenium 来写自动化测试就很繁琐。因为 Selenium 脚本并不像开发项目那么“高级”，一旦项目结构成型之后，大多时间，是在模拟用户重复的进行sendKeys()和click()操作；写多了自然觉得越简单越好。因此才想到了二次封装。
  
  该框架专注于 WebUI 自动化，只封装够用的浏览器操作为 API ，并充分简化/强化这些 API，以简约的风格去降低自动化的学习及使用成本。
  
  同时，也希望框架主要用于编写冒烟用例、其次是主干用例，少写逻辑复杂功能，不写边边角角功能，让用例也保持清爽，只需定期用例回归，易于后期维护。
  
## 特征

* 简单封装 selenium 原生的方法，精简为大约30个方法，使用更加简单，这些方法基本覆盖 WebUI 自动化测试
* 支持有多种定位方式（id,name,class,linkText,xpath,css）
* 结合 TestNG 单元测试框架，可以完整实现自动化用例的组织、运行和生成报告（也可通过 IDEA 运行结果导出测试结果）
* 支持单机多浏览器并发执行，大大缩短用例执行时间，并发为 TestNG 的特性
* 加入 arrow 之后，可以实现用例失败之后重试，重试次数也可自由配置，大大增加了用例的稳定性

## 推荐

* 集成开发工具：IntelliJ IDEA
* 项目管理：Maven
* 测试插件：TestNG
* 浏览器：推荐Chrome（启动快，支持好，Chromium团队在维护，质量靠谱，内存占用少，单测试机并发多开浏览器毫无压力）

## 依赖框架与库

* Selenium :http://www.seleniumhq.org/ (通过Maven更新)
* log4j :http://logging.apache.org/log4j/1.2/ (通过Maven更新)
* testNG :http://testng.org/doc/index.html (通过Maven更新)
* arrow :https://github.com/NetEase/arrow/releases (需要单独下载，并导入IDE，插件使用方法详见[这里](https://github.com/NetEase/arrow/blob/master/README.md))

## 如何使用

com.yvan.pageobject包里的Browser类封装了Selenium常用的方法，API详见项目目录里的 _*JavaDoc\index.html*_

例子：基本操作请查看 BaiduDemo

设置用例运行失败的重试次数：
* 打开项目根目录下的 config.properties 文件，修改 retrycount 参数值。注：利用 TestNG 的 dataProvider 时，除第一个参数外，其余参数执行失败后，重试次数为（设置次数-1次）

切换不同的浏览器、模式及超时时间：
* 打开项目根目录下的 prop.properties 文件，修改 BrowserType 参数值。

浏览器驱动需要另外下载和配置
* 下载地址（可能需要科学上网）：http://www.seleniumhq.org/download/

* 配置方法（参考）：
    1. 手动创建一个存放浏览器驱动的目录，如：D:\driver，将下载的浏览器驱动文件（例如：chromedriver、geckodriver）放入该目录下。注意：下载的版本和使用的浏览器版本要对应。
    2. 我的电脑–>属性–>系统设置–>高级–>环境变量–>系统变量–>Path，将“C:\driver”目录添加到Path的值中。
        * Path  ;C:\driver 
    3. 使用方法
        ```java      
        WebDriver driver = new ChromeDriver();    //Chrome浏览器
        
        WebDriver driver = new FirefoxDriver();   //Firefox浏览器
        
        WebDriver driver = new EdgeDriver();      //Edge浏览器
        
        WebDriver driver = new InternetExplorerDriver();  // Internet Explorer浏览器
        
        WebDriver driver = new OperaDriver();     //Opera浏览器
        
        WebDriver driver = new PhantomJSDriver();   //PhantomJS
        ```
     