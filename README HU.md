# 💊 Meditaken – Gyógyszerkezelő és emlékeztető alkalmazás

Egy biztonságos, Android platformra fejlesztett alkalmazás, amely Kotlin, Jetpack Compose, Room, SQLite és SQLCipher technológiák felhasználásával készült. Az alkalmazás célja, hogy segítse a felhasználókat gyógyszereik kezelésében, személyre szabott emlékeztetők létrehozásában és napi gyógyszerszedési rutinjuk betartásában.

Az alkalmazás egyesíti a gyógyszerkezelést, az egyedi ébresztési és emlékeztető ütemezést, az interaktív értesítéseket, a biometrikus hitelesítést és az AES-256 titkosítással védett helyi adatbázist, ezáltal kényelmes és biztonságos gyógyszerkövetési lehetőséget biztosít.

<table>
  <tr>
    <td align="center">
      <img src="./screenshots/lockscreen%20of%20meditaken%20with%20message.png" width="180">
      <br>Google Pixel 7 Pro – Zárolási képernyő
    </td>
    <td align="center">
      <img src="./screenshots/home%20screen%20diplaying%20next%20alarm.png" width="180">
      <br>Google Pixel 7 Pro – Főképernyő
    </td>
    <td align="center">
      <img src="./screenshots/home%20screen%20in%20dark%20mode%20.png" width="180">
      <br>Google Pixel 7 Pro – Főképernyő, sötét mód
    </td>
    <td align="center">
      <img src="./screenshots/addmedication%20screen%20dark%20mode%20empty.png" width="180">
      <br>Google Pixel 7 Pro – Gyógyszer hozzáadása, sötét mód
    </td>
  </tr>
</table>

## 📱 A projektről

A projekt egy natív Android gyógyszerkezelő alkalmazás, amely Kotlin programozási nyelven, Android Studio fejlesztőkörnyezetben készült.

Az elsődleges cél egy olyan alkalmazás létrehozása volt, amely segíti a felhasználókat gyógyszereik rendszerezésében, személyre szabott emlékeztetők beállításában, valamint az esedékes gyógyszerbevételről szóló időben érkező értesítések fogadásában.

Az alkalmazás teljes körű gyógyszerkezelési funkcionalitást biztosít, lehetővé téve a gyógyszeradatok hozzáadását, szerkesztését, törlését és lekérdezését. Emellett egy egyedi fejlesztésű ébresztési rendszert is tartalmaz, amely az Android AlarmManager szolgáltatását használja. Az interaktív értesítések segítségével a felhasználók leállíthatják vagy elhalaszthatják az emlékeztetőket.

A fejlesztés során kiemelt szempont volt az adatbiztonság. Az alkalmazás SQLCipher segítségével AES-256 titkosítással védi a helyi SQLite adatbázist, továbbá az Android BiometricPrompt és az eszköz zárolási képernyőjének hitelesítési mechanizmusait is alkalmazza az illetéktelen hozzáférés korlátozására.

A felhasználói felület elsősorban Jetpack Compose technológiával készült, amely modern, reszponzív és akadálymentességre törekvő felhasználói élményt biztosít, világos és sötét mód támogatásával.

<table>
<tr>
    <td align="center">
      <img src="./screenshots/Meditaken%20LockScreen%20on%20S22%20ultra.jpg" width="180">
      <br>Samsung Galaxy S22 Ultra – Zárolási képernyő
    </td>
    <td align="center">
      <img src="./screenshots/s22%20ultra%20home%20screen%20with%20a%20record%20white.jpg" width="180">
      <br>Samsung Galaxy S22 Ultra – Gyógyszerkezelő képernyő
    </td>
    <td align="center">
      <img src="./screenshots/s22%20ultra%20meditaken%20homescreen%20with%20added%20record%20dark%20mode.jpg" width="180">
      <br>Samsung Galaxy S22 Ultra – Főképernyő, sötét mód
    </td>
    <td align="center">
      <img src="./screenshots/s22%20ultra%20addmedicationscreen%20with%20data%20conjured%20dark%20mode.jpg" width="180">
      <br>Samsung Galaxy S22 Ultra – Gyógyszerkezelő képernyő, sötét mód
    </td>
  </tr>
