[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/x6ckGcN8)

[PA Write Up](https://markefontenot.notion.site/PA-05-8263d28a81a7473d8372c6579abd6481)
# 3500 PA05 Project Repo
## Program Pitch
Jay's Java Journal is designed to help users keep track of their day with ease. It's an application that is designed
to allow to user to have full control and optimization of weeks worth of planning in the palm of their hands. Jay's
Java Journal is the go-to application for people who want a concise and easy way to plan out their week. Through this
application, you will be able ot build great new habits and break old habits that may spawn from a lack of planning by
allowing you to set obtainable goals and keeping track of what you need to do!

Features:
- An easy-to-understand display that is sorted by week for user clarity. 

- Multiple different types of Entries for you to add to your week. These entries are easy to change, delete, and can be
put in a custom order by the user

- An easy way to save your weeks to keep better track of your weeks.

- Customizable settings that allow you to name your week, set limits in the amount of entries you want, set the start 
day of a week and the ability to show Tasks on your sidebar if you desire.

- The ability to run the application in multiple different ways, Including Jar Files!

## SOLID Principle Explanation
- Single Responsibility Principle
An instance of Single Responsibility Principle can be found within model with BujoReader and BujoWriter classes. these
classes are designed to do a single purpose only. For example, When the BujoReader is instantiated, it is used for a 
singular purpose, to read a given bujo file and reads the information to a source. The BujoWriter is also a good example 
because its only job is to create a new bujo using the given information. These two classes fulfill their function and 
have no excess functions outside their set purpose which displays proper use of Single Responsible Principle.

- Open-Closed Principle
A good example of the open-closed principle in our implementation is the Controller Interface. The controller leaves a 
lot of room to be expanded upon, only having the simple run method that can access the model and view of the journal. 
If a new developer opened our implementation, they could make a new Controller and design it anyway they want because
they would only need the run method for the method to be considered a controller and the JournalController wouldn't be
affected. This also applies to the Prompt and Entry abstract classes. A new developer would always be able to make new
prompts or entries. This displays proper use of the Open-Closed Principle.

- Liskov's Substitution Principle
A good example of Liskov's Substitution Principle is the Entry Abstract Class. an Entry can represent two different
kinds of entries, Tasks and Event's. There are multiple points throughout our implementation where neither of these are
called, instead calling Entry. You can substitute an Entry with a Task or an Event and our implementation wouldn't break.
this displays proper use of Liskov's Substitution principle.

- Interface Segregation Principle
A good example of Interface Segregation Principle is the GUIView. the GUIView contains method that every GUI 
implementation of the journal. There are no methods that would be left out if a user wanted to expand on the controller
and gives the user a perfect outline of what they would need for the journal. This shows proper use of the Interface 
Segregation Principle.

- Dependency Inversion Principle
A good example of Dependency Inversion Principle are the two abstract classes, Entry and CreationPrompt. Both of the 
subclasses have similar identical code so instead of having them under a singular interface, we have an abstract class.
This allows us to not duplicate code and helps decouple our code. This shows proper use of the Dependency Inversion 
Principle.

## Program Extension Description
There are many ways to expand upon our implementation. The main way you could expand upon our implementation is by
expanding on the Entry abstract class. As of now, there are two different kinds of entries, Tasks and Events. 
However, through the use of the base, the Entry abstract class, you can add other kinds of entries. Another way
you could expand on our code is adding my CreationPrompts. If a developer wants to allow the user to make a new kind
of entry, they would need a new CreationPrompt. That is why they would need the EntryCreationPrompt abstract class.
This further expands on the possibility to build off our program.

Another way our implementation can be expanded is through the use of the GUIView interface. Inside the interface 
contains every method you would need to make a functional GUI for the Java Journal. If a user wanted to add their
own design for the GUI, they can implement it using the GUI interface. It is the perfect outline to any developer
who may want to write a new GUI.

Finally, another key way you could expand our implementation is through the Controller interface. It only contains
one function, run. That one run function is what the Driver uses to start the program. Using this interface, a user
could make their own Controller with any specific implementation they may want and having it be able to run through
the Driver. Alongside the GUIView interface, uses would be able to use our model to the fullest and implement their
own vision of the Java Journal if they desired.

## Jar File Explanation
To run the program: Run the Driver
To run the JAR: [built JAR](build/fatJar/$pa05-jay-s-java-journal).jar

## Screenshot
[Opening View](opening_view.png)
[TaskQueue and Menu Bar Visible](Feature_Showcase.png)
[Splash Screen](Splash_Screen.png)
