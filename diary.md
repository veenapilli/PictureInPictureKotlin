# Observations of the base code

1. Available for Android versions 31 - 33
2. Use ConstraintLayout along with View binding
3. PiP is available since version 26 / Android 8

# Initial setup

1. Add .gitignore
2. Commit the original code base
3. Since the formatting usage is not same, added some local formatting. For a shared codebase, we
   should be able to follow the same set of formatting rules.

# Task 1 - Legacy support - Support Android version 21

Android 21 was released in 2014 which is now 10 years old

For Legacy support, a few things to consider would be:

1. How many years do we want to support any app? A good range would be 3-5 years. ~ version 29 or
   above
2. How many customers can we reach within 3-5 years coverage? 75% - 90% of our users
3. Do we have active customers in those that we leave out? Answer must be No. Active
   customers must be using our latest features
4. Is it worth a risk and effort to support older devices? No

Especially with Android, we consider its good to support all possible versions as we are generally
never forced to update min versions of the sdk. However, the fact remains that older versions may
have identified vulnerabilities. Also, supporting extra versions needs extra code paths since not
all new libraries support legacy versions of Android

Solution:

Do not reduce the minsdk version

Reasons:

1. We do not need to add the overhead of extra testing and special consideration of features for a
   small percentage of non active users
2. For older versions of devices that have earlier releases rolled out for them. If users
   choose to use old devices, they can continue to use the previously tested versions of the app.
3. Since the current codebase is supported and tested for version 31 (assuming we were supporting
   older versions years earlier + have dropped them), it's not necessary to lower the minSdk
   version.
4. If we have never rolled out an app, version 31 as minimum covers 4 years of supported devices.
5. Instead, we must focus on updating the target sdk to 34 to allow our code to use newer version
   that's already available. This allows us to use the latest build tools and when users update
   their
   devices, we would be providing them features using the latest options available.

# Task 2 - Unit tests

MainViewModel is responsible for:

1. Managing timer for the UI
   a. Start / Pause
   b. Clear time
2. Addition of persistence caused additional initialisation and saving of data. This can be tested
   as part of the StopwatchRepository testing

Dependency update for Testing:

1. Using MockK for mocking dependencies
2. For live data values update, we need to set the rule to use the correct thread for updating of
   x.value during testing
3. For coroutines, we will need to use the correct dispatcher to run the test coroutines

Changes to ViewModel:

1. Move SystemClock into a Repo to make it easy to mock a dependency
2. Update MainActivity and MainViewModelFactory to pass in appropriate dependencies

Tests:

1. Starting the stopwatch
2. Stopping the stopwatch
3. Clearing the stopwatch Improvement: Needs more work to debug: why time is null

Improvements: Test use of runTest to avoid delays in the tests

# Task 3 - Saving time

In this case, we need to save time across activities. The method we can use here is to use
persistent storage options:
Shared preferences
Datastore (successor of Shared preferences)
Database

For the case of storing time, we can use Shared preferences or datastore - as the amount of data we
have to save is small
Databases are more useful when we need to store large amounts of data

Changes:

1. Add the Datastore dependency; Create a new package "data" to include all the datastore preference
   related classes
2. To the MainActivity, add the creation of DataStore instance user-preference.
   a. This needs to be singleton. For simplicity, its just added to the MainActivity. Improvement:
   Use injection to maintain the singleton scope and for sharing the datastore across the app.
   b. This change causes the viewModel to be initialised in onCreate()
   c. The viewModel needs to pass on the DataStore instance to the Repository to access it. Hence
   the viewModel creation is updated to use the ViewModelProvideFactory
3. To the MainActivity, add the saving of time when the activity goes out of view - via the onPause
4. The MainViewModel now includes the StopwatchRepository to allow for saving and fetching the time
   when we go to the Movie Activity
5. StopwatchRepository accesses the datastore to fetch any existing time stored or returns an empty
   time of 0L.
6. StopwatchRepository also exposes a function to store time in the dataStore when the MainActivity
   goes out of view.

