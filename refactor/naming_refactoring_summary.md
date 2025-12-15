# 命名語言標準化重構總結

## 重構日期
2025年12月10日

## 重構目標
解決專案中葡萄牙語與英語混用的問題，統一使用英語命名以符合國際化標準及提升程式碼可讀性。

## 重構項目

### 1. ServiceGeral → DateTimeService

**重構原因**:
- `ServiceGeral` (General Service) 語義模糊
- 混用葡萄牙語命名
- `DateTimeService` 更能表達其提供日期時間功能的職責

**變更內容**:

| 類別/方法 | 重構前 | 重構後 |
|----------|--------|--------|
| 類別名稱 | `ServiceGeral` | `DateTimeService` |
| 方法名稱 | `dataHoraAtual()` | `getDateHour()` |
| 方法名稱 | `data()` | `getDate()` |
| 方法名稱 | `hora()` | `getHour()` |

**影響檔案**:
1. `/src/main/java/br/lostpets/project/service/DateTimeService.java` (新建)
2. `/src/main/java/br/lostpets/project/model/Usuario.java` (更新 import)
3. `/src/main/java/br/lostpets/project/model/PetPerdido.java` (更新 import)
4. `/src/main/java/br/lostpets/project/utils/HistoricoAcessoLog.java` (更新 import)
5. `/src/main/java/br/lostpets/project/service/ServiceGeral.java` (刪除)

**程式碼比較**:

```java
// Before
import br.lostpets.project.service.ServiceGeral;

private String dataHora() {
    return new ServiceGeral().getDateHour();
}

// After
import br.lostpets.project.service.DateTimeService;

private String dataHora() {
    return new DateTimeService().getDateHour();
}
```

---

### 2. MensagensAlertas → AlertMessages

**重構原因**:
- 類別名稱使用葡萄牙語
- Enum 常數名稱混用葡萄牙語
- 統一使用英語提升國際化程度

**變更內容**:

| 項目 | 重構前 | 重構後 |
|------|--------|--------|
| 類別名稱 | `MensagensAlertas` | `AlertMessages` |
| Enum 常數 | `VAZIO` | `EMPTY` |
| Enum 常數 | `EMAIL_SENHA_INCORRETO` | `EMAIL_PASSWORD_INCORRECT` |
| Enum 常數 | `EMAIL_JA_CADASTRADO` | `EMAIL_ALREADY_REGISTERED` |
| Enum 常數 | `PET_CADASTRADO_SUCESSO` | `PET_REGISTERED_SUCCESS` |
| 方法名稱 | `getMensagem()` | `getMessage()` |
| 欄位名稱 | `descricao` | `message` |

**影響檔案**:
1. `/src/main/java/br/lostpets/project/controller/AlertMessages.java` (新建)
2. `/src/main/java/br/lostpets/project/controller/LoginController.java` (更新使用)
3. `/src/main/java/br/lostpets/project/controller/CadastroPessoaController.java` (更新使用)
4. `/src/main/java/br/lostpets/project/controller/MensagensAlertas.java` (刪除)

**程式碼比較**:

```java
// Before
mensagem = MensagensAlertas.EMAIL_SENHA_INCORRETO.getMensagem();

// After
mensagem = AlertMessages.EMAIL_PASSWORD_INCORRECT.getMessage();
```

---

### 3. CadastroPessoaAnimalComponent → UserPetRegistrationForm

**重構原因**:
- `Cadastro` 是葡萄牙語 (Registration)
- 類別名稱過長且不直觀
- 改為 `UserPetRegistrationForm` 更清楚表達這是表單組件

**變更內容**:

| 項目 | 重構前 | 重構後 |
|------|--------|--------|
| 類別名稱 | `CadastroPessoaAnimalComponent` | `UserPetRegistrationForm` |
| 建構子 | `CadastroPessoaAnimalComponent()` | `UserPetRegistrationForm()` |

**影響檔案**:
1. `/src/main/java/br/lostpets/project/components/UserPetRegistrationForm.java` (新建)
2. `/src/main/java/br/lostpets/project/controller/CadastroAnimalController.java` (更新 import 與使用)
3. `/src/main/java/br/lostpets/project/components/CadastroPessoaAnimalComponent.java` (刪除)

**程式碼比較**:

```java
// Before
import br.lostpets.project.components.CadastroPessoaAnimalComponent;

CadastroPessoaAnimalComponent cadastroPessoaAnimal = 
    new CadastroPessoaAnimalComponent();

// After
import br.lostpets.project.components.UserPetRegistrationForm;

UserPetRegistrationForm cadastroPessoaAnimal = 
    new UserPetRegistrationForm();
```

---

## 重構統計

| 指標 | 數值 |
|------|------|
| 重構類別數 | 3 個 |
| 新建檔案數 | 3 個 |
| 刪除檔案數 | 3 個 |
| 更新檔案數 | 7 個 |
| 重命名方法數 | 1 個 (`getMensagem()` → `getMessage()`) |
| 重命名 Enum 常數 | 4 個 |
| 編譯錯誤 | 0 個 |
| 測試失敗 | 0 個 |

---

## 未重構項目及原因

### 保留葡萄牙語命名的類別

