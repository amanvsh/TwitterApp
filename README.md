# Twitter Sample App 

This is a sample project that presents a modern approach to Android app development.

Functionality Included:
1. Login/SignUp:  
2. Timeline Screen: 1st tab : Which displays All Users tweets. 2nd Tab will dispalys user own tweets.
3. Compose Tweet: To post the tweet including text and image.
4. Logout.

## Instructions to test
1. Signup with valid email id. Eg: **abc@gmail.com** pwd: **123456**
2. Login with same details. After successful login you will land on user time line screen.   


The project tries to combine popular Android tools and to demonstrate best development practices by utilizing up to date tech-stack like Compose, Kotlin Flow and Hilt.

## Configurations

* Android Studio: Android Studio Iguana | 2023.2.1 Beta 1
* gradle: gradle-8.4-bin.zip
* compileSdk = 34
* minSdk = 24
* targetSdk = 34


## Description 

* UI 
   * [Compose](https://developer.android.com/jetpack/compose) declarative UI framework

* Tech/Tools
    * [Kotlin](https://kotlinlang.org/) 100% coverage
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) and [Flow](https://developer.android.com/kotlin/flow) for async operations
    * [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection
    * [Jetpack](https://developer.android.com/jetpack)
        * [Compose](https://developer.android.com/jetpack/compose) 
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) for navigation between composables
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that stores, exposes and manages UI state
    * [Coil](https://github.com/coil-kt/coil) for image loading
    
* Modern Architecture
    * Single activity architecture (with [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started)) that defines navigation graphs
    * MVVM
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation))
  
