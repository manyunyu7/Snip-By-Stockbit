# Snip by Stockbit

Snip by Stockbit is an Android app designed to help beginners learn stock investing from scratch, without any prior knowledge or experience. The app is equipped with high-quality materials created by experts, structured in a way that caters to different learning styles and levels of expertise. Whether you prefer to watch videos or read texts, it's all up to you! Snip by Stockbit, making stock investing simple and accessible for everyone.

## Description

Snip by Stockbit is an Android app that provides users with comprehensive and structured materials to learn stock investing. The app is designed for beginners who have no prior knowledge or experience in stock investing. Users can choose to learn through either videos or texts, depending on their preference.

## Features

- High-quality learning materials created by experts
- Structured materials that cater to different learning styles and levels of expertise
- Videos and texts available for learning
- Easy-to-use interface
- Free to use

## Technical Details

- MVVM architecture pattern
- Kotlin programming language
- Clean architecture principles
- Retrofit for network requests
- Room for local data storage
- Kotlin Flow for handling asynchronous data streams
- Dependency injection using Dagger 2
- LiveData for communication between View and ViewModel layers
- Data binding for easier UI updates
- Navigation Component for handling app navigation
- Coroutines for concurrency and async code
- Room Database for offline caching and persistence
- Glide for image loading and caching
- Material Design Components for UI/UX
- JUnit and Mockito for testing
- Crashlytics for crash reporting and monitoring

## Contributing

We welcome contributions to Snip by Stockbit! Before getting started, please review our guidelines and conventions for contributing.

### Guidelines

1. Fork this repository and create a new branch for your changes.
2. Make your changes and ensure they meet the following technical requirements:
   - Follow the MVVM architecture pattern.
   - Use Kotlin for all new code and migrate existing Java code to Kotlin.
   - Use clean architecture principles to separate business logic from the presentation layer.
   - Use Retrofit for network requests and Room for local data storage.
   - Use Kotlin Flow for handling asynchronous data streams.
   - Ensure your code is well-documented and follows the Kotlin coding conventions.
3. Write tests for your changes to ensure they work as expected and do not break existing functionality.
4. Submit a pull request with a clear description of your changes and why they are necessary.

### Branches
- `main` branch always contains the latest stable code.
- `develop` branch is used for ongoing development and integration of features.
- Feature branches are created off of the `develop` branch using the following naming convention: `feature/<feature-name>`.
- Bug fix branches are created off of the `develop` branch using the following naming convention: `bugfix/<bug-name>`.
- Release branches are created off of the `develop` branch using the following naming convention: `release/<version-name>`.

### Commits
- Commit messages should be descriptive and concise, summarizing the changes made in the commit.
- Use present tense and imperative mood in commit messages.
- Use the following format for commit messages: `<emoji> <type>: <description>`.
- Examples of commit types include `✨ feat` for a new feature, `🐛 fix` for a bug fix, and `📝 docs` for documentation updates.


## Emojis in Commit Messages

At our company, we have found that using emojis in commit messages can be a helpful way to quickly understand the type of changes that have been made to a codebase. While this practice is **not mandatory**, we recommend using emojis as a way to make commit messages more engaging and easier to read.

Emojis can add visual cues to your commit messages that help communicate the type of changes you made. For example, you could use the following emojis to indicate different types of changes that you can help your teammates **quickly understand the nature of your changes**


Here are the emojis we use and what they represent:

### Automation
- 🤖  for automation-related changes
- 🕰️ for scheduling or timing changes
- 🚦  for optimizing resource utilization
Use 🤖 when making automation-related changes to the codebase, such as implementing automated testing or deployment processes. Use 🕰️ when making scheduling or timing changes, such as updating cron jobs or timeouts. Use 🚦 when optimizing resource utilization, such as reducing memory usage or optimizing database queries. Use 🤝 when integrating with external services or systems, such as implementing APIs or webhooks.

### Bug Fixes
- 🐛 `:bug:` for bug fixes
- 🛡️ `:shield:` for security bug fixes
- 🤕 `:face_with_head_bandage:` for critical bug fixes
- 🚨 `:rotating_light:` for emergency bug fixes
Use 🐛 when you fix a bug or issue in the code. Use 🛡️ when you fix a security-related bug, 🤕 when you fix a critical bug that could cause major issues, and 🚨 when you fix an emergency bug that requires immediate attention.

### Security
- 🔒 `:lock:` for security-related changes
- 🚪 `:door:` for access control changes
- 🛡️ `:shield:` for security bug fixes
- 🔓 `:unlock:` for removing security restrictions

Use 🔒 when you make security-related changes to the code, such as implementing encryption or adding password requirements. Use 🚪 when you make access control changes, such as updating permissions or adding new authentication methods. Use 🛡️ for security-related bug fixes, and use 🔓 when you remove security restrictions.

