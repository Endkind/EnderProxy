# EnderProxy

**EnderProxy** ist ein vielseitig einsetzbarer Proxy-Server, der auf Java basiert. Dieses Projekt ermöglicht es
Benutzern, verschiedene Verbindungsanforderungen über einen einzigen Proxy zu steuern.

## Voraussetzungen

- Mindest-Java-Version: 11 oder höher

## Installation

1. **Java installieren**:

   Stelle sicher, dass Java 11 oder höher installiert ist. Du kannst die Java-Version mit folgendem Befehl überprüfen:

   ```sh
   java -version
   ```

2. **EnderProxy herunterladen**:
   Lade die neuste Version von EnderProxy von der [Releases-Seite](https://github.com/Endkind/EnderProxy/releases) herunter.

## Docker

alle zur Verfügung stehenden Versionen:
- `latest`
- `0.3.0`

### Schnellstart

```shell
docker run -d --network host --name endkind-enderproxy endkind/enderproxy:0.3.0
```

### Empfohlene Methode

```shell
docker volume create endkind-enderproxy
docker run -d --network host --name endkind-enderproxy -v endkind-enderproxy:/enderproxy endkind/enderproxy:0.3.0
```

### Docker Compose

Erstelle eine docker-compose.yml Datei mit folgendem Inhalt oder verwende die Datei aus dem [Repository](https://github.com/Endkind/EnderProxy/blob/master/docker-compose.yml).

```yaml
version: "3"
services:
  enderproxy:
    network_mode: host
    container_name: endkind-enderproxy
    volumes:
      - endkind-enderproxy:/enderproxy
    image: endkind/enderproxy:0.3.0
volumes:
  endkind-enderproxy:
```

```shell
docker-compose up -d
```

## Nutzung

1. **Proxy starten**:

   Um EnderProxy zu starten, benutze foldenden Befehl, wobei `{version}` durch die aktuelle Versionsnummer ersetzt
   werden muss:

   ```shell
   java -jar EnderProxy-{version}-jar-with-dependencies.jar
   ```

2. **Konfigurationsdatei**:

   Beim ersten Start wird eine Datei namens `config.yml` im Ordner `EnderProxy` erstellt, der sich im gleichen Verzeichnis wie die `.jar`-Datei befindet.
   Diese Datei enthält die Konfigurationseinstellungen für den Proxy. Du kannst diese Datei nach deinen Bedürfnissen
   anpassen.

## Beispiel `config.yml`

Hier ist die Standardkonfiguration, wie sie in deiner `config.yml` aussehen könnte:

```yaml
version: 2

'survival.endkind.net':
   - listenPort: 25565
     backendHost: '192.168.0.100'
     backendPort: 25565
   - listenPort: 22
     backendHost: '192.168.0.100'
     backendPort: 22

'forge.endkind.net':
   - listenPort: 25565
     backendHost: '192.168.0.101'
     backendPort: 25565
   - listenPort: 22
     backendHost: '192.168.0.101'
     backendPort: 22

'www.endkind.net':
   - listenPort: 443
     backendHost: '192.168.0.102'
     backendPort: 443
   - listenPort: 22
     backendHost: '192.168.0.102'
     backendPort: 22
```

## Beitrag leisten

Beiträge sind willkommen! Wenn du einen Bug melden oder ein Feature vorschlagen möchtest, öffne bitte ein [Issue](https://github.com/Endkind/EnderProxy/issues).
Pull-Requests sind ebenfalls willkommen.

## Zusätzliche Information

- [GitHub Repository](https://github.com/Endkind/EnderProxy)
- [Docker Repository](https://hub.docker.com/r/endkind/enderproxy)
- Besuche unsere [Website](https://www.endkind.net) für weitere Informationen über unsere Projekte und Dienstleistungen.

## Lizenz

Dieses Projekt ist unter der [MIT Lizenz](https://github.com/Endkind/EnderProxy/blob/master/LICENSE) veröffentlicht.