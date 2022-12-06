# CM3110Coursework

Rory Cameron 2002511

FILMIFY
A movie recommendation app for when you want a film just like one you have seen before!

Key User Functionalities of this App
-	A user can input a movie they desire to see recommendations based off
-	A user can view movie recommendations in a Spotify playlist style format
-	A user can watch a trailer of each recommended movie
-	A user can save movies to a favourites list
-	A user can view all the movies they have saved
-	A user can delete movies from their favourites list
-	A user can easily navigate between each page of the app
-	A user can view and edit their favourites list even without an internet connection

![image](https://user-images.githubusercontent.com/72762899/205931100-1a67b2d2-9137-455e-ad08-30bbd094287b.png)
- Movie Search Activity
- Widgets Used:
- A textview was used to allow a user to enter a desired movie, this was used as it allows users to search any movie they would like, and easily has input validation implemented
- A button was used to allow the user to search for a movie, Using an event listener, the button sends the user inputted movie to the Movie Reccomendation activity aswell as starts it through an Intent when the button is pressed
- A button was also used to allow the user to navigate to the Movie Favourites Activity
- Layout Used: Constraint Layout

![image](https://user-images.githubusercontent.com/72762899/205932632-22335f43-3299-45c2-92ec-ddc8cc00a13b.png)
- Movie Reccomendation Activity
- Widgets Used:
- A textview is used to display the name of the reccommended movie to the user
- Buttons were used to navigate to the next and previous movies, These were used as allowed for easy implementation with no duplication of code
- A button was used to add a movie to a users favourites list, this was used as the button can be disabled if a movie is already in their favourites
- A button was used to allow the user to navigate to both the Movie Search Activity and Movie Favourites Activity
- A WebView was used to display a video to the user, This was used as it allowed for easy implementation of video playback of any source, in this cause a youtube URL
- Layout Used: Constraint Layout

![image](https://user-images.githubusercontent.com/72762899/205932664-d7d2be16-6b06-4067-a100-aad9a0dc3e5a.png)
- Movie Favourites Activity
- Widgets Used:
- A RecyclerView was used to display a users favourites list, Each item in the recyclerview was a card view with a textview inside displaying the movie name set in a relative layout
- A button was used to allow the user navigate back to the Movie Search Page
- A user can delete movies from their favourites list by using a Swipe mobile specific gesture. Within this implementation, the user is able to swipe lift or left on any item within the recylerview which will in turn delete it from the recyclerview as well as delete that movie from the database
- Layout Used: Constraint Layout

![image](https://user-images.githubusercontent.com/72762899/205932702-4d00a133-01d1-49fb-9f24-fbb036bf3f71.png)
- More Movie Info Activity
- Widgets Used:
- A textview was used to display the movie name to the user
- A Webview was again used to display a video to the user
- A second textview was used to display a brief movie description to the user
- A button was used to allow the user to navigate back to the Movie Favourites Activity

![image](https://user-images.githubusercontent.com/72762899/205932727-02787638-e0c3-4824-9e9f-b98182be26bc.png)
- This Image shows how a user would navigate through the app between pages

Data about movie reccomendations was aquired from the Tastedive API
https://tastedive-api-documentation.readthedocs.io/en/latest/

Data was recieved in JSON as follows:
![image](https://user-images.githubusercontent.com/72762899/205941578-185943f4-09f5-43e8-8b83-d4da18ff5767.png)

Details of Users faouvrited movies were stored in a Room DB, local to each user, the structure of how this data was stored is as follows:
- PK Integer movieID (auto generated = true)
- String movieName
- String movieDescription
- String movieUrl

Testing:
- TEST1 - JSON Data Retrieved Successfully
  - ![image](https://user-images.githubusercontent.com/72762899/205944981-bd6d2be4-ce27-44cd-8aca-a663a65524d3.png)
  - Expected Result - Array of dictionary elements containing movies
  - Actual Outcome:
  - ![image](https://user-images.githubusercontent.com/72762899/205945266-fd615c5f-5af2-4d58-bf99-514552e48fb6.png)
TEST1 - SUCCESS

On reflection, I enjoyed the development and implementation of this app and coursework. There were numerous parts which I thought went well, All functionality outlined within the design and thought and thought of whilst planning were managed to successfully be fully implemented. Having all functionalities divided into subtasks from the get-go, it was very easy to work on each part at a time without it impacting the rest of the app’s functionality. This led to very fast and efficient development, especially when creating the database and its surrounding components. As well as this, looking specifically, I thought the implementation of the API went well. I initially had some problems whilst developing this due to some APIs not working in the way I intended too. It also took me significant time to get the API movie recommendation data to display in the format I wanted to, as well as having programming difficulties in getting the data to display as intended, but after fixing these issues I am especially happy with how the Movie recommendation data is displayed. One thing that did not go well was designing the UI, due to time constraints, less time was spent on developing the front end compared to the backend functionality, If this app was to be further developed, a significant amount of time would be spent furthering the user interface. While users can successfully move across the app, it is also through button components, In future I would have liked to use a navigation bar and navigation graphs. Finally, I had mixed opinions on the Android Studio IDE, I found programming especially easy to do with this IDE, thanks to the code completer which finishes lines of code that u were going to type, especially length ones such as event listeners or constructors/getters and setters, However I did experience numerous issues with the Pixel 2 emulator, There were numerous times where it would crash or be unresponsive, even with no errors in code. This would force me to have to wipe all data from the emulator and restart the de, which was very frustrating and time consuming. However, overall, I thought the development of this app was relatively straightforward, thanks to a generally good development environment, as well as a good plan developed before starting the implementation.
