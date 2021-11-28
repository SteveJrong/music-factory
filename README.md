# music-factory

## 介绍

音频文件元数据信息补全、音频批量转码工具。

## 说明

* 为何要做这样一个工具？
    * 音乐是疲劳的缓释剂，再忙也要照顾好自己，包括情绪。所以很爱听音乐。
    * 于是从很多平台下载了各种有损和无损音乐，有损的WMA、MP3-192K/320K、无损的WAV、FLAC、APE，甚至是DSD，应有尽有。
    * 但是：
        * 由于音频资源的来源不确定，这就导致了很多时候，下载下来的音频文件，有的缺专辑封面、有的专辑封面太小看不清、有的缺歌曲名称、有的缺演唱者等等。
        * 有人说，能听就行了，哪那么多麻烦事。
        * 此时突然想听Taylor Swift的歌，然后去播放器里根据艺术家去找Taylor Swift，然而发现根本没有！最后苦苦寻找，原来是被播放器分类到了`未知艺术家`一栏。
        * 好不容易啥信息都显示的很全，而偏偏专辑封面模糊的像一坨屎，瞬间没了欣赏音乐的心情。
* 目前实现的功能：
    * 音频文件内的`元数据信息填充补全`
      。包括歌曲标题、歌曲艺术家、歌曲所属的专辑名称、歌曲所属的专辑封面、歌曲内嵌歌词、歌曲所属专辑的艺术家、歌曲所属专辑的发布时间、歌曲所属专辑的描述、歌曲所属专辑的语言类型和歌曲所属专辑的版权信息。
    * 对音频文件内的元数据信息填充补全时，是通过过滤器来判断哪些文件是需要做元数据信息补全的。此`元数据属性的判断依据`，可通过配置文件`灵活配置`。
    * 针对音频文件内原有内嵌的专辑封面尺寸过小而导致模糊的问题，也能够重新`替换尺寸更大更清晰的专辑封面`。
    * 音频文件批量转码（业务改造中，目前未开放）。
* 音频文件元数据的获取：
    * 是基于第三方在线音乐服务提供商暴露的API接口实现的。

## 快速开始

* 使用本工具，有两种途径，分别是`运行已编译好的Jar包`和`自行编译Jar包并运行`。
* 工具基于Java开发。要运行工具，请安装并配置`Java（v1.8）`环境变量；要自行编译并运行工具，请安装并配置`Java（v1.8）`及`Gradle（v6.6）`环境变量。

### 使用已编译好的Jar包【基础】

* 适用场景：
    * 默认配置足够实现需求的用户。
    * 不想自己编译Jar包的用户。
* 基于的默认配置：
    * 原始音频文件存放目录: /Users/stevejrong/Desktop/test
    * songTitleFilter:        已开启
    * songArtistFilter:    已开启
    * albumNameFilter:    已开启
    * albumPictureFilter:    已开启
    * songLyricsFilter:    已开启
    * albumArtistFilter:    已开启
    * albumPublishDateFilter:    已开启
    * albumDescriptionFilter:    已开启
    * albumLanguageFilter:    已开启
    * albumCopyrightFilter:    已开启
* 快速上手指南：
    1. 将要进行元数据信息补全的音频文件，复制一份到别处作为`备份`，以免数据损坏无法恢复。
    2. 在电脑任意位置`新建`一个`空文件夹`，将要进行元数据信息补全的音频文件拖入其内。
    3. 打开命令提示符（for Microsoft Windows）/终端（for macOS and Linux）窗口，切换到项目`distribution`目录下。
    4. 执行`java -jar music-factory-XXX-XXXXXX-all.jar` 命令，启动工具。
    5. 根据提示，输入序号1，设置原始音频文件的存放目录。将目录位置复制到窗口中，并按回车键确认。
    6. 根据提示，输入序号2，分析音频文件元数据信息。
    7. 分析完成后会显示有多少个文件需要补全。根据提示，输入Y或y，补全音频文件元数据信息。

### 自行编译Jar包【高级】

* 适用场景：
    * 默认配置无法实现需求，需要自定义配置的用户。
* 基于的默认配置：
    * 原始音频文件存放目录: /Users/stevejrong/Desktop/test
    * songTitleFilter:        已开启
    * songArtistFilter:    已开启
    * albumNameFilter:    已开启
    * albumPictureFilter:    已开启
    * songLyricsFilter:    已开启
    * albumArtistFilter:    已开启
    * albumPublishDateFilter:    已开启
    * albumDescriptionFilter:    已开启
    * albumLanguageFilter:    已开启
    * albumCopyrightFilter:    已开启
* 快速上手指南：
    1. 将要进行元数据信息补全的音频文件，复制一份到别处作为`备份`，以免数据损坏无法恢复。
    2. 在电脑任意位置`新建`一个`空文件夹`，将要进行元数据信息补全的音频文件拖入其内。
    3. 打开命令提示符（for Microsoft Windows）/终端（for macOS and Linux）窗口，切换到项目的根目录目录下。
    4. 根据实际情况，更改位于`src/main/resources`目录下的`custom-config.yml`配置文件相关配置。
    5. 执行`gradle releaseJar`命令，对项目进行编译和打包。生成的Jar包，位于`distribution`目录下。
    6. 打开命令提示符（for Microsoft Windows）/终端（for macOS and Linux）窗口，切换到项目`distribution`目录下。
    7. 执行`java -jar music-factory-XXX-XXXXXX-all.jar` 命令，启动工具。
    8. 根据提示，输入序号1，设置原始音频文件的存放目录。将目录位置复制到窗口中，并按回车键确认。
    9. 根据提示，输入序号2，分析音频文件元数据信息。
    10. 分析完成后会显示有多少个文件需要补全。根据提示，输入Y或y，补全音频文件元数据信息。

