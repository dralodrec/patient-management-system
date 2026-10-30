# Patient Management System

A Java console application for managing a patient waiting room and browsing individual patient visit histories. Developed as a Data Structures and Algorithms midterm project, it demonstrates a queue implemented with a singly linked list and a patient history implemented with a doubly linked list.

## Features

### Patient waiting room

- Register patients with an ID, name, and reason for visit.
- Add patients to the rear of the queue and serve them from the front.
- Insert emergency patients at a specified position, starting at position 1.
- Display waiting patients without removing them.
- Handle an empty queue safely.
- Prevent duplicate registered IDs and duplicate queue entries through the registration menu.
- Confirm whether matching names belong to different people.
- Suggest a new ID using the highest registered ID plus one.
- Allow served patients to return using their existing ID and a new visit reason.

### Patient history

- Maintain a separate history for each registered patient.
- Store visit dates, diagnoses, and treatment notes.
- Insert visits in chronological order, including visits entered out of order.
- Display all visits from newest to oldest in a table.
- Navigate to previous and next visits or jump to the oldest and newest visits.
- Prevent navigation beyond either end of the history.
- Add visit records through the history menu.
- Load a fictional sample patient with 10 visit records at startup.

## Technology

| Tool | Purpose |
|---|---|
| Java | Application code and standard library |
| Maven | Build and dependency management |
| JUnit 5 | Automated unit tests |
| Git and GitHub | Version control and project submission |

The application does not require Spring Boot, a web server, or a database. It can run from a terminal or a Java IDE such as IntelliJ IDEA.

## Getting started

### Prerequisites

- A JDK compatible with the Java release configured in `pom.xml`.
- Maven available on your terminal's PATH.
- Git, if cloning the repository.

Check your installations:

```bash
java -version
javac -version
mvn -version
git --version
```

Check the Java version shown by Maven as well as the one selected in your IDE. Both must support the project's configured compiler release.

### Download the project

Copy the repository's clone URL from GitHub's **Code** button, then replace `YOUR_REPOSITORY_URL` below:

```bash
git clone YOUR_REPOSITORY_URL
cd patient-management-system
```

### Build and run

From the project root, where `pom.xml` is located:

```bash
mvn compile
java -cp target/classes org.keyin.Main
```

I used IntelliJ IDEA, open the project folder, load the Maven project, select a compatible JDK, and run `Main.java`.

The menu uses ANSI escape sequences to clear the screen. Use a terminal that supports them, such as Ubuntu's terminal, if your IDE's Run console does not clear correctly.

## Using the application

### Main menu

| Option | Action |
|---|---|
| 1 | Add a new or returning patient to the waiting queue |
| 2 | Serve the next patient |
| 3 | Insert an emergency patient at a chosen position |
| 4 | Display waiting patients, then optionally select a patient history |
| 5 | Open a registered patient's history by ID |
| 0 | Exit the application |

Enter `0` at the patient-ID registration prompt to request a suggested ID. At the history-selection prompt, `0` returns without selecting a patient.

Patient IDs identify registered patients; names do not have to be unique. If an ID is already registered, confirm whether this is the returning patient. A patient who is already waiting cannot be added again through the menu.

### History menu

| Option | Action |
|---|---|
| 1 | Display all visits, newest to oldest |
| 2 | View the newest visit |
| 3 | View the oldest visit |
| 4 | View the previous, older visit |
| 5 | View the next, newer visit |
| 6 | Add a visit record |
| 0 | Return to the main menu |

Enter visit dates in `YYYY-MM-DD` format, such as `2026-10-28`. Invalid dates and blank diagnosis or treatment-note fields are rejected by the input prompts.

### Sample data

`SampleData` registers **Betty Boop, ID 101**, with 10 fictional visits. The sample patient is registered but is not automatically added to the waiting queue.

To view the sample history, choose main-menu option **5**, enter **101**, and choose history option **1**. To place Tim in the queue, choose main-menu option **1**, enter **101**, confirm the returning patient, and provide a visit reason.

## Project organization

