# Lab07 — Game Store: Software Design & Principles Explanation

โปรเจกต์: `lab07-673380065-6-sec2` (Spring Boot + Thymeleaf + PostgreSQL)
Package root: `cp.com.lab076733800656sec2`

---

## ส่วนที่ 1: Software Design & Principles Explanation

### 1.1 สถาปัตยกรรมและ GRASP Patterns

โปรเจกต์นี้แบ่งเลเยอร์ออกเป็น 4 ส่วนหลัก:

```
controller/  GameController.java
service/     GameService.java
repository/  GameRepository.java
model/       Game.java
strategy/    DiscountStrategy, DiscountContext, NoDiscountStrategy,
             StudentDiscountStrategy, SeasonalSaleStrategy
```

| Layer | ไฟล์ | หน้าที่ |
|---|---|---|
| Entity (Model) | `model/Game.java` | เก็บโครงสร้างข้อมูลเกม (mapping ไปตาราง `games`) และตรรกะที่ผูกกับตัวมันเอง เช่น `getFinalPrice()`, `getDiscountName()` |
| Repository | `repository/GameRepository.java` | เข้าถึงข้อมูลจากฐานข้อมูล (extends `JpaRepository<Game, Integer>`) |
| Service | `service/GameService.java` | ตรรกะทางธุรกิจ (business logic) ระหว่าง Controller กับ Repository |
| Controller | `controller/GameController.java` | รับ HTTP Request/ส่ง Response กำหนดเส้นทาง (routing) และเลือก View |

การแบ่งหน้าที่นี้สอดคล้องกับ GRASP Patterns ดังนี้:

**Controller Pattern**
`GameController` ทำหน้าที่เป็น "ตัวกลาง" รับ request จาก browser (`@GetMapping`, `@PostMapping`) แล้วส่งต่อให้ `GameService` จัดการ ไม่ได้เขียน business logic หรือ query ฐานข้อมูลเองใน Controller เลย เช่น เมธอด `save()` (`GameController.java:34-38`) แค่รับ `Game` object แล้วเรียก `gameService.saveGame(game)` — Controller จึงมีหน้าที่แค่ "ประสานงาน" ไม่ใช่ "ทำงานเอง"

**High Cohesion (ความเชื่อมโยงในคลาสสูง)**
แต่ละคลาสมีหน้าที่เดียวที่ชัดเจน:
- `GameRepository` ทำแค่เรื่อง data access (สืบทอดจาก `JpaRepository` ทำให้ได้ CRUD ทันทีโดยไม่ต้องเขียนเพิ่ม)
- `GameService` ทำแค่ประสาน business logic (`getAll`, `saveGame`, `getById`, `deleteByid`)
- `GameController` ทำแค่ routing/HTTP handling

เพราะทุกเมธอดในคลาสเดียวกันมี "จุดประสงค์เดียวกัน" คลาสจึงมี cohesion สูง — เปลี่ยนแปลงส่วนหนึ่งไม่กระทบส่วนที่ไม่เกี่ยวข้อง

**Low Coupling (การพึ่งพากันต่ำ)**
`GameController` ไม่รู้จัก `GameRepository` เลย — มันพึ่งพาแค่ `GameService` เท่านั้น (`GameController.java:19`) และ `GameService` ก็ไม่รู้เรื่อง HTTP หรือ View ใดๆ พึ่งพาแค่ `GameRepository` (`GameService.java:13`) การพึ่งพากันแบบเป็นชั้นๆ (layered) แบบนี้ทำให้เปลี่ยน Controller โดยไม่กระทบ Repository ได้ หรือเปลี่ยนจาก JPA ไปใช้ MyBatis ก็กระทบแค่ Repository/Service โดยไม่กระทบ Controller

**Information Expert (ผู้เชี่ยวชาญข้อมูล)**
ตรรกะการคำนวณราคาสุทธิ (`getFinalPrice()`) และชื่อส่วนลด (`getDiscountName()`) ถูกวางไว้ใน `Game.java:41-56` เพราะ `Game` เป็นคลาสที่ "มีข้อมูล" (`price`, `discountType`) ที่จำเป็นต่อการคำนวณอยู่แล้ว ตาม Information Expert ผู้ที่ถือข้อมูลที่จำเป็นต่อการทำงานควรเป็นผู้รับผิดชอบทำงานนั้น (ในที่นี้ `Game` มอบหมายงานคำนวณจริงต่อให้ `DiscountContext` อีกที ซึ่งเป็นการประยุกต์ Information Expert ร่วมกับ Indirection)

