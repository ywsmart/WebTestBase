package com.yvan.pageobject;

/**
 * Function：登录页面
 * Created by YangWang on 2018-01-04 21:15.
 */
public class LoginPage {
    Browser driver;

    public LoginPage(Browser driver) {
        this.driver = driver;
    }

    // 页面元素的索引，UI变更在此修改
    public static class getElementIndex {
        public static final String TITLE = "scm | 登录"; // 标题
        public static final String USER_INPUT_ID = "id=>userid"; // 用户名输入框
        public static final String PASSWORD_INPUT_ID = "id=>password"; // 密码输入框
        public static final String LOGIN_BUTTEN_ID = "xpath=>/html/body/div/div[2]/form/div[3]/div[2]/a"; // 登录按钮
        public static final String LOGIN_SUCCESS_TEXT_XPATH = ""; //
        public static final String IFRAME_ID = "";
    }

    // 检测页面是否加载登录页面
    public boolean isLoaded() {
        return getElementIndex.TITLE.equals(driver.getTitle());
    }

    // 检测页面是否登录后加载首页
    public boolean isLogged() {
        return HomePage.getElementIndex.TITLE.equals(driver.getTitle());
    }

    // 表单的进入
    public void loginIframeIn() throws BaseException {
        driver.enterFrame(getElementIndex.IFRAME_ID);
    }

    // 表单的退出
    public void loginIframeOut() {
        driver.leaveFrame();
    }

    // 登陆方法，加载网页即登入，然后根据title 判断跳转是否成功
    public void login(String user, String password) throws BaseException {
        driver.waitElement(getElementIndex.USER_INPUT_ID, driver.timeout);
        driver.inputText(getElementIndex.USER_INPUT_ID, user);
        driver.waitElement(getElementIndex.PASSWORD_INPUT_ID, driver.timeout);
        driver.inputText(getElementIndex.PASSWORD_INPUT_ID, password);
        driver.waitElement(getElementIndex.LOGIN_BUTTEN_ID, driver.timeout);
        driver.click(getElementIndex.LOGIN_BUTTEN_ID);

    }

    // 返回登录成功之后的用户名
    public String getUser() throws BaseException {
        driver.waitElement(getElementIndex.LOGIN_SUCCESS_TEXT_XPATH, driver.timeout);
        String text = driver.getElement(getElementIndex.LOGIN_SUCCESS_TEXT_XPATH).getText();
        return text;
    }
}
