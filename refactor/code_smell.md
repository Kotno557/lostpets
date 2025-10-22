# Code Smells

### Shin-Jie Lee (李信杰)
### Associate Professor
### Department of CSIE
### National Cheng Kung University

---

## Unresolved warnings (未解決的警告)

*   程式仍然可以執行 (The program is still runnable)，但可能會導致**意外的錯誤 (unexpected errors)**。

**程式碼範例 (Java):**
```java
1 public void printSomething() {
2 int size = 3
3 String target = null;
4
5 for(int i = 0; i < size; i++) {
6 System.out.println(“i = ” + i);
7 }
8
9
System.out.println(target.toString( ));
}
```

---

## Every dynamic allocated memory is de-allocated or there is garbage collection

*   當大量實例化的物件不再被使用但未被刪除時，記憶體可能會被完全佔用。
*   **記憶體洩漏 (Memory Leak)**。

**程式碼範例 (C++ style array allocation):**
```cpp
1 int main() {
2 int size = 10; 3 int result = 0;
4 int array = new int[size];
5
6 // Assign value to the array
7 for(int i = 0; i < size; i++) {
8 array[i] = i;
9 }
10
11 for(int i = 0; i < size; i++) {
12 result += array[i];
13 }
}
```

---

## Long method (過長的方法)

*   那些擁有**短方法 (short methods)** 的物件程式，活得最好也最長久。
*   程序越長，越難理解。
*   很難為長方法命名。
*   透過 **Extract Method** 將長方法分解為短方法。

**程式碼範例 (Java - 潛在的長方法):**
```java
1 public void createPartControl(Composite parent) {
2 _failnodes = new HashSet<Object>();
3 _comps = new ConcurrentLinkedQueue<IComponent>();
4 _viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL);
5 _viewer.setInput(getViewSite());
// … … (略去多行)
59 _selectionHandler = new SelectionChangHandler();
60 _selectionHandler.setViewer(_viewer);
61 }
```

---

## Feature Envy (依戀情結)

*   一個方法似乎對**它實際所屬的類別之外的另一個類別**更感興趣。
*   Feature Envy 引入了類別之間的**緊密耦合 (tight coupling)**。
*   「被羨慕的 (envied)」類別中的變更可能會影響到具有 Feature Envy 的類別。

**Feature Envy 範例：**
在以下範例中，`Customer` 類別中的 `getFullAddress()` 方法過度存取 `Address` 類別的資料：

```java
class Address {
    private String street;
    private String city;
    private String country;
    // ... getters and constructor
}

class Customer {
    private String name;
    private Address address;
    // ... constructor
    public String getFullAddress() {
        // Feature Envy: This method accesses too much of Address class data
        return address.getStreet()+ ", "+address.getCity()+ ", "+address.getCountry();
    }
}
```

*   **實驗室練習 – Feature Envy 重構：** 使用 **Move Method** 進行程式碼重構。
*   重構時比較程式碼前後的差異，並考慮為 `Address` 添加屬性 `String street` 的情況。

---

## Unsuitable naming (不合適的命名)

*   為類別、方法或變數提供合適的名稱，將使程式設計師**易於理解**。

**不合適命名範例 (T, xyz):**
```java
1 public class T() {
2 boolean b = false;
3
4 public int xyz(int x, int y, int z) {
5 int r = 0;
6 r = (x + y) * z / 2;
7 return r;
8 }
9 }
```
**改善後命名範例 (Trapezoid, calculateArea):**
```java
1 public class Trapezoid() {
2 boolean isIsosceles = false;
3
4 public int calculateArea(int top, int bottom, int height) {
5 int area = 0;
6 area = (top + bottom) * height / 2;
7 return area;
8 }
9 }
```

---

## Downcasting (向下轉型)

*   轉型是 Java 程式設計師生活中的另一個禍根。
*   應盡可能避免讓類別的使用者進行向下轉型。

**導致 `ClassCastException` 的向下轉型範例：**
```java
1 class Animal() {}
2
3 class Mammal extends Animal() {}
4
5 class Cat extends Mammal() {}
6
7 class Dog extends Mammal() {}
1 Mammal m = new Cat()
2 Dog c = (Dog)m; // Throws ClassCastException
```

**實驗室練習 (Lab)：什麼樣的情況會使用到 Downcasting？**
*   與 APIs 或 Frameworks 互動？
*   處理遺留程式碼 (Legacy Code)？
*   處理混合型別的集合 (Collections of Mixed Types)？
*   反序列化 (Deserialization)？

---

## Loop termination conditions are obvious and invariably achievable

*   迴圈終止條件應該是明顯且總是可達成的。