**Indirection (ตัวกลาง/ชั้นแทรก)**
`DiscountContext` (`strategy/DiscountContext.java`) เป็นตัวกลางที่แยก `Game` ออกจาก logic การเลือกและคำนวณส่วนลดจริง (`DiscountStrategy` implementations) `Game` ไม่จำเป็นต้องรู้ว่ามี strategy กี่แบบ หรือเงื่อนไขการเลือกเป็นอย่างไร — มันแค่เรียก `discountContext.matchingDiscount(price, discountType)` (`Game.java:52-56`) การมีชั้นกลางนี้ทำให้ `Game` (Entity) ไม่ผูกติดกับรายละเอียดของอัลกอริทึมคำนวณส่วนลดโดยตรง

---

### 1.2 หลักการ SOLID ในระบบ

**S — Single Responsibility Principle**
แต่ละคลาสมีหน้าที่เดียว: `GameController` จัดการ HTTP, `GameService` จัดการ business flow, `GameRepository` จัดการ data access, และแต่ละ `*DiscountStrategy` รับผิดชอบการคำนวณส่วนลดแบบเดียว (เช่น `StudentDiscountStrategy` คำนวณเฉพาะส่วนลดนักศึกษา 10% — `StudentDiscountStrategy.java:5-8`) ไม่มีคลาสไหนทำหลายหน้าที่ปนกัน

**O — Open/Closed Principle**
ระบบส่วนลดถูกออกแบบให้ "เปิดสำหรับการขยาย แต่ปิดสำหรับการแก้ไข" ผ่าน `DiscountStrategy` interface (`strategy/DiscountStrategy.java`) — ถ้าต้องเพิ่มส่วนลดแบบใหม่ (เช่น "ส่วนลดวันเกิด") สามารถสร้างคลาสใหม่ที่ implements `DiscountStrategy` ได้เลย โดยไม่ต้องแก้ `Game.java` หรือคลาส strategy เดิมที่มีอยู่แล้ว (รายละเอียดเพิ่มเติมในหัวข้อ 1.3)

**L — Liskov Substitution Principle**
`NoDiscountStrategy`, `StudentDiscountStrategy`, และ `SeasonalSaleStrategy` implements `DiscountStrategy` ตัวเดียวกัน และสามารถใช้แทนกันได้ทุกที่ที่ต้องการ `DiscountStrategy` โดยไม่ทำให้พฤติกรรมของ `DiscountContext.matchingDiscount()` (`DiscountContext.java:5-17`) ผิดเพี้ยนไป — ทุกคลาสรับ `double price` แล้วคืนค่า `double` ที่เป็นราคาที่คำนวณแล้วเสมอ ตรงตาม contract ของ interface

**I — Interface Segregation Principle**
`DiscountStrategy` มีเพียงเมธอดเดียวคือ `calculate(double price)` (`DiscountStrategy.java:3-5`) ทำให้เป็น interface ที่เล็กและเจาะจง (lean interface) ไม่มีเมธอดที่ implementer ต้อง implement โดยไม่ได้ใช้งานจริง

**D — Dependency Inversion Principle**
`GameController` ไม่ได้ขึ้นกับ `GameServiceImpl` (concrete class) โดยตรงในทางความหมาย แต่ Spring จะ inject bean ของ `GameService` ให้ผ่าน `@Autowired` (`GameController.java:18-19`) — และ `GameService` เองก็ไม่ได้สร้าง `GameRepository` เอง (`new GameRepositoryImpl()`) แต่ Spring Container จัดการสร้าง implementation ของ `JpaRepository` แล้ว inject ให้ (`GameService.java:12-13`) เลเยอร์บนจึงพึ่งพา "สัญญา" (interface/abstraction) มากกว่ารายละเอียดการ implement จริง

