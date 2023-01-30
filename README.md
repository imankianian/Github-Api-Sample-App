# Github-Api-Sample-App
This is a sample Android app that:
1. Displays users avatars and logins from https://api.github.com/users
2. Clicking on a user, displays the user's repositories and some details from https://api.github.com/users/{login}/repos. Clicking on a repository, transfers the user to the repository at github.com.
3. All data is saved on the device. So on next visits, the data is loaded in this order: loader &rarr; data from database &rarr; data from API

**Note**: Checkout the tdd branch for a test-driven development approach.

This sample is implemented using the following stack:
* Kotlin + Coroutines + StateFlow
* Jetpack Compose
* Repository pattern + MVVM + Single Activity
* Retrofit
* Hilt
* Coil
* Clean Architecture

Below is the diagram of the app architecture:

![app architecture](https://user-images.githubusercontent.com/47268306/209476221-8e6c0ddb-7faf-4ce3-81ca-6d919be621f1.svg)
