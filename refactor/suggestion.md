以下僅依 code_smell.md 所列種類，且能從你目前的原始碼結構/檔名直接推論者；增加了更多可疑點與待確認重點。

Unsuitable naming（不合適的命名）

檔案：service/ServiceGeral.java、controller/MensagensAlertas.java、components/CadastroPessoaAnimalComponent.java、service/CriptografaDescriptografa.java、utils/ConverterCSV.java、service/PdfRequestService.java
味道：名稱籠統或偏動作，難以推知單一職責。
建議：以名詞與領域語意重命名，將「動作」變為方法，必要時拆分。
Inconsistent coding standard（不一致的編碼標準）

檔案/命名：ProjectApplication（英） vs 多數類別（葡）、PetsPerdidosController vs PetPerdidosRestController（單複數不一致）、RestController 與 MVC Controller 命名規則不統一（rest 子包 vs 非 rest）
味道：命名風格/語言不一致，降低可讀性。
建議：統一語言與單複數規則；REST/MVC 明確以後綴與套件劃分。
Operation Class（操作類別）

檔案：service/CriptografaDescriptografa.java、utils/ConverterCSV.java、service/PdfRequestService.java
味道：類別名為動作或流程，容易累積手續式程式碼，難以擴充。
建議：重構為名詞導向服務介面（如 CryptoService、CsvConverter、PdfService），協調流程另建 Facade/UseCase。
Middle Man（中間人）

檔案：controller/*Controller.java、controller/rest/*RestController.java
味道：典型薄控制器可能僅轉發至服務，若沒有驗證、裝配或轉換邏輯，等同中間人。
建議：若僅轉發，將控制器精簡為 Request/Response 轉換；共用邏輯上移到攔截器/切面或下沉至服務。
Alternative Classes with Different Interfaces（不同介面的替代類別）

檔案：controller/PetsPerdidosController.java（MVC）與 controller/rest/PetPerdidosRestController.java（REST）；controller/rest/UsuarioRestController.java 與 controller/CadastroPessoaController.java（用戶註冊）
味道：可能為同一領域提供兩組接口，易出現重複邏輯。
建議：確保所有業務僅實作於 service 層，控制器只做 I/O 轉換；共用 DTO/Mapper，避免重複驗證與流程。
Large class / Divergent Change（大類別／發散式改變）—傾向

檔案：service/ServiceGeral.java、controller/MensagensAlertas.java
味道：名稱泛化，易承載多種變更原因。
建議：用 Extract Class 將不同職責（通知、首頁聚合、統計等）拆分。
Message Chains（訊息鏈）—風險點

檔案：model/Usuario.java、Endereco.java、DonoPetPerdido.java、InfoPet.java 等聚合可能在控制器/服務形成鏈式存取。
味道：客戶端可能出現 user.getDono().getEndereco()... 的鏈式呼叫。
建議：在服務或模型提供便捷方法/Facade，避免跨多層物件鏈。
Primitive Obsession（基本型別癡迷）—風險點

檔案：model/Endereco.java 存在，顯示已嘗試對地址分裝；但其他領域值（如 CEP、電話、Email）可能仍以 String/基本型別出現於多類別。
建議：將 CEP/Telefone/Email 提升為值物件並內嵌驗證。
Long parameter list（長參數列表）—風險點

檔案：controller/CadastroAnimalController.java、CadastroPessoaController.java 這類建立/更新動作，常見長參數。
建議：以請求 DTO 或值物件（如 Endereco）聚合參數。
Duplicated Code（重複程式碼）—風險點

檔案：controller/rest/* vs MVC Controller、Usuario 相關功能在多控制器/服務之間可能重複驗證與轉換。
建議：提取共用驗證/映射至服務或組件（Mapper、Validator）。
接下來要精準落槌，請開啟以下檔案供逐段確認與指出具體行號：

controller: CadastroPessoaController.java、PetsPerdidosController.java、rest/PetPerdidosRestController.java、rest/UsuarioRestController.java
service: ServiceGeral.java、PdfRequestService.java、CriptografaDescriptografa.java、UsuarioService.java、SessionService.java
model: Usuario.java、Endereco.java、DonoPetPerdido.java、InfoPet.java
