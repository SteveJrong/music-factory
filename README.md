# music-factory

#### 介绍
音频文件元数据信息补全、音频批量转码工具 

#### 项目结构
```
com.stevejrong.music.factory ------------------------------------------------------ 项目根包 
com.stevejrong.music.factory.analysis --------------------------------------------- 音频分析相关（隶属于音频信息补全业务） 
com.stevejrong.music.factory.analysis.datasource ---------------------------------- 第三方数据源提供商API接口交互相关。用于在线搜索音乐信息 
com.stevejrong.music.factory.analysis.metadata ------------------------------------ 第三方数据源提供商API接口数据返回的数据解析器相关。针对不同的第三方数据源提供商来实现不同的API解析方式 
com.stevejrong.music.factory.boot ------------------------------------------------- 启动main()方法 
com.stevejrong.music.factory.common ----------------------------------------------- 通用类 
com.stevejrong.music.factory.logs ------------------------------------------------- 基于Spring AOP的业务模块日志处理 
com.stevejrong.music.factory.module ----------------------------------------------- 业务模块 
com.stevejrong.music.factory.transcode -------------------------------------------- 音频文件转码相关（隶属于音频文件转码业务)  
com.stevejrong.music.factory.util ------------------------------------------------- 通用工具类 
```

#### 说明
1. 由于开发时间仓促，仅实现了功能，但代码结构和实现方式还待优化。原本计划使用```Swing```开发，第一时间不允许，第二Swing没有很深入的接触，使用此技术开发会浪费时间
2. 目前实现的功能有：
 - 音频文件元数据信息补全功能，基于第三方音乐提供商API
 - 音频批量转码功能，基于ffmpeg命令行转码
3. 基于Microsoft Windows环境开发，可正常运行；其他环境未测试，不能保证功能正常，有时间会测试和修复
4. 为什么要开发：
    - 音乐叉烧一枚，于是本地存有不少音频文件，无损有损、WAV、FLAC各种格式都有。
    但是！
    - 这些资源的来源都参差不齐。有的音频元数据信息不全，导入到手机里搜不到对应的歌曲（因为被播放器分类到“未知”专辑里了），有的音频文件名有特殊符号，强迫症受不了，易读性也差
    - 本人有iPhone、Walkman和安卓手机。后两者还好，FLAC、WAV基本都能识别，但是iPhone真的让我犯了难（无损太大，iPhone 32GB根本不够放，我只能从几百首中一个个挑出来，再用Foobar2000
    转为ALAC格式，再用爱思助手导入进去，这已经够烦了，重点是音频文件的元数据不准确，导致有时候想搜一首想听的歌，就是搜不到，最后发现在最后的“未知”里……）

#### 如何使用
###### 先决条件
1. 下载并安装Java IDE。以下任意一种均可：
```
Visual Code（系统需要安装JDK并配置JDK环境变量，IDE中需要安装Java相关插件。将项目打开后，会自动提示安装哪些插件）
IntelliJ IDEA（无需安装和配置JDK变量，IDE中自带OpenJDK）
Eclipse（系统需要安装JDK并配置JDK环境变量）
```

2. 下载并解压Gradle插件。以下为下载地址：
```
https://services.gradle.org/distributions/gradle-4.5.1-all.zip
```
> 下载后无需配置系统环境变量，在IDE中设置Gradle的解压目录即可

###### 执行功能
1. 想批量补全本地文件夹中所有音频文件的元数据信息
☞ 指南：
    - 定位到项目中的以下类：
    ```
    com.stevejrong.music.factory.boot.MusicFactoryApplication
    ```
    - 执行此功能只需执行```Setup 1```和```Setup 2```的代码，将```Setup 3```的代码注释掉即可
    - 定位到项目中的以下配置文件：
    ```
    resources/application-bean.xml
    ```
    修改Bean名称为```analysisOriginalMusicFileModule```中```musicFileDirectory```属性的值，将此值改为电脑本地存储音频文件的文件夹路径
    - 再次定位到项目中的以下类，右键运行即可：
    ```
    com.stevejrong.music.factory.boot.MusicFactoryApplication
    ```
    > 执行期间会有两次日志生成，日志文件位于项目根目录的```log```文件夹下，分别会生成需要补全的音频文件和补全成功或失败的音频文件信息

2. 想批量转换本地文件夹中所有音频文件为另一格式
☞ 指南：
    - 定位到项目中的以下类：
    ```
    com.stevejrong.music.factory.boot.MusicFactoryApplication
    ```
    - 执行此功能只需执行```Setup 3```的代码，将```Setup 1```和```Setup 2```的代码注释掉即可
    - 定位到项目中的以下配置文件：
    ```
    resources/application-bean.xml
    ```
    修改Bean名称为```musicFormatConvertModule```中：
        - ```musicFileDirectory```属性的值，将此值改为电脑本地存储音频文件的文件夹路径
        - ```convertedMusicFileDirectory```属性的值，将此值改为转换后的音频文件存储的文件夹路径
    - 再次定位到项目中的以下类，右键运行即可：
    ```
    com.stevejrong.music.factory.boot.MusicFactoryApplication
    ```
    > 执行期间无日志生成。目前仅支持FLAC转换M4A（Apple Lossless）格式，使用ffmpeg命令行转换，电脑中必须要安装并配置好Ffmpeg
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
#### 版本信息
1.0

#### 开发计划
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