**不合適的迴圈終止條件範例：**
```c
1 for(int i = 1; (i % 2) ? ((i + 100) < 200) : ((i* 30) < 50); i++) {
2 Do something
3 }
4 5 for(int i = 0; i < 100; i++) { 6 Do something 7 i = i * 5; 8 }
```

---

## Parentheses are used to avoid ambiguity (使用括號避免歧義)

*   使用括號可以提高可讀性 (readability) 並防止邏輯錯誤。

**不使用括號範例 (可能導致邏輯錯誤):**
```java
1 public int trapezoidArea(int top, int bottom, int height) {
2 int area = top + bottom * height / 2;
3 return area;
4 }
```
**使用括號改善範例 (明確計算順序):**
```java
1 public int trapezoidArea(int top, int bottom, int height) {
2 int area = (top + bottom) * height / 2;
3 return area;
4 }
```

---

## Lack of comments (缺乏註解)

*   使用註解的好時機是你**不知道該怎麼做**的時候。
*   註解可以指出你不確定的領域。
*   註解是說明**為什麼 (why)** 你這樣做的好地方，這有助於未來的修改者。

**增加註解改善範例：**
```java
1 public RSSIMapCollection() {
2 _maps = new Hashtable<String, RSSIMap>();
3 _listeners = new Vector<RSSIMapCollectionEventListener>();
4
5 // Initialize a selection property for multiple stabilizations
6 _stabilizes = new SelectionProperty(STABILIZES_LABEL);
7 _stabilizes.addElement(Stabilize.NONE);
// ...
11 _stabilizes.setSelectedItem(Stabilize.THRESHOLD);
}
```

---

## Files are checked for existence before attempting to access them

*   開啟檔案之後沒有測試檔案是否正確載入就進行操作 (以 C++ 為例)。
*   **➢** 開啟檔案之後應該測試檔案是否已正確開啟。

**檢查檔案開啟狀態的範例 (C++):**
```cpp
8 inputFileStream.open(“MyText.txt”);
9 char output;
10 if (inputFileStream.is_open()) { // Check if file has been opened successfully.
11 while (!inputFileStream.eof()) {
12 inputFileStream >> output;
// … // process read-in data
16 }
17 } else {
18 … // error-handling code
20 }
22 }
```

---

## Duplicated Code (重複程式碼)

*   如果你在多於一個地方看到相同的程式碼結構，如果你能找到方法將它們統一，你的程式會更好。
*   最簡單的重複程式碼問題是當你在**同一個類別的兩個方法**中擁有相同的表達式時。
*   **➢** 解決方法是 **Extract Method**，並從兩個地方呼叫該程式碼。

**重構方式 (Extract Class):**
*   將計算平均的邏輯提取到獨立的 `AverageCalculator` 類別中。

**重構後的類別呼叫範例：**
```java
1 public class classAReportCard {
2 private List<Integer> classAScores;
// …
4 public int calculateAverage (AverageCalculator ac) {
5 retrun ac.calculateAverage(classAScores);
6 }
}
```

---

## All methods have appropriate access modifiers and return types

*   類別、建構子、方法和欄位的存取是透過**存取修飾詞 (access modifiers)** 來規範的。
*   一個類別可以控制哪些資訊或資料可以被其他類別存取。

**存取修飾詞範例 (C++ - 將密碼設為私有):**
```cpp
1 Class Account {
2 public: 3 string getPassword(); 4 ... 5 private: 6 string _password; 7 ...
};
```

*   新增一個**合適的回傳型別 (return type)** 來幫助檢查方法是否成功執行。
**使用回傳型別檢查成功執行範例：**
```cpp
1 bool openAndProcessFile(string filePath) {
// ...
4 if (!ifs.is_open())
5 return false; // Return false if file is not opened successfully.
// ...
10 return true;
11 }
```

---

## Are there any redundant or unused variables? (是否有冗餘或未使用的變數？)

*   從原始碼中刪除未使用的變數。

**刪除未使用的變數範例 (刪除未使用的 `rank` 變數):**
```java
1 public int calculateClassAverage (List<Integer> scores) {
2 int sum, average = 0;
3 for (int i = 0; I < scores.size(); i++) {
4 sum += scores.get(i);
5 }
6 return average;
7 }
```

---

## Indexes or subscripts are properly initialized, just prior to the loop

*   用於終止條件的變數應在迴圈開始前正確**初始化 (initialized)**。

**變數初始化範例：**
```c
1 int i = -10;
2 while (i < 0) {
3 doSomething();
4 i++;
5 }
```

---

## Is overflow or underflow possible during a computation?

