# SuperProgressWheel
 **Code refactoring!** A progress wheel for Android, support roatate animation and progress text.

## ScreenShot
<img src="https://raw.githubusercontent.com/li-yu/SuperProgressWheel/master/Screenshot.png" alt="Drawing" width="300px" />

## Demo apk
[Download](https://github.com/li-yu/SuperProgressWheel/blob/master/sample-debug.apk?raw=true)

## Installation
Add to module *build.gradle*:

`compile 'com.liyu.widget:ProgressWheel:1.0.0'`


Library is available in jcenter repository.

## Include View in your layout

``` xml
<com.liyu.widget.SuperProgressWheel
        android:id="@+id/spw"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
		spw:roundColor="#9d55b8"
        spw:roundProgressColor="#a9b7b7"
        spw:roundWidth="10dp"
        spw:maxProgress="100"
        spw:displayStyle="DRAWABLE"
        spw:drawablePadding="20dp"/>
```

## Set Listener in java

```java
spw.setOnProgressListener(new ProgressWheelView.onProgressListener() {
    @Override
    public void onCompleted(View v) {
                
    }
});
```

## XML attributes
| Name | Type | Default | Description |
|:----:|:----:|:-------:|:-----------:|
|roundColor|Color|Color.BLUE| Progress bar background color |
|roundProgressColor|Color|Color.GRAY| Progress color |
|roundWidth|dimen|35| Thicknes of progress bar |
|textColor|Color|Color.BLACK| Progress text color |
|textSize|dimen|45| Progress text size |
|maxProgress|integer|100| Max Progress |
|displayStyle|enum|NONE| NONE/TEXT/DRAWABLE |
|displayDrawable|reference|The built-in blue icon| Image resources |
|drawablePadding|dimen|0| The padding between image and progress bar |
|rotateDuration|integer|1600| Rotate speed: The image rotate duration |

## TODO
- Support more animations, now only support rotation.
- Waiting for your suggestions.

## Thanks
- [Android 高手进阶之自定义View，自定义属性（带进度的圆形进度条）](http://blog.csdn.net/xiaanming/article/details/10298163)
- [android-library-gradle-publish](https://github.com/LiangMaYong/android-library-gradle-publish)
