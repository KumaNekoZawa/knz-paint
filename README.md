# 熊猫沢ペイント KumaNekoZawa Paint

This project started as a MS Paint clone but soon got out of hand. This program was and is developed live on [Twitch](https://www.twitch.tv/kumanekozawa). There are still a lot of features missing and it still contains a lot of bugs. Use at your own discretion.

# How to build

Requires `ant 1.10.7` and Java JDK 11 to be built. Using `checkstyle 10.3` is also recommended.
Build via command line: `ant package`. This will generate the file `KNZ-Paint.jar`.
To run Checkstyle via command line: `ant checkstyle`. You will need to put `checkstyle-10.3-all.jar` and `knz-style.xml` into the parent directory of this directory.
You can get `checkstyle-10.3-all.jar` from [here](https://github.com/checkstyle/checkstyle/releases/tag/checkstyle-10.3).

# How to run

This program requires Java JRE 11 to be run.
Run via command line: `java -jar KNZ-Paint.jar`.
You can set `print-look-and-feel-info=true` in the `config.properties` file. This will print all available Look & Feels to the command line. Copy & paste the one you want to use into the `config.properties` file under `look-and-feel` (you might have to uncomment the line).
