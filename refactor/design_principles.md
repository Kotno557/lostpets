# Design Principles
## 物件導向設計原則
### Shin-Jie Lee (李信杰)
### Associate Professor
### Department of CSIE
### National Cheng Kung University

## Design Principles
*   **SRP**: The Single Responsibility Principle
*   **OCP**: The Open-Closed Principle
*   **LSP**: The Liskov Substitution Principle
*   **ISP**: The Interface Segregation Principle
*   **DIP**: The Dependency Inversion Principle
*   Encapsulate What Varies
*   Favor Composition Over Inheritance
*   Least Knowledge Principle
*   Acyclic Dependencies Principle (ADP)
*   Don’t Repeat Yourself (DRY)
*   Keep It Simple Stupid (KISS)

---

# 說不清的 Single Responsibility Principle?
### 兩種較具體的判定法

## SRP: THE SINGLE RESPONSIBILITY PRINCIPLE1
*   **歷史描述**: 一個模組應該只有一個，而且只有一個，改變的原因。
*   **重新闡述**: 一個模組應該對一個，而且只有一個，**角色** (actor) 負責。
*   如果一個類別中的三個方法對應到三個非常不同的角色，則違反了 SRP。

## SRP: THE SINGLE RESPONSIBILITY PRINCIPLE2
*   解決此問題最明顯的方法可能是**將資料與函式分離**。
*   每個類別只包含其特定功能所需的原始碼。

## 但是，判定SRP常是主觀的
*   對於 SRP 的滿足程度，可能存在主觀判定上的分歧。
*   爭辯的結果可能是先擱置不理，直到遇到「痛了」才討論。
*   「痛了」指的是當一個類別久了變成 **Large Class** 時的感受。
*   為了面對拆解 Large Class 的問題，是否有**更具體**的 SRP 判斷與拆解責任 (Responsibility) 方式？

## 兩種更具體的SRP判定法
1.  **結構內聚力判定法**
    *   基於方法 (Method) 間的結構關係。
    *   **基於 Hitz&Montazeri’s LCOM4 (Lack of Cohesion in Methods) 的內聚力度量觀念**。
    *   首先，檢視一個 class 中的 methods 是否相互依賴或共用屬性。
    *   接著，將 methods 根據依賴分群，**每一群可能代表一個 responsibility**。
    *   方法 A 與 B 相關的條件有二：1. 它們都存取相同的類別層級變數，或 2. A 呼叫 B，或 B 呼叫 A。

### 結構內聚力判定法範例 (Example 2)

```java
class MailServer {
    public void send(String to, String content){ encode(content); //… }
    private String encode(String content){ // encode content; }
    public void receive(String account) { connectViaPOP3(); // … }
    private void connectViaPOP3(){ // connect to a server via POP3 protocol; }
}
```

*   `send()` 依賴 `encode()`，成為一群。
*   `receive()` 依賴 `connectViaPOP3()`，成為一群。
*   **可將兩群 Method 拆解成兩個 Class (Refactoring by Extract Class)**。

```java
class MailSender {
    public void send()
    private String encode()
}
class MailReceiver {
    public void receive()
    private void connectViaPOP3()
}
```

*   結構內聚力判定法的精神是將一個 class 內**不互相依賴的 method 群拆解出去**。
*   進而維持**高內聚與低耦合 (high cohesion, loose coupling)** 的原則。
*   此方法提供了一個比較具體的 single/multiple responsibility 判定參考，即使有時需要搭配重構。

2.  **語意結合判定法**
    *   來自 “Head First Software Development”。
    *   Class name 代表一個物件的語意，method name 代表此物件的行為語意。
    *   可藉由**兩個 name 的結合語句來判定是否具合理語意**。

### 語意結合判定法範例 (Example)
假設 class 為 `Automobile`，包含多個方法：

```java
Automobile
+ start()
+ stop()
+ changeTires()
+ drive()
+ wash()
+ checkOil()
```

*   **判定方式**：依每個 method 填入語句：「The <class name> <method name> itself.」。
*   接著，判定此句子是否具合理語意：若合理則留下，若不合理則考慮將此 method 移出此 class。

| Class Name | Method Name | 遵循 SRP | 違反 SRP |
| :---: | :---: | :---: | :---: |
| Automobile | starts |  |  |
| Automobile | stops |  |  |
| Automobile | changes tires |  |  |
| Automobile | drives |  |  |
| Automobile | washes |  |  |
| Automobile | checks (oil) |  |  |
*（注意：表格內容為根據 提供的核選框所重建）*

*   **拆解結果**：拆解後的 `Automobile` class 在語意上符合 Single Responsibility Principle (僅剩 `start()` 和 `stop()`)。
*   不符合 SRP 的責任被拆解到其他類別，例如：

