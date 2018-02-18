# -*- coding: utf-8 -*-
"""
自动化压测小脚本
"""
import os
import time

# app APP的文件名(带.apk)
app = ""
# deviceID 设备ID
phoneID = ""
# execute times 单轮执行次数
execcount = 1
# execute interval(seconds) 执行间隔
execinterval = 1
# 循环轮数
monkeyclickcount = 1
# 脚本文件
script = ""
# 工作空间
WORKSPACE = os.path.abspath(".")


# 获取配置文件的参数
# phone待测试的设备ID、execcount循环执行次数、monkeyclickcount每次循环的时候点击的次数
def getWorkConfig():
    f = open(".ini", "r")
    config = {"app": app, "phoneID": phoneID, "execcount": execcount, "monkeyclickcount": monkeyclickcount}
    try:
        while True:
            line = f.readline()
            if line:
                line = line.strip()
                linesplit = line.split(":")
                if linesplit.__sizeof__() > 1:
                    if linesplit[0] == 'phoneID':
                        config['phoneID'] = linesplit[1]
                    elif linesplit[0] == 'monkeyclickcount':
                        config["monkeyclickcount"] = linesplit[1]
                    elif linesplit[0] == 'execcount':
                        config["execcount"] = linesplit[1]
                    elif linesplit[0] == 'app':
                        config["app"] = linesplit[1]
                    elif linesplit[0] == 'script':
                        config["script"] = linesplit[1]
            else:
                break
    except Exception:
        print("Oops！参数读取错误！")
    finally:
        f.close()
        print("config : %s" % config)
        return config


# 安装待测APK
def installApk(config):
    phoneAddr = config.get("phoneID")
    appName = config.get("app")
    print('Ready to start installing apk')

    if phoneAddr:
        installPhoneApk = "adb -s %s install -r %s\\apk\\%s" % (phoneAddr, WORKSPACE, appName)
        os.popen(installPhoneApk)
        print("installing phoneID apk\n")


# 杀掉待测APP，为保证测试环境的纯净
def killTestApp():
    forceStopApp = "adb -s %s shell am force-stop com.example.demo1" % workConfig.get('phoneID')
    os.popen(forceStopApp)
    print("kill APP done")


# 执行Monkey随机脚本
def fullmonkey(workconfig):
    # 杀死待测APP
    killTestApp()

    # 执行shell命令，实现随机模拟操作
    monkeycmd = "adb -s %s shell monkey -p com.example.demo1 " \
                "--ignore-timeouts --ignore-crashes --kill-process-after-error " \
                "--pct-touch 35 --pct-syskeys 30 --pct-appswitch 35 --hprof  " \
                "--throttle 300 -v -v -v %s" \
                % (workconfig.get("phoneID"), workconfig.get("monkeyclickcount"))
    os.popen(monkeycmd)


# 执行MonkeyScript自定义脚本
def fullmonkeyscript(workconfig):
    # 杀死待测APP
    killTestApp()
    # 把文件复制至/data/local/tmp/，没有root权限的手机把脚本放在SD卡里，这样可查看是否复制至手机
    # monkeycmd1 = "adb -s %s push %s\\%s /data/local/tmp/" \
    monkeycmd1 = "adb -s %s push %s\\%s /sdcard/tmp/" \
                 % (workconfig.get("phoneID"), WORKSPACE, workconfig.get("script"))
    os.popen(monkeycmd1)
    print("copy script to phone")
    # 等待脚本复制完成
    time.sleep(0.5)
    # 执行命令，运行script脚本
    # monkeycmd2 = "adb -s %s shell monkey -f /data/local/tmp/%s %s" \
    monkeycmd2 = "adb -s %s shell monkey -f /sdcard/tmp/%s %s" \
                 % (workconfig.get("phoneID"), workconfig.get("script"), workconfig.get("execcount"))
    os.popen(monkeycmd2)
    print("executing script...\n")


# 生成chkbugreport性能测试报告
def createChkbugreport():
    print("create bugreport file")
    # 生成bugreport.txt
    bugreport = "adb -s %s shell bugreport > %s\\bugreport.txt" % (workConfig.get("phoneID"), WORKSPACE)
    os.popen(bugreport)
    # 等待生成bugreport
    time.sleep(120)
    print("create bugreport.html file")
    # 用chkbugreport解析bugreport，生成测试报告
    chkbugreport = "java -jar %s\\chkbugreport.jar %s\\bugreport.txt" % (WORKSPACE, WORKSPACE)
    os.popen(chkbugreport)


# 生成battery-historian性能测试报告，调研应该需要安装整个框架，单独调用py脚本无效
def createBatteryHistorian(workconfig):
    # 启动batterystats
    print("enable batterystats")
    enablebatterystats = "adb -s %s shell dumpsys batterystats --enable full-wake-history" \
                         % (workconfig.get("phoneID"))
    os.popen(enablebatterystats)
    # 重置batterystats
    resetbatterystats = "adb -s %s shell dumpsys batterystats --reset" \
                        % (workconfig.get("phoneID"))
    os.popen(resetbatterystats)
    # 生成bugreport.txt
    print("create bugreport file")
    bugreport = "adb -s %s shell bugreport > %s\\bugreport.txt" % (workconfig.get("phoneID"), WORKSPACE)
    os.popen(bugreport)
    # 用historian.py解析bugreport生成测试报告
    print("generate battery.html file")
    batterybugreport = "python %s\\historian.py -a %s\\bugreport.txt > battery.html" % (WORKSPACE, WORKSPACE)
    os.popen(batterybugreport)


if __name__ == '__main__':
    workConfig = getWorkConfig()
    installApk(workConfig)
    time.sleep(4)
    forcount = int(workConfig.get("monkeyclickcount"))
    for i in range(forcount):
        print("-------------我是分割线---------------")
        print("execute monkey ,loop = %s" % (i + 1))
        # fullmonkey(workConfig)
        fullmonkeyscript(workConfig)
        print("-------------我是分割线---------------")
        time.sleep(execinterval)

    # createChkbugreport()
    # createBatteryHistorian(workConfig)

    print("Completion of the current round of testing")
    print("\n\n-------------我是END---------------")
    input("Enter key to close")
