# 熊猫沢ペイント KumaNekoZawa Paint

This project started as a MS Paint clone but soon got out of hand. This program was and is developed live on [Twitch](https://www.twitch.tv/kumanekozawa). There are still a lot of features missing and it still contains a lot of bugs. Use at your own discretion.

# How to build

Requires `ant 1.10.7` and Java JDK 11 to be built. Using `checkstyle 10.3` is also recommended.
Build via command line: `ant package`. This will generate the file `knz-paint.jar`.
To run Checkstyle, you will first need to checkout `knz-shared` and you will also need to put `checkstyle-10.3-all.jar` into the parent directory of this directory (so that `knz-paint`, `knz-shared` and `checkstyle-10.3-all.jar` are all in the same directory). Then run via command line: `ant checkstyle`.
You can get `checkstyle-10.3-all.jar` [here](https://github.com/checkstyle/checkstyle/releases/tag/checkstyle-10.3).

# How to run

This program requires Java JRE 11 to be run.
Run via command line: `java -jar knz-paint.jar`.
You can set `print-look-and-feel-info=true` in the `config.properties` file. This will print all available Look & Feels to the command line. Copy & paste the one you want to use into the `config.properties` file under `look-and-feel` (you might have to uncomment the line).
