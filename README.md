# WheelPickerCompose [![](https://jitpack.io/v/commandiron/WheelPickerCompose.svg)](https://jitpack.io/#commandiron/WheelPickerCompose)

## Usage
|Picker|Usage|
|------|-----|
|<img src="https://user-images.githubusercontent.com/50905347/189122534-72e2140f-e5cf-414c-897d-36e6876555a1.gif" width="256" height="256">|```WheelDateTimePicker { snappedDate, snappedTime -> }```|
|<img src="https://user-images.githubusercontent.com/50905347/189132165-6d2611a2-4f41-467d-900a-34d87dbbc68c.gif" width="256" height="256">|```WheelDatePicker { snappedDate -> }```|
|<img src="https://user-images.githubusercontent.com/50905347/189132816-2644bcde-577c-4532-8070-a15203dff020.gif" width="256" height="256">|```WheelTimePicker { snappedTime -> }```|
|<img src="https://user-images.githubusercontent.com/50905347/189137035-2490d804-f0fc-471e-93ab-165bac1eebe6.gif" width="256" height="256">|```WheelTextPicker(texts = (1..6).map { it.toString() })```|
|<img src="https://user-images.githubusercontent.com/50905347/189134369-8c01dba5-4331-474d-8010-d3926c8fe669.gif" width="256" height="256">|```WheelPicker(count = 6) { index, snappedIndex ->```<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;```Card(Modifier.size(128.dp).padding(8.dp)) {}```<br/>```}```|


## Setup
1. Open the file `settings.gradle` (it looks like that)
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // add jitpack here 👇🏽
        maven { url 'https://jitpack.io' }
       ...
    }
} 
...
```
2. Sync the project
3. Add dependency
```groovy
dependencies {
        implementation 'com.github.commandiron:WheelPickerCompose:1.0'
}
```

## Features

<table>
    <tr>
        <td>
            
            ```kotlin  
            WheelDateTimePicker(
                size = DpSize(200.dp, 100.dp),
                textStyle = MaterialTheme.typography.titleSmall,
                textColor = Color(0xFFffc300),
                infiniteLoopEnabled = true,
                selectorEnabled = true,
                selectorShape = RoundedCornerShape(0.dp),
                selectorColor = Color(0xFFf1faee).copy(alpha = 0.2f),
                selectorBorder = BorderStroke(2.dp, Color(0xFFf1faee))
            ) { snappedDate, snappedTime -> }
            ```
            
        </td>
        <td>  
            <img src="art/spinwheel_2.gif" width="256" height="256">
        </td>
    </tr>
</table>