```java
Driver
+ drive(a: Automobile)

CarWash
+ wash(a: Automobile)

Mechanic
+ changeTries(a: Automobile)
+ checkOil(a: Automobile)
```

## 總結：兩者判定法的限制
*   當一個 class 內 method 間結構關係複雜時，**結構內聚力判定法可能較困難**。
*   當一個 class name 語意太 general 時 (如 `XXXManager`/`Controller`)，會讓所有 method name 都可與 class name 語意結合，造成**語意結合判定失效**。
*   因此，兩者可以互補參考使用。

---

# Open-Close Principle (開放關閉原則)
## Open-Close Principle (開放關閉原則)
*   **Open for extension, but closed for modification**。
*   一個模組必須有彈性的開放往後的擴充，並且避免因為修改而影響到使用此模組的程式碼。

### 範例一：天氣播報系統
*   **需求描述 1**：天氣資料包含特定區域 (如美國或亞洲) 的溫度、濕度和氣壓。

```
USWeatherData
getTemperature()
getHumidity()
getPressure()

AsiaWeatherData
getTemperature()
getHumidity()
getPressure()
```

*   **需求描述 2**：系統提供三種氣象播報顯示 (`CurrentConditionsDisplay`, `StatisticDisplay`, `ForecastDisplay`)，當天氣資料更新時，所有顯示將會即時更新。
*   在初步設計中，例如 `USWeatherData` 類別會直接實例化並呼叫所有具體的 Display 類別。
*   **問題**：當有新的 Display 需新增時，則需修改此程式碼 (即 `USWeatherData` 類別)。

### 應用 Open-Closed Principle
*   引入一個介面 (Interface)：`<<interface>> Display`，包含 `update(Temperature, Humidity, Pressure)` 方法。
*   所有具體的 Display 類別 (如 `CurrentConditionsDisplay` 等) 實作此介面。
*   `USWeatherData` (或 `AsiaWeatherData`) 類別只依賴這個抽象的 `Display` 介面，並持有 `Display[]` 陣列。

## Open-Close Principle at the Architecture Level
*   將流程劃分為類別 (classes)，並將這些類別分離為元件 (components)。
*   所有元件關係都是**單向的 (unidirectional)**。
*   階層中較高層次的元件受到保護，不受對較低層次元件所做的更改的影響。
*   這就是 OCP 在架構層次上的運作方式。

---

# Liskov Substitution Principle (LSP)
## LSP: The Liskov Substitution Principle
*   由 Barbara Liskov 在 1988 年提出，作為定義子型別 (subtypes) 的一種方式。
*   **定義**：對於類型 S 的每個物件 o1，都有一個類型 T 的物件 o2，使得對於所有根據 T 定義的程式 P，當 o1 替換 o2 時，P 的行為保持不變，則 S 是 T 的子型別。
*   違反 LSP 的典型例子是 **square/rectangle problem** (正方形/矩形問題)。

---

# Interface Segregation Principle (ISP)
## ISP: The Interface Segregation Principle1
*   **問題描述**：假設 User1 只使用 op1，User2 只使用 op2，User3 只使用 op3。
    *   對 OPS 中 op2 原始碼的更改，將會迫使 User1 重新編譯和重新部署 (recompiled and redeployed)，即使 User1 所關心的內容實際上沒有改變。

## ISP: The Interface Segregation Principle2
*   **解決方法**：透過將操作分離到不同的介面中來解決這個問題。
*   如此一來，對 OPS 進行 User1 不關心的更改，將不會導致 User1 重新編譯和重新部署。

---

# Dependency Inversion Principle (依賴反向原則)
## Dependency Inversion Principle (依賴反向原則)
*   軟體設計程序始於簡單高層次的概念 (Conceptual)，再增加細節和特性。
*   設計應從高層次的模組開始，再設計低層詳細的模組。
*   **DIP 原則**：
    *   **高階模組不應該依賴低階模組**。
    *   **兩者必須依賴抽象 (即抽象層)**。

### 違反 Dependency Inversion Principle
*   在以下結構中，`Company` (高層模組) 直接依賴具體的 `Worker` (低層模組)。

```java
class Worker {
    public void work () {⋯..}
}
class Company {
    Worker worker;
    public void setWorker (Worker w) {worker = w;}
    public void produce() {worker.work ();}
}
```

*   **問題**：如果公司需要增聘具有專長的員工 (`SpecializedWorker`)，則必須修改複雜的 `Company` 模組 (高層模組) 的程式碼，這違反了 DIP 原則。