### Performance Improvements
- 🚀 `:rocket:` for performance improvements
- 🐎 `:racehorse:` for improving performance without changing functionality
- 📈 `:chart_with_upwards_trend:` for improving scalability
- 🚦 `:vertical_traffic_light:` for optimizing resource utilization
Use 🚀 when you make significant improvements to the performance of the code, such as optimizing algorithms, reducing latency, or increasing throughput. Use 🐎 when you improve the performance of the code without changing its functionality, such as by removing redundant code or improving memory management. Use 📈 when you improve the scalability of the code, such as by improving the efficiency of data structures or algorithms. Use 🚦 when you optimize the resource utilization of the code, such as by reducing memory or CPU usage.



### Features
- ✨ `:sparkles:` for new features
- 🆕 `:new:` for adding new functionality to an existing feature
- 🔄 `:repeat:` for making changes to an existing feature
Use ✨ when you add a new feature or functionality to the codebase. Use 🆕 when you add new functionality to an existing feature, and 🔄 when you make changes to an existing feature.

### Documentation
- 📝 `:memo:` for documentation updates
- 📚 `:books:` for adding new documentation
- 🚩 `:triangular_flag_on_post:` for updating documentation that needs attention
Use `:memo:` when you make changes to the documentation. Use `:books:` when you add new documentation, and `:triangular_flag_on_post:` when you update documentation that needs attention.

### Refactoring
- ♻️ `:recycle:` for refactoring
- 🧼 `:soap:` for cleaning up code
- 🚮 `:put_litter_in_its_place:` for removing dead code
- 🔀 `:twisted_rightwards_arrows:` for moving code to a new location
Use ♻️ when you refactor the code, which means you restructure or rewrite the code without changing its behavior. Use 🧼 when you clean up the code by removing unused variables, renaming variables for clarity, or improving the code style. Use 🚮 when you remove dead code, which is code that is no longer used. Use 🔀 when you move code to a new location, such as moving a function from one file to another.

### Work in Progress
- 🚧 `:construction:` for work in progress
Use 🚧 or 🏗️ when you are working on a feature or fixing a bug, and the code is not yet complete or ready for review. These emojis indicate that the code is a work in progress and should not be merged until it is complete.

### Removals
- 🔥 `:fire:` for removing code or files
- 🗑️ `:wastebasket:` for removing unused files or code
- 🙅‍♀️ `:no_good_woman:` for removing permissions or access
Use 🔥 when you remove code or files from the codebase. Use 🗑️ when you remove unused files or code. Use 🙅‍ when you remove permissions or access.

### Styling
- 🎨 `:art:` for code style/formatting, also for changes in uikit modules
- 📏 `:straight_ruler:` for adjusting spacing or indentation
- 🌈 `:rainbow:` for adding color or styling
- 🕶️ `:dark_sunglasses:` for changing the look and feel of the UI

Use 🎨 when you make changes to the code's style or formatting. Use 📏 when you adjust spacing or indentation in the code. Use 🌈 when you add color or styling to the code. Use 🕶️ when you change the look and feel of the user interface.


### Internationalization and Localization
- 🌐 `:globe_with_meridians:` for adding or updating internationalization and localization
- 🇺🇸  for adding or updating translations for English
- 🇪🇸  for adding or updating translations for Spanish
- 🇯🇵  for adding or updating translations for Japanese

Use :globe_with_meridians: when you add or update internationalization and localization features in the code. Use the appropriate country code flag emoji, such as :us: for English, :es: for Spanish, or :jp: for Japanese, when you add or update translations for a specific language.

## Conventions

To maintain consistency and readability throughout the codebase, we follow the following conventions:

1. Use meaningful and descriptive names for all variables, functions, and classes.
2. Use CamelCase for class names and lowerCamelCase for variable and function names.
3. Use four spaces for indentation.
4. Avoid using abbreviations or acronyms, except for commonly-used terms (e.g., URL).
5. Use a maximum line length of 120 characters for code and comments.
6. Add a space after keywords (e.g., if, while) and before opening parentheses (e.g., if (condition)).
7. Use braces for all control statements (e.g., if/else, for, while) even if the body contains only a single statement.

We appreciate all contributions to Snip by Stockbit and are committed to maintaining a welcoming and inclusive community.

## Disclaimer

All materials included in this project, such as texts, images, logos, and trademarks, belong to Stockbit and Bibit, and are protected by intellectual property laws. Any unauthorized use, reproduction, modification, or distribution of these materials is strictly prohibited. This project is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose, and non-infringement. In no event shall the contributors or owners of this project be liable for any claim, damages, or other liability, whether in an action of contract, tort, or otherwise, arising from, out of, or in connection with this project or the use or other dealings in this project. By contributing to this project, you agree to be bound by these terms and conditions. 