</table>

## ✨ Főbb funkciók

### 💊 Gyógyszerkezelés

- Új gyógyszeradatok hozzáadása.
- Meglévő gyógyszeradatok szerkesztése.
- Gyógyszeradatok törlése.
- Tárolt gyógyszeradatok lekérdezése és megjelenítése.
- Teljes körű CRUD funkcionalitás (Create, Read, Update, Delete).
- Helyi, tartós gyógyszeradat-tárolás.

### ⏰ Egyedi emlékeztetők ütemezése

- Személyre szabott gyógyszerszedési emlékeztetők létrehozása.
- Egyéni gyógyszerszedési rutinok szerinti időzítés.
- Android AlarmManager integráció.
- Egyedi fejlesztésű ébresztéskezelő rendszer.
- Ütemezett emlékeztetők aktiválása az alkalmazás normál használatán kívül is.

### 🔔 Interaktív értesítések és ébresztések

- Helyi értesítések az ütemezett gyógyszeremlékeztetőkhöz.
- Egyedi ébresztési funkcionalitás.
- Interaktív értesítési műveletek.
- **Elhalasztás (Snooze):** Az aktív emlékeztető ideiglenes elhalasztása.
- **Leállítás (Stop):** Az aktuális ébresztés megszüntetése.
- Integrált ébresztés- és értesítéskezelés.

### 🔐 Biztonság és hitelesítés

- SQLCipher AES-256 adatbázis-titkosítás.
- Titkosított helyi SQLite adatbázis.
- Android BiometricPrompt integráció.
- Biometrikus hitelesítés az alkalmazás elérése előtt.
- Eszköz zárolási képernyőjének hitelesítése.
- További védelem az érzékeny gyógyszeradatok számára.

### 🎨 Felhasználói felület és felhasználói élmény

- Modern Android felhasználói felület Jetpack Compose segítségével.
- Deklaratív UI-fejlesztés Composable függvényekkel.
- Egyszerű és átlátható navigáció.
- Világos és sötét mód támogatása.
- Újrafelhasználható felhasználói felületi komponensek.
- Felhasználóbarát gyógyszerkezelő képernyők.

### 💾 Adatbázis és helyi adattárolás

- SQLite relációs adatbázis.
- Room perzisztenciaréteg.
- SQLCipher titkosított adatbázis-integráció.
- Teljes körű adatbázis CRUD műveletek.
- Helyi adattárolás és adatmegőrzés.
- Az alapvető gyógyszerkezelési funkciókhoz nincs szükség távoli adatbázisra.

## 🛠️ Felhasznált technológiák és eszközök

Az alkalmazás fejlesztése során az alábbi technológiákat és eszközöket használtam:

| Technológia | Felhasználási terület |
|---|---|
| Kotlin | Elsődleges programozási nyelv |
| Android Studio | Android fejlesztőkörnyezet |
| Jetpack Compose | Felhasználói felület fejlesztése |
| SQLite | Helyi relációs adatbázis |
| Room | Adatbázis-kezelési absztrakció és perzisztencia |
| SQLCipher | AES-256 adatbázis-titkosítás |
| Android AlarmManager | Ébresztések és emlékeztetők ütemezése |
| Android BiometricPrompt | Biometrikus hitelesítés |
| Kotlin Serialization | Kotlin szerializációs támogatás |
| Android Notifications | Helyi értesítések és interaktív műveletek |
| Gradle | Projektépítés és függőségkezelés |

## 🏗️ Technikai megvalósítás

### 1. Felhasználói felület – Jetpack Compose

Az alkalmazás felhasználói felülete elsősorban a Jetpack Compose, az Android modern deklaratív UI-eszközkészletének felhasználásával készült.

A képernyők és az újrafelhasználható felületi elemek felépítése Composable függvényekkel történik. Ez a megközelítés elősegíti a moduláris felületi struktúra kialakítását, valamint a felhasználói felület és az alkalmazás mögöttes működésének elkülönítését.

