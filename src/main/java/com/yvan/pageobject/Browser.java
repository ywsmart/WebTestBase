package com.yvan.pageobject;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Function：浏览器类
 * 主要设置浏览器类型，二次封装一些界面动作，点击、输入、移动、刷新等与界面用户操作（动作）相关的动作
 * Created by YangWang on 2018-01-04 21:22.
 *
 * @author Yvan
 * @version 1.0
 */
public class Browser {

    static WebDriver driver;
    static Logger log = Logger.getLogger(Browser.class.getName());
    int timeout = Integer.parseInt(GlobalSettings.timeout);

    public Browser() throws BaseException {

        int browserType = GlobalSettings.browserType;

        if (browserType == 1) {
            driver = new FirefoxDriver();
        } else if (browserType == 2) {
            driver = new ChromeDriver();
        } else if (browserType == 3) {
            driver = new InternetExplorerDriver();
        } else if (browserType == 4) {
            driver = new EdgeDriver();
        } else if (browserType == 5) {
            driver = new OperaDriver();
        } else if (browserType == 6) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            driver = new ChromeDriver(chromeOptions);
        } else {
            throw new BaseException("浏览器设置项错误，请检查.");
        }

    }

    /**
     * 等待某个元素，在指定的时间内
     *
     * @param index  元素的索引
     * @param second 等待的时间，毫秒
     * @throws BaseException 异常
     */
    public void waitElement(String index, int second) throws BaseException {

        if (index.equals("")) {
            throw new BaseException("定位语法错误 , 缺少元素索引.");
        } else if (!index.contains("=>")) {
            throw new BaseException("定位语法错误 , 缺少 '=>'.");
        }

        String by = index.split("=>")[0];
        String value = index.split("=>")[1];
        By findElement = null;

        switch (by) {
            case "id":
                findElement = By.id(value);
                break;
            case "name":
                findElement = By.name(value);
                break;
            case "class":
                findElement = By.className(value);
                break;
            case "linkText":
                findElement = By.linkText(value);
                break;
            case "xpath":
                findElement = By.xpath(value);
                break;
            case "css":
                findElement = By.cssSelector(value);
                break;
            default:
                throw new BaseException("请输入正确的目标元素 ,'id','name','class','xpaht','css'.");
        }
        new WebDriverWait(driver, second).until(ExpectedConditions
                .presenceOfElementLocated(findElement));

    }

    /**
     * 分析目标元素和定位元素
     *
     * @param index 元素的索引
     * @return 返回元素
     * @throws BaseException 异常基类
     */
    public WebElement getElement(String index) throws BaseException {

        if (index.isEmpty()) {
            throw new BaseException("定位语法为空.");
        } else if (!index.contains("=>")) {
            throw new BaseException("定位语法错误, 缺少 '=>'.");
        }

        String by = index.split("=>")[0];
        String value = index.split("=>")[1];

        switch (by) {
            case "id": {
                WebElement element = driver.findElement(By.id(value));
                return element;
            }
            case "name": {
                WebElement element = driver.findElement(By.name(value));
                return element;
            }
            case "class": {
                WebElement element = driver.findElement(By.className(value));
                return element;
            }
            case "linkText": {
                WebElement element = driver.findElement(By.linkText(value));
                return element;
            }
            case "xpath": {
                WebElement element = driver.findElement(By.xpath(value));
                return element;
            }
            case "css": {
                WebElement element = driver.findElement(By.cssSelector(value));
                return element;
            }
            default:
                throw new BaseException("请输入正确的目标元素 ,'id','name','class','xpaht','css'.");
        }

    }

    /**
     * 刷新页面
     */
    public void refresh() {
        driver.navigate().refresh();
        log.info("刷新页面");
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 移动鼠标到元素上悬停
     *
     * @param index 元素索引
     * @throws BaseException 异常基类
     */
    public void moveToElement(String index) throws BaseException {
        waitElement(index, timeout);
        WebElement element = getElement(index);
        Actions action = new Actions(driver);
        try {
            action.moveToElement(element).perform();//鼠标移动到元素上面悬停
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 单击页面元素
     *
     * @param index 元素的索引
     * @throws BaseException 异常基类
     */
    public void click(String index) throws BaseException {
        waitElement(index, timeout);
        WebElement element = getElement(index);
        try {
            element.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 输入文本
     * 键入前尝试清除现有文本，后在页面元素中键入文本，
     *
     * @param index 元素索引
     * @param text  键入文本
     * @throws BaseException 异常基类
     */
    public void inputText(String index, String text) throws BaseException {
        waitElement(index, timeout);
        WebElement element = getElement(index);
        try {
            element.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            element.sendKeys(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 右击元素
     *
     * @param index 元素索引
     * @throws BaseException 异常基类
     */
    public void rightClick(String index) throws BaseException {
        waitElement(index, timeout);
        WebElement element = getElement(index);
        Actions action = new Actions(driver);
        try {
            action.contextClick(element).perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 单击元素并保持
     *
     * @param index 元素索引
     * @throws BaseException 异常基类
     */
    public void clickAndHold(String index) throws BaseException {
        waitElement(index, timeout);
        WebElement element = getElement(index);
        Actions action = new Actions(driver);
        try {
            action.clickAndHold(element).perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拖动某个元素到某个距离后松开
     *
     * @param so_index 起始元素索引
     * @param ta_index 目标元素索引
     * @throws BaseException 异常基类
     */
    public void dragAndDrop(String so_index, String ta_index) throws BaseException {
        waitElement(so_index, timeout);
        waitElement(ta_index, timeout);

        WebElement source = getElement(so_index);
        WebElement target = getElement(ta_index);
        Actions action = new Actions(driver);
        try {
            action.dragAndDrop(source, target).perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 单击含部分文本的链接元素
//     *
//     * @param index 元素的LinkText索引
//     */
//    public void clickText(String index) {
//        WebElement element = driver.findElement(By.partialLinkText(index));
//        try {
//            element.click();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 选择select标记值
     *
     * @param index 元素索引
     * @param value 设置的值
     * @throws BaseException 异常基类
     */
    public void selectValue(String index, String value) throws BaseException {
        waitElement(index, timeout);
        WebElement element = getElement(index);
        Select sel = new Select(element);
        sel.selectByValue(value);
    }

    /**
     * 执行JavaScript脚本
     *
     * @param js JavaScript脚本语句
     */
    public void js(String js) {
        log.info("执行JavaScript脚本");
        ((JavascriptExecutor) driver).executeScript(js);
    }

    /**
     * 进入iframe框架
     *
     * @param index iframe的索引
     * @throws BaseException 异常基类
     */
    public void enterFrame(String index) throws BaseException {
        waitElement(index, timeout);
        WebElement element = getElement(index);
        log.info("进入iframe框架");
        try {
            driver.switchTo().frame(element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 离开iframe框架
     */
    public void leaveFrame() {
        log.info("离开iframe框架");
        driver.switchTo().defaultContent();
    }

    /**
     * 打开新窗口
     * 并将handles切换到新打开的窗口
     *
     * @param index 打开窗口的元素索引
     * @throws BaseException 异常
     */
    public void openNewWindow(String index) throws BaseException {
        waitElement(index, timeout);
        String search_handle = driver.getWindowHandle();
        WebElement element = getElement(index);
        try {
            element.click();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(search_handle)) {
                driver.switchTo().window(handle);
            }
        }
    }

    /**
     * 从指定的Web元素返回文本
     *
     * @param index 元素索引
     * @return 返回获得的文本
     * @throws BaseException 异常基类
     */
    public String getText(String index) throws BaseException {
        waitElement(index, timeout);
        WebElement element = getElement(index);
        return element.getText();
    }

    /**
     * 获取当前页的标题
     *
     * @return 返回当前页标题
     */
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * 获取当前页的URL
     *
     * @return 返回当前页url
     */
    public String getUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * 获取元素属性的值
     *
     * @param index     元素索引
     * @param attribute 元素的属性名
     * @return 返回元素属性值
     * @throws BaseException 异常基类
     */
    public String getAttribute(String index, String attribute) throws BaseException {
        waitElement(index, timeout);
        WebElement element = getElement(index);
        String value = element.getAttribute(attribute);
        return value;
    }

    /**
     * 接受警告
     */
    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    /**
     * 取消警告
     */
    public void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    /**
     * 打开网址链接
     *
     * @param url 网址
     * @throws BaseException 异常基类
     */
    public void open(String url) throws BaseException {
        try {
            driver.get(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置浏览器窗口大小
     *
     * @param wide 宽
     * @param high 高
     */
    public void setWindow(int wide, int high) {
        driver.manage().window().setSize(new Dimension(wide, high));
    }

    /**
     * 窗口最大化
     */
    public void maxWindow() {
        driver.manage().window().maximize();
    }

    /**
     * 关闭浏览器
     * 模拟用户单击弹出栏标题栏中的“关闭”按钮
     */
    public void close() {
        driver.close();
    }

    /**
     * 退出浏览器
     */
    public void quit() {
        driver.quit();
    }

    /**
     * 等待指定的时间
     *
     * @param millis 毫秒
     * @throws BaseException 异常基类
     */
    public void sleep(long millis) throws BaseException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 截图
     *
     * @param file_path 文件路径
     * @throws BaseException 异常
     */
    public void takeScreenshot(String file_path) throws BaseException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(file_path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
