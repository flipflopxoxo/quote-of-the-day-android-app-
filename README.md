# Quote of the Day App
An Android app that gives you a random quote, and explore through a list of quotes

The app has two tabs to choose from, the Random tab and Explore tab.
## Random tab
The Random tab displays a random quote in the middle of the page. You can refresh the quote with the refresh button on the bottom right corner of the page.
![The Random page shows you a random quote](screenshots/random_tab.png)

## Explore tab
The Explore tab displays a list of quotes that the user can browse through. More data will be loaded as the user scrolls further down the list. In the event that the list loading fails, there will be an option to retry.
![The Explore page shows you a list of quotes](screenshots/explore_tab.png)
![There is a text button on the list if the loading of data fails](screenshots/explore_with_failure.png)

## Tools and libraries used
- Jetpack Compose and Material 3 Components - for creating a clean and responsive user interface
- Hilt - for compile-time depenedency injection, especially view models
- Retrofit, Gson - for making network requests to the Favq API
- Paging library - for dynamic loading of the quote list page as the user scrolls through the code
- MockK - for createing mock dependencies in unit tests
- Coroutines - for handling concurrency in a comprehensive manner