*   計算過程中的**溢位 (overflow)** 或**下溢 (underflow)** 可能會導致系統崩潰 (system crash)。

**檢查溢位/下溢的範例：**
```c
6 if (addend + augend > numeric_limits<short>::max() ||
7 (addend + augend < numeric_limits<short>::min()) {
8 throw “short integer overflow / underflow”
// ...
```

---

## Are divisors tested for zero? (除數是否經過零測試？)

*   除數在運行時不應該為零 (Divisors should not be zero at runtime)。

**測試零除數範例：**
```c
6 if (divisor == 0) {
7 throw “divisor is 0”;
8 } 9 int quotient = dividend /
divisor;
```

---

## Inconsistent coding standard (不一致的編碼標準)

*   使用有意義的名稱。
*   使用底線 (`_`) 作為類別屬性 (attribute) 的前綴。

**改善範例 (有意義的命名/屬性使用底線):**
```cpp
1 class Car {
2 public: 3 int getVehicleId (); 4 string getManufactureDate(); 5 … 6 private: 7 int _id; 8 string _manufactureDate; 9 … 10 };
```

---

## Data clumps (資料群集)

*   你經常會在許多地方看到**相同的兩三個或四個資料項目**聚集在一起：在幾個類別中作為欄位，在許多方法簽章中作為參數。

**Data Clumps 重構範例 (提取 `Address` 類別):**
```java
1 public class Address {
2 private String house;
3 private String street;
4 private String city;
5 private String country;
6 … 7 }

// Customer 類別使用 Address 物件
1 public class Customer {
// ...
4 private Address customerAddr;
// ...
}
```

---

## Simulated Polymorphism (模擬多型)

*   透過 `switch/if-else` 來模擬多型的行為。
*   **適合以 Polymorphism 代替 switch/if-else 的時機：**
    *   當 Condition 為**分類概念**時。
    *   且 Branch 行為未來可能需要**擴展**時。

**模擬多型範例 (使用 `switch`):**
```java
1 public int getLegsNum() {
2 switch(animal) {
3 case ‘chicken’: 4 return 2;
// ...
12 }
}
```
**使用多型改善範例：**
```java
1 public int getLegsNum(Animal a) {
2 return a.getLegs();
3 }
```

---

## Large class (大類別)

*   一個類別包含許多欄位/方法/程式碼行。
*   大類別通常違反**單一職責原則 (Single Responsibility Principle, SRP)**。
*   如果類別名稱感覺模糊或籠統（例如：Manager, Helper），該類別可能職責不清楚。

**實驗室練習 – Large Class 範例 (SystemManager):**
包含 `addUser()`, `loadConfiguration()`, `logInfo()`, `readFile()`, `backupSystem()` 等多項職責。

---

## Long parameter list (長參數列表)

*   長參數列表難以理解，並且變得不一致且難以使用。

**改善後的短參數列表範例 (將地址相關參數提取為 `Address` 物件):**
```java
1 public class Member {
2 public createMember(
3 Name name,
4 Address address) {
5 …
6 }
7 }
```

---

## Message Chains (訊息鏈)

*   當客戶端連續向多個物件請求另一個物件時發生。
*   它產生**緊密耦合 (tightly coupled code)**，並違反了**迪米特法則 (Law of Demeter)**（「不要與陌生人交談」）。
*   如果任何類別發生變化，`Client` 程式碼也需要修改。

**訊息鏈範例：**
```java
// Message Chain
String street = company.getManager().getAddress().getStreet();
```

### 重構方法2：新增中介 Class (Façade Pattern / 外觀模式)

*   新增一個 `CompanyService` 提供訊息鏈服務，讓 `Client` 呼叫。
*   **適用時機：** 如果 Message Chain 發生在**跨 Subsystem 間**，應採用 Façade Pattern。
*   **優點：** 符合 Single Responsibility Principle 和 Demeter’s Law。

---

## Literal constants (字面常數)

*   使用關鍵字 `(static) const` 或 `define` 來定義常數。

**改善範例：**
```java
1 public double potentialEnergy(double mass, double height) {
2 final static double GRAVITATION = 9.81;
3 return mass * GRAVITATION * height;
4 }
```

---

## Every variable is properly initialized (每個變數都應正確初始化)

**改善範例 (變數初始化):**
```java
1 Person person = new Person();
2 Manager = person.getManager();
3 int workHours = 40, hourlyWage = 120;
4 Int salary = workHours * hourlyWage;
```

---

## There are uncalled or unneeded procedures or any unreachable code

*   未呼叫、不需要或無法到達的程式碼可能會佔用不必要的記憶體。
*   時間和精力可能會花在維護和記錄實際上無法到達的程式碼上。

