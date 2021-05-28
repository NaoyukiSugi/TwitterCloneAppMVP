# TwitterCloneAppMVP

## Screens
Following User Timeline | Search View | Search Result
:--:|:--:|:--:
<img src="https://user-images.githubusercontent.com/7261910/119975414-c8643a00-bff0-11eb-8457-0efad6d0ae18.png" width="320px"> | <img src="https://user-images.githubusercontent.com/7261910/119975425-cbf7c100-bff0-11eb-9830-8217d54c8e4a.png" width="320px"> | <img src="https://user-images.githubusercontent.com/7261910/119975429-cd28ee00-bff0-11eb-93e6-fbd67ddb7791.png" width="320px">

## Spec
- Home Screen
  - Get and display list of tweets of users you are following
    - Uses Twitter API v1.1 and don't support pagination
- Search Result Screen
  - Get and display tweets based on search keywords
    - Uses Twitter API v2 and supports pagination
- Profile Screen
  - (Developping)
- Authentication (with Twitter account)
  - (Developping)
    - Currently, the Authorization string is read directly from the local environment into the Header

## Architecture
MVP Architecture

## Using libraries
- HTTP Client
  - Retrofit 2
- asynchronous
  - Coroutines
  - Flow
- DI
  - Dagger Hilt
- Jetpack Architecture Components
  - Navigation
  - Paging 3
- Testing
  - JUnit 5
  - mockito-kotlin
- Others
  - Gson
  - Glide
  - SwipeRefreshLayout
  - CircleImageView
  - OkHttp
