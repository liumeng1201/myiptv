# 看电视 (myiptv)

一款专为 **Android TV / 智能电视 / 电视盒子** 设计的极简开源 IPTV 电视直播客户端，具有体积小巧、秒级起播、极简无广告等特点。

[![GitHub Release](https://img.shields.io/github/v/release/liumeng1201/myiptv?style=flat-square)](https://github.com/liumeng1201/myiptv/releases)
[![License: GPL-3.0](https://img.shields.io/badge/License-GPL--3.0-blue.svg?style=flat-square)](LICENSE)
[![Platform](https://img.shields.io/badge/Platform-Android%20TV%20%7C%20Android-green.svg?style=flat-square)](https://github.com/liumeng1201/myiptv)

---

## ✨ 核心特性

- ⚡ **轻量纯净，秒速起播**：无任何内置广告与冗余服务，内存占用低，启动即看，换台秒切。
- 🔀 **手动频道路线切换**：支持遥控器【向右键 ▶】一键呼出右侧抽屉式线路切换面板，自动高亮当前路线，支持多源快速无缝换源。
- 🎨 **全新质感 UI 设计**：深邃夜空蓝质感底板与暖金渐变 TV 专属图标，深度适配 Android TV Leanback 标准 16:9 横屏横幅。
- 🌐 **局域网远程配置 (Web 管理)**：内置轻量 Web 服务，在同局域网内使用手机或电脑浏览器访问 `http://<电视IP>:34567`，即可在线编辑、批量导入或推送电视频道列表（支持 TXT、M3U、JSON 格式）。
- 📑 **EPG 电子节目单**：支持在线与本地 XMLTV / gz 格式节目单，随时掌握正在播出与即将播出的精彩节目。
- ⚙️ **丰富的个性化定制**：支持开机自启、换台反转、换台显示频道号、常驻时钟显示、启动默认进入我的收藏等。

---

## 🎮 操作说明

| 遥控器按键 / 触屏操作 | 对应功能 |
| :--- | :--- |
| **【确定 / OK / 中键】** / 触屏单击 | 打开频道分类与节目列表 |
| **【向右键 ▶】** | **呼出当前频道线路切换列表**（在屏幕右侧抽屉展开，上下键选择线路，确定切换；按返回键/左右键/菜单键自动隐藏） |
| **【向左键 ◀】** / 触屏长按 | 打开 **EPG 电子节目单** |
| **【菜单键 MENU】** / 触屏双击 | 打开 **系统设置**（配置换源、开机自启、时钟、关于等） |
| **【上下键 ▲/▼】** | 快速切换上一个 / 下一个频道（可在设置中开启“换台反转”） |
| **【返回键 BACK / ESC】** | 关闭当前打开的线路列表、频道列表、节目单或设置菜单 |
| **频道列表中聚焦某项时按【向右键 ▶】** | 快速**收藏 / 取消收藏**当前频道 |

---

## 📺 视频源配置格式

应用支持通过远程配置、自定义订阅或本地文件载入以下格式的视频源：

### 1. TXT 格式（常见通用格式）
```text
央视频道,#genre#
CCTV-1,http://<stream-url-1>
CCTV-1,http://<stream-url-2>
CCTV-2,http://<stream-url-3>

卫视频道,#genre#
湖南卫视,http://<stream-url-4>
浙江卫视,http://<stream-url-5>
```

### 2. M3U 格式（支持 EPG 与台标）
```text
#EXTM3U x-tvg-url="http://<server-ip>:8080/epg/epg.xml.gz"
#EXTINF:-1 tvg-id="CCTV-1" tvg-name="CCTV-1" tvg-logo="https://example.com/logo/cctv1.png" group-title="央视频道",CCTV-1
http://<stream-url-1>
#EXTINF:-1 tvg-id="CCTV-1" tvg-name="CCTV-1" tvg-logo="https://example.com/logo/cctv1.png" group-title="央视频道",CCTV-1
http://<stream-url-2>
```

### 3. JSON 格式（结构化格式）
```json
[
  {
    "group": "央视频道",
    "name": "CCTV-1",
    "title": "CCTV-1",
    "logo": "https://example.com/logo/cctv1.png",
    "number": "1",
    "uris": [
      "http://<stream-url-1>",
      "http://<stream-url-2>"
    ]
  }
]
```

---

## 🌐 局域网远程配置

1. 在电视端打开应用，按遥控器 **【菜单键 MENU】** 打开设置；
2. 选择 **【远程配置】**，屏幕将弹出提示与二维码，并显示局域网服务地址（例如 `http://192.168.1.100:34567`）；
3. 确保手机或电脑连接在同一 WiFi/局域网下，扫码或直接在浏览器中输入该地址；
4. 在网页中直接粘贴新的视频源地址或内容，点击提交即可实时推送并更新至电视端。

---

## 📦 安装方式

1. **U 盘安装**：将编译好的 `app-release.apk` 拷贝至 U 盘，插在电视或机顶盒上通过文件管理器安装。
2. **ADB 命令行无线安装**：
   ```shell
   # 连接电视机顶盒
   adb connect <电视IP>:5555

   # 安装 / 覆盖安装 APK
   adb install -r app-release.apk
   ```

---

## 🛠️ 编译与构建

### 环境要求
- **JDK**：OpenJDK 17 或兼容版本
- **Android SDK**：API Level 35 (Build-tools 35.0.0, Min SDK 21)
- **Gradle**：8.7+ (项目内置 gradlew 包装器)

### 构建命令
```shell
# 克隆仓库
git clone https://github.com/liumeng1201/myiptv.git
cd myiptv

# 构建 Debug 版本
./gradlew assembleDebug

# 构建 Release 签名版本（项目已内置 mytv.jks 签名配置）
./gradlew assembleRelease
```
编译产物输出位置：`app/build/outputs/apk/release/app-release.apk`。

---

## 📄 免责声明

1. 本项目仅为音视频流媒体播放与界面交互工具，**本身不提供、不制作、不传播任何视音频内容或直播服务器**。
2. 用户在应用内添加、订阅的所有直播源均由用户自行负责其来源的合法性与合规性。
3. 请在遵守当地法律法规的前提下使用本项目。
