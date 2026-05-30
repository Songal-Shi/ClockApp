# 🚀 使用GitHub Actions在线构建APK - 详细指南

## 📘 概述
GitHub Actions提供**免费**的云端构建服务，每月有2000分钟的免费额度，构建一次APK只需约5-8分钟。

---

## 📊 完整步骤（约10分钟）

### 第1步：创建GitHub账户（如果没有）
1. 访问 https://github.com
2. 点击 "Sign up" 注册
3. 验证邮箱

### 第2步：创建新仓库
1. 登录GitHub后，点击右上角 **"+"** → **"New repository"**
2. 填写仓库信息：
   - **Repository name**: `ClockApp` （或任何你喜欢的名字）
   - **Description**: `Android Clock Application`
   - 选择 **Public**（私有仓库会消耗免费额度）
   - ✅ 勾选 **"Add a README file"**
3. 点击 **"Create repository"**

### 第3步：上传代码到GitHub

#### 方法A：使用GitHub网页上传（最简单）

**3.1 准备代码压缩包**
我为你准备了完整的代码包，需要先将代码文件打包。

**3.2 在GitHub网页上传**
1. 进入你的新仓库
2. 点击 **"Add file"** → **"Upload files"**
3. 将 `ClockApp` 文件夹内的**所有文件和文件夹**拖拽到网页
4. 提交信息填写：`Initial commit - Clock App`
5. 点击 **"Commit changes"**

#### 方法B：使用Git命令行（适合开发者）

```bash
# 在你的电脑上执行
cd C:\Users\SONOFF\.qclaw\workspace-x5kuz49xple53hhg\ClockApp
git init
git add .
git commit -m "Initial commit - Clock App"
git branch -M main
git remote add origin https://github.com/你的用户名/ClockApp.git
git push -u origin main
```

### 第4步：触发GitHub Actions自动构建

**4.1 确认配置文件存在**
确保仓库中包含文件：`.github/workflows/build.yml`
（我已经为你创建了这个文件）

**4.2 推送代码触发构建**
- 如果是网页上传，推送后自动触发
- 如果是命令行，push后自动触发

**4.3 查看构建进度**
1. 进入你的GitHub仓库页面
2. 点击顶部 **"Actions"** 标签页
3. 你会看到构建任务正在运行（黄色圆点）
4. 点击任务可查看详细日志

### 第5步：下载构建好的APK

**5.1 等待构建完成**
- 构建成功：绿色✅
- 构建失败：红色❌（检查日志）

**5.2 下载APK**
1. 点击完成的构建任务
2. 在页面底部找到 **"Artifacts"** 区域
3. 点击 **"ClockApp-Debug-APK"** 下载
4. 解压下载的zip文件，得到 `app-debug.apk`

---

## 🎯 快速验证清单

上传到GitHub的文件结构应该是：
```
ClockApp/
├── .github/
│   └── workflows/
│       └── build.yml          ✅ 必须有这个文件
├── app/
│   ├── build.gradle
│   ├── proguard-rules.pro
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/com/example/clock/
│           │   ├── MainActivity.java
│           │   ├── ClockFragment.java
│           │   ├── AlarmFragment.java
│           │   ├── StopwatchFragment.java
│           │   ├── TimerFragment.java
│           │   ├── ClockPagerAdapter.java
│           │   ├── AlarmReceiver.java
│           │   ├── AlarmService.java
│           │   └── NotificationActionReceiver.java
│           └── res/
│               ├── layout/
│               ├── values/
│               ├── drawable/
│               └── mipmap-anydpi-v26/
├── build.gradle
├── settings.gradle
├── gradle.properties
├── local.properties
└── gradlew.bat
```

---

## ⚠️ 常见问题

### Q1：构建失败，提示"SDK not found"
**A**：这是正常的，GitHub Actions会自动安装SDK。检查 `build.yml` 中是否正确配置了 `android-actions/setup-android@v3`

### Q2：构建成功但找不到APK
**A**：在Actions页面的右侧 **"Artifacts"** 区域下载，不是在项目文件里

### Q3：免费额度够用吗？
**A**：完全够用！
- 每月2000分钟免费
- 构建一次约5-8分钟
- 可以构建250次/月

### Q4：可以构建Release版本的APK吗？
**A**：可以！我已经配置了同时构建Debug和Release。Release版本需要签名密钥，适合发布到应用商店。

---

## 📱 构建完成后：安装到手机

1. 将下载的 `app-debug.apk` 复制到手机
2. 在手机上打开文件管理器
3. 点击APK文件
4. 允许"安装未知来源应用"
5. 点击"安装"

---

## 🔄 后续更新代码

如果你修改了代码想要重新构建：
1. 在GitHub仓库中直接编辑文件，或
2. 本地修改后push到GitHub
3. GitHub Actions会自动重新构建
4. 下载最新的APK

---

## ✅ 完成！

按照以上步骤，你将在 **10-15分钟** 内获得一个可安装的APK文件，完全免费，无需本地安装Android Studio！

**需要帮助？**
- 构建失败？把错误日志发给我
- 不知道怎么操作？告诉我卡在哪一步
- 想要添加功能？我可以帮你改代码

开始吧！🚀