> หมายเหตุ: ในจุดนี้ `DiscountContext.matchingDiscount()` (`DiscountContext.java:6-16`) ยังใช้ `new StudentDiscountStrategy()`, `new SeasonalSaleStrategy()` ตรงๆ (ไม่ได้ inject ผ่าน Spring) ซึ่งต่างจากเลเยอร์ Controller/Service/Repository ที่ Spring จัดการ DI ให้ทั้งหมด — จุดนี้ยัง "ทำตาม" DIP ในระดับ compile-time ผ่าน interface `DiscountStrategy` แต่ยังไม่ใช้ DI ของ Spring ในการเลือก instance

---

### 1.3 Strategy Pattern สำหรับคำนวณส่วนลดราคาเกม

**องค์ประกอบ:**

| ส่วนประกอบ | ไฟล์ | บทบาท |
|---|---|---|
| Strategy Interface | `DiscountStrategy.java` | นิยาม contract `calculate(double price): double` |
| Concrete Strategy | `NoDiscountStrategy.java` | ไม่มีส่วนลด คืนราคาเดิม |
| Concrete Strategy | `StudentDiscountStrategy.java` | ลด 10% (`price - price * 0.1`) |
| Concrete Strategy | `SeasonalSaleStrategy.java` | ลด 20% (`price - price * 0.2`) |
| Context | `DiscountContext.java` | เลือก strategy ที่เหมาะสมตาม `discountType` แล้วเรียกใช้ |

**วิธีทำงาน:**
1. `Game` (Entity) เก็บ field `discountType` เป็น `String` (เช่น `"NONE"`, `"STUDENT"`, `"SEASONAL"`)
2. เมื่อเรียก `game.getFinalPrice()` (`Game.java:52-56`) จะสร้าง `DiscountContext` แล้วส่ง `price` และ `discountType` เข้าไป
3. `DiscountContext.matchingDiscount()` (`DiscountContext.java:5-17`) ใช้ `if/else` เพื่อเลือก concrete strategy ที่ตรงกับ `discountType` แล้วเรียก `discountStrategy.calculate(price)`
4. ผลลัพธ์ที่ได้คือราคาสุทธิหลังหักส่วนลด — โดย `Game` ไม่จำเป็นต้องรู้สูตรคำนวณของส่วนลดแต่ละแบบเลย

**ประโยชน์ด้าน Open/Closed Principle (OCP):**
- หากต้องการเพิ่มส่วนลดใหม่ เช่น "ส่วนลดสมาชิก VIP 30%" สามารถสร้างคลาส `VipDiscountStrategy implements DiscountStrategy` ใหม่ได้ทันที **โดยไม่ต้องแก้ไขโค้ดเดิม** ของ `NoDiscountStrategy`, `StudentDiscountStrategy`, `SeasonalSaleStrategy`, หรือ `Game.java`
- สิ่งที่ต้องแก้คือเพิ่ม branch ใน `DiscountContext` (จุดเดียวที่เป็น "จุดตัดสินใจ" ของระบบ) ซึ่งเป็นจุดรวมความเปลี่ยนแปลงที่คาดการณ์ได้ ต่างจากการเขียน `if/else` กระจายอยู่หลายที่ในโค้ด
- ทำให้แต่ละอัลกอริทึมส่วนลด "แยกส่วน" (encapsulate) ออกจากกันอย่างสมบูรณ์ — การแก้สูตรคำนวณของ `StudentDiscountStrategy` ไม่มีผลกระทบต่อ `SeasonalSaleStrategy` หรือคลาสอื่นเลย
- ช่วยให้ทดสอบ (unit test) แต่ละ strategy ได้อย่างอิสระจากกัน เพราะแต่ละคลาสมีความรับผิดชอบเดียว (สอดคล้องกับ SRP ด้วย)

---

### 1.4 Layered Architecture — ทำไมต้องแยก Service Layer ออกจาก Controller และ Repository

ถ้าไม่มี Service Layer, `GameController` จะต้องเรียก `GameRepository` ตรงๆ ทำให้ Controller ต้องรู้ทั้งเรื่อง HTTP และเรื่อง data access ไปพร้อมกัน ซึ่งขัดกับ Single Responsibility และทำให้ **coupling สูง**