### 符合 Dependency Inversion Principle
*   在公司模組與員工模組之間介入一種**「抽象層」(Abstract Layer)**，通常是一種介面，亦即 `IWorker` 類別。
*   公司模組與員工模組皆依存於這種抽象層。
*   **Program to an interface, not an implementation** (針對介面而非實作進行程式設計)。

```java
class Company {
    IWorker worker;
    publich void setWorker (IWorker w) { // worker 依賴 IWorker (抽象)
        worker = w;
    }
    public void produce () {
        worker.work ();
    }
}
```

## Dependency Inversion Principle 優點
*   高層的抽象層包含宏觀和重要商務邏輯。
*   低層的實作層包含實作相關演算法與次要商業邏輯。
*   DIP 可讓實作改變時，商業邏輯無須變動。

## Tips (DIP)
*   將 DIP 視為一項規則是不切實際的。
*   例如，Java 中的 `String` 類別是具體的 (concrete)，但它非常穩定，極少改變且受到嚴格控制。
*   當涉及作業系統和平台設施等穩定的背景時，我們傾向於忽略 DIP，因為我們可以依賴它們不會改變。
*   優秀的軟體設計師致力於降低介面 (interfaces) 的波動性，嘗試在不更改介面的情況下為實作增加功能。

---

# Encapsulate what varies (封裝改變)
## Encapsulate what varies (封裝改變)
*   將易改變之程式碼部份封裝起來。
*   以後若需修改或擴充這些部份時，能避免不影響到其他不易改變的部份。
*   將潛在可能改變的部份隱藏在一個**介面 (Interface)** 之後，並成為一個**實作 (Implementation)**。
*   爾後當此實作部份改變時，參考到此介面的其他程式碼部份將不需更改。

### 範例一：PizzaStore (初探)
*   **問題**：當有新口味的 Pizza 時，`PizzaStore` 類別則需新增一個 Method (`createNYStyleCheesePizza`, `createChicagoStylePizza` 等)。
*   **應用**：應將 Pizza 建立的邏輯 (What Varies) 封裝到子類別中 (`NYPizzaStore`, `ChicagoPizzaStore`)，每個子類別實作自己的 `createPizza()`。

### 範例二：排版策略 (Linebreaking Strategy)
*   **需求描述 4**：當需要新增新的排版方式時 (例如 Simple Composition, Tex Composition, Array Composition)。
*   **初步設計問題**：若將所有排版邏輯 (`simpleCompose()`, `textCompose()`, `arrayCompose()`) 放在 `Composition` 類別中，並用 `if/else if/else` 判斷排版類型。
*   **問題**：當需要新增新的排版策略時，`Composition` 類別則需要被修改。

*   **應用原則 (Encapsulate what Varies)**：
    *   將排版邏輯 (What Varies) 封裝到一個抽象介面 `<<interface>> Compositor` 中，該介面包含 `compose()` 方法。
    *   具體的排版策略 (如 `SimpleCompositor`, `TexCompositor` 等) 實作此介面。
    *   `Composition` 類別接收一個 `Compositor` 物件作為參數，只呼叫 `comp.compose()`。

---

# Favor composition over inheritance (善用合成取代繼承)
## Favor composition over inheritance (善用合成取代繼承)
*   物件類別可藉由 **Composition (合成)** 來達到**多型 (Polymorphism)** 與**程式碼重用 (Code Reuse)** 的效果，而非一定得使用繼承。
*   不要一味地使用繼承，只為了達到程式碼的重用。
*   **只有當兩者真的有 IS-A 的關係時才使用繼承**。
*   有別於繼承，Composition 可在 Runtime 時更有彈性地動態新增或移除功能。

### 繼承 (Inheritance) 優缺點
*   **優點**：透過簡便的擴充 (Extend) 繼承，就可以實做新的功能。
*   **缺點**：
    *   **父類別修改會影響到子類別**：因為子類別繼承父類別的屬性和方法，一旦修改，將可能會影響到所有繼承它的子類別。
    *   **無法在執行時期改變所需物件**：從父類別實做繼承，無法在執行時期設定不同物件以呼叫不同的 Method 功能。

### 合成 (Composition) 優缺點
*   **優點**：
    *   **封裝性良好**：物件透過介面 (interfaces) 存取被合成或被包含的物件。
    *   **執行時期動態組合新功能**：例如 `Sale` 物件可以在執行時期透過 `setElement` 動態設定不同的 `Element` 物件。
*   **缺點**：
    *   造出較多物件。

---

# Least Knowledge Principle (最小知識原則)
## Least Knowledge Principle (最小知識原則)
*   設計系統時必須注意類別的數量，並且避免製造出太多類別之間的耦合關係。
*   **知道子系統中的元件越少越好**。

