# Kravspecifikation

## Funktionella krav

### Enheter

Systemet ska stödja minst tre olika typer av smarta enheter.

Exempel:

* Lamp
* Camera
* Radiator

Du får även använda egna typer av enheter.

Alla enheter ska:

* Ha ett unikt id.
* Ha ett namn.
* Ha tid och datum för installation.
* Ha tid och datum för senaste aktivering.
* Ha tid och datum för senaste avaktivering.
* Kunna aktiveras.
* Kunna avaktiveras.
* Kunna rapportera sin status.
  * Aktiv
  * Inaktiv
  
Systemet ska förhindra:

* Aktivering av en redan aktiv enhet.
* Avaktivering av en redan avaktiverad enhet.

### Funktionalitet

Systemet ska tillhandahålla funktionalitet för att:

* Lägga till en enhet.
* Ta bort en enhet.
* Söka efter en enhet med namn eller id.
* Lista alla enheter.
* Aktivera en enhet.
* Avaktivera en enhet.
* Demonstration
* Det ska finnas minst en startpunkt (main) som demonstrerar systemets funktionalitet.

**Demonstrationen ska visa:**

* Skapande av flera enheter.
* Aktivering och avaktivering.
* Söka efter en enhet.
* Listning av enheter.
* Minst ett exempel på felhantering.

## Icke-funktionella krav

### Plattform
Systemet ska utvecklas i Java 25.

Systemet ska köras som en konsolapplikation.

### Kodkvalitet
Koden ska vara strukturerad, läsbar och följa vedertagna Java-konventioner.

Lösningen ska vara uppdelad i relevanta paket och klasser.

### Objektorienterad programmering
Lösningen ska använda objektorienterade principer.

#### Projektet ska visa användning av:

* Inkapsling
* Arv
* Polymorfism
* Interface
* Abstraktion
* Komposition
* SOLID
* Projektet ska visa förståelse för SOLID-principerna.

#### Minst ett designbeslut ska kunna motiveras med hjälp av en SOLID-princip.

### Felhantering
* Fel ska hanteras med hjälp av exceptions.

* Minst ett eget exception ska implementeras och användas.

### Datastrukturer
* Data ska lagras med lämpliga Collection-klasser.

* Val av datastrukturer ska kunna motiveras.

* Datum och tid
* Datum och tid ska använda Java Time API.

### Testning
#### Projektet ska innehålla automatiserade tester.

Följande ska testas:

* Normalfall
* minst ett gränsfall
* minst en felhantering
* Externa beroenden ska kunna mockas i enhetstester.

Exempel kan vara:

* Repository-klasser
* Tjänster
* En klocka (Clock)
* Andra beroenden som den testade klassen samarbetar med

### Byggsystem
* Projektet ska använda Maven eller Gradle.

#### Bygget ska kunna:

* Hantera beroenden
* Kompilera projektet
* Köra tester
* Skapa en körbar JAR-fil
* Versionshantering
* Git ska användas kontinuerligt under projektets gång.

#### Arbetet ska visas genom:

* Meningsfulla commits
* Branch-hantering
* Merge av förändringar

### AI-användning
* AI-verktyg får användas som stöd under utvecklingen.

Om AI-genererad kod används ska studenten:

* Kunna förklara koden
* Granska resultatet kritiskt
* Dokumentera minst ett tillfälle där ett AI-genererat förslag har förbättrats eller korrigerats

### Dokumentation
#### Projektet ska innehålla en README som beskriver:

* Hur programmet startas
* Hur tester körs
