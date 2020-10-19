# Development Log

## Outline
This project will be a games collection catalouge. It will allow you to store, edit and remove games of various platforms, genres and purchase dates. It will display a list of titles inputted by the user, and sorted using various methods. The user can rate games in their collection, at that rating can be compared to other games. 

The software will use the Chicken Coop API to help with populating fields, as information such as release year, developers, and images, as it can pull this information from the MetaCritic database from a game's title.

The UI will be powered by Java's Swing framework, as it is the easiest way to communcate with a Java backend. As one of the requirements of the project is console I/O, there will be a Java console UI built in, but hidden unless enabled through console arguments.

As this project is a university assignment, and so all code must be written by me, there are some functions that would normally be taken from libraries, such as a JSON parser, that will have to be hand developed instead

---
>#### Each new row is a new entry, i.e. a unique task being documented
## Design Phase

<table style="width:100%;">
    <tr>
        <th>Date</th>
        <th>Timeframe (Hours)</th>
        <th>Task</th>
        <th>Issues</th>
    </tr>
    <tr>
        <td rowspan="2">22/09/2020</td>
        <td style="text-align:center">2</td>
        <td>Created basic structure for the digital layout of the project, i.e. the project folders, the methods I'll use to document, log and plan the project (Markdown), and I have begun thinking of ideas for what the project could be, but so far there is nothing concrete</td>
        <td style="text-align:center">N/A</td>
    </tr>
    <tr>
        <td style="text-align:center">1</td>
        <td>Begun developing the idea of what the project will be. I have also researched the scope of the project, what it will be built in, and begun developing the basic structure of the software</td>
        <td style="text-align:center">N/A</td>
    </tr>
    <tr>
        <td style="text-align:center">23/09/2020</td>
        <td style="text-align:center">1</td>
        <td>Begun the basis of the code structure, i.e. the beginning of the UML of the project.</td>
        <td style="text-align:center">As the way I plan projects is done more code side than in initial planning phases, it is most likely that I'll start working on the codebase before most of the UML is completed, and will only really plan out the UI design before writing the source code</td>
    </tr>
</table>

## Implementation Phase

