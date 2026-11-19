# myiptv

电视视频播放软件，支持自定义视频源与局域网订阅。

[myiptv 项目主页](https://github.com/liumeng1201/myiptv)

## 使用说明

* 遥控器中键 / 触屏单击：打开视频列表
* 遥控器右键 / 触屏双击：打开设置
* 遥控器左键 / 触屏长按：打开节目单
* 遥控器返回键：关闭视频列表 / 设置
* 聚焦视频标题时，按右键收藏 / 取消收藏
* 打开设置后，选择远程配置，扫描二维码或直接访问 `http://<电视IP>:34567` 即可通过网页配置视频源
* 默认遥控器下键 / 触屏下滑切换到下一个视频（可在设置中开启换台反转）

## 视频源配置格式

* **txt 格式**：
    ```text
    组名,#genre#
    频道名称,视频地址
    ```
* **m3u 格式**：
    ```text
    #EXTM3U x-tvg-url="http://<server-ip>:8080/epg/epg.xml.gz"
    #EXTINF:-1 tvg-id="" tvg-name="CCTV-1" tvg-logo="" group-title="央视频道",CCTV-1
    http://<stream-url>
    ```
* **json 格式**：
    ```json
    [
      {
        "group": "央视频道",
        "name": "CCTV-1",
        "title": "CCTV-1",
        "logo": "",
        "number": "1",
        "uris": [
          "http://<stream-url>"
        ]
      }
    ]
    ```

## 安装方式

建议通过 ADB 直接安装至机顶盒或电视：

```shell
adb install app-release.apk
```

## 编译构建

项目内置自签名证书 `app/mytv.jks`，克隆后可直接通过 Gradle 构建：

```shell
# 构建 Debug 版本
./gradlew assembleDebug

# 构建 Release 签名版本
./gradlew assembleRelease
```
构建产物输出位于 `app/build/outputs/apk/` 目录下。