การมี `GameService` เป็นชั้นกลางให้ประโยชน์ดังนี้:

**Low Coupling**
`GameController` ผูกกับ `GameService` เพียงตัวเดียว (`GameController.java:18-19`) ไม่ได้ผูกกับ `GameRepository` หรือ JPA โดยตรง หากในอนาคตต้องเปลี่ยนวิธีเข้าถึงข้อมูล (เช่น เปลี่ยนจาก JPA เป็นเรียก external API) จะกระทบแค่ `GameService`/`GameRepository` เท่านั้น ไม่จำเป็นต้องแก้ `GameController` เลย

**High Cohesion**
`GameService` รวมตรรกะทางธุรกิจที่เกี่ยวกับ "เกม" ไว้ในที่เดียว (`getAll`, `saveGame`, `getById`, `deleteByid` — `GameService.java:15-29`) ทำให้ตรรกะทางธุรกิจไม่กระจัดกระจายอยู่ใน Controller หลายเมธอด หากมี business rule เพิ่มขึ้นในอนาคต (เช่น "ห้ามลบเกมที่มีส่วนลดกำลัง active") จะเพิ่ม logic ที่ `GameService` จุดเดียว ไม่ต้องไล่แก้ทุก endpoint ใน Controller

**แยกความสนใจ (Separation of Concerns)**
- Controller สนใจ "การสื่อสารกับ browser" (HTTP request/response, routing, view)
- Service สนใจ "กฎทางธุรกิจ" (business rules)
- Repository สนใจ "การเก็บ/ดึงข้อมูล" (persistence)

ทำให้ทดสอบแต่ละเลเยอร์แยกกันได้ง่าย (เช่น mock `GameRepository` เพื่อเทส `GameService` โดยไม่ต้องมี HTTP หรือฐานข้อมูลจริง) และเปลี่ยนแปลงแต่ละเลเยอร์ได้อย่างเป็นอิสระจากกันมากขึ้น

---

### 1.5 Execution Flow — ลำดับการทำงานเมื่อมี HTTP Request เข้ามา

ตัวอย่าง flow ของการ "เพิ่มเกมใหม่" (`POST /games/save`) และ "ดูรายการเกม" (`GET /games`):

**ตัวอย่าง A: เพิ่มเกมใหม่**
1. ผู้ใช้กรอกฟอร์มจากหน้า `games/add` (Thymeleaf view) แล้วกด submit → Browser ส่ง `POST /games/save` พร้อมข้อมูลฟอร์ม
2. Spring MVC route request มาที่ `GameController.save()` (`GameController.java:34-38`) โดย `@ModelAttribute Game game` จะ bind ข้อมูลจากฟอร์ม (title, genre, platform, rating, releaseDate, price, discountType) เข้าเป็น object `Game` อัตโนมัติ
3. `GameController` เรียก `gameService.saveGame(game)` — ส่งต่อ object ไปยัง Service Layer โดยไม่รู้รายละเอียดว่าจะบันทึกอย่างไร
4. `GameService.saveGame()` (`GameService.java:19-21`) เรียก `gameRepository.save(game)` ต่อไปยัง Repository Layer
5. `GameRepository` (extends `JpaRepository`) ผ่าน Spring Data JPA/Hibernate จะแปลง object `Game` เป็นคำสั่ง SQL (`INSERT INTO games ...`) แล้วส่งไปบันทึกที่ PostgreSQL จริง
6. เมื่อบันทึกสำเร็จ, control กลับขึ้นไปตามลำดับ (Repository → Service → Controller)
7. `GameController.save()` คืนค่า `"redirect:/games"` → บอกให้ browser ทำ redirect ไปที่ `GET /games`

