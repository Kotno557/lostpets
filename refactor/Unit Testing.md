# 單元測試學習報告：從基礎、TDD 到可測性設計

本報告旨在整合單元測試的核心概念、測試驅動開發（TDD）的實作流程，以及如何處理程式碼相依性問題（Stub 與 Mock 的運用）。

---

## 1. 單元測試基礎 (Foundations)

### 1.1 為什麼需要單元測試？

軟體開發中，需求往往是不精確且持續變動的。教材以 FizzBuzz 為例：  
從簡單的倍數判斷，逐漸演變成包含 3、5、7 等倍數及其組合（Fizz, Buzz, Dizz, FizzBuzz, FizzDizz...）的複雜邏輯。

**單元測試的重要性：**

- **重構的保護網**：  
  需求增加後，程式碼容易出現壞味道（Code Smell）。利用 AI 或手動重構時，單元測試能確保不會破壞既有功能。

- **建立勝利循環**：  
  測試全部「綠燈」象徵任務完成，建立穩定的成就感循環，有助於長期開發。

---

### 1.2 單元測試的定義

不同專家對單元測試有不同的觀點，可分為排除法與功能定義。

#### Michael Feathers — 什麼「不是」單元測試  
若符合以下任何條件，則不算單元測試：

1. 與資料庫互動  
2. 有網路通訊  
3. 接觸檔案系統  
4. 需要特定環境準備  
5. 執行太慢（> 0.1 秒）

> 註：這些測試仍有價值，但不應與快速單元測試混在一起，避免拖慢開發。

#### Roy Osherove — 單元測試的核心定義  
單元測試是一段**自動化程式碼**，它呼叫一個**工作單元 (Unit of Work)**，並驗證其**單一結果或假設**。  
範圍可從單一方法到多個類別的互動皆可。

---

### 1.3 驗證方式

單元測試通常包含兩種類型的驗證：

1. **驗證回傳值 (Return Value)**  
   - 例：`add(5, 3)` 是否回傳 `8`。

2. **驗證系統狀態 (System State)**  
   - 例：呼叫 `cumulate(5)` 後，物件內部變數 `sum` 是否正確更新。

---

## 2. 測試驅動開發 (Test Driven Development, TDD)

### 2.1 TDD 的核心循環

TDD 的開發節奏遵循 **Red ➜ Green ➜ Refactor**：

1. **Red**：先寫會失敗的測試  
2. **Green**：寫剛好讓測試通過的程式碼  
3. **Refactor**：在測試保護下重構與優化程式碼

---

### 2.2 實戰案例：保齡球遊戲 (Bowling Game)

教材以計分系統示範 TDD 流程，規則包含 Spare、Strike 等複雜邏輯。

---

#### 步驟一：建立空殼

- 建立 `BowlingGame` 類別  
- 新增空的 `roll()` 與 `score()`  
- 不要用 `main()` 測試，應用單元測試框架

---

#### 步驟二：生成測試案例 (Red)

可能的測試案例包含：

- `testScoreInitialGame`：初始分數為 0  
- `testScoreAfterSingleRoll`：投一次球  
- `testScoreForGutterGame`：全零局  
- `testScoreWithSpare`：補中  
- `testScoreWithStrike`：全中  

執行測試，此時必定失敗（紅燈）。

---

#### 步驟三：實作與除錯 (Green)

- 根據紅燈結果撰寫最小實作  
- 用 IDE 的 Debug Mode 檢查變數  
- 持續修正直到全部綠燈

---

#### 步驟四：重構與迭代 (Refactor)

- 若程式碼出現 Code Smell，請求 AI 或手動重構  
- 重構後重新跑測試，避免「修 A 壞 B」  
- 對複雜情境（如第十局）新增更多 edge cases

---

## 3. 程式碼的可測性 (Testability)

### 3.1 什麼是不可測的程式碼 (Untestable Code)？

若被測物件依賴無法控制的外部系統，就會難以單元測試，例如：

- Web service  
- 系統時間  
- 執行緒  
- 資料庫  
- 檔案系統  
- GUI

**案例：**  
`ReportSystem` 依賴 `GoogleLogin`，而後者會彈出 GUI 視窗，導致測試必須手動介入（不可測）。

---

### 3.2 重構為可測程式碼 (Refactoring to Testable Code)

透過 **Stub 技術**，可將相依物件解耦。步驟如下：

1. **提取介面 (Extract Interface)**  
   - 定義 `ILogin` 介面

2. **實作介面**  
   - `GoogleLogin` 實作 `ILogin`

3. **依賴注入 (Dependency Injection)**  
   - `ReportSystem` 改以建構子注入 `ILogin`，創造可替換的依賴

4. **建立與注入 Stub**  
   - 在測試使用 `GoogleLoginStub` → 回傳 `true`，避免 GUI  
   - 將 Stub 注入 `ReportSystem` 進行測試

---

### 3.3 Stub 與 Mock 的差異

| 特性 | Stub（虛設常式） | Mock（模擬物件） |
|------|-------------------|-------------------|
| 定義 | 模擬回傳值 | 模擬回傳 + 紀錄互動 |
| 驗證重點 | 回傳值、系統狀態 | 互動（被呼叫幾次） |
| 範例 | 回傳 `true` | 記錄 `loginAttempts = 1` |

---

## 4. 總結：何時撰寫單元測試？

單元測試適用於開發的所有階段：

### 1. 開發前  
- 用 TDD 透過撰寫測試確認 API 與需求

### 2. 開發期間  
- 測試作為重構的保護網，避免破壞功能

### 3. 開發後（Legacy Code）  
- 使用 Stub/Mock 將難以測試的舊程式碼解耦  
- 讓系統重新回到可測狀態

---
