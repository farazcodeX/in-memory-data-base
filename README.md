
# in_Memory_database

```markdown
# 🧠 In-Memory Database (Java)

A **high-performance, modular** in-memory key-value database built in pure Java — perfect for fast prototyping, caching, and testing workflows without the overhead of a persistent database.

## ⚙️ Features

- 🗃️ **In-Memory Storage** — Fast, lightweight key-value store.
- 🔁 **Custom Cleanup Strategy** — Efficient memory management for stale or outdated data.
- 🧩 **Modular, Extensible Design** — Easy to maintain and extend.
- 🧪 **Test-Ready** — Built with testability in mind, ideal for unit/integration testing.
- 📦 **No Dependencies** — Pure Java, zero external libraries.

---

## 🚀 Quick Start

### 1. Clone the Repo

```bash
git clone https://github.com/farazcodeX/in-memory-data-base.git
cd in-memory-data-base
```

### 2. Compile & Run

#### Using Maven
```bash
mvn clean compile exec:java -Dexec.mainClass="com.example.Main"
```

#### Or plain Java
```bash
javac -d out src/**/*.java
java -cp out com.example.Main
```

---

## 🧑‍💻 Usage Example

```java
InMemoryDatabase db = new InMemoryDatabase();

// Create
db.insert("user:1", new User("Alice", "alice@example.com"));

// Read
User user = db.get("user:1");

// Update
db.update("user:1", new User("Alice Smith", "alice.smith@example.com"));

// Delete
db.delete("user:1");

// Cleanup unused entries
db.cleanup();
```

✔️ Supports generic values — plug in any object you need.

---

## 📁 Project Structure

```
in-memory-data-base/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── example/
│                   ├── InMemoryDatabase.java
│                   ├── User.java
│                   └── Main.java
├── pom.xml
└── README.md
```

---

## 🧠 Design Philosophy

- **KISS**: Keep It Simple, Stupid — clear logic, minimal boilerplate.
- **Single Responsibility**: Each class does one thing, and does it well.
- **OOP Principles**: Encapsulation, composition, and interface-driven design.
- **Open for Extension**: Want to add TTL support? Swap in a persistence layer? Easy.

---

## 🧪 Testing

Coming soon: unit tests with JUnit 5.  
📌 Want to contribute tests? PRs are welcome!

---

## 🛠️ Contributing

Wanna make this sharper?

1. Fork the repo
2. Create a branch (`feat/my-awesome-thing`)
3. Submit a PR

---

## 📜 License

MIT — use it, fork it, profit from it.

---

## 💬 Feedback

Got ideas? Found a bug?  
[Open an issue](https://github.com/farazcodeX/in-memory-data-base/issues) or drop a ⭐ if you dig it.

---

Made with ☕ by [Faraz](https://github.com/farazcodeX)
```

---

Let me know if you want badges, GitHub Actions CI/CD section, or even diagrams added — we can make it *ultra* god-tier 😎