**無法到達程式碼範例：**
```java
1 if(i < 60) {
2 //unreachable
3 if(i == 60) { // 此條件永遠無法成立
// ...
```

---

## Does every switch statement have a default?

*   每個 `switch-case` 都應定義一個預設動作 (default action)。

**定義 default 範例：**
```java
8 default:
9 System.out.println(“休息”);break;
12 }
```

---

## The code avoids comparing floating-point numbers for equality

*   建議阻止直接比較兩個浮點數是否相等。

**改善範例 (使用容忍度 `1e-5`):**
```java
3 if(Math.abs(x – y) < 1e-5) {
4 System.out.println(“X == Y”);//成立
5 }
```

---

## All comments are consistent with the code

*   註解必須與程式碼中的實際參數和邏輯一致。

**改善範例 (註解提及所有參數):**
```java
1 // 計算一年獲利, 傳入參數(int amount, double rate)
2 public void countProfit(int amount, double rate) {
3 _profit = amount * ( 1 + rate );
4 }
```

---

## Divergent Change (發散式改變)

*   一個類別會因為因應**太多變更原因**而需要修改。
*   可透過 **Extract Class** 操作來進行重構，將不同的行為抽出至不同的 Class。
*   **目標：** 重構後遵循**單一職責原則 (Single Responsibility Principle, SRP)**。

**Refactoring by Extract Class 範例：**
*   將 `MailServer` (同時負責收信和寄信) 分解為 `MailSender` 和 `MailReceiver`。

---

## Shotgun Surgery (散彈式修改)

*   每次為因應同一種變更，你必須同時在**許多類別上做出許多修改**。
*   常發生於**複製貼上編程 (Copy and Paste Programming)**。
*   可透過 **Extract Method**, **Move Method** 或 **Move Field** 來進行重構。

**重構方式 (Extract Method)：**
*   將重複的資料庫連線程式碼提取到一個新的類別 `DBConnection` 中，以集中管理。

---

## Primitive Obsession (基本型別癡迷)

*   使用**基本型別 (primitives)**，而不是小型、專用的物件來處理簡單的任務。
*   **問題：** 喪失型別安全；缺乏封裝行為。

**Replacing Primitives with (Value) Objects (使用值物件替換基本型別) 範例：**
*   將 `String postalCode` 提取為一個新的 `PostalCode` 類別，並在其中包含驗證邏輯。

---

## Operation Class (操作類別)

*   類別名稱通常為**動詞 (Verb)** (如 `CreateReport`)，而非物件名詞 (如 `Report`)。
*   這導致很難擴充 Method，且難以運用繼承關係和動態多型的優勢。

**Refactored (重構後):**
*   將操作方法 (例如 `create()`, `display()`, `copy()`) 合併到單一的物件名詞類別 `Report` 中。

---

## Alternative Classes with Different Interfaces (不同介面的替代類別)

*   兩個功能相同但實作和介面不同的類別同時存在。

**重構方式：**
*   **若在同一 Library：** 取 Method 聯集，綜合出一個 Class。
*   **若在不同 Library：** 使用 **Extract Superclass** 創建一個 Super Class，將重複 Method 調整為單一通用 Method。

---

## Refused Bequest (拒絕遺贈)

*   子類別只使用從其父類別繼承的一些方法和屬性，導致層次結構不平衡。
*   違反了**里氏替換原則 (Liskov Substitution Principle)**。

**Refactored 範例：**
*   引入中間抽象層 `MotorVehicle`，將引擎相關方法 (`startEngine()`, `stopEngine()`) 下移，讓 `Bicycle` 不必繼承不必要的方法。

---

## Parallel Inheritances Hierarchies (平行繼承體系)

*   **特徵：** 每當你為一個類別創建子類別時，你需要為另一個類別也創建子類別。
*   **重構方式：** **Defer Identification of State Variables Pattern (延遲狀態變數識別模式)**。

---

## Middle Man (中間人)

*   一個類別主要將工作委託給其他類別，除了轉發方法呼叫之外**幾乎沒有增加任何價值**。

**Middle Man 範例 (`PaymentManager`):**
```java
class PaymentManager {
// ...
public void processPayment(double amount) {
    // Simply forwards the request to PaymentProcessor
    paymentProcessor.processPayment(amount);
}
}
```

---

## Speculative Generality (臆測性泛化/預想未來)

*   編寫程式碼來處理**潛在的未來需求或擴展**，但這些需求目前並不需要。
*   這導致過度設計，增加了不必要的抽象或介面，增加了複雜性。