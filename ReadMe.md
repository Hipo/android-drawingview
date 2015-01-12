# DrawingView

DrawingView is an android library which provides a customizable canvas view which you can paint on it and save



#Usage
You can simply add this view into your layout file set parameters and start drawing

  - stroke width
  - color
  - background

```

       <com.hipo.drawingview.DrawingView
               android:layout_width="match_parent"
               android:layout_height="match_parent"
               app:paint_color="@android:color/white"
               app:stroke_width="10"/>
```

Then you call the ```
                          drawingView.save()
                  ``` method to save your art


#Installation
###### These steps are required for publishing aar file into your local repository.For further detail see https://www.linkedin.com/pulse/publishing-aar-local-repository-baris-emre-efe?trk=prof-post
 - Clone library into your workspace
 - Modify build.gradle file and change the repository location
 - Run
 - ```sh
        ./gradlew uploadArchives
```
from Android Studio's terminal

 - Add this dependency into your project
   ```
        compile 'com.hipo.drawingview:library:+'
```


### Credits
DrawingView is brought to you by Baris Emre Efe and the Hipo Team


