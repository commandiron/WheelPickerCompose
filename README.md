# WheelPickerCompose [![](https://jitpack.io/v/commandiron/WheelPickerCompose.svg)](https://jitpack.io/#commandiron/WheelPickerCompose)

## Usage
|Picker|Usage|
|------|-----|
|<img src="https://user-images.githubusercontent.com/50905347/189122534-72e2140f-e5cf-414c-897d-36e6876555a1.gif" width="256" height="256">|```WheelDateTimePicker { snappedDate, snappedTime -> }```|
|<img src="https://user-images.githubusercontent.com/50905347/189132165-6d2611a2-4f41-467d-900a-34d87dbbc68c.gif" width="256" height="256">|```WheelDatePicker { snappedDate -> }```|
|<img src="https://user-images.githubusercontent.com/50905347/189132165-6d2611a2-4f41-467d-900a-34d87dbbc68c.gif" width="256" height="256">|```WheelDatePicker { snappedDate -> }```|

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
WheelTimePicker { snappedTime -> }
WheelTextPicker(texts = listOf("Test 1", "Test 2", "Test 3")) { snappedTime -> }
WheelPicker(count = 3) { index, snappedIndex -> }
```
