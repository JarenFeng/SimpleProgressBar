###### 欢迎关注我

GitHub: https://github.com/GeekTR

---

# Simple ProgessBar

我这里集成了一些简单的ProgressBar，继承自官方的ProgrssBar。

他使用起来非常简单。

#### 本项目不支持在eclipse中编译，希望各位童鞋尽块转到Android Studio

* Demo运行时效果图.
    <div class='row'>
        <img src='http://oagaawgkn.bkt.clouddn.com/demo.gif' width="500px" style='border: #f1f1f1 solid 1px'/>
    </div>

# 使用方式

#### 中央库依赖

项目已经发布到了Jcenter，可以在Maven或者Gradle下可如下直接引入:

最新版版本号: `1.0.1`

Android Studio引用:

Step 1.Add it in your root build.gradle at the end of repositories:

```
allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
```

Step 2. Add the dependency

```
dependencies {
            compile 'com.github.GeekTR:SimpleProgressBar:265cff0d08'
    }
```

maven引用：

Step 1. Add the JitPack repository to your build file

```xml

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

```

Step 2. Add the dependency


```xml

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

```

#### 配置

HorizontalProgressBar有14个参数可配置:

* Reach bar的颜色

    默认: 0xFFFF0000，对应的属性为tr_reach_color。

* Reach bar的高度

    默认，`2dp`，对应的属性为tr_reach_height。

* UnReach bar的颜色

    默认 0xFF0000FF，对应的属性为tr_unreach_color。

* UnReach bar的高度

    默认，`2dp`，对应的属性为tr_unreach_height。

* 显示百分比的字是否可见

	默认, 可见 ,对应的属性为tr_text_visible。

* 显示百分比的字颜色

    默认，0xFF00FF00 ，对应的属性为tr_text_color。

* 显示百分比的字大小

	默认, 14sp ,对应的属性为tr_text_size。

* 显示百分比的字颜色与周围的间距

    默认，3dp ，对应的属性为tr_text_padding,tr_text_padding_left,tr_text_padding_right,tr_text_padding_top,tr_text_padding_bottom。若
	tr_text_padding和后面四个同时设置，则以后四个为准。

* 字的显示模式

    默认，Percentage，对应的属性为tr_text_show_model。分别有Percentage，Fraction和Other三种模式。Percentage表示以百分数显示，Fraction表示以分数的形式显示，Other则表示用户自定义，用户需设置ProcessTextAdapter来自定义显示内容。
    
* 字的显示位置

    默认，Center，对应的属性为tr_text_location。分别有Center,RightTop,LeftTop,RightBottom和LeftBottom五种位置。Center表示字显示在进度条中间，LeftTop表示字显示在进度条左上...

##### xml中配置示例

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin">

    <cn.geektang.simpleprogressbar.widget.HorizontalProgressBar
        android:id="@+id/hpb_1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:progress="50"
        app:tr_reach_color="@android:color/holo_blue_light"
        app:tr_reach_height="3dp"
        app:tr_text_color="@android:color/holo_red_dark"
        app:tr_text_location="Center"
        app:tr_text_padding_bottom="0dp"
        app:tr_text_padding_left="5dp"
        app:tr_text_padding_right="5dp"
        app:tr_text_padding_top="0dp"
        app:tr_text_show_model="Percentage"
        app:tr_text_size="16sp"
        app:tr_text_visible="true"
        app:tr_unreach_color="@android:color/holo_green_light"
        app:tr_unreach_height="1dp"/>
</LinearLayout>

```

### 也可以在java代码中配置

```java
mProgressBar.setReachColor(Color.RED);
mProgressBar.setProgress(40);
mProgressBar.setMax(200);
mProgressBar.setReachHeight(60);
mProgressBar.setUnReachColor(Color.BLUE);
mProgressBar.setUnReachHeight(50);
mProgressBar.setTextPositon(HorizontalProgressBar.CENTER);
mProgressBar.setTextPadding(10);
```

CircleProgressBar有10个参数可配置:

* 和HorizontalProgressBar相同的参数

    除了tr_text_location，tr_text_paing及tr_text_paing相关的四个属性外，其余的HorizontalProgressBar均适用于CircleProgressBar。

* Reach bar的起始角度

    默认 , 0° ，对应的属性为tr_start_angle。

* Circle的半径

    默认 , 30dp ，对应的属性为tr_radius。

##### xml中配置示例

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin">

    <cn.geektang.simpleprogressbar.widget.CircleProgressBar
        android:id="@+id/cpb_1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:progress="0"
        app:tr_reach_color="@android:color/holo_orange_light"
        app:tr_reach_height="3dp"
        app:tr_text_color="@android:color/black"
        app:tr_text_padding_top="5dp"
        app:tr_radius="50dp"
        app:tr_start_angle="0"
        app:tr_text_show_model="Other"
        app:tr_text_size="14sp"
        app:tr_unreach_color="@android:color/holo_blue_bright"
        app:tr_unreach_height="2dp"/>
</LinearLayout>

```

### 与HorizontalProgressBar相同，也可在代码中设置属性


### 其他配置

*  使用自定义显示方式时需要设置setProcessTextAdapter()方法，不然系统会抛出异常。


# 联系方式和问题建议

* 邮箱: tr@geektang.cn
* QQ : 1045621558