| Location | Responsibility |
|---|---|
| `src/main/java/org/keyin/Main.java` | Starts the application |
| `src/main/java/org/keyin/model/` | `Patient` and `PatientRecord` data types |
| `src/main/java/org/keyin/repository/` | `PatientRepository` stores registration and patient-history associations |
| `src/main/java/org/keyin/service/` | `PatientQueue` and `PatientHistory` implement the custom data structures |
| `src/main/java/org/keyin/ui/` | `WaitingRoomMenu` and `PatientHistoryMenu` handle console interaction |
| `src/main/java/org/keyin/data/` | `SampleData` loads demonstration records |
| `src/test/java/org/keyin/service/` | History unit tests |
| `pom.xml` | Maven configuration and dependencies |

## Data structures and design

### Waiting queue

`PatientQueue` uses a singly linked list. Each node contains a patient and a reference to the next node. The queue keeps `front` and `rear` references.

Normal arrivals follow **FIFO: First In, First Out**. If [person1] arrives before [person2], [person1] is normally served first. Emergency insertion deliberately overrides the normal order by inserting at a specified position. A stack would instead use LIFO, serving the latest arrival first and potentially delaying earlier arrivals repeatedly.

### Visit history

`PatientHistory` uses a doubly linked list. Each node contains a visit record and `next` and `previous` references. `head` identifies the oldest visit, `tail` identifies the newest visit, and `current` identifies the selected visit.

Both links must be maintained during insertion. An incorrect update can cause visits to be skipped, make nodes unreachable, or introduce a cycle. Navigation checks for a missing neighbour before moving; an unsuccessful move returns `null` and keeps the current selection unchanged.

Arrays and `ArrayList` can also navigate using indexes. The doubly linked list makes the previous/next relationships explicit and demonstrates the structure required. It uses additional memory for nodes and links, while arrays and `ArrayList` provide faster direct indexed access.

### Supporting collections

- An `ArrayList` holds registered patients.
- A `HashMap` associates patient IDs with their individual `PatientHistory` objects.
- An array holds the sample visits before they are inserted into a history.
- `getRecordsNewestFirst()` returns an `ArrayList` for display while preserving the current viewing position.

Serving a patient removes their queue entry, not their registration or visit history. Visit records are added separately through the history menu.

## Unit tests

Run all tests:

```bash
mvn test
```

Run only the three history tests:

```bash
mvn -Dtest=PatientHistoryTest test
```

| Test | What it verifies |
|---|---|
| `sortsOutOfOrderVisitsAndNavigatesBothDirections()` | Visits added out of order are returned in descending date order, and forward/backward navigation follows the correct neighbours. |
| `preventsMovingPastOldestAndNewestRecords()` | Moving past either boundary returns `null` without changing the selected visit. |
| `handlesEmptyHistorySafely()` | An empty history returns `null` for record access and navigation, returns an empty display list, and does not throw an exception. |

Each test creates a fresh history and runs independently of the console menus and sample data. Check Maven's actual result for the number of tests, failures, and errors; this README does not substitute for a test run.

## Current limitations

- Data is stored in memory. Manually entered patients and visits are lost when the application closes.
- Sample data is loaded again on startup and should be loaded only once per repository instance.
- Emergency ordering is selected manually; the application does not calculate medical priority.
- Some validation and registration decisions currently live in the UI layer.
- Console column widths are minimum widths, so unusually long text may affect alignment.
- The application is an educational demonstration using fictional patient information.

## Possible improvements

- Save and load data from a file or database.
- Separate stable patient identity from each waiting-room attendance and visit reason.
- Move registration rules into a dedicated service and console formatting out of data-structure classes.
- Extend tests to cover queue boundaries, duplicate registration, patient-history isolation, and invalid inputs.
- Add defined urgency levels with arrival order as a tie-breaker.
- Add stronger validation, including handling ID-number limits.
- Add authentication, access permissions, and audit records if developing the application beyond a classroom demonstration.


## Documentation questions

### 1. Why was a queue appropriate for the waiting room?

A queue is appropriate because patients normally need to be served in the order they arrive. A new patient joins the rear, while the next patient to be served is removed from the front. This provides a predictable order for ordinary arrivals.

In this project, `PatientQueue` uses linked nodes and keeps references named `front` and `rear`. `addPatient()` connects a new node at the rear, and `servePatient()` removes the front node. Because both ends are directly accessible, these normal operations take O(1) time: they do not require searching through every waiting patient.

For example, if Tim arrives before Betty and Piper, the normal serving order is Tim, Betty, then Piper. Emergency insertion is an intentional exception: `insertPatient()` lets the user place someone at a specified position. Therefore, the system follows FIFO for normal arrivals but permits an explicit emergency override. It does not automatically assess medical urgency.

