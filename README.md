# Runway and Flight Management System

## **System Description**
The system manages airport runways and associated flights using an efficient and well-structured design. Runways are stored in a `HashMap` using the runway ID as the key. This approach ensures fast and efficient lookup of runways by identifier, achieving amortized constant-time access O(1).

## **Class Descriptions**

### **1. Airplane**
The abstract class `Airplane` represents a plane and contains its basic information. All airplane types (`NarrowBody` and `WideBody`) extend this class.  
- **Key Methods**:
    - `getId()`: Returns the airplane's ID.
    - `getStatus()`: Returns the airplane's status (active or completed flight).
    - `getIdealTime()`: Returns the ideal landing/takeoff time.
    - `checkEmergency()`: Checks if the airplane is in an emergency state.
    - `maneuver()`: Performs a maneuver (landing/takeoff), changing the airplane's state.

---

### **2. NarrowBodyAirplane**
Subclass of `Airplane`, representing narrow-body planes. Extends the abstract class functionality to include smaller airplane specifics.  
- **Key Method**:
    - `toString()`: Returns a textual representation of the narrow-body airplane.

---

### **3. WideBodyAirplane**
Subclass of `Airplane`, representing wide-body planes. Extends the abstract class functionality to include larger airplane specifics.  
- **Key Method**:
    - `toString()`: Returns a textual representation of the wide-body airplane.

---

### **4. Runway**
The `Runway` class represents a landing/takeoff runway and manages airplanes waiting to use it. Runways are stored in a `HashMap` for efficient lookup.  
- **Key Methods**:
    - `add(Airplane airplane)`: Adds an airplane to the runway.
    - `makeManeuver(LocalTime timeStamp, String folderPath)`: Performs a maneuver (landing/takeoff) on the runway, throwing exceptions in case of conflicts.
    - `toString()`: Returns a textual representation of the runway.
    - `toStringWithAvailability(LocalTime timeStamp)`: Returns information about runway availability at a given time.

---

### **5. IncorrectRunwayException**
Exception thrown when an airplane is allocated to a runway that does not match its type.  
- **Key Method**:
    - `logException(String folderPath)`: Logs exception details to a file for tracking.

---

### **6. UnavailableRunwayException**
Exception thrown when a runway is occupied and cannot accept additional maneuvers at the requested time.  
- **Key Method**:
    - `logException(String folderPath)`: Logs exception details to a file for tracking.

---

### **7. LandingComparator**
Compares airplanes to prioritize landings, based on emergency status and ideal landing time.  
- **Key Method**:
    - `compare(T plane1, T plane2)`: Compares airplanes for landing prioritization.

---

### **8. TakeOffComparator**
Compares airplanes to prioritize takeoffs, based on ideal takeoff time.  
- **Key Method**:
    - `compare(T plane1, T plane2)`: Compares airplanes for takeoff prioritization.

---

### **9. Commands**
The `Commands` class handles all user commands and controls their execution.  
- **Key Methods**:
    - `addRunway(String folderPath, HashMap<String, Runway<? extends Airplane>> runways, LocalTime timeStamp, String id, String type, String planeType)`: Adds a runway to the list.
    - `addPlane(String folderPath, HashMap<String, Runway<? extends Airplane>> runways, LocalTime timeStamp, String id, String start, String end, String model, boolean emergency, LocalTime idealTime, String planeType, String emergencyType)`: Adds a plane to a runway.
    - `makeManeuver(String folderPath, HashMap<String, Runway<? extends Airplane>> runways, LocalTime timeStamp, String runwayId)`: Performs a landing/takeoff maneuver for a plane.
    - `runwayInfo(String folderPath, HashMap<String, Runway<? extends Airplane>> runways, LocalTime timeStamp, String runwayId)`: Generates information about a specific runway.
    - `airplaneInfo(String folderPath, HashMap<String, Runway<? extends Airplane>> runways, LocalTime timeStamp, String airplaneId)`: Generates information about a specific airplane.

---

## **Data Structures – Technical Documentation**

### **Architecture and Data Organization**
The system manages runways and associated flights through an efficient and well-structured architecture.

- **Runways** are stored in a `HashMap` using the runway ID as the key, providing fast, amortized O(1) lookup.
- At each runway, flights are managed using a `PriorityQueue` with two comparators:
    - **TakeOffComparator** – prioritizes takeoffs.
    - **LandingComparator** – prioritizes landings.  
  Comparators ensure airplanes are ordered by urgency and ideal times, allowing critical flights to be handled before less urgent ones.

### **Generic Runway Management**
Runways are implemented using generics, allowing efficient handling of both `WideBodyAirplane` and `NarrowBodyAirplane`. This approach improves code flexibility and reuse, supporting multiple airplane types without duplicating logic.

### **Limitations and Optimizations**
While the runway management system is optimized, searching for a specific flight across all runways is not ideal. Currently, locating a flight requires iterating through each runway and checking each airplane.

#### Possible Improvement:
A supplementary `HashMap` in **Main** could directly associate flight IDs with their corresponding flights, reducing search time from O(n) (iterating all runways and flights) to O(1).

#### Implementation Decision:
This optimization was not applied because the project requirements limit the attributes managed to the `Main` class level.
