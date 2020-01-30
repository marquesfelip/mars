# Mars

Mars is a little app that uses the InSight: Mars Weather Service API distributed by Nasa. It takes continuous weather measurements (temperature, wind, pressure) on the surface of Mars at Elysium Planitia, a flat, smooth plain near Mars’ equator.

[More about](https://api.nasa.gov/?search=weather+service#insight)  InSight: Mars Weather Service API.

## How to run

__Mars__ requires [JDK 8+](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html) to run.

```sh
$ cd Mars/jar/
$ java -jar mars-weather-1.0.jar
```

### More infos

- The [JSON-Java](https://github.com/stleary/JSON-java) external library was used to manipulate JSON objects.

- The project was made in [Visual Studio Code](https://code.visualstudio.com/) with [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) plugin.
