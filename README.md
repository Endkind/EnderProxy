# EnderProxy

**EnderProxy** ist ein vielseitig einsetzbarer Proxy-Server, der auf Java basiert. Dieses Projekt ermöglicht es
Benutzern, verschiedene Verbindungsanforderungen über einen einzigen Proxy zu steuern.

## Voraussetzungen

- Mindest-Java-Version: 1.8 oder höher

## Installation

1. **Java installieren**:

   Stelle sicher, dass Java 1.8 oder höher installiert ist. Du kannst die Java-Version mit folgendem Befehl überprüfen:

   ```sh
   java -version
   ```

2. **EnderProxy herunterladen**:
   Lade die neuste Version von EnderProxy von der [Releases-Seite](https://github.com/Endkind/EnderProxy/releases) herunter.

## Nutzung

1. **Proxy starten**:

   Um EnderProxy zu starten, benutze foldenden Befehl, wobei `{version}` durch die aktuelle Versionsnummer ersetzt
   werden muss:

   ```sh
   java -jar EnderProxy-{version}-jar-with-dependencies.jar
   ```

2. **Konfigurationsdatei**:

   Beim ersten Start wird eine Datei namens `config.yml` im Ordner `EnderProxy` erstellt, der sich im gleichen Verzeichnis wie die `.jar`-Datei befindet.
   Diese Datei enthält die Konfigurationseinstellungen für den Proxy. Du kannst diese Datei nach deinen Bedürfnissen
   anpassen.

## Beispiel `config.yml`

Hier ist die Standardkonfiguration, wie sie in deiner `config.yml` aussehen könnte:

```yaml
version: 1

'survival.endkind.net':
  listenPort: 25565
  backendHost: '192.168.0.100'
  backendPort: 25565

'forge.endkind.net':
  listenPort: 25565
  backendHost: '192.168.0.101'
  backendPort: 25565
```

## Beitrag leisten

Beiträge sind willkommen! Wenn du einen Bug melden oder ein Feature vorschlagen möchtest, öffne bitte ein [Issue](https://github.com/Endkind/EnderProxy/issues).
Pull-Requests sind ebenfalls willkommen.

## Lizenz

Dieses Projekt ist unter der [MIT Lizenz](https://github.com/Endkind/EnderProxy/blob/master/LICENSE) veröffentlicht.