### 2. Explain the FIFO principle

FIFO means First In, First Out. The first item added is the first item removed, provided there has been no emergency insertion that changes the order.

| Action | Queue from front to rear |
|---|---|
| Tim arrives | Tim |
| Betty arrives | Tim, Betty |
| Piper arrives | Tim, Betty, Piper |
| Serve the next patient | Betty, Piper |

Tim leaves first because Tim arrived first. Adding patients changes the rear of the queue, while serving patients changes the front. Displaying patients should not remove anyone: the printing method walks through nodes using a temporary reference.

### 3. What could happen if a stack were used instead of a queue?

A stack follows LIFO: Last In, First Out. If Tim, Betty, and Piper arrived in that order, a stack would serve Piper first, followed by Betty and Tim.

This would favour the latest arrival instead of the person who has waited longest. If new patients kept arriving, an earlier patient might be repeatedly delayed. This is a possible starvation problem: some patients may wait indefinitely while newer arrivals are served.

A stack can be useful for operations such as undo or backtracking, but its normal removal order does not match this waiting room's FIFO requirement.

### 4. Suggest ways to improve this system

| Proposed improvement | Explanation and benefit |
|---|---|
| Persistent storage | Save patients and visits to a file or database so manually entered information survives a restart. Currently, only the predefined sample data is loaded again. |
| Defined urgency rules | Use a priority queue with arrival order as a tie-breaker to automate ordering by a defined urgency level. This would extend the current manual emergency-position feature. |
| Stronger validation | Enforce valid IDs and visit data in the service or model layer as well as the menu. Guard against integer overflow when suggesting an ID above the highest existing value. |
| Access control and privacy | Add authenticated accounts, appropriate access permissions, and audit records if the application were developed beyond a classroom demonstration. |
| Separate identity from attendance | Store stable patient details separately from each waiting-room entry, because the reason for today's visit can change when a patient returns. |
| Clearer separation of responsibilities | Move registration rules into a patient service and console formatting out of the queue, making the business logic easier to reuse and test. |
| Broader automated tests | Add tests for emergency insertion at every boundary, invalid positions, duplicate registration, suggested IDs, and separate histories for different patients. |

Some improvements are already present: duplicate-ID checking, same-name confirmation, returning-patient registration, next-ID suggestions, date-sorted histories, and readable tables. These should be described as existing features rather than future work.

### 5. Why is a doubly linked list appropriate for patient history?

A doubly linked list allows each visit node to refer to both the next and previous visit. This fits the requirement to browse history in both directions.

The implementation uses `head` for the oldest record, `tail` for the newest record, and `current` for the record being viewed. Moving forward follows `current.next`; moving backward follows `current.previous`. The newest and oldest commands jump directly to `tail` and `head`.

Each navigation step takes O(1) time because the neighbouring node is directly referenced. The list can also grow as visits are added. In this project, records are inserted by visit date, so the program may need O(n) time to find the correct insertion position. Updating the links after finding that position takes O(1) time.

Displaying the full history starts at `tail` and follows `previous` links. The newer `getRecordsNewestFirst()` method uses its own temporary node reference, so printing all records does not change the user's current viewing position.

### 6. What happens if next and previous links are not updated correctly?

Incorrect link updates can break navigation or make part of the list unreachable. For example, suppose visits A and C are connected and a new visit B is inserted between them. Four relationships must be established:

```java
B.previous = A;
B.next = C;
A.next = B;
C.previous = B;
```

These letters are illustrative node names, not additional code to paste into the project.

If `A.next` is not changed, forward navigation may skip B. If `C.previous` is not changed, backward navigation may skip B even though forward navigation includes it. If the existing neighbour reference is overwritten before it is saved, the program may lose access to the remaining nodes in that direction.

Other mistakes can introduce a cycle, causing traversal to repeat indefinitely, or leave an unexpected null reference that causes an error if it is dereferenced. When inserting at either end, `head` or `tail` must also be updated correctly. These are Java object references; they are often called pointers when discussing linked-list concepts.

### 7. Why use a doubly linked list compared with an array or ArrayList?

A doubly linked list directly represents the previous/next relationships required by this assignment and lets the program practise maintaining those links. However, arrays and `ArrayList` can also support forward and backward browsing using an index. A doubly linked list is a suitable choice here, not the only possible one.
