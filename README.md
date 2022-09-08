# WheelPickerCompose [![](https://jitpack.io/v/commandiron/WheelPickerCompose.svg)](https://jitpack.io/#commandiron/WheelPickerCompose)

## Usage
|Picker|Usage|
|------|-----|
|<img src="https://user-images.githubusercontent.com/50905347/189122534-72e2140f-e5cf-414c-897d-36e6876555a1.gif" width="256" height="256">|```WheelDateTimePicker { snappedDate, snappedTime -> }```|
|<img src="https://user-images.githubusercontent.com/50905347/189132165-6d2611a2-4f41-467d-900a-34d87dbbc68c.gif" width="256" height="256">|```WheelDatePicker { snappedDate -> }```|
|<img src="https://user-images.githubusercontent.com/50905347/189132816-2644bcde-577c-4532-8070-a15203dff020.gif" width="256" height="256">|```WheelTimePicker { snappedTime -> }```|
|<img src="https://user-images.githubusercontent.com/50905347/189133279-66dae6fe-de42-43f2-91de-fd9a84da58a8.gif" width="256" height="256">|```WheelTextPicker(texts = (1..6).map { it.toString() })```|
|<img src="https://user-images.githubusercontent.com/50905347/189134369-8c01dba5-4331-474d-8010-d3926c8fe669.gif" width="256" height="256">|```WheelPicker(count = 6) { index, snappedIndex ->```<br/>&nbsp;&nbsp;&nbsp;```Card(Modifier.size(128.dp).padding(8.dp)) {}```<br/>&nbsp;&nbsp;&nbsp;```}```|


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
```kotlin  
```
