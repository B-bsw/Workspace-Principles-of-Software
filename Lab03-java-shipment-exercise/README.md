# java-shipment-exercise

แบบฝึกหัด Java OOP — ระบบคำนวณค่าขนส่ง Shipment
วิชา Object-Oriented Programming | ปีการศึกษา 2569

---

##  คำอธิบายโจทย์

บริษัทขนส่งแห่งหนึ่งมีรายการ **Shipment** หลายรายการ
ให้เขียนโปรแกรม Java คำนวณค่าขนส่งตามน้ำหนักและประเภท แล้วแสดงยอดรวม

**กฎการคำนวณ:**

| ประเภท | อัตรา |
|--------|-------|
| STANDARD (มาตรฐาน) | 40 บาท / กิโลกรัม |
| EXPRESS (ด่วน) | 100 บาท / กิโลกรัม |

---

## 📁 ไฟล์ในโปรเจกต์

```
java-shipment-exercise/
├── pom.xml                              ← Maven config (JUnit 5)
├── src/
│   ├── main/java/com/example/
│   │   ├── ShipmentSection1_Exercise.java
│   │   ├── ShipmentSection2_Exercise.java
│   │   ├── ShipmentSection3_Exercise.java
│   │   └── ShipmentSection4_Exercise.java
│   └── test/java/com/example/
│       └── ShipmentTest.java            ← JUnit 5 test cases
├── ExpectedOutput_Section1.md  ...  Section4.md
├── screenshots/                         ← ใส่รูปผลลัพธ์ตรงนี้
└── README.md
```

> เฉลยไม่ได้อยู่ใน repo นี้ — อาจารย์จะแจกให้หลังส่งงาน

---

## 🔧 เครื่องมือที่ใช้ในงานนี้

| เครื่องมือ | ใช้ทำอะไร |
|-----------|----------|
| **Eclipse หรือ VS Code** | เปิดไฟล์ แก้ Bug ตาม TODO รันโปรแกรมดูผลลัพธ์ |
| **CMD / Terminal** | รัน `mvn test` เพื่อทดสอบด้วย JUnit |

---

## 🍴 วิธีเริ่มต้น (Fork & Clone)

### Step 1 — Fork repo นี้

กดปุ่ม **Fork** มุมบนขวาของหน้านี้

> Fork แล้ว repo จะไปอยู่ใน GitHub ของตัวเอง

**ตั้งชื่อ repo ให้ถูกต้อง:**
- รูปแบบ: `lab03_67XXXXX` (แทน XXXXX ด้วยรหัสนักศึกษาแบบไม่มีหรือมีก็ได้)
- ตัวอย่าง: `lab03_6712345`

> ถ้าลืมตั้งชื่อตอน Fork → ไปแก้ที่ **Settings → Repository name → Rename**

link repo ที่ส่งให้อาจารย์จะเป็น:
`https://github.com/your-username/lab03_67XXXXX`

---

### Step 2 — Clone ลงเครื่อง

```bash
git clone https://github.com/your-username/java-shipment-exercise.git
```

---

### Step 3 — เปิดโปรเจกต์ใน Eclipse หรือ VS Code

---

#### 🟦 ใช้ VS Code

1. ติดตั้ง Extension: **Extension Pack for Java** (Microsoft) — ถ้ายังไม่มี
2. **File → Open Folder** → เลือกโฟลเดอร์ที่ Clone มา
3. VS Code อ่าน `pom.xml` แล้วโหลด JUnit 5 ให้อัตโนมัติ
4. เปิด Terminal ใน VS Code (**Terminal → New Terminal**)
5. รัน test ได้เลย:

```bash
mvn -Dtest=ShipmentTest_SectionX test
```

---

#### 🟧 ใช้ Eclipse

> ⚠️ ห้าม **File → New → Java Project** — ต้อง **Import** เท่านั้น

1. เปิด Eclipse → **File → Import**
2. เลือก **Maven → Existing Maven Projects** → กด **Next**
3. กด **Browse** → เลือกโฟลเดอร์ที่ Clone มา
4. กด **Finish**
5. Eclipse อ่าน `pom.xml` แล้วโหลด JUnit 5 ให้อัตโนมัติ
6. เปิด CMD แล้วรัน test:

```bash
cd path/ที่เก็บโปรเจกต์
mvn -Dtest=ShipmentTest_SectionX test
```

> ต้องมี Java 17+ ในเครื่องก่อน ดาวน์โหลดได้ที่ https://adoptium.net

---


### Step 3.5 — ⚠️ ลบไฟล์ Section อื่นออก

หลัง Fork แล้วให้ลบไฟล์ของ Section อื่นออกทั้งหมด ทั้ง Exercise และ Test file

**ตัวอย่าง: ถ้าได้รับมอบหมาย Section 2**

```bash
# ลบ Exercise file
git rm src/main/java/com/example/ShipmentSection1_Exercise.java
git rm src/main/java/com/example/ShipmentSection3_Exercise.java
git rm src/main/java/com/example/ShipmentSection4_Exercise.java

# ลบ Test file
git rm src/test/java/com/example/ShipmentTest_Section1.java
git rm src/test/java/com/example/ShipmentTest_Section3.java
git rm src/test/java/com/example/ShipmentTest_Section4.java

git commit -m "remove: ลบไฟล์ Section อื่นออก เหลือแค่ Section ของตัวเอง"
git push origin main
```

