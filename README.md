# HelpMeSee
Android mobile app assistant for visually impaired people, developed as part of my BSc thesis in "Computer and Software Engineering".

Demo video: https://drive.google.com/file/d/1aS2_OGxDwcsUxPdTkoDwG2awWTpTd6n_/view?usp=sharing

Documentation can be found in my repository ["BT_Documentation"](https://github.com/mariusolariu/BT_Documentation/blob/master/Marius_Olariu_Bachelor_Thesis.pdf)

## Short Description

The application helps the visually impaired user to get to a certain destination by giving real-time 
voice instructions (e.g. “Continue straight”, “turn to right” etc.). The user can interact with the app by using 
the implemented Voice User Interface; as an extra feature – the user can find at any point on his journey his
current physical address and broadcast it to a list of friends (e.g. in case it needs help).
This application makes use of the cloud resources provided by Google Directions and Google Maps API, 
the support provided by Android platform for speech recognition, reverse geocoding and **most important** it uses an adaptation (by me) of Craig Reynolds' “Steering behaviors for autonomous characters” algorithm - [download my thesis to see how this was done] (https://github.com/mariusolariu/BT_Documentation/blob/master/Marius_Olariu_Bachelor_Thesis.pdf). The author has a paper with the aforementioned title for a curios mind. By using the aforementioned 
services the core functionality of the app (to provide directions guidance to the visually impaired user) is implemented.

