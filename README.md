# WheelPickerCompose [![](https://jitpack.io/v/commandiron/WheelPickerCompose.svg)](https://jitpack.io/#commandiron/WheelPickerCompose)

Add Wheel Date - Time Picker in Android Jetpack Compose.

## Usage
|Picker|Usage|
|------|-----|
|<img src="https://user-images.githubusercontent.com/50905347/201921058-82c7813d-b9c4-448c-a296-62465845152d.gif" width="256" height="256">|```WheelDateTimePicker { snappedDateTime -> }```|
|<img src="https://user-images.githubusercontent.com/50905347/201921069-14a8410b-5952-4130-80b0-71f9ca286a93.gif" width="256" height="256">|```WheelDatePicker { snappedDate -> }```|
|<img src="https://user-images.githubusercontent.com/50905347/201921066-b94b9fcd-c447-4b01-833f-03600e20ed44.gif" width="256" height="256">|```WheelTimePicker { snappedTime -> }```|


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
        implementation 'com.github.commandiron:WheelPickerCompose:1.1.0'
}
```

## Features

<table>
<tr>
<td>
            
```kotlin  
WheelDateTimePicker(
    startDateTime = LocalDateTime.of(
        2025, 10, 30, 5, 0
    ),
    minYear = 1950,
    maxYear = 2050,
    size = DpSize(200.dp, 100.dp),
    backwardsDisabled = true,
    textStyle = MaterialTheme.typography.titleSmall,
    textColor = Color(0xFFffc300),
    selectorProperties = WheelPickerDefaults.selectorProperties(
        enabled = true,
        shape = RoundedCornerShape(0.dp),
        color = Color(0xFFf1faee).copy(alpha = 0.2f),
        border = BorderStroke(2.dp, Color(0xFFf1faee))
    )
){ snappedDateTime -> }
```
</td>
<td>  
    
<img src="https://user-images.githubusercontent.com/50905347/190863946-3b095696-a4be-4d29-ad01-9489bb2ddb27.gif" width="256" height="256">
    
</td>
</tr>
</table>

## Todo ✔️
   * seamless text alpha animation