Az alkalmazás sötét módot is támogat, amely alternatív megjelenítési lehetőséget biztosít.

<table>
<tr>
    <td align="center">
      <img src="./screenshots/s22%20ultra%20home%20screen%20with%20a%20record%20white.jpg" width="180">
      <br>Samsung Galaxy S22 Ultra – Gyógyszerkezelő képernyő
    </td>
    <td align="center">
      <img src="./screenshots/s22%20ultra%20addmedicationscreen%20with%20data%20conjured%20dark%20mode.jpg" width="180">
      <br>Samsung Galaxy S22 Ultra – Gyógyszerkezelő képernyő, sötét mód
    </td>
  </tr>
</table>

### 2. Adatbázis-kezelés – Room és SQLite

A Room az alkalmazás SQLite adatbázisának perzisztenciarétegeként működik.

Az adatbázis-műveletek a teljes CRUD életciklust támogatják:

- **Create (Létrehozás):** Új gyógyszeradatok rögzítése.
- **Read (Lekérdezés):** A tárolt gyógyszeradatok lekérdezése és megjelenítése.
- **Update (Módosítás):** Meglévő gyógyszeradatok frissítése.
- **Delete (Törlés):** Gyógyszeradatok eltávolítása az adatbázisból.

Ennek köszönhetően a gyógyszeradatok az alkalmazás újraindítása után is megmaradnak.

### 3. Adatbázis-titkosítás – SQLCipher AES-256

Az SQLCipher integrációja AES-256 titkosítást biztosít a helyi SQLite adatbázis számára.

Ez a megvalósítás további védelmi réteget biztosít a tárolt gyógyszeradatok számára, és segít megakadályozni az adatbázishoz való illetéktelen hozzáférést az alkalmazás rendeltetésszerű hozzáférési mechanizmusain kívül.

<img src="./screenshots/meditakendb%20encrypted%20evidence.png" width="1500">

### 4. Ébresztéskezelés – Android AlarmManager

A gyógyszeres emlékeztetők ütemezésére és aktiválására egy egyedi ébresztési rendszer készült az Android AlarmManager szolgáltatásának felhasználásával.

Az ébresztési funkcionalitás támogatja az ütemezett emlékeztetőket, és integrálódik az alkalmazás értesítési rendszerével.

Az egyedi leállítási és elhalasztási funkciók lehetővé teszik, hogy a felhasználók aktívan kezeljék az emlékeztetőket, ne csupán passzív értesítéseket kapjanak.

### 5. Hitelesítés – BiometricPrompt

Az alkalmazás az Android BiometricPrompt API segítségével valósítja meg a biometrikus hitelesítést.

Emellett az eszköz zárolási képernyőjének hitelesítési mechanizmusát is alkalmazza egy további biztonsági rétegként, mielőtt hozzáférést biztosítana az alkalmazás tartalmához.

Ez további védelmet nyújt a személyes gyógyszeradatok számára.

<img src="./screenshots/Meditaken%20LockScreen%20on%20S22%20ultra.jpg" width="300"> <img src="./screenshots/lockscreen%20of%20meditaken%20with%20message.png" width="300">

## 🔒 Adatvédelem és biztonság

Az alkalmazás tervezése és fejlesztése során kiemelt figyelmet fordítottam a helyi adattárolásra és az érzékeny felhasználói adatok védelmére.

A biztonsággal kapcsolatos megvalósítások:

- AES-256 titkosítással védett SQLite adatbázis SQLCipher segítségével.
- Helyi adatbázis-perzisztencia Room használatával.
- Biometrikus hitelesítés.
- Eszköz zárolási képernyőjének hitelesítése.
- Hitelesítés az alkalmazás tartalmához való hozzáférés előtt.

A gyógyszeradatok helyben, az eszközön kerülnek tárolásra. Az alkalmazás alapvető funkcióinak működéséhez nincs szükség távoli adatbázisra vagy felhőalapú gyógyszeradat-tárolási szolgáltatásra.

## 🧪 Tesztelés

Az alkalmazás fő funkcionális területei az alábbiak szerint tesztelhetők:

