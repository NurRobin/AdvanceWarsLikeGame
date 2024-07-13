# Projektname

## Überblick

Dieses Projekt ist eine Java-Anwendung, die mit Gradle gebaut und verwaltet wird. Es soll ein rundenbasiertes Strategiespiel ähnlich wie Advance Wars implementieren. Das Spiel wird in JavaFX implementiert und verwendet die MVC-Architektur.

## Voraussetzungen

Um dieses Projekt auszuführen, stellen Sie sicher, dass Sie Folgendes installiert haben:

- Java JDK 17
- Gradle (optional, da das Gradle Wrapper-Skript verwendet werden kann)
- OpenJFX 17 (JavaFX SDK 17.0.2 wird empfohlen)

## Erste Schritte

Um mit dem Projekt zu beginnen, klonen Sie das Repository und navigieren Sie in das Projektverzeichnis:

```sh
git clone https://gitlab.nurrobin.de/tim23/java/AdvanceWarsLikeGame.git
cd AdvanceWarsLikeGame
```

Führen Sie dann das Gradle-Wrapper-Skript aus, um das Projekt zu bauen und die Tests auszuführen:

```sh
./gradlew clean run
```