### 範例一：家庭劇院系統
*   **初步設計 (違反最小知識原則)**：`Client` 類別直接實例化並呼叫家庭劇院中所有設備 (如 `PopcornPopper`, `TheaterLights`, `Screen`, `Projector`, `Amplifier`, `DVDPlayer`) 的多個方法。
*   **問題**：當系統有更新時 (如新增設備或設備功能修改時)，`Client` 的程式碼需要配合修改。

*   **應用 Least Knowledge Principle**：
    *   引入一個新的高層次元件 `WatchAMovie` (或稱為 Facade 模式)。
    *   將所有複雜的操作細節封裝在 `WatchAMovie` 類別的 `watchAMovie()` 方法中。
    *   `Client` 只需要知道並呼叫 `WatchAMovie` 類別的單一方法即可。

---

# Acyclic Dependencies Principle (ADP)
## Acyclic Dependencies Principle (ADP)
*   **定義**：套件或元件的依賴圖應該沒有循環 (Robert C. Martin 語錄)。
*   ADP 主要針對的是**套件之間的關係**，適用於架構級。
*   **範例**：Dependency inversion principle (依賴反向原則)。
*   **循環打破策略 (Cycle breaking strategies)**：建立一個新的套件，並將共同的依賴項移至該處。

---

# Don’t Repeat Yourself (DRY)
## Don’t Repeat Yourself (DRY)
*   對於每個知識點，系統中都只有**一個明確而權威的表示**。
*   **單一事實源 (Single Source of Truth)**。
*   適用於所有的軟體工作，包括文件、架構和設計、測試程式和原始程式。

### Avoid duplicate code by separation (透過分離避免重複程式碼)
*   **問題**：在 `UserNameUtil`, `BookUtil`, `StoreUtil` 類別中，重複執行了建立資料庫連線的程式碼 (Class.forName, DriverManager.getConnection)。
*   **解決**：將重複的連線建立邏輯分離到一個專門的類別 `DBConnection` 中，讓其他工具類別呼叫此靜態方法。

### Avoid duplicate code by abstraction (透過抽象化避免重複程式碼)
*   **問題**：`FristDate` 和 `SecondDate` 類別中，`validate(e: Event)` 程式碼幾乎相同 (Nearly identical code)。
*   **解決**：將共同的驗證邏輯抽象化到一個父類別 `Date` 中，讓 `FristDate` 和 `SecondDate` 繼承並重用該邏輯。

---

# Keep It Simple Stupid (KISS)
## Keep It Simple Stupid (KISS)
*   簡潔性應該是設計和開發中的主要目標。
*   主張採用直接且簡單的解決方案，避免不必要的複雜性。
*   簡潔是軟體系統設計的重要目標，應避免引入不必要的複雜性。

### Keep It Simple Stupid (KISS) 好處
*   **Ease of Understanding (易於理解)**：簡單的解決方案對於開發人員和最終用戶都更容易理解。
*   **Reduced Errors (減少錯誤)**：複雜的系統因其複雜性而更容易出錯。
*   **Improved Maintenance (改善維護)**：簡單的系統更容易長期維護。
*   **Faster Development (加快開發)**：開發人員可以專注於實施基本功能，而不會被不必要的複雜性拖累。
*   **Enhanced Scalability (增強可擴展性)**：當出現新需求或用戶需求變化時，與複雜的架構相比，簡單的系統可以用更少的努力進行修改或擴展。

### Apply KISS Principle (應用 KISS 原則)
*   **Identify Core Objectives (確定核心目標)**：確定基本目標和要求。
*   **Focus on Essentials (專注於要素)**：優先考慮實現目標所必需的基本功能或組件。
*   **Simplify Design and Workflow (簡化設計和工作流程)**：消除多餘的步驟或不必要的複雜性。
*   **Prioritize Clarity and Understandability (優先考慮清晰度和可理解性)**：確保解決方案清晰且易於被所有相關方理解。
    *   在文件和溝通中使用簡單明了的語言。
*   **Iterate and Refine (迭代和完善)**：不斷審查和完善解決方案以進一步簡化。
*   **Use Simple Tools and Techniques (使用簡單的工具和技術)**：避免在工具和技術選擇中引入不必要的複雜性。

---

# References
*   “Clean Architecture: A Craftsman's Guide to Software Structure and Design,” Robert C. Martin, Prentice Hall, 2017.
*   “Clean Code: A Handbook of Agile Software Craftsmanship,” Robert C. Martin, Prentice Hall, 2008.
*   Refactoring for Software Design Smells Managing Technical Debt. G. Suryanarayana, G. Samarthyam, T. Sharma. (2014)
*   Head First Software Development. Dan Pilone, Russ Miles. (2007)