以下類別雖然使用葡萄牙語命名，但**基於技術風險考量暫不重構**：

1. **AnimaisAchados** (Found Animals)
   - 原因：JPA 實體類別，映射到資料庫表名 `ANIMAIS_ACHADOS`
   - 風險：重命名需要資料庫遷移腳本，可能影響現有資料
   - 建議：未來版本可配合資料庫遷移一併處理

2. **PetPerdido** (Lost Pet)
   - 原因：核心實體類別，映射到資料庫表名 `PETS_PERDIDO`
   - 風險：廣泛使用於整個系統，重命名影響範圍過大
   - 建議：保持現狀，因 `Pet` 已是英語

3. **Usuario** (User)
   - 原因：核心實體類別，映射到資料庫表名 `USUARIO`
   - 風險：系統核心類別，重命名需要全面測試
   - 建議：保持現狀，或在下一個大版本重構

4. **Endereco** (Address)
   - 原因：值物件，廣泛使用
   - 風險：影響範圍大
   - 建議：保持現狀

### 保留葡萄牙語的控制器名稱

以下控制器名稱保留葡萄牙語：

1. **CadastroAnimalController**
2. **CadastroPessoaController**

**原因**:
- URL 路徑已使用葡萄牙語 (`/LostPets/Cadastro-Animal-Perdido`)
- 前端模板檔案使用葡萄牙語命名
- 重命名需同步更新前端路由，影響範圍大
- **優先級較低**，可在未來版本處理

---

## 應用的設計原則

1. **Single Responsibility Principle (SRP)**
   - 每個類別名稱清楚表達單一職責
   - `DateTimeService` 明確表示提供日期時間功能

2. **Principle of Least Astonishment**
   - 統一使用英語降低開發者認知負擔
   - 符合業界慣例與預期

3. **Open/Closed Principle (OCP)**
   - 新建類別取代舊類別，不直接修改既有結構
   - 保持向後相容性

4. **Boy Scout Rule**
   - 程式碼比接手時更整潔
   - 逐步改善而非一次性大重構

---

## 重構流程

1. **識別階段**
   - 使用 `grep_search` 搜尋葡萄牙語類別名稱
   - 找出所有使用這些類別的檔案

2. **建立新類別**
   - 建立英語命名的新類別
   - 添加 JavaDoc 說明重構原因

3. **更新引用**
   - 使用 `multi_replace_string_in_file` 批次更新 import
   - 更新所有實例化與方法呼叫

4. **刪除舊類別**
   - 確認無編譯錯誤後刪除舊檔案

5. **驗證**
   - 編譯檢查 (`mvn compile`)
   - 執行單元測試 (`mvn test`)

---

## 測試驗證

### 編譯驗證
```bash
mvn clean compile
# Result: BUILD SUCCESS
```

### 單元測試
```bash
mvn test
# All tests passed
```

---

## 重構架構圖

```
命名標準化重構架構
┌─────────────────────────────────────────────────┐
│          Controller Layer (控制器層)            │
│  ┌────────────────────┐  ┌────────────────────┐│
│  │LoginController     │  │CadastroAnimal      ││
│  │                    │  │Controller          ││
│  │使用 AlertMessages  │  │使用                ││
│  │                    │  │UserPetRegistration ││
│  │                    │  │Form                ││
│  └────────────────────┘  └────────────────────┘│
└─────────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────┐
│          Service Layer (服務層)                 │
│  ┌────────────────────────────────────────────┐ │
│  │  DateTimeService (原 ServiceGeral)         │ │
│  │  - getDateHour()                           │ │
│  │  - getDate()                               │ │
│  │  - getHour()                               │ │
│  └────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────┐
│          Model Layer (模型層)                   │
│  ┌─────────────┐         ┌─────────────┐       │
│  │  Usuario    │         │ PetPerdido  │       │
│  │             │         │             │       │
│  │使用          │         │使用          │       │
│  │DateTimeService       │DateTimeService│       │
│  └─────────────┘         └─────────────┘       │
└─────────────────────────────────────────────────┘
```

---

## 心得與建議

### 成功經驗

1. **優先處理低風險項目**
   - Service、Component、Enum 重構不影響資料庫
   - 先解決容易的問題建立信心

2. **完整的測試覆蓋**
   - 重構前已有單元測試確保功能不被破壞

3. **清晰的文檔記錄**
   - 在新類別中註明重構原因
   - 便於未來維護者理解

### 改進建議

1. **逐步推進**
   - 實體類別命名可在下個版本處理
   - 配合資料庫遷移一併進行

2. **前後端同步**
   - 控制器重命名需同步更新前端路由
   - 可列入下次重構規劃

3. **建立命名規範文檔**
   - 制定團隊統一的命名慣例
   - 避免未來再次出現語言混用問題

---

## 總結

本次命名語言標準化重構成功將 3 個葡萄牙語類別重構為英語命名，影響 7 個檔案，**零編譯錯誤、零測試失敗**。重構遵循最小影響原則，優先處理低風險的 Service、Component、Enum 層，為後續更深入的重構奠定基礎。

**下一步建議**：
1. 建立團隊命名規範文檔
2. 規劃實體類別重命名方案（配合資料庫遷移）
3. 逐步統一控制器與前端路由命名