- Gyógyszerek létrehozása, szerkesztése, lekérdezése és törlése.
- Egyedi emlékeztetők ütemezése.
- Ébresztések aktiválása és leállítása.
- Értesítések elhalasztási funkciója.
- Biometrikus hitelesítés.
- Eszköz zárolási képernyőjének hitelesítése.
- Adatbázis-perzisztencia.
- Világos és sötét mód működése.

Az alkalmazást több különböző Android-eszközkonfiguráción teszteltem, hogy értékeljem annak működését, kompatibilitását, megbízhatóságát és teljesítményét eltérő működési körülmények között.

A tesztelés során Android Studio emulátorokat és egy fizikai Android-eszközt is használtam, így az alkalmazás szimulált és valós használati környezetben egyaránt vizsgálható volt.

### Tesztelésre használt eszközök

A tesztelés az alábbi eszközökön történt:

**Google Pixel 6 Pro**

- Android Studio emulátor.
- Az alkalmazás funkcionalitásának és kompatibilitásának tesztelése.

**Google Pixel 7 Pro**

- Android Studio emulátor.
- Az alkalmazás funkcionalitásának és kompatibilitásának tesztelése.

**Samsung Galaxy S22 Ultra**

- Fizikai Android-eszköz.
- Valós eszközön végzett funkcionalitási és teljesítménytesztelés.

### Funkcionális tesztelés

Az alkalmazás főbb funkcióit annak ellenőrzésére teszteltem, hogy azok az elvárásoknak megfelelően működnek-e, többek között:

- Gyógyszerek létrehozása, szerkesztése, törlése és lekérdezése.
- Adatbázis-perzisztencia és adathozzáférés.
- Egyedi gyógyszeres emlékeztetők ütemezése.
- Ébresztések aktiválása az ütemezett időpontokban.
- Interaktív értesítési funkcionalitás.
- Ébresztések elhalasztása és leállítása.
- Biometrikus hitelesítés.
- Eszköz zárolási képernyőjének hitelesítése.
- Világos és sötét mód működése.

### Teljesítménytesztelés

A teljesítménytesztelést egy fizikai Samsung Galaxy S22 Ultra készüléken végeztem, az Android fejlesztői beállításainak és CPU-monitorozó alkalmazásoknak a segítségével.

Az alkalmazást több különböző működési körülmény között vizsgáltam annak érdekében, hogy megfigyeljem a működését és az erőforrás-felhasználását.

A tesztelési forgatókönyvek a következőket tartalmazták:

**1. Normál alkalmazásműködés**

- CPU-használat monitorozása normál alkalmazásműködés közben.
- Erőforrás-felhasználás megfigyelése az alkalmazásban történő navigáció során.
- A teljesítmény vizsgálata gyógyszerkezelési és adatbázis-műveletek közben.

**2. Ébresztések aktiválása**

- CPU-használat monitorozása aktív, csengő gyógyszeres emlékeztetők mellett.
- Az alkalmazás működésének megfigyelése az ébresztések aktiválása során.
- Erőforrás-felhasználás vizsgálata az ébresztési funkciókkal történő interakció közben.

**3. Különböző alkalmazáskonfigurációk**

- Tesztelés különböző emlékeztető- és alkalmazásbeállítások mellett.
- Teljesítmény megfigyelése eltérő működési konfigurációk esetén.
- Az ébresztési viselkedés értékelése különböző ütemezési forgatókönyvekben.

**4. Háttérben és nem futó alkalmazás mellett történő működés**

- Ütemezett ébresztések tesztelése akkor, amikor az alkalmazás nincs aktívan megnyitva.
- Annak megfigyelése, hogy az ütemezett emlékeztetők az alkalmazás megnyitása nélkül is megfelelően aktiválódnak-e.
- Az eszköz működésének és CPU-használatának monitorozása az ilyen ébresztések során.

### A tesztelés célkitűzései

A tesztelési folyamat az alábbi szempontokra összpontosított:

- Az alkalmazás stabilitása és reakciókészsége.
- Az ütemezett gyógyszeres emlékeztetők megbízhatósága.
- Az ébresztési funkciók működése akkor is, amikor az alkalmazás nincs aktívan futtatva.
- Adatbázis-műveletek és adatmegőrzés.
- CPU-erőforrások felhasználása normál működés és ébresztésaktiválás közben.
- Működés emulált és fizikai Android-eszközökön.

Az emulátoros tesztelés, a fizikai eszközön végzett vizsgálatok és a teljesítménymonitorozás kombinációja lehetőséget biztosított az alkalmazás alapvető funkcionális ellenőrzésen túlmutató értékelésére is.

### Fejlesztői mód és ébresztési tesztek

<table>
  <tr>
    <td align="center">
      <img src="./screenshots/4%20alarms%20at%20once%20with%20developer%20usage%20stats%201.jpg" width="180">
      <br><b>Normál használat</b>
      <br>Samsung Galaxy S22 Ultra – Fejlesztői mód
    </td>
    <td align="center">
      <img src="./screenshots/4%20alarms%20at%20once%20with%20developer%20usage%20stats%20not%20actively%20running%20stats.jpg" width="180">
      <br><b>Háttérben történő működés</b>
      <br>Samsung Galaxy S22 Ultra – Az alkalmazás nem aktívan fut
    </td>
    <td align="center">
      <img src="./screenshots/s22%20u%20alarm%20message%20with%20interactable%20buttons.jpg" width="180">
      <br><b>Interaktív ébresztési értesítés</b>
      <br>Samsung Galaxy S22 Ultra – Értesítési műveletgombok
    </td>
    <td align="center">
      <img src="./screenshots/alarm%20going%20off%20when%20app%20is%20not%20running%20in%20background.png" width="180">
      <br><b>Háttérben aktiválódó ébresztés</b>
      <br>Google Pixel 7 Pro – Emulátor
    </td>
  </tr>
</table>

## 🔮 Lehetséges jövőbeli fejlesztések

Az alkalmazás további fejlesztésének lehetséges irányai:

- Gyógyszerszedési előzmények és adherenciakövetés.
- Gyógyszerszedési statisztikák és kimutatások.
- Továbbfejlesztett emlékeztető-testreszabási lehetőségek.
- Gyógyszerszedési előzmények és kihagyott adagok nyomon követése.
- Adatbázis biztonsági mentése és visszaállítása.
- További akadálymentességi fejlesztések.

## 🎓 Projektinformációk

- **Projekt:** Android gyógyszerkezelő és emlékeztető alkalmazás.
- **Platform:** Android.
- **Programozási nyelv:** Kotlin.
- **Fejlesztőkörnyezet:** Android Studio.
- **Felhasználói felület:** Jetpack Compose.
- **Adatbázis:** Room / SQLite, SQLCipher titkosítással.
- **Projekt állapota:** Befejezett – A forráskód elérhető ebben a repositoryban.

## 👨‍💻 Fejlesztő

Az alkalmazás egy akadémiai Android-alkalmazásfejlesztési projekt keretében készült, amely az alábbi gyakorlati fejlesztési ismereteket és megvalósításokat mutatja be:

- Natív Android-alkalmazásfejlesztés.
- Kotlin programozás.
- Deklaratív felhasználói felület fejlesztése Jetpack Compose segítségével.
- Relációs adatbázis-kezelés.
- AES-256 adatbázis-titkosítás.
- Biometrikus hitelesítés.
- Háttérben működő ébresztések ütemezése.
- Helyi értesítések és interaktív emlékeztető-funkcionalitás.

## 📄 Szerzői jogok és felhasználási feltételek

Copyright © 2026 Laszlo Major. Minden jog fenntartva.

Ez a repository nyilvánosan elérhető portfólió- és oktatási bemutatási célokra.

A forráskód megtekintés és értékelés céljából érhető el. A szerzői jog tulajdonosának előzetes írásbeli engedélye nélkül a projekt másolása, módosítása, terjesztése vagy kereskedelmi célú felhasználása nem engedélyezett.

A harmadik féltől származó könyvtárakra és függőségekre azok saját licencfeltételei vonatkoznak.