**ตัวอย่าง B: ดูรายการเกม (พร้อมคำนวณส่วนลด)**
1. Browser ส่ง `GET /games`
2. `GameController.list()` (`GameController.java:21-26`) ถูกเรียก
3. เรียก `gameService.getAll()` → `GameService.getAll()` (`GameService.java:15-17`) เรียก `gameRepository.findAll()`
4. Repository ยิง SQL (`SELECT * FROM games`) ไปที่ PostgreSQL แล้วแปลงผลลัพธ์กลับเป็น `List<Game>` (ผ่าน Hibernate ORM)
5. `List<Game>` ถูกส่งกลับขึ้นมาที่ Controller แล้วใส่ใน `Model` ด้วย `model.addAttribute("games", ...)`
6. Controller คืนชื่อ view `"games/list"` → Thymeleaf template `games/list.html` จะถูก render
7. **ระหว่าง render** ทุกครั้งที่ template เรียกใช้ `game.finalPrice` หรือ `game.discountName` (Thymeleaf จะเรียก getter ของ object โดยอัตโนมัติ) จะไปทริกเกอร์ `Game.getFinalPrice()` (`Game.java:52-56`) ซึ่งจะสร้าง `DiscountContext` ใหม่แล้วเรียก `matchingDiscount(price, discountType)` → `DiscountContext` เลือก concrete `DiscountStrategy` ที่ตรงกับ `discountType` ของเกมนั้นๆ แล้วคำนวณราคาสุทธิ (Strategy Pattern ทำงานที่จุดนี้)
8. ผลลัพธ์ HTML ที่ render สมบูรณ์แล้วถูกส่งกลับไปแสดงที่ browser

สรุป flow แบบภาพรวม:

```
Browser (HTTP Request)
   │
   ▼
GameController        ← @GetMapping/@PostMapping, model binding, เลือก view
   │  (เรียกผ่าน interface ที่ Spring inject มา)
   ▼
GameService            ← business logic, ประสานงานระหว่าง Controller/Repository
   │
   ▼
GameRepository (JpaRepository) ← แปลง object เป็น SQL ผ่าน Hibernate
   │
   ▼
PostgreSQL Database    ← บันทึก/ดึงข้อมูลจริง
   │
   ▼ (ข้อมูลไหลกลับขึ้นมาตามเส้นทางเดิม)
Game entity → getFinalPrice() → DiscountContext → DiscountStrategy (เลือกตาม discountType)
   │
   ▼
Thymeleaf View (games/list.html ฯลฯ) → HTML Response → Browser
```

---

## ส่วนที่ 2: Code Implementation & Explanation

### โครงสร้างโค้ดปัจจุบัน

```
src/main/java/cp/com/lab076733800656sec2/
├── model/
│   └── Game.java                    # Entity (JPA)
├── repository/
│   └── GameRepository.java          # Data Access (JpaRepository)
├── service/
│   └── GameService.java             # Business Logic
├── controller/
│   └── GameController.java          # HTTP Layer
└── strategy/
    ├── DiscountStrategy.java        # Strategy interface
    ├── DiscountContext.java         # Context (เลือก strategy)
    ├── NoDiscountStrategy.java      # Concrete strategy: ไม่มีส่วนลด
    ├── StudentDiscountStrategy.java # Concrete strategy: ลด 10%
    └── SeasonalSaleStrategy.java    # Concrete strategy: ลด 20%
```

### Entity — `model/Game.java`
```java
@Entity
@Table(name = "games")
public class Game {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String genre;
    private String platform;
    private double rating;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private double price;
    private String discountType;

    public String getDiscountName() { ... }   // แปลง discountType เป็นข้อความภาษาไทย
    public double getFinalPrice() {            // Information Expert: มอบหมายให้ DiscountContext คำนวณ
        DiscountContext discountContext = new DiscountContext();
        return discountContext.matchingDiscount(price, discountType);
    }
}
```
ใช้ Lombok (`@Getter`, `@Setter`, `@NoArgsConstructor`) เพื่อลด boilerplate getter/setter — ตัว entity เก็บเฉพาะ field ที่แม็ปกับตาราง `games` และมี derived method (`getFinalPrice`, `getDiscountName`) ที่ Thymeleaf เรียกใช้ได้เหมือน property ปกติ (`game.finalPrice`, `game.discountName`)

### Repository — `repository/GameRepository.java`
```java
@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {}
```
ไม่มีเมธอดเพิ่มเติมเพราะ CRUD พื้นฐาน (`save`, `findAll`, `findById`, `deleteById`) เพียงพอต่อความต้องการของระบบแล้ว — Spring Data JPA จะสร้าง implementation ให้อัตโนมัติตอน runtime (proxy-based) โดยที่โค้ดในโปรเจกต์ไม่ต้องเขียน implementation class เอง

