### 1. Klassdesign: beskriv två klasser

#### SmartEquipment (model)

- Ansvar: Håller en enhets "data" och tillstånd (namn, id, installationstid, på/av, senaste ändring). 
Den har också sina egna regler t.ex.: setId gör en throw om id:t är negativt och kan bara sättas en gång, 
och setStateActive gör också en throw om enheten redan är på.

- Varför: Modeller ska veta vad en enhet är men inte när eller hur den skapas eller sparas, den ska vara "dum".
Tiden skickas in utifrån, och modellen slår aldrig upp tid själv.

#### SmartEquipmentService (service)

- Ansvar: Logiken i sitt system. Den validerar namn, skapar enheter via SmartEquipmentType, 
sätter tid via den injicerade Clock, tar bort enheter (med kontroller av negativt och okänt id) och hämtar enheter från
repository.

- Varför: Den kopplar ihop modeller och repository utan att någon av dem behöver känna till den andre(förutom i Map:en då).

Om man alternativ jämför med en enda stor klass som både läser input, validerar och sparar. Den blir svår att testa 
eftersom allt sitter ihop och förmodligen kommer innehålla flera tusen(tiotusen rader etc. 
beroende på storlek av systemet) och varje ändring riskerar att förstöra något annat. Det är just därför man vill dela
upp systemet genom Single Responsibility Principle(SRP) med Objekt Orienterad Programmering(OOP).

### 2. Arv eller komposition

#### Arv: SmartEquipment -> Lamp, Camera, Radiator

- Lösning: abstrakt basklass som innehåller allt gemensamt (id, tider, på/av-logik, toString). 
Subklasserna implementerar bara getStateMessages() och anropar super.setStateActive(...) för att sedan returnera 
sitt eget meddelande, t.ex. "heating is on".

- Varför: alla enheter är smarta enheter och delar beteende. Det är en tydlig "är-en"-relation. 

- Fler exempel på arv: BaseForMenu -> alla menyer, och SmartEquipmentRepository -> InMemoryRepository.

#### Komposition: SmartEquipmentType + SmartEquipmentCreate
- Varje enum-värde har en "fabriks"-funktion (LAMP("Lamp", Lamp::new)), och servicen anropar type.getCreator().create(...).

- Services är också komponerade via konstruktorn. SmartEquipmentHandlerService har ett SmartEquipmentServiceInterface 
och en Clock, och MenuMain har alla sina undermenyer.

#### Polymorfism: toString + List/Search

- SmartEquipment har en toString() som anropar getStateMessages(). getStateMessages() returnerar en lista med olika 
versioner av meddelanden beroende på vilken enhet.
- List och Search itererar över SmartEquipment utan att veta vilken typ av enhet det är.

Alternativa lösningar:

- En enda klass med typfält (type = "LAMP") och switch överallt. Varje ny typ kräver då ändringar i flera switchar, 
vilket bryter mot SOLID Open/Closed.

- Komposition av förmågor: en enhet får en lista av funktioner (ljus, inspelning, värme) i stället för att ärva. 
Det skulle nog passa bättre för enheter med flera funktioner. Kameran har t.ex. redan två (inspelning och lampa)
och det är det enda fallet där arvet började skava.

### 3. Interface

#### SmartEquipmentRepositoryInterface har tre metoder:

- insertSmartEquipment
- removeSmartEquipment
- getSmartEquipmentRepositoryMap

***

- Problemet det löser: Servicen vet inte hur data ska lagras. Idag finns bara InMemoryRepository men en fil- eller 
databasvariant kan läggas till utan att servicen behöver ändras något.

- Testning: I SmartEquipmentServiceTest mockas repot med when(repo.insertSmartEquipment(lamp)).thenReturn(-5) för att 
testa felhanteringen med ett negativt id. Det går inte att framkalla med ett live repo.

- Utan interfacet: servicen skulle vara låst till InMemoryRepository istället för att bara använda interfacet 
som beskriver mallen på repot. Den kunde inte bytas ut och felfall som "repot ger ogiltigt id" kunde inte testas.

### 4. SOLID:

#### Single Responsibility Principle (SRP):

- Syns genom: Modellerna innehåller bara data och tillstånd, logiken ligger i services och UI-texter ligger i UI. 
Exempel: "Try Again!" flyttades från InvalidSmartEquipmentNameException till OptionAddSmartEquipment när demon visade 
att texten inte passade överallt.
- Vad förhindrar det: Att inte kodbas blir för stor i en fil och att kod blir svårare att underhålla och förstå.
Om du har en bug i "ett ansvar" har du oftast redan en "regel" på vad som ska in och ut. Hade allt varit i samma klass
hade det varit en heldag att förstå kodbasen istället för bara t.ex. en klass med SRP.

### 5. Vidareutveckling: tio nya enhetstyper

- Väl förberett för nya enhetstyper(Så länge dom inte har mer än två On/Off funktioner): Den abstrakta klassen 
SmartEquipment(som implementerar SmartEquipmentInterface) gör det enkelt att lägga till nya enhetstyper utan 
att ändra på befintlig kod.

    #### How-To: 
    Skapar en ny subklass för varje ny enhetstyp och nytt enum-värde i SmartEquipmentType.
    <br><br>

- Det jag skulle vilja ändra:
  - Bara ett extra tillstånd per enhet. setSmartEquipmentState(eq, on, extraState) har en boolean-flagga och 
    ExtraStateControllable stöder bara ett extra tillstånd. En termostat med temperatur och fläkt passar inte in. 
    Lösningen vore att använda komposition genom att ge enheten en lista av förmågor.
  
  - Repot är "baklänges": Map<SmartEquipment, Integer>. Att söka på id kräver att man går igenom alla enheter.
  Med Map<Integer, SmartEquipment> blir sökningen på id direkt.
  - Luddiga exceptions som ...DidFailSomewhereException säger inte så mycket om vad som gick fel.

### 6. AI-användning
- Jag lät den generera tester för olika enhetstyper och olika tillstånd, vissa står kvar som Orörda med testfunktioner
som vi inte gått igenom än men har försökt satt mig in i ändå och till referens för mig själv att studera mera.
- Jag tog bort 14 tester som AI gjorde som utfyllnad bara för att jag gav den ett krav på att nå 2 normalfall, 2 gränsfall, 
2 felhanterings-tester.


### 7. Motivering Collections

- LinkedHashMap i BaseForMenu och alla menyer: Den behåller insättningsordningen så att menyvalens nummer blir samma. 
En vanlig HashMap hade inte garanterat ordningen.
- TreeMap som SortedMap i getAllSmartEquipmentsSortedMapById: sorterar automatiskt på nyckeln, så listan kommer i id-ordning.
- HashMap i repot: snabb Map, men som jag skrivit ovan är nyckeln "fel" för sökning på id.
- Map.copyOf: repot lämnar ut en kopia som inte kan ändras, så ingen utanför kan ändra innehållet förbi servicen.
