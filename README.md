The telegram bot. 
(In the development)
Collect some information store and process it. In the current state, though, does not do any processing.
Based on Java, Spring Boot and Docker containers.
Empoyes microservices architecture, multithread REST and Kafka interaction. 
To start, require a Telegram token in the gitignore file boot/src/main/resources/logging.properties (bot.token=). It is not presented in the public repository.

- Bot
    Receive and send messages
- tgProcessor
    Processes the messages from the bot
- Users
    Stores information of users
- Stat
    Save and process data (for now does nothing)