## 结构

### 代码结构

```text
com.stevejrong.music.factory.boot ------------------------------------------------- 启动入口类所在的包 
com.stevejrong.music.factory.common ----------------------------------------------- 通用类和工具类所在的包 
com.stevejrong.music.factory.config ----------------------------------------------- 综合配置所在的包 
com.stevejrong.music.factory.provider.service.music ------------------------------- 主要核心业务服务提供根包 
 |__com.stevejrong.music.factory.provider.service.music.analyzing ----------------- 原始音频文件分析服务所在的包 
 |__com.stevejrong.music.factory.provider.service.music.formatConversion ---------- 音频文件格式批量转换服务所在的包 
 |__com.stevejrong.music.factory.provider.service.music.metadata.resolver --------- 音频文件解析和元数据信息存储服务所在的包
com.stevejrong.music.factory.spi.music - 主要核心业务服务接口根包 
 |__com.stevejrong.music.factory.spi.music.bean/bo/vo - PoJo类所在的包 
 |__com.stevejrong.music.factory.spi.service.music - 主要核心业务服务接口所在的包 
     |__com.stevejrong.music.factory.spi.service.music.analyzing - 原始音频文件分析服务接口所在的包 
     |__com.stevejrong.music.factory.spi.service.music.filter - 过滤器接口所在的包
     |__com.stevejrong.music.factory.spi.service.music.formatConversion - 音频文件格式批量转换服务接口所在的包
     |__com.stevejrong.music.factory.spi.service.music.metadata.resolver - 音频文件解析和元数据信息存储服务接口所在的包
```

### 资源文件结构

```text
src/main/resources ------------------------------------------------- 资源文件根目录
 |__src/main/resources/img ----------------------------------------- 图片存储目录
     |__src/main/resources/img/default_album_pic.png --------------- 歌曲专辑默认封面图片
src/main/resources/application-bean.xml ---------------------------- Spring XML配置文件
src/main/resources/base-config.yml --------------------------------- 项目基础YML/YAML配置文件
src/main/resources/custom-config.yml ------------------------------- 动态配置（用户自定义配置）文件
src/main/resources/logback.xml ------------------------------------- 日志记录组件配置文件
build.gradle ------------------------------------------------------- Gradle构建配置文件
config.gradle ------------------------------------------------------ Gradle基础配置文件
settings.gradle ---------------------------------------------------- Gradle综合设置文件
```

## 版本信息

当前主版本：2.0.1-SNAPSHOT

#### 后续开发计划

- 架构优化，插件化开发，多平台兼容支持
- 更多第三方API对接
- 支持多种音频格式互转，加速音频文件的转换（如GPU加速转换）
- 对于更为复杂的情况下，音频文件元数据补全，采用AI音频分析和智能补全方案
- 常听歌曲辨别和记忆，从常听歌曲中进行格式一键转换，无需一首首歌曲勾选
- 控制台呈现改为窗体呈现（跨平台。实现的技术选型列表：Electron、Qt、Swing），提升使用体验
- 增加基本的音频播放器功能和音频元数据编辑功能
- 最终要基本实现：音频播放器 + 一键补全音频元数据 + 一键格式转换
- 支持电影元数据修改和转换

#### 更新日志

2020/01/17：

1. 对接QQ音乐提供商，支持音频文件信息补全（补全歌曲名称、歌曲演唱者、专辑名称和专辑图片）
2. gradle依赖包版本号提取

2020/01/18：

1. 音频文件信息补全功能若干Bug修复，支持特殊文件名
2. 日志保存优化
3. 异常处理优化
4. Gradle配置文件优化

2020/01/19：

1. 音频文件信息补全功能若干Bug修复
2. 搜索专辑图片无结果时，设置默认的专辑图片
3. 支持FLAC批量转换为Apple Lossless（m4a）格式

2020/01/21：

1. 去掉无用配置，启动入口放开注释掉的方法
2. RADEME.md文件补充更新

2021/01/10：

1. 完善音频文件格式转换功能，支持WAV、M4A和APE到FLAC的转换
2. 系统配置类优化，根据功能进行拆分
3. 其他代码优化

2021/11/08：

1. 修复音频信息补全时，标签残缺的MP3音频文件，读取ID3vX标签NPE的问题。

2021/11/22：

1. 切换到develop-2.0.0分支。
2. 代码重构，使用过滤器，来优化音频文件分析逻辑以及元数据信息抓取逻辑（未完成，待收尾）。
3. 删除腾讯QQ音乐在线音乐服务提供商的相关API，用酷狗音乐在线音乐服务提供商相关API代之。

2021/11/28：

1. 已完善音频文件元数据信息分析及补全。
2. 日志方案由Spring AOP改为SLF4J+Logback方案。
3. 已支持Gradle一键打包（在项目根目录下，执行`gradle shadowJar`即可一键打包）。
4. 修复Gradle打包不正确的Bug。
5. 支持自定义原始音频文件存放目录。
6. Gradle的Jar包构建脚本优化。
7. 全新发布2.0.0-RELEASE版本。
8. 切换到develop-2.0.1分支。

2021/11/29：

1. 修复保存专辑图片元数据信息时，第三方在线音乐服务平台没有专辑图片时，没有正确设置默认专辑图片的Bug。
2. 文件工具类中的图片转字节数组方法性能优化。