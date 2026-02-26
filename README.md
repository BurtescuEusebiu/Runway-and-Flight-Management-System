# Sistem de Gestionare a Pistelor și Aterizărilor/Decolărilor

## **Descrierea Sistemului**
Sistemul gestionează pistele de aterizare/decolare și zborurile asociate acestora printr-o structură eficientă și bine organizată. Pistele (Runways) sunt stocate într-un HashMap utilizând ID-ul pistei ca și cheie. Această abordare asigură o căutare rapidă și eficientă a pistelor după identificator, reducând timpul de acces la constantă amortizată O(1).


## **Descrierea Claselor**

### **1. Airplane**
Clasa abstractă `Airplane` reprezintă un avion și conține informațiile de bază ale acestuia. Toate tipurile de avioane (NarrowBody și WideBody) vor extinde această clasă.
- **Metode cheie**:
    - `getId()`: Returnează ID-ul avionului.
    - `getStatus()`: Returnează starea avionului (dacă zborul este activ sau completat).
    - `getIdealTime()`: Returnează timpul ideal de aterizare/decolare.
    - `checkEmergency()`: Verifică dacă avionul este în stare de urgență.
    - `maneuver()`: Făcând o manevră (aterizare/decolare) schimbă starea avionului.

---

### **2. NarrowBodyAirplane**
Subclasă a clasei `Airplane`, aceasta reprezintă avioanele narrow-body. Extinde funcționalitatea clasei abstracte pentru a adăuga specificul avioanelor mai mici.
- **Metoda cheie**:
    - `toString()`: Returnează o reprezentare textuală a avionului narrow-body.

---

### **3. WideBodyAirplane**
Subclasă a clasei `Airplane`, aceasta reprezintă avioanele wide-body. Extinde funcționalitatea clasei abstracte pentru a adăuga specificul avioanelor mai mari.
- **Metoda cheie**:
    - `toString()`: Returnează o reprezentare textuală a avionului wide-body.

---

### **4. Runway**
Clasa `Runway` reprezintă o pistă de aterizare/decolare și gestionează avioanele care așteaptă să utilizeze pista respectivă. Pistele sunt stocate într-un HashMap pentru căutări eficiente.
- **Metode cheie**:
    - `add(Airplane airplane)`: Adaugă un avion pe pista curentă.
    - `makeManeuver(LocalTime timeStamp, String folderPath)`: Efectuează o manevră pe pista curentă (aterizare/decolare), aruncând excepții în caz de conflicte.
    - `toString()`: Returnează o reprezentare textuală a pistei.
    - `toStringWithAvailability(LocalTime timeStamp)`: Returnează informații despre disponibilitatea pistei la un anumit moment.

---

### **5. IncorrectRunwayException**
Excepție aruncată atunci când un avion este alocat unei piste care nu corespunde tipului acesteia.
- **Metode cheie**:
    - `logException(String folderPath)`: Loghează detaliile excepției într-un fișier pentru urmărire.

---

### **6. UnavailableRunwayException**
Excepție aruncată atunci când o pistă este ocupată și nu poate accepta alte manevre în timpul solicitat.
- **Metode cheie**:
    - `logException(String folderPath)`: Loghează detaliile excepției într-un fișier pentru urmărire.

---

### **7. LandingComparator**
Compară avioanele pentru prioritizarea aterizărilor. Prioritizează avioanele în funcție de urgențe și de timpul ideal.
- **Metoda cheie**:
    - `compare(T plane1, T plane2)`: Comparați avioanele pentru prioritizarea aterizărilor.

---

### **8. TakeOffComparator**
Compară avioanele pentru prioritizarea decolărilor. Prioritizează avioanele în funcție de timpul ideal.
- **Metoda cheie**:
    - `compare(T plane1, T plane2)`: Comparați avioanele pentru prioritizarea decolărilor.

---

### **9. Commands**
Clasa `Commands` gestionează toate comenzile primite de la utilizator și controlează procesarea acestora.
- **Metode cheie**:
    - `addRunway(String folderPath, HashMap<String, Runway<? extends Airplane>> runways, LocalTime timeStamp, String id, String type, String planeType)`: Adaugă o pistă în lista de piste.
    - `addPlane(String folderPath, HashMap<String, Runway<? extends Airplane>> runways, LocalTime timeStamp, String id, String start, String end, String model, boolean emergency, LocalTime idealTime, String planeType, String emergencyType)`: Adaugă un avion pe pistă.
    - `makeManeuver(String folderPath, HashMap<String, Runway<? extends Airplane>> runways, LocalTime timeStamp, String runwayId)`: Efectuează o manevră de aterizare/decolare pentru avionul curent.
    - `runwayInfo(String folderPath, HashMap<String, Runway<? extends Airplane>> runways, LocalTime timeStamp, String runwayId)`: Generază informații despre pista specifică.
    - `airplaneInfo(String folderPath, HashMap<String, Runway<? extends Airplane>> runways, LocalTime timeStamp, String airplaneId)`: Generază informații despre avionul specific.

---

## **Structuri de Date – Documentație Tehnică**

### **Arhitectura și Organizarea Datelor**
Sistemul gestionează pistele de aterizare/decolare și zborurile asociate acestora printr-o structură eficientă și bine organizată.

- **Pistele (Runways)** sunt stocate într-un HashMap utilizând ID-ul pistei ca și cheie. Această abordare asigură o căutare rapidă și eficientă a pistelor după identificator, reducând timpul de acces la constantă amortizată O(1).

- La nivelul fiecărei piste, zborurile sunt gestionate printr-o `PriorityQueue`. Aceasta este creată utilizând două comparatoare:
    - **TakeOffComparator** – Prioritizează decolările.
    - **LandingComparator** – Prioritizează aterizările.
      Comparatoarele asigură ordonarea avioanelor în funcție de urgențe și de timpul ideal de aterizare/decolare, facilitând astfel procesarea avioanelor critice înaintea celor mai puțin urgente.

### **Gestionarea Generică a Pistei**
Implementarea pistelor este realizată utilizând genericitate, permițând astfel gestionarea eficientă atât a avioanelor de tip `WideBodyAirplane`, cât și a celor de tip `NarrowBodyAirplane`. Această abordare îmbunătățește flexibilitatea și reutilizarea codului, oferind suport pentru diferite tipuri de aeronave fără a duce la duplicarea logicii.

### **Limitări și Optimizări**
Deși sistemul de gestionare a pistelor este optimizat, căutarea unui zbor specific în cadrul tuturor pistelor nu este ideală. În prezent, pentru a localiza un zbor, aplicația iterează prin fiecare pistă și verifică fiecare avion.

#### Posibilă Îmbunătățire:
Căutarea zborurilor ar putea fi optimizată prin introducerea unui HashMap suplimentar în **Main**, care să asocieze direct ID-ul zborului cu zborul corespunzător. Această modificare ar reduce timpul de căutare de la O(n) (iterarea prin toate pistele și zborurile) la O(1).

#### Decizia de Implementare:
Totuși, această optimizare nu a fost aplicată, deoarece cerințele proiectului specifică limitarea atributelor gestionate la nivelul clasei `Main`.