**โครงสร้างที่ถูกต้องหลังลบ (ตัวอย่าง Section 2):**
```
src/
├── main/java/com/example/
│   └── ShipmentSection2_Exercise.java   ← เหลือแค่ของตัวเอง
└── test/java/com/example/
    └── ShipmentTest_Section2.java        ← เหลือแค่ของตัวเอง
```

---

### Step 4 — แก้ Bug ใน Eclipse

เปิดไฟล์ Exercise ของ Section ตัวเองใน Eclipse แล้วแก้ตามลำดับ TODO ในไฟล์

| Section | บริษัท | Bug หลักที่ซ่อนไว้ |
|:-------:|--------|-------------------|
| 1 | SpeedEx Logistics | enum ขาด, parameter สลับ, อัตราสลับ |
| 2 | FlashMove Express | enum ขาด, assignment สลับ, อัตราผิด |
| 3 | RocketShip Thailand | if-condition สลับ, index ผิด |
| 4 | SwiftCargo Co., Ltd. | return ค่าผิด, loop condition ผิด |

---

### Step 5 — รันโปรแกรมดูผลลัพธ์ (Eclipse หรือ VS Code)

**ใช้ Eclipse:**
1. คลิกขวาที่ไฟล์ `ShipmentSectionX_Exercise.java`
2. เลือก **Run As → Java Application**
3. ดูผลลัพธ์ใน **Console** ด้านล่าง

**ใช้ VS Code:**
1. เปิดไฟล์ `ShipmentSectionX_Exercise.java`
2. กดปุ่ม **▷ Run** มุมบนขวา หรือคลิกขวา → **Run Java**
3. ดูผลลัพธ์ใน **Terminal** ด้านล่าง

เปรียบเทียบกับ `ExpectedOutput_SectionX.md` ว่าตรงกันมั้ย

---

### Step 6 — รัน mvn test ใน CMD

เมื่อผลลัพธ์โปรแกรมตรงแล้ว ให้เปิด CMD แล้วเข้าไปที่โฟลเดอร์โปรเจกต์:

```bash
cd path/ที่เก็บโปรเจกต์
# ตัวอย่าง Windows: cd C:\Users\YourName\java-shipment-exercise
```

รัน test ของ Section ตัวเอง:

```bash
# Section 1
mvn -Dtest=ShipmentTest#sec1* test

# Section 2
mvn -Dtest=ShipmentTest#sec2* test

# Section 3
mvn -Dtest=ShipmentTest#sec3* test

# Section 4
mvn -Dtest=ShipmentTest#sec4* test
```

ผลลัพธ์ที่ต้องการ:

```
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

> ⚠️ ถ้าขึ้น `BUILD FAILURE` — กลับไปแก้ Bug ใน Eclipse ต่อ

---

### Step 7 — ถ่ายภาพและแนบใน screenshots/

สร้างโฟลเดอร์ `screenshots/` แล้วแนบรูป **2 ภาพ**:

```bash
mkdir screenshots
```

| ไฟล์รูป | ต้องเห็นอะไรในภาพ |
|---------|-----------------|
| `screenshots/mvn_test_result.png` | ผลลัพธ์ `mvn test` — ต้องเห็น `BUILD SUCCESS` และ `Failures: 0` |
| `screenshots/program_output.png` | ผลลัพธ์ใน Eclipse Console — ต้องเห็นรายการและยอดรวมครบ |

```bash
git add screenshots/
git commit -m "screenshot: แนบผลลัพธ์ mvn test และ program output"
git push origin main
```

---

### Step 8 — Push และส่งงาน

```bash
git add src/main/java/com/example/ShipmentSectionX_Exercise.java
git commit -m "fix: แก้ไข TODO Section X เสร็จสมบูรณ์"
git push origin main
```

**ส่ง link repo ให้อาจารย์:**
`https://github.com/your-username/java-shipment-exercise`

---

## ❗ Error ที่พบบ่อย

| Error | สาเหตุ | ดู TODO |
|-------|--------|--------|
| `error: cannot find symbol` | enum ไม่ครบ | A (Sec 1, 2) |
| `NullPointerException` | ลืม `new ArrayList<>()` | E (Sec 1, 2) |
| ทุกรายการได้ `0.00` | `return 0` แทน `return cost` | A (Sec 4) |
| ตัวเลขสลับ STANDARD/EXPRESS | if-condition ผิด | A (Sec 3) |
| ยอดรวมผิด (น้อยกว่าที่ควร) | loop วนไม่ครบ | C (Sec 3, 4) |
| ไม่มีบรรทัดรายการใดแสดง | `printSummary()` ไม่มี loop | G/D |
| `BUILD FAILURE` ใน mvn | มี test ไม่ผ่าน — อ่าน expected/actual | แก้ Bug ต่อ |

---

## ✅ Checklist ก่อนส่ง

- [x] Fork และ Clone repo แล้ว
- [x] Import โปรเจกต์ใน Eclipse หรือ VS Code แล้ว
- [x] ลบไฟล์ Section อื่นออกแล้ว
- [x] แก้ TODO ครบทุกจุดใน Eclipse
- [x] รันใน Eclipse หรือ VS Code — ผลลัพธ์ตรงกับ ExpectedOutput
- [x] รัน `mvn test` ใน CMD — ขึ้น `BUILD SUCCESS`
- [x] `Failures: 0, Errors: 0`
- [x] แนบรูป `screenshots/mvn_test_result.png` แล้ว
- [x] แนบรูป `screenshots/program_output.png` แล้ว
- [x] `git push` ขึ้น GitHub แล้ว
- [x] ส่ง link repo ให้อาจารย์แล้ว