<table style="width:100%;">
    <tr>
        <th>Date</th>
        <th>Timeframe (Hours)</th>
        <th>Task</th>
        <th>Issues</th>
    </tr>
    <tr>
        <td>22/09/2020</td>
        <td style="text-align:center">1</td>
        <td>Started development of the application. Currently, the application starting point (main application class) has been created, as an IntelliJ Forms Designer class.</td>
        <td style="text-align:center">N/A</td>
    </tr>
    <tr>
        <td>27/09/2020</td>
        <td style="text-align:center">6</td>
        <td>Created the basis of game storage. This includes a class for storing games, for a library of games, and any associated enums. Also, implemented a system for storing Java objects directly into files.</td>
        <td style="text-align:center">My first cosideration was to store games as some type of encoded string, like JSON or just as plain text, however Java has native object serialization, which means object data can just be stored and read directly from files. The only issue is that any changes to the class structure of a serialized class will make any already created files unreadable.</td>
    </tr>
    <tr>
        <td>3/10/2020</td>
        <td style="text-align:center">2</td>
        <td>Moved from IntelliJ's Form UI Designer to hardcoding the user interface.</td>
        <td style="text-align:center">While the Form's designer is great, as this project needs to be converted into an Eclipse project for it's submission, i cannot submit the .form files, as they will not play well with eclipse, without 3rd party plugins. Therefore, I made the decision to cut my losses early and just work as hardcoded Java.</td>
    </tr>
    <tr>
        <td>6/10/2020</td>
        <td style="text-align:center">4</td>
        <td>Added API data retrieval and storage, as well as UI image displaying, which means games can now be added to the UI.</td>
        <td style="text-align:center">The API outputs data as raw JSON, and because all the code needs to be written by me, I haven't allowed myself to use 3rd party libraries, so I have to parse the JSON myself. Which, as well as that, I also have to use Java's built in networking tools, which are not ideal.</td>
    </tr>
    <tr>
        <td>13/10/2020</td>
        <td style="text-align:center">1</td>
        <td>Refactored project for the Eclipse IDE and Eclipse WindowBuilder.</td>
        <td style="text-align:center">Although I would've loved to keep on working on this project in IntelliJ, the design of the UI without the assistance of a UI designer was just too time consuming. So, I had to refactor the entire project structure as an eclipse project. Fortunately, this caused minial bugs, and from what I can tell no invisible bugs, as any issues that it caused simply meant that the program just wouldn't start.</td>
    </tr>
    <tr>
        <td rowspan="4">14/10/2020</td>
        <td style="text-align:center">3</td>
        <td>Added the ability to remove games, as well as improving the way API calls are made. Also added the ability to sort and search andthrough a library with collection sorting</td>
        <td style="text-align:center">As it turns out, dynamically updating a UI is not the easiest task on the planet. It's not immensly complex, but it does lead to some strange issues, escpecially around game selection and removal. This is even more evident as the games are JPanels, and not in a JList, so selection and removal has to be done by my code, and not the almost certainly much better Swing code. The solution, as it turns out, is to just recalculate the entire game's list UI on every update, instead of just trying to update the specific game to remove it. 
        Also, as I want multiple sorting methods, and I don't want it all coded into the library class, or 10 different sorting classes, the solution I had was just to make one class with a static method that returns Comparator classes with the correct override methods.</td>
    </tr>
    <tr>
        <td style="text-align:center">2</td>
        <td>Added text entry validation</td>
        <td style="text-align:center">Swing UI validation calls are only made when an element has lost focus. What I want is for the field to live update with the validation result, so that the user knows, as they are typing, that their text is valid or not. So, the admittedly hacky solution to this, is to have, on every key release of a JTextField, the focus is pushed to another element, causing the validation to fire, then it is immediatly called back, so that the user can keep on typing as if nothing happended. None of this would have to happen if a text field's validation could be called as a function, but sometimes we can't have everything we want, can we.</td>
    </tr>
    <tr>
        <td style="text-align:center">2</td>
        <td>Added the ability to update games that have already been added to the library</td>
        <td style="text-align:center">In order to update an entry in a ArrayList, you would either have to change every single value of the item in the list, or just replace it with another item. As I decided to just replace it, there was the issue that unneeded API calls where being made, as what was the purpose of making an API call that can take updwards of 2 seconds, when the price is the only field being updated. Therefore, I had to implent the ability to set the API results from other API results, so they could be copied over</td>
    </tr>
    <tr>
        <td style="text-align:center">3</td>
        <td>Added more sorting methods, and made more game API data displayed</td>
        <td style="text-align:center">If a game does not have API data to display, trying to display that data doesn't end well. So there needed to be checks if the game has API data, if the sorting method being used requires API data, and to hide them if that is the case</td>
    </tr>
    <tr>
        <td rowspan="2">15/10/2020</td>
        <td style="text-align:center">2</td>
        <td>Added UI configurations, so that the UI state is stored over sessions. Also added console input</td>
        <td style="text-align:center">The console input had some issues being implemented, but most had to do with needing custom exceptions to throw, as well as unparsed newline characters.</td>
    </tr>
    <tr>
        <td style="text-align:center">1/2</td>
        <td>Added window title, and an icon</td>
        <td style="text-align:center">N/A</td>
    </tr>
    
</table>

## Testing Phase 

<table style="width:100%;">
    <tr>
        <th>Date</th>
        <th>Timeframe (Hours)</th>
        <th>Issues</th>
        <th>Details</th>
    </tr>
    <tr>
        <td rowspan = "2">15/10/2020</td>
        <td style="text-align:center">1</td>
        <td>Fixed extra sorting methods</td>
        <td style="text-align:center">As the sorting methods and their associated enums are not intrinsically linked, when I added extra SortingMethod enums, there was the problem that many errors wold be spat out when those where selected from the ComboBox, so all SortingMethod enums NEED an associated case in the Sort.get method</td>
    </tr>
        <td style="text-align:center">3</td>
        <td>Changed class serialization structure</td>
        <td style="text-align:center">As the only objects being stored where initally Librarys, the addition of another serializable class meant that it was much simpler to abstract the data serialization into the Config class, instead of defining it specifically in the Library class. This meant that the state of the UI could be abstracted from the Config class as well.</td>
    </tr>
    </tr>
        <td rowspan="2">19/10/2020</td>
        <td style="text-align:center">1</td>
        <td>Fixed API result not being null</td>
        <td style="text-align:center">During the final testing, I found numerous issues. The most paramount was that the function that tested for whether the game had API data or not was not functional, which meant that any point in the codebase that called for API data was being called, even if a game didn't have data, which lead to several, such as data not being displayed or updated properly. Another issue that was found was that when the serialization structure was changed, the Game class was never changed back to implementing Serializable, which meant that every read and write operation would fail.</td>
    </tr>
    </tr>
        <td style="text-align:center">1</td>
        <td>Console input fixes</td>
        <td style="text-align:center">There was a few points at which the scanner would call next() instead of nextLine(), causing entries with just newline characters to bug out the data entry by the user, and send garbled data to the removal and update functions.</td>
    </tr>
</table>


