# WheelPickerCompose [![](https://jitpack.io/v/commandiron/WheelPickerCompose.svg)](https://jitpack.io/#commandiron/WheelPickerCompose)

## How it looks

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

## Usage
```kotlin  
WheelDateTimePicker { snappedDate, snappedTime -> }
WheelDatePicker { snappedDate -> }
WheelTimePicker { snappedTime -> }
WheelTimePicker { snappedTime -> }
```