### Strategy Package — `strategy/*`
```java
public interface DiscountStrategy {
    double calculate(double price);
}

public class StudentDiscountStrategy implements DiscountStrategy {
    @Override
    public double calculate(double price) { return price - price * 0.1; }
}

public class DiscountContext {
    public double matchingDiscount(double price, String discountType) {
        DiscountStrategy discountStrategy;
        if (discountType.equals("STUDENT")) discountStrategy = new StudentDiscountStrategy();
        else if (discountType.equals("SEASONAL")) discountStrategy = new SeasonalSaleStrategy();
        else discountStrategy = new NoDiscountStrategy();
        return discountStrategy.calculate(price);
    }
}
```
`DiscountContext` ทำหน้าที่เป็นจุดตัดสินใจเดียวว่าจะใช้ strategy ไหน แล้วมอบหมายการคำนวณจริงให้ concrete strategy แต่ละตัว

### Service — `service/GameService.java`
```java
@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    public List<Game> getAll() { return gameRepository.findAll(); }
    public void saveGame(Game game) { gameRepository.save(game); }
    public Game getById(int id) { return gameRepository.findById(id).get(); }
    public void deleteByid(int id) { gameRepository.deleteById(id); }
}
```

### Controller — `controller/GameController.java`
```java
@Controller
@RequestMapping("/games")
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("games", gameService.getAll());
        return "games/list";
    }
    // add / save / delete / edit / update ...
}
```

### การใช้ Dependency Injection ในแต่ละ Layer

โค้ดปัจจุบันใช้ **Field Injection** ผ่าน `@Autowired` วางไว้บน field โดยตรง:

- `GameController.java:18-19` → `@Autowired private GameService gameService;`
- `GameService.java:12-13` → `@Autowired private GameRepository gameRepository;`

**หลักการทำงาน:** Spring IoC Container จะสแกนหาคลาสที่มี annotation `@Controller`, `@Service`, `@Repository` แล้วสร้าง bean ของแต่ละคลาสไว้ใน Application Context จากนั้นเมื่อเจอ field ที่มี `@Autowired`, Spring จะ inject bean ที่ตรง type ให้โดยอัตโนมัติ (reflection-based) — โปรแกรมเมอร์ไม่ต้องเขียน `new GameService()` หรือ `new GameRepository()` เอง

**ข้อแตกต่างจาก Constructor Injection (แนวทางที่มักแนะนำมากกว่า):**

```java
// Field Injection (ที่ใช้อยู่จริงในโปรเจกต์)
@Autowired
private GameService gameService;

// Constructor Injection (แนวทางที่ Spring แนะนำ แต่โปรเจกต์นี้ไม่ได้ใช้)
private final GameService gameService;

public GameController(GameService gameService) {
    this.gameService = gameService;
}
```

ข้อดีของ Constructor Injection ที่มักถูกพูดถึง:
- ทำให้ dependency เป็น `final` ได้ → immutable และปลอดภัยจาก null reference มากขึ้น
- เขียน unit test ได้ง่ายกว่า (สร้าง object ผ่าน constructor พร้อม mock ได้ตรงๆ โดยไม่ต้องพึ่ง Spring Context หรือ reflection)
- เห็น dependency ทั้งหมดของคลาสได้ชัดเจนจาก signature ของ constructor

ทุกเลเยอร์ในระบบ (Controller → Service → Repository) ใช้แนวคิดเดียวกันคือ **ไม่สร้าง object ของเลเยอร์ล่างเอง แต่ให้ Spring Container เป็นผู้ประกอบ (compose) ความสัมพันธ์ระหว่าง bean ให้** ซึ่งเป็นแก่นของ Dependency Injection และเป็นสิ่งที่ทำให้ Low Coupling / Dependency Inversion Principle เกิดขึ้นได้จริงในทางปฏิบัติ — ไม่ว่าจะใช้ Field Injection หรือ Constructor Injection ก็ตาม
