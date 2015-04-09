Shopping with Friends
==
####*or: How I Learned to Stop Worrying and Love Mobile Development*

Shopping with friends is an Android application, wherein users show interest in certain goods at certain prices, and friends of those users look to find these items at or below those prices.

This application was created to fulfill the requirements of Georgia Tech's Spring Semester CS-2340 class, taught by [Dr. Robert Waters][6].

For a list of the members of this team, see below, in the __Members__ section. For a full, lengthy description of this project and its outcomes, see the __Description__ section.

##Members
In alphabetical order, the authors of this project are:

[Mike Adkison][1],
[Collin Caldwell][2],
[Veronica LeBlanc][3], and
[Mitchell Manguno][4],

with some early assistance from [Alex Labanowski][5].

##Description
*From CS-2340 Homepage, authored by [Dr. Robert Waters][6].*

####General
Not many of us have time to look at all the sale ads in the paper. Most people don't even subscribe to a paper anymore. How many times have you been out shopping and seen an item that your friend has been waiting to go on sale? This is where "Shopping with Friends" comes in. You can bring up the application on your phone and post information about the price , location and availability of an item.

Projects in this class are open-ended and encourage you to be creative about possible solutions. Anything not specifically outlined in this description means you are free to design it however you like. For M11, we will be exchanging applications, and you will be able to see how user-friendly your choices really were.

####Networks and Databases
Clearly an application like this would need a server and a database to become a commercial reality. Since these advanced courses are not pre-reqs for 2340, we are going to make a simple stand-alone application that mimics the core features, but would not be commercially viable. Adding in a web server and database would be extra credit opportunities for the project.

####Types of Users
User = this represents a regular person using the applicaiton [*sic*]. They are either a friend who has seen a deal and is posting it, or they are a person looking for deals. A person logged in as a user has no access to any Admin features.

Admin = the admin can unlock accounts, permaneantly [*sic*] ban users, and remove any inappropriate posts that might be stored on the system. While logged in as admin, you cannot access any normal user features (like posting sales, etc).

This means that if a person wants to use the system normally and be an admin, they would need either two separate accounts, or some way to tell the system at login which user type they will be for the session. The way you enforce this is up to your design.

####Login/Registration
The application should allow new users to register. Registration would consist of selecting a username, providing an email address and a password. After registering, the user can login to the application to begin using it. It is up to your design to determine whether a first-time registration automatically logs you in, or requires you to go through the login process after login.

When attempting to login, after 3 incorrect logins, the account will be locked out. An admin will need to login to the system and unlock the account.

If a person who is attempting to login is banned, then they should get some type of message indicating they cannot log on.

How the admin knows an account is locked, and how users are told they are banned is a design decision for your team. You are allowed to handle this off-line and outside of the system if you wish. In-app messaging and email, password recovery, etc, would be considered extra credit, but will not be required for our implementation.

####Logout
Since we are simulating the app on a single device, we need a way to change users. The application should allow the current user to log out so that a different user or admin can log in. Each users information should be kept private from the others. Only an admin should be able to browse all the reports and interests.

####Reporting a Sale
When a user is out shopping and they see an item that one of their friends is waiting for, they can enter an item report into the application. The item report should allow entry of the item name, the quantity you can see available on the shelf (this can be actual amount, or a quick selection of only one, a few, many) depending on what you think is best. It should also include a store name, the sale price, and location. For location, you can use the Google map integration (coolest way) or simply allow manual entry of a location.

####Registering Interest
A user can go into the system and register items that they are interested in buying. Registering the item should include the item name and a threshold price (above which you are not interested in the item).

####Friends
A user can designate one or more other people as their friends. To make a friend you just have to know their username. Unlike facebook, the system does not need to confirm you want someone as a friend. Upon designation by either user, the two users are automatically mutual friends. Adding confirmation a la facebook would be extra credit.

A user can display thier [*sic*] current list of friends. From this list, they can add new friends or delete friends. The list should show the friends username and their rating (see below). Selecting a friend from the list should bring up a detailed view showing the name, email, rating and number or sales reports they have generated for you.

####Ratings
Friends can rate each other (1-5 stars) for how helpful they are. Friends with no stars means that they have never received feedback (either because they have never reported a sale to anyone, or their friends never bothered to rate them).

####Internal System Logic
When a user enters a Sale Report, the system must go through all their friends and see if anyone has registered an interest in the item. It must also check that the price is at or below the threshold. If this is the case, it must notify the friend that the item is available. As soon as the friend logs into the application, they should be notified of all the items that have been reported to be on sale.

####Map
A Google Map display should be available with pins marking the locations where the sale items are located.


[1]: https://github.com/mikeadkison
[2]: https://github.com/cheychc
[3]: https://github.com/coolCharizard
[4]: https://github.com/mmanguno
[5]: https://github.com/Labanowski
[6]: http://www.cc.gatech.edu/home/watersr/
